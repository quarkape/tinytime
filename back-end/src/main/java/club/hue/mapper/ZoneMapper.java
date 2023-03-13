package club.hue.mapper;

import club.hue.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ZoneMapper {

    @Select("select * from notify where id=#{id}")
    List<PostNotify> getAllNotify(String id);

    @Update("update notify set status=1 where notifyid=#{notifyid}")
    int changeNotifyStatus(@Param("notifyid") String notifyid);

    @Insert("insert into message values(#{postid}, #{messageid}, #{rolea}, #{roleb}, #{type}, 0, #{date})")
    int addMessage(HashMap<String, Object> map);

    @Update("update message set status=1 where roleb=#{roleb} and messageid=#{messageid}")
    int changeMessageStatus(String roleb, String messageid);

    @Select("select * from message as a " +
            "left join " +
            "(select postid, title from posts) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select id, name from users) as c " +
            "on a.rolea=c.id " +
            "where roleb=#{roleb} " +
            "order by date DESC")
    List<PostMessage> getAllMessage(String roleb);

    @Select("select count(*) from notify where id=#{id} and status=0")
    int getUnreadNotify(String id);

    @Select("select count(*) from message where roleb=#{roleb} and status=0")
    int getUnreadMessage(String roleb);

    @Select("select * from posts as a " +
            "left join " +
            "(select * from postscontent) as b " +
            "on a.postid=b.postid " +
            "where a.authorid=#{authorid} " +
            "order by a.date DESC")
    List<MyPost> getMyPosts(String authorid);

    @Select("select * from postscomment as a " +
            "left join " +
            "(select title, postid from posts) as b " +
            "on a.postid=b.postid " +
            "where a.clientid=#{clientid} " +
            "order by a.date DESC")
    List<MyComment> getMyComment(String clientid);

    @Select("select * from postsinfomation as a " +
            "left join " +
            "(select postid, authorid, title from posts) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select postid, content from postscontent) as c " +
            "on a.postid=c.postid " +
            "left join " +
            "(select id, name from users) as d " +
            "on b.authorid=d.id " +
            "where a.clientid=#{clientid} and a.collect=1 " +
            "order by a.date DESC")
    List<MyCollect> getMyCollect(String clientid);


}
