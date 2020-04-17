package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.MyIntegral;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.vo.ScoreGroupVO;
import com.jiaoyiyu.back.coreservice.MyIntegralService;
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

/**
 * MyIntegralController class
 * 我的积分页面
 * @author maochaoying
 * @date 2019/11/11
 */
@Controller
@RequestMapping("/myIntegral")
@CrossOrigin
public class MyIntegralController {

    @Autowired
    UserService userService;
    @Autowired
    MyIntegralService myIntegralService;
    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index(HttpServletRequest request, ModelMap modelMap) {
        String token = CookieUtils.getCookieValue(request, "oldToken", true);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        MyIntegral myIntegral = null;
        if (decode != null) {
            Integer memberId = (Integer) decode.get("memberId");
            if (memberId != null) {
                // 根据menmberId查询积分信息
                User userInfoByMemberId = userService.getUserInfoByMemberId(memberId);
                Integer points = userInfoByMemberId.getPoints();
                modelMap.put("points", points);
                List<ScoreGroupVO> scoreGroupVOs = myIntegralService.selectScoreInfoByMemberId(memberId);
                if (scoreGroupVOs != null) {
                    modelMap.put("scoreGroupVOs", scoreGroupVOs);
                }
                //TODO
//                前台展示
            }else {
                String phonenum = (String) decode.get("phonenum");
                if (phonenum != null) {
                    //根据手机来查询memberId  而后查询信息。
                    User userInfoByPhoneNum = userService.getUserInfoByPhoneNum(phonenum);
                }
            }

        }
        return "myIntegral";
    }
    
    
    private String checkToken(String token) {
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        if (decode == null) {
            return "failed";
        } else {
            Integer memberId = (Integer) decode.get("memberId");
            if (memberId != null) {
                return memberId + "";
            }else {
                String phonenum = (String) decode.get("phonenum");
                if (phonenum != null) {
                    return phonenum;
                }
            }
            
        }
        return "success";
    }

}
