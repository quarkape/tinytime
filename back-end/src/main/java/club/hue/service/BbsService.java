package club.hue.service;

import club.hue.pojo.CertainPost;
import club.hue.pojo.PostComment;
import club.hue.pojo.PostInfomation;
import club.hue.pojo.SimplePost;

import java.util.HashMap;
import java.util.List;

public interface BbsService {

//    List<SimplePost> getAllPosts(String lastdate);

    List<SimplePost> getHotPosts(String lastdate);

    List<SimplePost> getTypePosts(String type, String lastdate);

    List<SimplePost> getSearchPosts(String keyword, String lastdate);

    int addPost(HashMap<String, Object> map);

    int addPostContent(String postid, String content);

    CertainPost getCertainPost(String postid);

//    PostInfomation getPostInfomation(String postid, String clientid);
    PostInfomation getPostInfomation(String postid);

    List<PostComment> getPostComment(String postid);

    int addInfomation(PostInfomation postInfomation);

    int addComment(HashMap<String, Object> map);

    int changeAgreeStatus(int agree, String postid, String clientid);

    int changeCollectStatus(int collect, String date, String postid, String clientid);

    int addReport(String postid, String clientid, String detail, String date);

}
