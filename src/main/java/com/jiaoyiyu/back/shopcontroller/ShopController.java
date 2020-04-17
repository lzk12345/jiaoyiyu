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
import java.util.Map;

@Controller
@RequestMapping("/shop")
@CrossOrigin
public class ShopController {
    @Autowired
    SellService sellService;
    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "oldToken", true);
        User user = new User();
        if (token != null) {
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            Map<String, Object> decode = JwtUtil.decode(token, key, salt);


            if (decode == null) {
                return null;
            } else {
                Integer memberId = (Integer) decode.get("memberId");
                String phonenum = (String) decode.get("phonenum");
                // memberId不为空 则通过memberId来获取头像
                if (memberId != null) {
                    // 通过memberId查pic
                    user = userService.getUserInfoByMemberId(memberId);
                }
                //如果memberID为空，而手机号不为空，则通过手机号来获取
                if (phonenum != null) {
                    user = userService.getUserInfoByPhoneNum(phonenum);
                }
            }
        }
        Integer memberId = user.getId();

        TbSellservice tbSellservices = sellService.getSellService(memberId,CookieUtils.getCookieValue(request,"serviceId",true));
        modelMap.put("sellService", tbSellservices);
        return "store";
    }
}
