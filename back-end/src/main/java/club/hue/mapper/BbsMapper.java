package club.hue.mapper;

import club.hue.pojo.CertainPost;
import club.hue.pojo.PostComment;
import club.hue.pojo.PostInfomation;
import club.hue.pojo.SimplePost;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface BbsMapper {

    @Select("select * from posts as a " +
            "left join " +
            "(select * from postscontent) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select count(*) as view, postid from postsinfomation group by postid) as c " +
            "on a.postid=c.postid " +
            "left join " +
            "(select count(*) as comment, postid from postscomment group by postid) as d " +
            "on a.postid=d.postid " +
            "where view > 0 and (unix_timestamp(date) + 1296000000 > UNIX_TIMESTAMP()) and date < #{lastdate} " +
            "order by view DESC " +
            "limit 0,20 ")
    List<SimplePost> getHotPosts(String lastdate);

    @Select("select * from posts as a " +
            "left join " +
            "(select * from postscontent) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select count(*) as view, postid from postsinfomation group by postid) as c " +
            "on a.postid=c.postid " +
            "left join " +
            "(select count(*) as comment, postid from postscomment group by postid) as d " +
            "on a.postid=d.postid " +
            "where type=#{type} and date < #{lastdate} " +
            "order by date DESC " +
            "limit 0,20")
    List<SimplePost> getTypePosts(@Param("type") String type, @Param("lastdate") String lastdate);

    @Select("select * from posts as a " +
            "left join " +
            "(select * from postscontent) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select count(*) as view, postid from postsinfomation group by postid) as c " +
            "on a.postid=c.postid " +
            "left join " +
            "(select count(*) as comment, postid from postscomment group by postid) as d " +
            "on a.postid=d.postid " +
            "where a.title like CONCAT('%',#{keyword},'%') and date < #{lastdate} " +
            "order by date DESC " +
            "limit 0,20")
    List<SimplePost> getSearchPosts(@Param("keyword") String keyword, @Param("lastdate") String lastdate);

    @Insert("insert into posts values(#{postid}, #{authorid}, #{type}, #{anonymous}, #{title}, #{imgtype}, #{imgurl}, #{date})")
    int addPost(HashMap<String, Object> map);

    @Insert("insert into postscontent values(#{postid}, #{content})")
    int addPostContent(@Param("postid") String postid, @Param("content") String content);

    @Select("select * from posts as a " +
            "left join " +
            "(select * from postscontent) as b " +
            "on a.postid=b.postid " +
            "left join " +
            "(select count(*) as view, count(agree=1 or null) as agree, count(collect=1 or null) as collect, postid from postsinfomation group by postid) as c " +
            "on a.postid=c.postid " +
            "left join " +
            "(select count(*) as comment, postid from postscomment group by postid) as d " +
            "on a.postid=d.postid " +
            "left join " +
            "(select id, name, mark, avatarurl from users) as e " +
            "on a.authorid=e.id " +
            "where a.postid=#{postid}")
    CertainPost getCertainPost(String postid);

    @Select("select * from postsinfomation where postid=#{postid}")
    PostInfomation getPostInfomation(@Param("postid") String postid);

    @Select("select * from postscomment as a " +
            "left join " +
            "(select id, name, mark, avatarurl from users) as b " +
            "on a.clientid=b.id " +
            "where a.postid=#{postid} " +
            "order by a.date DESC")
    List<PostComment> getPostComment(String postid);

    @Insert("insert into postsinfomation values(#{postid}, #{clientid}, #{view}, #{agree}, #{collect}, #{date})")
    int addInfomation(PostInfomation postInfomation);

    @Insert("insert into postscomment values(#{postid}, #{type}, #{clientid}, #{detail}, #{quote}, #{date})")
    int addComment(HashMap<String, Object> map);

    @Update("update postsinfomation set agree=#{agree} where postid=#{postid} and clientid=#{clientid}")
    int changeAgreeStatus(@Param("agree") int agree, @Param("postid") String postid, @Param("clientid") String clientid);

    @Update("update postsinfomation set collect=#{collect}, date=#{date} where postid=#{postid} and clientid=#{clientid}")
    int changeCollectStatus(@Param("collect") int collect, @Param("date") String date, @Param("postid") String postid, @Param("clientid") String clientid);

    @Insert("insert into postsreport values(#{postid}, #{clientid}, #{detail}, #{date})")
    int addReport(@Param("postid") String postid, @Param("clientid") String clientid, @Param("detail") String detail, @Param("date") String date);

}
