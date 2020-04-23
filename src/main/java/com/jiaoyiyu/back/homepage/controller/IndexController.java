package com.jiaoyiyu.back.homepage.controller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.Assignment;
import com.jiaoyiyu.back.bean.Settlement;
import com.jiaoyiyu.back.bean.TbHotHomepageData;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.vo.HotData;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.HotDataService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.back.coreservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class IndexController {

    @LoginRequired(loginSuccess = false)
    @RequestMapping("/")
    public String index(ModelMap modelMap) {

        return "indextest";
    }
}
