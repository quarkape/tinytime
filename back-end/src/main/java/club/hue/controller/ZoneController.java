package club.hue.controller;

import club.hue.pojo.*;
import club.hue.service.LoginService;
import club.hue.service.ZoneService;
import club.hue.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class ZoneController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ZoneService zoneService;

    @RequestMapping("getBasicInfo")
    public ResponseEntity getBasicInfo(HttpSession session) {
        String id = (String) session.getAttribute("userid");
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", loginService.getUserById(id));
            map.put("unreadnotify", zoneService.getUnreadNotify(id));
            map.put("unreadmessage", zoneService.getUnreadMessage(id));
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getAllNotify")
    public ResponseEntity getAllNotify(HttpSession session) {
        String id = (String) session.getAttribute("userid");
        try {
            List<PostNotify> postNotifyList = zoneService.getAllNotify(id);
            return ResponseEntity.ok().body(ResultVOUtil.success(postNotifyList));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("changeNotifyStatus")
    public ResponseEntity changeNotifyStatus(String notifyid) {
        try {
            zoneService.changeNotifyStatus(notifyid);
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getAllMessage")
    public ResponseEntity getAllMessage(HttpSession session) {
        try {
            String roleb = (String) session.getAttribute("userid");
            List<PostMessage> postMessageList = zoneService.getAllMessage(roleb);
            return ResponseEntity.ok().body(ResultVOUtil.success(postMessageList));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("changeMessageStatus")
    public ResponseEntity changeMessageStatus(String messageid, HttpSession session) {
        String roleb = (String) session.getAttribute("userid");
        try {
            zoneService.changeMessageStatus(roleb, messageid);
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getMyPosts")
    public ResponseEntity getMyPosts(HttpSession session) {
        String authorid = (String) session.getAttribute("userid");
        try {
            List<MyPost> myPostList = zoneService.getMyPosts(authorid);
            return ResponseEntity.ok().body(ResultVOUtil.success(myPostList));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getMyComments")
    public ResponseEntity getMyComments(HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            List<MyComment> myCommentList = zoneService.getMyComment(clientid);
            return ResponseEntity.ok().body(ResultVOUtil.success(myCommentList));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("getMyCollects")
    public ResponseEntity getMyCollects(HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            List<MyCollect> myCollectList = zoneService.getMyCollect(clientid);
            return ResponseEntity.ok().body(ResultVOUtil.success(myCollectList));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }


}
