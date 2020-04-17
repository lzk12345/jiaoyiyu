package com.jiaoyiyu.back.personcenter.controller.accountmanagementcontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * XhyAccountBindingController class
 *
 * @author maochaoying
 * @date 2019/11/12
 */
@CrossOrigin
@Controller
@RequestMapping("/xhyAccountBinding")
public class XhyAccountBindingController {

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "xhyAccountBinding";
    }
}
