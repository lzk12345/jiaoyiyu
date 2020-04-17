package com.jiaoyiyu.back.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AccessController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */
@Controller
@RequestMapping("/access")
@CrossOrigin
public class AccessController {

    @RequestMapping("/index")
    public String index() {
        return "assess";
    }
}
