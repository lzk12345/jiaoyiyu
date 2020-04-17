package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * XhyWorksUnderSaleController class
 *
 * @author maochaoying
 * @date 2019/11/11
 */
@CrossOrigin
@Controller
@RequestMapping("/xhyWorksUnderSale")
public class XhyWorksUnderSaleController {
    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "xhyWorksUnderSale";
    }

}
