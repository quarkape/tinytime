package club.hue.service;

import club.hue.pojo.User;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

public interface LoginService {

    User getUserByName(String name, String password);

    User getUserByEmail(String name, String email);

    User getUserById(String id);

    User checkUserRepeatByName(String name);

    User checkUserRepeatByEmail(String email);

    int addUser(User user);

    User getUserByEmailSimple(String email);

    int modifyPassword(String password, String email);

    int getDataNormal();

    Integer[] getPicCount();

}
