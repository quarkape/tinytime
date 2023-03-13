package club.hue.service;

import club.hue.pojo.*;

import java.util.HashMap;
import java.util.List;

public interface ZoneService {

    List<PostNotify> getAllNotify(String id);

    int changeNotifyStatus(String notifyid);

    int addMessage(HashMap<String, Object> map);

    int changeMessageStatus(String roleb, String messageid);

    List<PostMessage> getAllMessage(String roleb);

    int getUnreadNotify(String id);

    int getUnreadMessage(String roleb);

    List<MyPost> getMyPosts(String authorid);

    List<MyComment> getMyComment(String clientid);

    List<MyCollect> getMyCollect(String clientid);

}
