package com.jiaoyiyu.back.user.controller;

import com.jiaoyiyu.back.bean.Catalog2;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * UserController class
 *
 * @author maochaoying
 * @date 2020/4/2
 */

@Controller
@RequestMapping("/userInfo")
@CrossOrigin
public class UserController {

}
