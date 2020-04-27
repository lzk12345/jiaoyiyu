package com.jiaoyiyu.back.homepage.controller;

import com.jiaoyiyu.back.bean.vo.XytVO;
import com.jiaoyiyu.back.coreservice.XytService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

/**
 * XytController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

@Controller
@RequestMapping("/xyt")
@CrossOrigin
public class XytController {

    @Autowired
    XytService xytService;

    @RequestMapping("/index")
    public String index (@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, ModelMap modelMap) throws ParseException {
        List<XytVO> xytVOList = xytService.getXytVO(pageNum);
        modelMap.put("xytVOList", xytVOList);
        return "xyt";
    }
}
