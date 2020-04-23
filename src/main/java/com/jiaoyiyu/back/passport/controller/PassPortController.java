package com.jiaoyiyu.back.passport.controller;

import com.alibaba.fastjson.JSON;
import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 1。 进行登录界面的跳转
 * 2。 登录的验证
 * 3。 token的颁发和验证
 */
@RequestMapping("/passport")
@Controller
@CrossOrigin
public class PassPortController {

    @Autowired
    UserService userService;

    /**
     * 登录首页
     * @param request
     * @param modelMap
     * @return
     */
    @LoginRequired(loginSuccess = false)
    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap) {
        String returnUrl = request.getParameter("returnUrl");
        // 如果url地址中没有带returnUrl说明不是从系统哪个页面跳转来，登陆后进入首页，即"/index";
        if (StringUtils.isBlank(returnUrl)) {
            returnUrl = "http://121.36.85.218:80/index";
        }
        modelMap.put("returnUrl", returnUrl);
        return "loginindex";
    }

    /**
     * 进行登录的校验
     * @param username 前台传来的用户名
     * @param password 前台传来的密码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpServletRequest request, HttpServletResponse response) {
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        String token = "";
        // 从缓存中获取该对象
        User user = userService.queryFromCache(username, password);
        if (user == null) {
            //说明缓存中没有数据  需要从数据库中查询
            user = userService.queryUserOne(username, password);
            if (user != null) {
                //用户名密码正确
                // 制作token
                token = makeToken(salt,key,user);
                CookieUtils.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                // 将用户信息存入缓存
                userService.saveUserInfoToCache(user);
            }else {
                return "failed";
            }
        }else {
            // 制作token
            token = makeToken(salt,key,user);
            CookieUtils.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setPath("/");
                cookie.setDomain("121.36.85.218");
                response.addCookie(cookie);
            }
        }

        return token;
    }

    /**
     * 制作token 其中包含phonenum，memberId，nickname
     * @param salt
     * @param key
     * @param user
     * @return
     */
    private String makeToken(String salt, String key, User user) {
        Map<String,Object> param = new HashMap<>();
        String phone = user.getPhone();
        System.out.println(phone);
        param.put("phonenum", user.getPhone());
        param.put("memberId", user.getId());
        param.put("nickname", user.getNickName());
        String token = JwtUtil.encode(key, param, salt);
        return token;
    }

    /**
     * 拦截器中调用 用来验证request中的token是否正确
     * @param request
     * @return
     */
    @RequestMapping("/verify")
    @ResponseBody
    public String verify(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();
        String token = request.getParameter("token");
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        if (decode == null) {
            map.put("status", "failed");
        }else{
            Integer memberId = (Integer) decode.get("memberId");
            if (memberId != null) {
                map.put("memberId", memberId + "");
            }
            String nickname = (String) decode.get("nickname");
            if (nickname != null) {
                map.put("nickname", nickname);
            }
            String phonenum = (String) decode.get("phonenum");
            if (phonenum != null) {
                map.put("phonenum", phonenum);
            }
            map.put("status", "success");
        }
        return JSON.toJSONString(map);
    }
}
