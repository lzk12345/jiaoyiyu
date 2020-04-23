package com.jiaoyiyu.back.interceptor;

import com.alibaba.fastjson.JSON;
import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        // 获得方法的注解
        LoginRequired methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
        // 如果方法上没有注解，则代表不需要被拦截器拦截
        if (methodAnnotation == null) {
            return true;
        }
        // 通过loginSuccess来判断是否必须登录
        boolean loginResult = methodAnnotation.loginSuccess();
        // 如果newToken 和 oldToken都有值说明 oldToken 过期
        String token = "";
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }
        String verifyResult = "";
        //验证token
        if (StringUtils.isNotBlank(token)) {
            verifyResult = HttpclientUtil.doGet("http://121.36.85.218:80/passport/verify?token=" + token);
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setPath("/");
                    cookie.setDomain("121.36.85.218");
                    response.addCookie(cookie);

                }
            }
        }
        Map<String, String> verifyResultMaps = null;
        if (StringUtils.isNotBlank(verifyResult)) {
            verifyResultMaps = JSON.parseObject(verifyResult,Map.class);
        }else {
            verifyResultMaps = new HashMap<>();
            verifyResultMaps.put("status", "failed");
        }

        if (loginResult) {
            //必须登录
            if (verifyResultMaps.get("status").equals("success")) {
                // 取出token信息
                if ( verifyResultMaps.get("nickname") != null) {
                    request.setAttribute("nickname", verifyResultMaps.get("nickname"));
                }
                if (verifyResultMaps.get("memberId") != null) {
                    request.setAttribute("memberId", verifyResultMaps.get("memberId"));
                }
                if (verifyResultMaps.get("phonenum") != null) {
                    request.setAttribute("phonenum", verifyResultMaps.get("phonenum"));
                }
                // 更新token的过期时间
                if (StringUtils.isNotBlank(token)) {
                    CookieUtils.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                }
                return true;
            }else {
                //重定向回passport页面登录
                // 重定向回原来的界面
                StringBuffer requestURL = request.getRequestURL();
                String url = requestURL.toString();
                response.sendRedirect("http://121.36.85.218:80/passport/index?returnUrl=" + url);
                return false;
            }
        }else {
            // 不是必须登录
            // 如果登录
            if (verifyResultMaps.get("status").equals("success")) {
                // 取出token信息
                if ( verifyResultMaps.get("nickname") != null) {
                    request.setAttribute("nickname", verifyResultMaps.get("nickname"));
                }
                if (verifyResultMaps.get("memberId") != null) {
                    request.setAttribute("memberId", verifyResultMaps.get("memberId"));
                }
                if (verifyResultMaps.get("phonenum") != null) {
                    request.setAttribute("phonenum", verifyResultMaps.get("phonenum"));
                }
                if (StringUtils.isNotBlank(token)) {
                    CookieUtils.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                }
            }else{
                // 如果没登陆
                return true;
            }
        }
        return true;
    }
}