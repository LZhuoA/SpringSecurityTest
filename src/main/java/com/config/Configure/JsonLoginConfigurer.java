package com.config.Configure;

import com.config.filter.MyUsernamePasswordAuthenticationFilter;
import com.config.handler.JsonLoginFailHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * @Author LZA
 * @Date 2020/9/16 14:18
 */

/**
 * 把登陆流程的组合在一起
 */
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private MyUsernamePasswordAuthenticationFilter authFilter;

    public JsonLoginConfigurer(){
        this.authFilter = new MyUsernamePasswordAuthenticationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        //设置Filter使用的AuthenticationManager,这里取公共的即可
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置失败的Handler
        authFilter.setAuthenticationFailureHandler(new JsonLoginFailHandler());
        //不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        MyUsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
        //制定filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
    }

    //设置成功的Handler
    public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }

}
