package com.jiaoyiyu.back.shopcontroller;

import com.jiaoyiyu.back.bean.TbSellservice;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.SellService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/worksdetails")
@CrossOrigin
public class WorksDetailsController {

    @RequestMapping("/index")
    public String index() {

        return "worksdetails";
    }
}
