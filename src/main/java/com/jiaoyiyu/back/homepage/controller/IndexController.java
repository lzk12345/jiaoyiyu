package com.jiaoyiyu.back.homepage.controller;

import com.jiaoyiyu.back.bean.vo.XytVO;
import com.jiaoyiyu.back.coreservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.List;

@Controller
@CrossOrigin

public class IndexController {


    @Autowired
    XytService xytService;
    @RequestMapping("/")
    public String index(ModelMap modelMap) throws ParseException {
        List<XytVO> xytVOList = xytService.getXytVO1();
        modelMap.put("xytVOList", xytVOList);
        return "indextest";
    }
}
