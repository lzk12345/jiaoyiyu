package com.jiaoyiyu.back.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HelpCenterController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

@Controller
@RequestMapping("/helpcenter")
@CrossOrigin
public class HelpCenterController {
    @RequestMapping("/index")
    public String index() {
        return "help_center";
    }
}
