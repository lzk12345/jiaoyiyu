package com.jiaoyiyu.back.personcenter.controller.accountmanagementcontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PhonecertificationController class
 *
 * @author maochaoying
 * @date 2019/11/12
 */
@CrossOrigin
@Controller
@RequestMapping("/phonecertification")
public class PhonecertificationController {
    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "phonecertification";
    }
}
