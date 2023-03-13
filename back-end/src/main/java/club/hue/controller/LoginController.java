package club.hue.controller;

import club.hue.pojo.User;
import club.hue.service.InfoManagerService;
import club.hue.service.LoginService;
import club.hue.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private InfoManagerService infoManagerService;

//    private String basePath = "/www/apps/lightme/material/";
    private String basePath = "G:\\Projects\\Materials\\lightme\\";

    @RequestMapping("/checkLogin")
    public ResponseEntity checkLogin(String name, String password, HttpSession session) {
        try {
            User user = loginService.getUserByName(name, new Md5Util().getMd5Code(password));
            if (user != null) {
                // add a login record
                String currentDate = new DateTransition().TimeStamp2StringSimple();
                Calendar calendar = Calendar.getInstance();
                int curHour24 = calendar.get(calendar.HOUR_OF_DAY);
                String currentTime = "0";
                if (curHour24 / 6 == 0) {
                    // 如果小于6小时
                    currentTime = "0";
                } else if (curHour24 / 12 == 0 && curHour24 / 6 == 1) {
                    // 如果时间段介于6-12之间
                    currentTime = "1";
                } else if (curHour24 / 12 == 1 && curHour24 / 24 == 0) {
                    // 如果时间段介于12-24之间
                    currentTime = "2";
                } else {
                    // 如果时间段大于24
                    currentTime = "3";
                }
                infoManagerService.addLoginRecord(user.getId(), currentDate, currentTime);
                session.setAttribute("userid", user.getId());
                HashMap<String, Object> map = new HashMap<>();
                map.put("SESSIONID", session.getId());
                map.put("name", user.getName());
                return ResponseEntity.ok().body(ResultVOUtil.success(map));
            } else {
                return ResponseEntity.ok().body(ResultVOUtil.error(2, "用户名或密码错误"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/getAuthCodeAtRegister")
    public ResponseEntity getAuthCodeAtRegister(String name, String email, HttpSession session) {
        try {
            if (loginService.checkUserRepeatByName(name) != null) {
                return ResponseEntity.ok().body(ResultVOUtil.error(2, "用户名已经注册过"));
            }
            if (loginService.checkUserRepeatByEmail(email) != null) {
                return ResponseEntity.ok().body(ResultVOUtil.error(3, "邮箱已经注册过"));
            }
            String authcode = new RandomUtil().getCode();
            SendMailUtil.sendEmail(email, authcode);
            session.setAttribute("authcode", authcode);
            session.setAttribute("beforeRegisterSessionId", session.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("SESSIONID", session.getId());
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
//            System.out.println(e);
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/register")
    public ResponseEntity register(String name, String password, String email, String authcode, HttpSession session) {
        if (!session.getId().equals(session.getAttribute("beforeRegisterSessionId"))) {
            return ResponseEntity.ok().body(ResultVOUtil.error(3, "会话已过期，请重新获取验证码并及时填写"));
        }
        if (!authcode.equals(session.getAttribute("authcode"))) {
            return ResponseEntity.ok().body(ResultVOUtil.error(4, "验证码错误，请检查后重试"));
        }
        String id = System.currentTimeMillis() + "";
        // 拼接文件名，用当前时间戳加上文件后缀
        try {
            String md5Password = new Md5Util().getMd5Code(password);
            loginService.addUser(new User(id, name, md5Password, email, "NONE", "/wxbg/defaultavatar.png"));
            session.removeAttribute("authcode");
            session.removeAttribute("beforeRegisterSessionId");
            String detail = "欢迎注册使用话题起源说。您可以随时发布话题和参与到话题讨论中，可以读到正能量和负能量句子并制作表情包，平衡忙碌的一天。希望你爱上思考与交流。";
            String notifyid = id + id;
            String title = "注册成功通知";
            infoManagerService.addNotify(id, notifyid, title, detail, new DateTransition().TimeStamp2StringSimple());
            File file = new File(basePath + "users/" + id + "/", "mkdir.log");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，稍后再试吧"));
        }
    }

    @RequestMapping("/getAuthcodeAtFindBackPassword")
    public ResponseEntity getAuthcodeAtFindBackPassword(String email, HttpSession session) {
        try {
            if (loginService.getUserByEmailSimple(email) == null) {
                return ResponseEntity.ok().body(ResultVOUtil.error(2, "当前邮箱尚未注册"));
            }
            String authcode = new RandomUtil().getCode();
            SendMailUtil.sendEmail(email, authcode);
            session.setAttribute("authcode", authcode);
            session.setAttribute("beforeRegisterSessionId", session.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("SESSIONID", session.getId());
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，稍后再试吧"));
        }
    }

    @RequestMapping("/findBackPassword")
    public ResponseEntity findBackPassword(String password, String email, String authcode, HttpSession session) {
        if (!session.getId().equals(session.getAttribute("beforeRegisterSessionId"))) {
            return ResponseEntity.ok().body(ResultVOUtil.error(2, session.getId()));
        }
        if (!authcode.equals(session.getAttribute("authcode"))) {
            return ResponseEntity.ok().body(ResultVOUtil.error(3, "验证码错误，请检查后重试"));
        }
        try {
            String md5Password = new Md5Util().getMd5Code(password);
            Long dateMills = System.currentTimeMillis();
            loginService.modifyPassword(md5Password, email);
            session.removeAttribute("authcode");
            session.removeAttribute("beforeRegisterSessionId");
            String detail = "您刚刚通过邮箱修改了密码。如果不是您本人操作，很可能你的账户被他人使用。您可以再次通过邮箱修改密码，请注意不要外泄邮箱邮件中的验证码。";
            String userid = loginService.getUserByEmailSimple(email).getId();
            String notifyid = userid + (dateMills + "");
            String title = "修改密码通知";
            infoManagerService.addNotify(userid, notifyid, title, detail, new DateTransition().DateMills2String(dateMills));
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，稍后再试吧"));
        }
    }

    // 控制小程序部分功能是否展示
    @RequestMapping("/getDataNormal")
    public ResponseEntity getDataNormal() {
        return ResponseEntity.ok().body(loginService.getDataNormal());
    }

    // 获取表情包模板个数
    @RequestMapping("/getPicCount")
    public ResponseEntity getPicCount() {
        Integer[] res = loginService.getPicCount();
        return ResponseEntity.ok().body(ResultVOUtil.success(res));
    }
}
