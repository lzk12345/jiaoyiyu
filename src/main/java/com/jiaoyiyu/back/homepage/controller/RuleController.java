package com.jiaoyiyu.back.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RuleController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

@Controller
@RequestMapping("/rule")
@CrossOrigin
public class RuleController {
    @RequestMapping("/index")
    public String index() {
        return "ruleOverView";
    }
}
