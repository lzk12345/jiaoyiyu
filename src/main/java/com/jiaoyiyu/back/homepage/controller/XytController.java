package com.jiaoyiyu.back.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * XytController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

@Controller
@RequestMapping("/xyt")
@CrossOrigin
public class XytController {

    @RequestMapping("/index")
    public String index () {
        return "xyt";
    }
}
