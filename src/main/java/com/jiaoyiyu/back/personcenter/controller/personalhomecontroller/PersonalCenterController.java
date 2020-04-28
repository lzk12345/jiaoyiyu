package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.User;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * PersonalCenterController class
 * 个人中心首页
 * @author maochaoying
 * @date 2019/11/9
 */

@Controller
@CrossOrigin
@RequestMapping("/personal")
public class PersonalCenterController {

    @Autowired
    PublishTaskService publishTaskService;
    @RequestMapping("/index")
    @LoginRequired(loginSuccess = true)
    public String index(ModelMap modelMap, HttpServletRequest request) {
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(oldToken, key, salt);
        modelMap.put("token", oldToken);
        String pic = HttpclientUtil.doGet("http://121.36.85.218:9999/index/getPic?token=" + oldToken);
        modelMap.put("uploadImg", pic);

        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            if (nickname != null) {
                modelMap.put("nicknames", nickname);
            }else {
                modelMap.put("nickname", phonenum);
            }
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
                Integer vipType = user.getVipType();
                // 拿到积分
                Integer points = user.getPoints();
                if (points == null) {
                    points = 0;
                }
                // 拿到上次登录时间
                Date lastLoginTime = user.getLastLoginTime();
                if (lastLoginTime == null) {
                    lastLoginTime = new Date(System.currentTimeMillis());
                }
                modelMap.put("lastLoginTime", lastLoginTime);
                modelMap.put("points", points);
                int lev = 1;
                if (user.getUserLevel() != null) {
                    lev = user.getUserLevel();
                }
                modelMap.put("personlevel", "Lv" +lev);
                BigDecimal accountBalance = new BigDecimal(0.0);
                if (user.getAccountBalance() != null) {
                    accountBalance = user.getAccountBalance();
                    BigDecimal earnTheAmount = user.getEarnTheAmount();
                    modelMap.put("earnTheAmount",earnTheAmount);
                    modelMap.put("accountBalance", accountBalance);
                }
                Integer isEmailCheck = user.getIsEmailCheck();
                Integer isMobileCheck = user.getIsMobileCheck();
                Integer isPayCheck = user.getIsPayCheck();
                Integer isRealNameCheck = user.getIsRealNameCheck();
                modelMap.put("isEmailCheck",isEmailCheck);
                modelMap.put("isMobileCheck",isMobileCheck);
                modelMap.put("isPayCheck",isPayCheck);
                modelMap.put("isRealNameCheck",isRealNameCheck);

                Integer allHideResidualTimes = user.getAllHideResidualTimes();
                Integer taskHideResidualTimes = user.getTaskHideResidualTimes();
                Integer taskUrgentResidualTimes = user.getTaskUrgentResidualTimes();
                Integer taskTopResidualTimes = user.getTaskTopResidualTimes();
                modelMap.put("allHideResidualTimes",allHideResidualTimes);
                modelMap.put("taskHideResidualTimes",taskHideResidualTimes);
                modelMap.put("taskUrgentResidualTimes",taskUrgentResidualTimes);
                modelMap.put("taskTopResidualTimes",taskTopResidualTimes);
            }
        }
        return "PersonalCenter";
    }
}
