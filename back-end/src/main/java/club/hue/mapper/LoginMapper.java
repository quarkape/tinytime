package club.hue.mapper;

import club.hue.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Mapper
@Repository
public interface LoginMapper {

    @Select("select * from users where name=#{name} and password=#{password}")
    User getUserByName(@Param("name") String name, @Param("password") String password);

    @Select("select * from users where name=#{name} and email=#{email}")
    User getUserByEmail(@Param("name") String name, @Param("email") String email);

    @Select("select * from users where id=#{id}")
    User getUserById(String id);

    @Select("select * from users where name=#{name}")
    User checkUserRepeatByName(String name);

    @Select("select * from users where email=#{email}")
    User checkUserRepeatByEmail(String email);

    @Insert("insert into users values(#{id}, #{name}, #{password}, #{email}, #{mark}, #{avatarurl})")
    int addUser(User user);

    @Select("select * from users where email=#{email}")
    User getUserByEmailSimple(String email);

    @Update("update users set password=#{password} where email=#{email}")
    int modifyPassword(@Param("password") String password, @Param("email") String email);

    @Select("select * from control_func")
    int getDataNormal();

    @Select("select count from pic_count order by type asc")
    Integer[] getPicCount();

}
