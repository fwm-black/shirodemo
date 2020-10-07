package com.wm.shirodemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wm.shirodemo.model.Permission;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> getPermissionsByRoleId(int[] roleIds);

}
