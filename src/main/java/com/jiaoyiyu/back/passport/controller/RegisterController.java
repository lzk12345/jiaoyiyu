package com.jiaoyiyu.back.passport.controller;

import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.RegisterService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * RegisterController class
 * 注册逻辑
 * @author maochaoying
 * @date 2019/11/6
 */
@Controller
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @Autowired
    UserService userService;

    /**
     * 手机验证码登录并注册
     * @param phonenum 手机号
     * @param messageCode 验证码
     * @param modelMap
     * @return
     */
    @RequestMapping("/msgRegister")
    public String registerByMsg(@RequestParam("phonenum") String phonenum, @RequestParam("messageCode") String messageCode, ModelMap modelMap) {
        // 手机号注册
        String result = registerService.registerByMsg(phonenum, messageCode);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        String token = "";
        User user = new User();
        user.setPhone(phonenum);
        if (result.equals("success")) {
            token = makeToken(salt, key, user);
            // 手机号存入数据库
            User user1 = userService.savePhoneNumToDB(phonenum);
            // 清除redis缓存的验证码
            registerService.delCodeCache(phonenum, messageCode);
            return "redirect:http://121.36.85.218:80/index?token=" + token + "&phonenum=" + phonenum;
        }else if (result.equals("验证码已过期")){
            modelMap.put("result", "验证码已过期");
            return "loginindex";
        }else {
            // 验证码不正确
            modelMap.put("result", "验证码不正确");
            return "loginindex";
        }
    }

    /**
     * 生成带有电话的token
     * @param salt
     * @param key
     * @param user
     * @return
     */
    private String makeToken(String salt, String key, User user) {
        Map<String,Object> param = new HashMap<>();
        String phone = user.getPhone();
        String username = user.getUsername();
        if (phone != null) {
            param.put("phonenum", user.getPhone());
        }
        if (username != null) {
            param.put("username", username);
        }
        String token = JwtUtil.encode(key, param, salt);
        return token;
    }

    /**
     * 使用用户名和密码进行注册
     * @param username 用户名
     * @param password 密码
     * @param phonenum 手机号
     * @param modelMap
     * @return
     */
    @RequestMapping("/userAndPassRegister")
    public String userAndPassRegister(@RequestParam("username1") String username, @RequestParam("password1") String password,
                                      @RequestParam("phonenum1") String phonenum, ModelMap modelMap) {
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        String token = "";
        // 保存用户名和密码到数据库
        String result = userService.saveUserAndPassToDB(username,password,phonenum);
        if (result.equals("success")) {
            User user = new User();
            user.setUsername(username);
            user.setPhone(phonenum);
            token = makeToken(salt, key, user);
            // 跳转到注册成功界面
            return "registerok";
        }else if (result.equals("账号已经存在，请直接登陆")){
            //前台显示信息
            modelMap.put("result", "账号已经存在，请直接登陆");
            return "loginindex";
        }else if(result.equals("用户名已经存在")) {
            //前台显示信息
            modelMap.put("result", "用户名已经存在");
            return "loginindex";
        }else {
            //前台显示信息
            modelMap.put("result", "服务器异常，请稍后重试！");
            return "loginindex";
        }
    }
}
