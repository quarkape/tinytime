package club.hue.service.impl;

import club.hue.mapper.InfoManagerMapper;
import club.hue.pojo.SimplePost;
import club.hue.pojo.TimePercent;
import club.hue.pojo.TypePercent;
import club.hue.service.InfoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoManagerServiceImpl implements InfoManagerService {

    @Autowired
    private InfoManagerMapper infoManagerMapper;

    public void setInfoManagerMapper(InfoManagerMapper infoManagerMapper) {
        this.infoManagerMapper = infoManagerMapper;
    }

    @Override
    public int addNotify(String id, String notifyid, String title, String detail, String date) {
        return infoManagerMapper.addNotify(id, notifyid, title, detail, date);
    }

    @Override
    public int addFeedback(String clientid, String detail, String date) {
        return infoManagerMapper.addFeedback(clientid, detail, date);
    }

    @Override
    public int changeName(String newname, String id) {
        return infoManagerMapper.changeName(newname, id);
    }

    @Override
    public int deletePostSelf(String postid) {
        return infoManagerMapper.deletePostSelf(postid);
    }

    @Override
    public int deletePostContent(String postid) {
        return infoManagerMapper.deletePostContent(postid);
    }

    @Override
    public int deletePostInfomation(String postid) {
        return infoManagerMapper.deletePostInfomation(postid);
    }

    @Override
    public int deleteMessage(String postid) {
        return infoManagerMapper.deleteMessage(postid);
    }

    @Override
    public SimplePost getImgUrl(String postid) {
        return infoManagerMapper.getImgUrl(postid);
    }

    @Override
    public int changeAvatarUrl(String avatarurl, String id) {
        return infoManagerMapper.changeAvatarUrl(avatarurl, id);
    }

    @Override
    public int getSentenceCount(int type, String date) {
        return infoManagerMapper.getSentenceCount(type, date);
    }

    @Override
    public Integer getVoteState(int type, String userid, String date) {
        return infoManagerMapper.getVoteState(type, userid, date);
    }

    @Override
    public int addVote(int type, String date, String userid) {
        return infoManagerMapper.addVote(type, date, userid);
    }

    @Override
    public int changeVoteState(int type, int state, String userid, String date) {
        return infoManagerMapper.changeVoteState(type, state, userid, date);
    }

    @Override
    public int addLoginRecord(String id, String date, String time) {
        return infoManagerMapper.addLoginRecord(id, date, time);
    }

    @Override
    public Map<String, Integer> getTimePercent(String userid) {
        return infoManagerMapper.getTimePercent(userid);
    }

    @Override
    public Map<String, Integer> getPreferencePercent(String clientid) {
        return infoManagerMapper.getPreferencePercent(clientid);
    }

    @Override
    public Map<String, Integer> getBehaviorPercent(String clientid) {
        return infoManagerMapper.getBehaviorPercent(clientid);
    }

    @Override
    public HashMap<String, Object> checkValidationDeletePost(String postid, String authorid) {
        return infoManagerMapper.checkValidationDeletePost(postid, authorid);
    }

    @Override
    public int deleteComments(String postid) {
        return infoManagerMapper.deleteComments(postid);
    }
}
