package com.jiaoyiyu.back.homepage.controller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.Assignment;
import com.jiaoyiyu.back.bean.Settlement;
import com.jiaoyiyu.back.bean.TbHotHomepageData;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.vo.HotData;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.HotDataService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.back.coreservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class IndexController {
    @Autowired
    UserService userService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    HotDataService hotDataService;

    @Autowired
    PublishTaskService publishTaskService;
    @LoginRequired(loginSuccess = false)
    @RequestMapping("/")
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
}
