package club.hue.service;


import club.hue.pojo.SimplePost;
import club.hue.pojo.TimePercent;
import club.hue.pojo.TypePercent;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

public interface InfoManagerService {

    int addNotify(String id, String notifyid, String title, String detail, String date);

    int addFeedback(String clientid, String detail, String date);

    int changeName(String newname, String id);

    int deletePostSelf(String postid);

    int deletePostContent(String postid);

    int deletePostInfomation(String postid);

    int deleteMessage(String postid);

    SimplePost getImgUrl(String postid);

    int changeAvatarUrl(String avatarurl, String id);

    int getSentenceCount(int type, String date);

    Integer getVoteState(int type, String userid, String date);

    int addVote(int type, String date, String userid);

    int changeVoteState(int type, int state, String userid, String date);

    int addLoginRecord(String id, String date, String time);

    Map<String, Integer> getTimePercent(String userid);

    Map<String, Integer> getPreferencePercent(String clientid);

    Map<String, Integer> getBehaviorPercent(String clientid);

    HashMap<String, Object> checkValidationDeletePost(String postid, String authorid);

    int deleteComments(String postid);

}
