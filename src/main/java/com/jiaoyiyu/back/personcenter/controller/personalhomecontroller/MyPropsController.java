package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.coreservice.MyPropsService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.HttpclientUtil;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * MyPropsController class
 * 我的道具
 * @author maochaoying
 * @date 2019/11/11
 */
@Controller
@RequestMapping("/myProps")
@CrossOrigin
public class MyPropsController {

    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    MyPropsService myPropsService;

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index(ModelMap modelMap, HttpServletRequest request) {
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(oldToken, key, salt);
        modelMap.put("token", oldToken);
        String pic = HttpclientUtil.doGet("/index/getPic?token=" + oldToken);
        modelMap.put("uploadImg", pic);

        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            Integer memberId = (Integer) decode.get("memberId");
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            }else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
                // 拿到会员类型

                Integer allHideResidualTimes = user.getAllHideResidualTimes();
                Integer taskHideResidualTimes = user.getTaskHideResidualTimes();
                Integer taskUrgentResidualTimes = user.getTaskUrgentResidualTimes();
                Integer taskTopResidualTimes = user.getTaskTopResidualTimes();
                modelMap.put("allHideResidualTimes",allHideResidualTimes);
                modelMap.put("taskHideResidualTimes",taskHideResidualTimes);
                modelMap.put("taskUrgentResidualTimes",taskUrgentResidualTimes);
                modelMap.put("taskTopResidualTimes",taskTopResidualTimes);
                Integer vipType = user.getVipType();
                // 通过memberId查询道具对象
                PropTaskTopUser propTaskTopUser = myPropsService.selectMoneyAndIntegralUserdTop();
                PropTaskUrgentUser propTaskUrgentUser = myPropsService.selectMoneyAndIntegralUserdUrgent();
                PropTaskHideUser propTaskHideUser = myPropsService.selectMoneyAndIntegralUserdHide();
                PropAllHideUser propAllHideUser = myPropsService.selectMoneyAndIntegralUserdAllHide();
                modelMap.put("propTaskTopUser", propTaskTopUser);
                modelMap.put("propTaskUrgentUser", propTaskUrgentUser);
                modelMap.put("propTaskHideUser", propTaskHideUser);
                modelMap.put("propAllHideUser", propAllHideUser);

                // 后期考虑注册后加入默认值，防止查询数据为空后报错，后期处理，目前先将大概功能实现。
                // TODO
            }
        }
        return "MyProps";
    }
}
