package com.config.handler;

import com.config.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author LZA
 * @Date 2020/9/16 16:06
 */

/**
 * 退出后清除token(使用redis的方式)
 */
public class TokenClearLogoutHandler implements LogoutHandler {

    private CustomUserDetailsService userService;

    public TokenClearLogoutHandler(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        clearToken(authentication);
    }

    protected void clearToken(Authentication authentication) {
        if(authentication == null)
            return;
        UserDetails user = (UserDetails)authentication.getPrincipal();
        if(user!=null && user.getUsername()!=null)
            userService.deleteUserLoginInfo(user.getUsername());
    }

}
