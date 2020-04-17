package com.jiaoyiyu.back.taskhall.controller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.es.AssignmentES;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.back.coreservice.TaskHallService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
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
 * TaskHallController class
 *
 * @author maochaoying
 * @date 2019/12/2
 */
@Controller
@RequestMapping("/taskhall")
@CrossOrigin
public class TaskHallController {

    @Autowired
    UserService userService;
    @Autowired
    PublishTaskService publishTaskService;

    @Autowired
    CatalogService catalogService;
    @Autowired
    TaskHallService taskHallService;


    @RequestMapping("/index")
    public String index(ModelMap modelMap, HttpServletRequest request, AssignSearchParam assignSearchParam, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                        @RequestParam(value = "recordNum", defaultValue = "10") Integer recordNum) {
        List<AssignmentES> lists = null;
        try{
            lists = taskHallService.getAssignmentFromES(assignSearchParam, currentPage, recordNum);
        }catch (Exception e) {
            lists = taskHallService.getAssignmentFromES(assignSearchParam, currentPage, recordNum);
            lists = taskHallService.getAssignmentFromES(assignSearchParam, currentPage, recordNum);
        }

        Long total = taskHallService.getAssignmentFromESTotal(assignSearchParam);
        modelMap.put("queryTotal", total);
        modelMap.put("currentPage", currentPage);
        List<Catalog1> catalog1 = catalogService.getCatalog1();
        List<TbTaskModeType> taskModeType = catalogService.getTaskModeType();
        List<TbTaskTimeType> taskTimeType = catalogService.getTaskTimeType();
        List<SearchCromb> searchCrombs = taskHallService.getSearchCrombs(assignSearchParam, catalog1, taskModeType, taskTimeType);
        modelMap.put("taskTimeType", taskTimeType);
        modelMap.put("catalog1",catalog1 );
        modelMap.put("taskModeType", taskModeType);
        // 得到urlParam
        String urlParam = taskHallService.getUrlParam(assignSearchParam);
        modelMap.put("urlParam", urlParam);
        // 下午要做的展示
        modelMap.put("assignmentESList",lists);
        // 面包屑
        modelMap.put("attrValueSelectedList", searchCrombs);

        //从cookie中拿到member的信息  然后加入界面的渲染中
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        if (StringUtils.isNotBlank(oldToken)) {
            decode = JwtUtil.decode(oldToken, key, salt);
        }

        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            if (nickname != null) {
                modelMap.put("nickname", "尊敬的" + nickname + ",欢迎您回来！");
            } else {
                modelMap.put("nickname", "尊敬的" + phonenum + ",欢迎您回来！");
            }
            Integer memberId = (Integer) decode.get("memberId");
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到会员类型
                Integer vipType = user.getVipType();
                if (vipType != null) {
                    int lev = 1;
                    if (user.getUserLevel() != null) {
                        lev = user.getUserLevel();
                    }
                    if (vipType == 0) {
                        modelMap.put("viptypeandlevel", "当前为普通用户，lv" + lev);
                        modelMap.put("isvip", "0");
                    }
                    if (vipType == 1) {
                        modelMap.put("viptypeandlevel", "当前为普通会员，lv" + lev);
                        modelMap.put("isvip", "1");
                    }
                    if (vipType == 2) {
                        modelMap.put("viptypeandlevel", "当前为超级会员，lv" + lev);
                        modelMap.put("isvip", "1");
                    }
                } else {
                    modelMap.put("viptypeandlevel", "当前为普通用户，lv" + 1);
                    //存入数据库
                    //TODO
                }
            }
        }
        return "taskHall";
    }




    /**
     * 获得登录后的昵称
     * @param token
     * @param request
     * @return
     */
    @RequestMapping("/getNickname")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String getNickname(@RequestParam("token") String token, HttpServletRequest request) {
        // 如果url中的token为空，从cookie中获取
        if (token == null || token.equals("")) {
            token = CookieUtils.getCookieValue(request, "oldToken", true);
        }
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        if (token != null) {
            Map<String, Object> decode = JwtUtil.decode(token, key, salt);
            if (decode == null) {
                return "failed";
            } else {
                String nickname = (String) decode.get("nickname");
                if (nickname != null) {
                    return nickname;
                } else {
                    String phonenum = (String) decode.get("phonenum");
                    return phonenum;
                }
            }
        }
        //如果没有 返回默认昵称
        return "昵称";
    }

    /**
     * 获取登录后的头像地址
     * @param token
     * @param request
     * @return
     */
    @RequestMapping("/getPic")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String getPic(@RequestParam("token") String token, HttpServletRequest request) {
        // 如果url中的token为空，从cookie中获取
        if (token == null || token.equals("")) {
            token = CookieUtils.getCookieValue(request, "oldToken", true);
        }
        if (token != null) {
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            Map<String, Object> decode = JwtUtil.decode(token, key, salt);
            String headPic = "";
            if (decode == null) {
                return "failed";
            } else {
                Integer memberId = (Integer) decode.get("memberId");
                String phonenum = (String) decode.get("phonenum");
                // memberId不为空 则通过memberId来获取头像
                if (memberId != null) {
                    // 通过memberId查pic
                    User user2 = userService.getUserInfoByMemberId(memberId);
                    if (user2 != null) {
                        headPic = user2.getHeadPic();
                        return headPic;
                    }
                }
                //如果memberID为空，而手机号不为空，则通过手机号来获取
                if (phonenum != null) {
                    User user3 = userService.getUserInfoByPhoneNum(phonenum);
                    if (user3 != null) {
                        headPic = user3.getHeadPic();
                        if (headPic != null) {
                            return headPic;
                        }
                    }
                }
            }
        }
        // 返回默认头像
        return "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2824750375,3556518153&fm=26&gp=0.jpg";
    }

}
