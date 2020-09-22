package com.config.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.config.CustomUserDetailsService;
import com.config.JwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * @Author LZA
 * @Date 2020/9/15 10:37
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private CustomUserDetailsService userDetailsService;

    public JwtAuthenticationProvider(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
        if(jwt.getExpiresAt().before(Calendar.getInstance().getTime())){
            logger.info("Token 过期");
            throw new NonceExpiredException("Token 过期");
        }
        String username = jwt.getSubject();
        UserDetails user = userDetailsService.getUserLoginInfo(username);
        if(user==null||user.getPassword()==null){
            logger.info("Token加密的salt 过期");
            throw new NonceExpiredException("Token加密的salt 过期");
        }
        String encryptSalt = user.getPassword();
        try{
            Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(username)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (UnsupportedEncodingException e) {
            logger.info("Token认证失败");
            throw new BadCredentialsException("Token认证失败",e);
        } catch (JWTVerificationException e){
            logger.info("Token认证失败");
            throw new BadCredentialsException("Token认证失败",e);
        }
        //成功后返回认证信息，filter会将认证信息放入SecurityContext
        JwtAuthenticationToken token = new JwtAuthenticationToken(user,jwt,user.getAuthorities());
        return token;
    }

    /**
     * supports支持很多子类，比如JwtAuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

}
