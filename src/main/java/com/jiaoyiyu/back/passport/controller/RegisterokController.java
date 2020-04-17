package com.jiaoyiyu.back.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RegisterokController class
 * 跳转到注册成功页面
 * @author maochaoying
 * @date 2019/11/12
 */


@Controller
@RequestMapping("/registerok")
@CrossOrigin
public class RegisterokController {

    @RequestMapping
    public String index() {
        return "registerok";
    }
}
