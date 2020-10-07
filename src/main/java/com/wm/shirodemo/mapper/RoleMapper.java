package com.wm.shirodemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wm.shirodemo.model.Role;

import javax.annotation.Resource;

@Resource
public interface RoleMapper extends BaseMapper<Role> {

    int[] getRoleIdByUserId(int userId);

}
