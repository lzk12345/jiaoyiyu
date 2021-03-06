package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MyWorkController class
 *
 * @author maochaoying
 * @date 2019/11/11
 */
@CrossOrigin
@Controller
@RequestMapping("/myWork")
public class MyWorkController {
    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "my_work";
    }
}
