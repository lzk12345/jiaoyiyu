package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.bean.Catalog2;
import com.jiaoyiyu.back.bean.TbVisitor;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GroupupController class
 *
 * @author maochaoying
 * @date 2019/12/17
 */
@Controller
@RequestMapping("/growup")
@CrossOrigin
public class GroupupController {

    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap) throws ParseException {
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
        modelMap.put("user",user);
        List<Catalog2> list = userService.getTagsByUserId(user.getId());
        modelMap.put("tagList", list);
        SimpleDateFormat myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date lastLoginTime = user.getLastLoginTime();
        Date currentTime = new Date();
        long l = (currentTime.getTime() - lastLoginTime.getTime()) / (60 * 60 * 1000);
        modelMap.put("timeHour", l);
        List<TbVisitor> tbVisitorList = userService.getVisitors(user.getId());
        modelMap.put("tbVisitorList", tbVisitorList);
        //获得memberId
        return "growUp";
    }


    @RequestMapping("/visitor")
    public String toVisitor(HttpServletRequest request,ModelMap modelMap) throws ParseException {
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
        Integer visitorId = user.getId();
        // origin ID
        String uid = request.getParameter("uid");
        String stringDate = getStringDate();
        int res = userService.addVisitor(visitorId, uid, stringDate);
        if (StringUtils.isNotBlank(uid)) {
            User user1 = userService.getUserInfoByMemberId(Integer.parseInt(uid));
            modelMap.put("user",user1);
            List<Catalog2> list = userService.getTagsByUserId(user1.getId());
            modelMap.put("tagList", list);
            SimpleDateFormat myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            Date lastLoginTime = user.getLastLoginTime();
            Date currentTime = new Date();
            long l = (currentTime.getTime() - lastLoginTime.getTime()) / (60 * 60 * 1000);
            modelMap.put("timeHour", l);
            List<TbVisitor> tbVisitorList = userService.getVisitors(Integer.parseInt(uid));
            modelMap.put("tbVisitorList", tbVisitorList);
        }
        return "growUp";
    }

    public String getStringDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
