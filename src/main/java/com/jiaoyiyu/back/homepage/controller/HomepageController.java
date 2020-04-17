package com.jiaoyiyu.back.homepage.controller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.vo.HotData;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.HotDataService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
@RequestMapping("/index")
@Controller
@CrossOrigin
public class HomepageController {

    @Autowired
    UserService userService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    HotDataService hotDataService;

    @Autowired
    PublishTaskService publishTaskService;
    /**
     * 跳转到主页
     * @param modelMap
     * @return
     */
    @LoginRequired(loginSuccess = false)
    @RequestMapping("")
    public String index(ModelMap modelMap) {
        List<HotData> hotDataList = new ArrayList<>();
        // 查询最受欢迎的设计师
        List<User> userList =  userService.getFavorableUserInfoToHomePageLimitSix();
        // 查询热点数据
        List<TbHotHomepageData> hotHomepageDataList = hotDataService.getHotData();
        if (hotHomepageDataList != null) {
            for (TbHotHomepageData tbHotHomepageData : hotHomepageDataList) {
                Integer assignmentId = tbHotHomepageData.getAssignmentId();
                Assignment assignment = publishTaskService.getAssignmentInfoById(assignmentId + "");
                HotData hotData = new HotData();

                hotData.setId(tbHotHomepageData.getId());
                hotData.setAssignmentId(assignmentId);
                hotData.setImgUrl(tbHotHomepageData.getImgUrl());
                hotData.setStatus(tbHotHomepageData.getStatus());
                hotData.setTitle(assignment.getTitle());
                hotData.setDetails(assignment.getDetails());

                if (assignment.getTasktype() != null) {
                    if (assignment.getTasktype() == 0) {
                        //投标
                        if (assignment.getTestStatus() != null) {
                            if (assignment.getTestStatus() == 0) {
                                // 没有测试题
                                hotData.setTaskModeTypeId(1);
                            }
                            if (assignment.getTestStatus() == 1) {
                                // 有测试题
                                hotData.setTaskModeTypeId(2);
                            }
                        }
                        //带测试题
                    }
                    if (assignment.getTasktype() == 1) {
                        if (assignment.getXuanshangType() != null) {
                            //悬赏
                            if (assignment.getXuanshangType() == 0) {
                                //单人悬赏
                                hotData.setTaskModeTypeId(3);
                            }
                            if (assignment.getXuanshangType() == 1) {
                                //多人悬赏
                                hotData.setTaskModeTypeId(4);
                            }
                            if (assignment.getXuanshangType() == 2) {
                                //计件悬赏
                                hotData.setTaskModeTypeId(5);
                            }
                        }
                    }
                }
                Settlement settlementInfoByAssId = publishTaskService.getSettlementInfoByAssId(assignmentId + "");
                if (settlementInfoByAssId != null) {
                    hotData.setTotalMoney(settlementInfoByAssId.getTotalAmount());
                }
                hotDataList.add(hotData);
            }
        }
        modelMap.put("hotDataList",hotDataList);
        modelMap.put("userList",userList);
        return "index";
    }

    /**
     * 验证手机登录的token
     * @param token
     * @return
     */
    @RequestMapping("/checkToken")
    @ResponseBody
    public String checkToken(@RequestParam("token") String token) {
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        if (decode == null) {
            return "failed";
        } else {
            String phonenum = (String) decode.get("phonenum");
            if (phonenum != null) {
                return phonenum;
            }
        }
        return "success";
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
     * 注销功能实现
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/loginOut")
    @ResponseBody
    public String loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //删除cookie中的值
        CookieUtils.deleteCookie(request, response, "oldToken");
        //返回登录界面
        return "/passport/index";
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

                    /**********************************三级分类请求*********************************************/
    /**
     * 一级分类
     *
     * @return
     */
    @RequestMapping("/getCatalog1")
    @ResponseBody
    public List<Catalog1> getCatalog1() {
        return catalogService.getCatalog1();
    }

    /**
     * 二级分类
     *
     * @return
     */
    @RequestMapping("/getCatalog2")
    @ResponseBody
    public List<Catalog2> getCatalog2(@RequestParam("catalog1Id") Integer catalog1Id) {
        return catalogService.getCatalog2(catalog1Id);
    }

    /**
     * 三级分类
     *
     * @return
     */
    @RequestMapping("/getCatalog3")
    @ResponseBody
    public List<Catalog3> getCatalog3(Integer catalog2Id) {
        return catalogService.getCatalog3(catalog2Id);
    }

    /**
     * 拿到所有的任务时间类型
     * @return
     */
    @RequestMapping("/getTaskTimeType")
    @ResponseBody
    public List<TbTaskTimeType> getTaskTimeType() {
        return catalogService.getTaskTimeType();
    }

    /**
     * 拿到所有的任务模式类型
     * @return
     */
    @RequestMapping("/getTaskModeType")
    @ResponseBody
    public List<TbTaskModeType> getTaskModeType() {
        return catalogService.getTaskModeType();
    }

}
