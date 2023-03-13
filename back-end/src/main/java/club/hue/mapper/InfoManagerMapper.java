package club.hue.mapper;


import club.hue.pojo.SimplePost;
import club.hue.pojo.TimePercent;
import club.hue.pojo.TypePercent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Mapper
@Repository
public interface InfoManagerMapper {

    @Insert("insert into notify values(#{id}, #{notifyid}, #{title}, #{detail}, '0', #{date})")
    int addNotify(@Param("id") String id, @Param("notifyid") String notifyid, @Param("title") String title, @Param("detail") String detail, @Param("date") String date);

    @Insert("insert into feedback values(#{clientid}, #{detail}, #{date})")
    int addFeedback(@Param("clientid") String clientid, @Param("detail") String detail, @Param("date") String date);

    @Update("update users set name=#{newname} where id=#{id}")
    int changeName(@Param("newname") String newname, @Param("id") String id);

    @Delete("delete from posts where postid=#{postid}")
    int deletePostSelf(String postid);

    @Delete("delete from postscontent where postid=#{postid}")
    int deletePostContent(String postid);

    @Delete("delete from postsinfomation where postid=#{postid}")
    int deletePostInfomation(String postid);

    @Delete("delete from message where postid=#{postid}")
    int deleteMessage(String postid);

    @Select("select * from posts where postid=#{postid}")
    SimplePost getImgUrl(String postid);

    @Delete("delete from postscomment where postid=#{postid}")
    int deleteComments(String postid);

    @Update("update users set avatarurl=#{avatarurl} where id=#{id}")
    int changeAvatarUrl(@Param("avatarurl") String avatarurl, @Param("id") String id);

    @Select("select count(*) from votesentence where type=#{type} and date=#{date} and state=1")
    int getSentenceCount(@Param("type") int type, @Param("date") String date);

    @Select("select state from votesentence where type=#{type} and userid=#{userid} and date=#{date}")
    Integer getVoteState(@Param("type") int type, @Param("userid") String userid, @Param("date") String date);

    @Insert("insert into votesentence values(#{date}, #{userid}, 1, #{type})")
    int addVote(@Param("type") int type, @Param("date") String date, @Param("userid") String userid);

    @Update("update votesentence set state=#{state} where type=#{type} and userid=#{userid} and date=#{date}")
    int changeVoteState(@Param("type") int type, @Param("state") int state, @Param("userid") String userid, @Param("date") String date);


    // 增加一条登陆记录
    @Insert("insert into loginrecord values(#{id}, #{date}, #{time})")
    int addLoginRecord(@Param("id") String id, @Param("date") String date, @Param("time") String time);

    // 查找登录记录，得到时段
    @Select("select count(time=0 or null) as timea,count(time=1 or null) as timeb,count(time=2 or null) as timec,count(time=3 or null) as timed\n" +
            "from loginrecord\n" +
            "where userid=#{userid}")
    Map<String, Integer> getTimePercent(String userid);

    // 板块儿偏好
    @Select("select count(typeb=0 or null) as ta, count(typeb=1 or null) as tb, count(typeb=2 or null) as tc from\n" +
            "(select * from postsinfomation as a\n" +
            "left join\n" +
            "(select postid as postidb, type as typeb from posts) as b\n" +
            "on a.postid=b.postidb\n" +
            ") as c\n" +
            "where c.clientid=#{clientid}")
    Map<String, Integer> getPreferencePercent(String clientid);

    // 评论引用占比
    @Select("select count(type=0 or null) as typea, count(type=1 or null) as typeb from postscomment where clientid=#{clientid}")
    Map<String, Integer> getBehaviorPercent(String clientid);

    // 用户删帖时检验是否是该用户发帖
    @Select("select * from posts where postid=#{postid} and authorid=#{authorid}")
    HashMap<String, Object> checkValidationDeletePost(String postid, String authorid);
}
