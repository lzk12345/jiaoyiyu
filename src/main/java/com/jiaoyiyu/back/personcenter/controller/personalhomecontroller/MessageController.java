package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MessageController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */
@Controller
@RequestMapping("/message")
@CrossOrigin
public class MessageController {

    @RequestMapping("/index")
    public String index() {
        return "messageInterface";
    }
}
