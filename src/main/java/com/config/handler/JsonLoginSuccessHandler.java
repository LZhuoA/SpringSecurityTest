package com.config.handler;

import com.config.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author LZA
 * @Date 2020/9/16 14:07
 */

/**
 * 校验成功返回token给前端
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoginFailHandler.class);

    private CustomUserDetailsService userDetailsServicel;

    public JsonLoginSuccessHandler(CustomUserDetailsService userDetailsService){
        this.userDetailsServicel = userDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = userDetailsServicel.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
        response.setHeader("Authorization",token);
        logger.info("验证成功");
    }

}
