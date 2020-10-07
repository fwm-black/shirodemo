package com.wm.shirodemo.service;

import com.wm.shirodemo.model.User;

import java.util.List;

public interface UserService {

    User getUserByName(String username);

    List<String> getUserRoles(String username);

    void register(String username, String password);
}
