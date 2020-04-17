package com.jiaoyiyu.back.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OpenVipController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */
@Controller
@RequestMapping("/openvip")
@CrossOrigin
public class OpenVipController {
    @RequestMapping("/index")
    public String index() {
        return "openVip";
    }
}
