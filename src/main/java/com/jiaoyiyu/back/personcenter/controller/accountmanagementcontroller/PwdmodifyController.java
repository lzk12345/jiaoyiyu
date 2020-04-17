package com.jiaoyiyu.back.personcenter.controller.accountmanagementcontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.back.coreservice.PwdModifyService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * PwdmodifyController class
 *
 * @author maochaoying
 * @date 2019/11/12
 */
@Controller
@CrossOrigin
@RequestMapping("/pwdmodify")
public class PwdmodifyController {

    @Autowired
    PwdModifyService pwdModifyService;
    @Autowired
    PublishTaskService publishTaskService;


    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "pwdmodify";
    }

    @RequestMapping("/updatePayPwd")
    @ResponseBody
    public int updatePayPwd(HttpServletRequest request, @RequestParam("newPayPwd") Integer newPayPwd){
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum1 = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum1);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        int res = pwdModifyService.updatePayPwd(memberId, newPayPwd);
        return res;
    }
}
