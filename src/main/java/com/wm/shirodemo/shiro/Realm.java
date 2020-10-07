package com.wm.shirodemo.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wm.shirodemo.mapper.PermissionMapper;
import com.wm.shirodemo.mapper.RoleMapper;
import com.wm.shirodemo.mapper.UserMapper;
import com.wm.shirodemo.model.User;
import com.wm.shirodemo.service.UserService;
import com.wm.shirodemo.shiro.salt.SaltByteSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义realm
 */
@Slf4j
@Component("Realm")
public class Realm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = principalCollection.getPrimaryPrincipal().toString();

        List<String> roles = this.userService.getUserRoles(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(roles);

        log.info(permissionMapper.getPermissionsByRoleId(roleMapper.getRoleIdByUserId(userMapper.selectOne(new QueryWrapper<User>().eq("username",username)).getId())).toString());

        simpleAuthorizationInfo.addStringPermissions(permissionMapper.getPermissionsByRoleId(roleMapper.getRoleIdByUserId(userMapper.selectOne(new QueryWrapper<User>().eq("username",username)).getId())));

        return simpleAuthorizationInfo;
    }

    /**
     * 身份信息认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 已知的用户名
        String username = authenticationToken.getPrincipal().toString();
        // 根据用户名从数据库获取密码
        User user = userService.getUserByName(username);

        String password = user.getPassword();
        // AuthenticationInfo封装username和password
        // 参数1: 用户名     参数二：数据库密码       参数三：盐       参数四：realm
        SimpleAuthenticationInfo simpleAuthorizationInfo = new SimpleAuthenticationInfo(
                username,
                password,
                //salt需要序列化，默认的ByteSource没有实现序列化，需要自定义
                new SaltByteSource("mySlat"),
                this.getName());
        return simpleAuthorizationInfo;

    }
}
