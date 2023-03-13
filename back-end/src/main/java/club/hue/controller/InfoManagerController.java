package club.hue.controller;

import club.hue.pojo.SimplePost;
import club.hue.pojo.TimePercent;
import club.hue.pojo.User;
import club.hue.service.InfoManagerService;
import club.hue.service.LoginService;
import club.hue.utils.COSUploadUtil;
import club.hue.utils.CheckFolder;
import club.hue.utils.DateTransition;
import club.hue.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoManagerController {

    @Autowired
    private InfoManagerService infoManagerService;

    @Autowired
    private LoginService loginService;

//    private String basePath = "/www/apps/lightme/material";
    private String basePath = "G:\\Projects\\Materials\\lightme\\";

    @RequestMapping("/addFeedback")
    public ResponseEntity addFeedback(String detail, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            infoManagerService.addFeedback(clientid, detail, new DateTransition().TimeStamp2StringSimple());
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差啦,稍后再试吧"));
        }
    }

    @RequestMapping("changeName")
    public ResponseEntity changeName(String newname, HttpSession session) {
        String id = (String) session.getAttribute("userid");
        try {
            infoManagerService.changeName(newname, id);
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差啦,稍后再试吧"));
        }
    }

    @RequestMapping("changeAvatar")
    public ResponseEntity changeAvatar(MultipartFile newavatar, HttpSession session) {
        if (newavatar == null) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
        String userid = (String) session.getAttribute("userid");
        User user = loginService.getUserById(userid);
        // 拼接文件名，用当前时间戳加上文件后缀
        String[] fileNames = newavatar.getOriginalFilename().split("\\.");
        String fileName = "avatar" + "." + fileNames[fileNames.length - 1];
//        String baseUrl = "https://test-1300070782.cos.ap-chengdu.myqcloud.com/tinytime/";
        File tempFile = null;
        if (user.getAvatarurl().indexOf("/wxbg/") != -1) {
            // 当前用户头像为默认头像
            tempFile = new File(basePath + "/users/" + userid + "/", fileName);
        } else {
            tempFile = new File(basePath + user.getAvatarurl());
        }
        try {
            newavatar.transferTo(tempFile);
            infoManagerService.changeAvatarUrl("/users/" + userid + "/" + fileName, userid);
            return ResponseEntity.ok().body(ResultVOUtil.success("/users/" + userid + "/" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(2, "对象存储过程中出错了，稍后再试吧"));
        }
    }

    @RequestMapping("refreshSession")
    public ResponseEntity refreshSession() {
        return ResponseEntity.ok().body(ResultVOUtil.successSimple());
    }

    @RequestMapping("deletePost")
    public ResponseEntity deletePost(String delpostid, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        HashMap<String, Object> res = infoManagerService.checkValidationDeletePost(delpostid, userid);
        if (res == null) {
            return ResponseEntity.ok().body(ResultVOUtil.error(2, "操作失败!"));
        }
        try {
            SimplePost imgurl = infoManagerService.getImgUrl(delpostid);
            if (imgurl.getImgtype() == 0 && imgurl.getImgurl().indexOf("/wxbg/nbg") == -1) {
                String filekey = imgurl.getImgurl().substring(20);
                File file = new File(basePath + "/users/" + userid + filekey);
                file.delete();
//                new COSUploadUtil().deleteFileFromCOS(filekey);
            }
            infoManagerService.deletePostSelf(delpostid);
            infoManagerService.deletePostContent(delpostid);
            infoManagerService.deletePostInfomation(delpostid);
            infoManagerService.deleteMessage(delpostid);
            infoManagerService.deleteComments(delpostid);

            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getVoteDetail")
    public ResponseEntity getVoteDetail(String type, String date, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        int typee = Integer.parseInt(type);
        HashMap<String, Object> map = new HashMap<>();
        try {
            int voteCount = infoManagerService.getSentenceCount(typee, date);
            Integer voteState = infoManagerService.getVoteState(typee, userid, date);
            // 如果还没有投票
            if (voteState != null) {
                map.put("voteState", voteState);
            } else {
                // 2表示用户还没有投票
                map.put("voteState", 2);
            }
            map.put("voteCount", voteCount);
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("changeVoteState")
    public ResponseEntity changeVoteState(String type, String date, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        int typee = Integer.parseInt(type);
        HashMap<String, Object> map = new HashMap<>();
        try {
            Integer currentState = infoManagerService.getVoteState(typee, userid, date);
            if (currentState == null) {
                infoManagerService.addVote(typee, date, userid);
                int voteCount = infoManagerService.getSentenceCount(typee, date);
                Integer voteState = infoManagerService.getVoteState(typee, userid, date);
                map.put("voteCount", voteCount);
                map.put("voteState", voteState);
                return ResponseEntity.ok().body(ResultVOUtil.success(map));
            }
            if (currentState == 0) {
                infoManagerService.changeVoteState(typee, 1, userid, date);
            } else {
                infoManagerService.changeVoteState(typee, 0, userid, date);
            }
            int voteCount = infoManagerService.getSentenceCount(typee, date);
            Integer voteState = infoManagerService.getVoteState(typee, userid, date);
            map.put("voteCount", voteCount);
            map.put("voteState", voteState);
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    // 获取用户在什么时间段登录
    @RequestMapping("getLoginTimePercent")
    public ResponseEntity getLoginTimeRes(HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        Map<String, Integer> map = infoManagerService.getTimePercent(userid);
        return ResponseEntity.ok().body(ResultVOUtil.success(map));
    }

    //获取用户偏好哪个板块(使用访问作为依据)
    @RequestMapping("getPreferencePercent")
    public ResponseEntity getPreferencePercent(HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        Map<String, Integer> map = infoManagerService.getPreferencePercent(clientid);
        return ResponseEntity.ok().body(ResultVOUtil.success(map));
    }

    // 获取用户用户引用和评论的百分比
    @RequestMapping("getBehaviorPercent")
    public ResponseEntity getBehaviorPercent(HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        Map<String, Integer> map = infoManagerService.getBehaviorPercent(clientid);
        return ResponseEntity.ok().body(ResultVOUtil.success(map));
    }
}
