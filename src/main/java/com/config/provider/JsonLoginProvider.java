package com.config.provider;

import com.config.CustomUserDetailsService;
import com.entity.SysUser;
import com.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author LZA
 * @Date 2020/9/18 11:12
 */
public class JsonLoginProvider extends DaoAuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoginProvider.class);

    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *  这里必须指定密码加密的加密方式
     */
    public JsonLoginProvider(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
        this.userDetailsService = userDetailsService;
    }

    /**
     *  这里实现了认证流程，参数authentication里面是登录filter传入的token
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SysUser sysUser = sysUserService.getByUserName(username);

        if(sysUser == null){
            logger.info("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password,sysUser.getPassword())){
            logger.info("密码错误，请重试");
            throw new BadCredentialsException("密码错误，请重试");
        }

        return super.authenticate(authentication);
    }


}
