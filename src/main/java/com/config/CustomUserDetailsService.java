package com.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.entity.Permission;
import com.entity.Role;
import com.entity.SysUser;
import com.service.PermissionService;
import com.service.RoleService;
import com.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author LZA
 * @Date 2020/8/4 11:12
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    public CustomUserDetailsService(){
        //默认使用 bcrypt， strength=10
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public UserDetails getUserLoginInfo(String username){
        //盐可以从数据库或者缓存中取出jwt token生成时用的salt，这里直接自定义
        String salt ="123456ef";
        UserDetails user = loadUserByUsername(username);
        return User.builder()
                .username(user.getUsername())
                .password(salt)
                .authorities(user.getAuthorities())
                .build();
    }

    /**
     *  生成jwt
     */
    public String saveUserLoginInfo(UserDetails user) throws UnsupportedEncodingException {
        //BCrypt.gensalt();  正式开发时可以调用该方法实时生成加密的salt
        String salt ="123456ef";
        //然后将salt保存到数据库或者缓存中
        Algorithm algorithm = Algorithm.HMAC256(salt);
        //设置1小时后过期
        Date date = new Date(System.currentTimeMillis()+3600*1000);
        return "lza-"+JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        SysUser sysUser = sysUserService.getByUserName(username);

        //添加角色
        List<Role> roleList = roleService.getByUserName(username);
        ArrayList<String> list = new ArrayList<>();
        for (Role role:roleList){
            list.add(role.getRoleName());
        }
        String[] roleName = list.toArray(new String[list.size()]) ;

        //添加权限
        for(Role role:roleList){
            List<Permission> permissionList = permissionService.getByRoleName(role.getRoleName());
            for (Permission permission:permissionList){
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            }
        }

        //坑  角色验证和权限认证，只能选择其中之一去效验，框架源码里面最后还是返回authorities
        return User.builder().username(sysUser.getUserName()).password(sysUser.getPassword())
                .roles(roleName)
                .authorities(authorities)
                .build();
    }


    public void deleteUserLoginInfo(String username) {
        /**
         * @todo 清除数据库或者缓存中的token
         */
        logger.info("退出成功");
    }

}
