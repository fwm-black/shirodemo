package com.wm.shirodemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wm.shirodemo.mapper.UserMapper;
import com.wm.shirodemo.model.User;
import com.wm.shirodemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public List<String> getUserRoles(String username) {
        log.info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        return userMapper.getRolesByName(username);
    }

    @Override
    public void register(String username, String password) {
        String pwd = new Md5Hash(password,"mySlat",22).toHex();
        userMapper.insert(new User(username,pwd));
    }

}
