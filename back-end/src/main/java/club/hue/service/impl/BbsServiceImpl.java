package club.hue.service.impl;

import club.hue.mapper.BbsMapper;
import club.hue.pojo.CertainPost;
import club.hue.pojo.PostComment;
import club.hue.pojo.PostInfomation;
import club.hue.pojo.SimplePost;
import club.hue.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BbsServiceImpl implements BbsService {

    @Autowired
    private BbsMapper bbsMapper;

    public void setBbsMapper(BbsMapper bbsMapper) {
        this.bbsMapper = bbsMapper;
    }

//    @Override
//    public List<SimplePost> getAllPosts(String lastdate) { return bbsMapper.getAllPosts(lastdate); }

    @Override
    public List<SimplePost> getHotPosts(String lastdate) {
        return bbsMapper.getHotPosts(lastdate);
    }

    @Override
    public List<SimplePost> getTypePosts(String type, String lastdate) {
        return bbsMapper.getTypePosts(type, lastdate);
    }

    @Override
    public List<SimplePost> getSearchPosts(String keyword, String lastdate) {
        return bbsMapper.getSearchPosts(keyword, lastdate);
    }

    @Override
    public int addPost(HashMap<String, Object> map) {
        return bbsMapper.addPost(map);
    }

    @Override
    public int addPostContent(String postid, String content) {
        return bbsMapper.addPostContent(postid, content);
    }

    @Override
    public CertainPost getCertainPost(String postid) {
        return bbsMapper.getCertainPost(postid);
    }

    @Override
    public PostInfomation getPostInfomation(String postid) {
        return bbsMapper.getPostInfomation(postid);
    }

    @Override
    public List<PostComment> getPostComment(String postid) {
        return bbsMapper.getPostComment(postid);
    }

    @Override
    public int addInfomation(PostInfomation postInfomation) {
        return bbsMapper.addInfomation(postInfomation);
    }

    @Override
    public int addComment(HashMap<String, Object> map) {
        return bbsMapper.addComment(map);
    }

    @Override
    public int changeAgreeStatus(int agree, String postid, String clientid) {
        return bbsMapper.changeAgreeStatus(agree, postid, clientid);
    }

    @Override
    public int changeCollectStatus(int collect, String date, String postid, String clientid) {
        return bbsMapper.changeCollectStatus(collect, date, postid, clientid);
    }

    @Override
    public int addReport(String postid, String clientid, String detail, String date) {
        return bbsMapper.addReport(postid, clientid, detail, date);
    }

}
