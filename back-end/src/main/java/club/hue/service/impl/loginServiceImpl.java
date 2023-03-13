package club.hue.service.impl;

import club.hue.mapper.LoginMapper;
import club.hue.pojo.User;
import club.hue.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

@Service
public class loginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public User getUserByName(String name, String password) {
        return loginMapper.getUserByName(name, password);
    }

    @Override
    public User getUserByEmail(String name, String email) {
        return loginMapper.getUserByEmail(name, email);
    }

    @Override
    public User getUserById(String id) {
        return loginMapper.getUserById(id);
    }

    @Override
    public User checkUserRepeatByName(String name) {
        return loginMapper.checkUserRepeatByName(name);
    }

    @Override
    public User checkUserRepeatByEmail(String email) {
        return loginMapper.checkUserRepeatByEmail(email);
    }

    @Override
    public int addUser(User user) {
        return loginMapper.addUser(user);
    }

    @Override
    public User getUserByEmailSimple(String email) {
        return loginMapper.getUserByEmailSimple(email);
    }

    @Override
    public int modifyPassword(String password, String email) {
        return loginMapper.modifyPassword(password, email);
    }

    @Override
    public int getDataNormal() {
        return loginMapper.getDataNormal();
    }

    @Override
    public Integer[] getPicCount() {
        return loginMapper.getPicCount();
    }
}
