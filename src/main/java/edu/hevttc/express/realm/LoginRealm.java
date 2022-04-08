package edu.hevttc.express.realm;

import edu.hevttc.express.enums.SysUserStatusEnum;
import edu.hevttc.express.service.SysUserService;
import edu.hevttc.express.enums.RoleEnum;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.utils.PasswordUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 身份验证
 */
@Component
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名和密码
        String email = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        SysUser sysUser = userService.getByUserName(email);

        // 登录失败
        if (sysUser == null || !PasswordUtils.validatePassword(password, sysUser.getPassword())) {
            throw new IncorrectCredentialsException("登录失败，用户名或密码不正确！");
        } else if (sysUser.getStatus().equals(SysUserStatusEnum.AUDITING.getIndex())) {
            throw new IncorrectCredentialsException("登录失败，用户注册审核暂未通过！");
        }
        // 身份验证通过,返回一个身份信息
        return new SimpleAuthenticationInfo(email, password, getName());
    }

    /**
     * 身份授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String email = (String) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysUser sysUser = userService.getByUserName(email);
        // 获取角色对象
        Integer roleId = sysUser.getRoleId();
        //通过用户名从数据库获取权限/角色信息
        Set<String> r = new HashSet<>();
        if (roleId != null) {
            r.add(RoleEnum.getName(roleId));
            info.setRoles(r);
        }
        return info;
    }
}
