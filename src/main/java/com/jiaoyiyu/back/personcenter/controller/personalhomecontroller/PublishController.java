package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.Assignment;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.vo.PersonPublishTaskVO;
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
import java.util.List;
import java.util.Map;

/**
 * PublishController class
 *
 * @author maochaoying
 * @date 2019/11/11
 */
@Controller
@RequestMapping("/publishperson")
@CrossOrigin
public class PublishController {

    @Autowired
    PublishTaskService publishTaskService;

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index(HttpServletRequest request, ModelMap modelMap) {
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(oldToken, key, salt);
        modelMap.put("token", oldToken);
        String pic = HttpclientUtil.doGet("/index/getPic?token=" + oldToken);
        modelMap.put("uploadImg", pic);
        Integer memberId = null;
        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            if (nickname != null) {
                modelMap.put("nicknames", nickname);
            }else {
                modelMap.put("nickname", phonenum);
            }
            memberId = (Integer) decode.get("memberId");
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            }else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                memberId = user.getId();
            }

            // 通过memberId查询该用户发布的任务
            List<Assignment> assignmentList = publishTaskService.getAssignmentInfoByMemberId(memberId);

            List<PersonPublishTaskVO> personPublishTaskVOList = publishTaskService.transAssignmentToPersonPublishTaskVO(assignmentList);

            modelMap.put("personPublishTaskVOList",personPublishTaskVOList);
        }

        return "publish";
    }
}
