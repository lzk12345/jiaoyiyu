package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CollectionofPostsController class
 *
 * @author maochaoying
 * @date 2019/11/11
 */
@Controller
@RequestMapping("/collectionofPosts")
@CrossOrigin
public class CollectionofPostsController {

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "CollectionofPosts";
    }
}
