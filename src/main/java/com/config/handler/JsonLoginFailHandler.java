package com.config.handler;

/**
 * @Author LZA
 * @Date 2020/9/16 14:15
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

/**
 * 身份校验失败返回  401
 */
public class JsonLoginFailHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoginFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("通过失败");
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        LinkedHashMap<Object,Object> map = new LinkedHashMap<>();
        map.put("success",true);
        map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
        //这里获取异常的信息
        map.put("msg",e.getMessage());
        map.put("data","");
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(map);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

}
