package com.wm.shirodemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wm.shirodemo.model.User;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户角色信息
     * @param username
     * @return
     */
    List<String> getRolesByName(String username);

}
