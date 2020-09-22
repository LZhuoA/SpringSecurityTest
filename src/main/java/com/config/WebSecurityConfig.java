package com.config;

import com.config.Configure.JsonLoginConfigurer;
import com.config.Configure.JwtLoginConfigurer;
import com.config.filter.OptionsRequestFilter;
import com.config.handler.JsonLoginSuccessHandler;
import com.config.handler.SimpleAccessDeniedHandler;
import com.config.handler.TokenClearLogoutHandler;
import com.config.provider.JsonLoginProvider;
import com.config.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @Author LZA
 * @Date 2020/8/4 11:42
 */

/**
 * 该类的三个注解分别是标识该类是配置类、开启 Security 服务、开启全局 Securtiy 注解。
 */
@EnableWebSecurity
@ComponentScan(value = {"com.service","com.dao"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jsonLoginProvider()).authenticationProvider(jwtAuthenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //静态资源访问无需认证
//                .antMatchers("/image/**").permitAll()
                //admin开头的请求，需要admin权限
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                //需要登陆才能访问的url
//                .antMatchers("/article/**").hasRole("USER")
                ////默认其它的请求都需要认证，这里一定要添加
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                //权限校验失败走这个类里面的处理方法
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                .and()
                //csrf禁用,因为不使用session
                .csrf().disable()
                //禁用session
                .sessionManagement().disable()
                //禁用form登陆
                .formLogin().disable()
                //支持跨域
                .cors()
                .and()
                //支持header设置，支持跨域和ajax请求
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                        new Header("Access-control-Allow-Origin","*"),
                        new Header("Access-Control-Expose-Headers","Authorization"))))
                .and()
                //拦截OPTIONS请求，直接返回header
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                //添加登陆filter
                .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(jsonLoginSuccessHandler())
                .and()
                //添加token的filter
                .apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jsonLoginSuccessHandler())
                .and()
                //使用默认的logoutFilter
                .logout()
                    //默认时"/logout"
                    .logoutUrl("/logout")
                    .addLogoutHandler(tokenClearLogoutHandler())
                    //logout成功后返回200
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .sessionManagement().disable();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(userDetailsService);
    }

    @Bean("jsonLoginProvider")
    protected AuthenticationProvider jsonLoginProvider(){
        return new JsonLoginProvider(userDetailsService, new BCryptPasswordEncoder());
    }

    @Bean
    protected TokenClearLogoutHandler tokenClearLogoutHandler() {
        return new TokenClearLogoutHandler(userDetailsService);
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler(userDetailsService);
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
