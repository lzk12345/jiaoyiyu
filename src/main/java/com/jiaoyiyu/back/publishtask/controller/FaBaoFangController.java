package com.jiaoyiyu.back.publishtask.controller;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.MyPropsService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

/**
 * FaBaoFangController class
 *
 * @author maochaoying
 * @date 2019/11/28
 */

@Controller
@CrossOrigin
@RequestMapping("/fabao")
public class FaBaoFangController {

    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    CatalogService catalogService;
    @Autowired
    MyPropsService myPropsService;
    /**
     * 支付成功后回调
     *
     * @return
     */
    @RequestMapping("/taskPage")
    public String taskPage(HttpServletRequest request, ModelMap modelMap) throws ParseException {
        PropTaskTopUser propTaskTopUser = myPropsService.selectMoneyAndIntegralUserdTop();
        PropTaskUrgentUser propTaskUrgentUser = myPropsService.selectMoneyAndIntegralUserdUrgent();
        PropAllHideUser propAllHideUser = myPropsService.selectMoneyAndIntegralUserdAllHide();
        modelMap.put("propTaskTopUser", propTaskTopUser);
        modelMap.put("propTaskUrgentUser", propTaskUrgentUser);
        modelMap.put("propAllHideUser", propAllHideUser);
        // 重点： 每次查询到的数据中都要放置隐藏的assignmentid
        // 而后点击详情将assignmentId存入cookie  该方法统一使用Cookie中的数据
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        publishTaskService.AddBrowsePeopleNumAssId(assignmentId);
        // 使用token中的数据
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);

        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(oldToken, key, salt);
        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            if (nickname != null) {
                modelMap.put("nickname", "尊敬的" + nickname + ",欢迎您回来！");
            } else {
                modelMap.put("nickname", "尊敬的" + phonenum + ",欢迎您回来！");
            }
            Integer memberId = (Integer) decode.get("memberId");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
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
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }

            // 展示assignment的信息
            Assignment assignment = publishTaskService.getAssignmentInfoById(assignmentId);
            if (assignment != null) {
                // 获取标题
                String title = assignment.getTitle();
                // 获取总需求
                String details = assignment.getDetails();
                // 获取二级分类id
                Integer category2id = assignment.getCategoryid();
                //通过category2id查询catalog2表获取名称
                Catalog2 catalog2 = catalogService.getCatalog2ById(category2id);
                Integer catalog1Id = catalog2.getCatalog1Id();
                String catalog2Name = catalog2.getName();
                //通过category1id查询catalog1表获取名称
                Catalog1 catalog1 = catalogService.getCatalog1ById(catalog1Id);
                String catalog1Name = catalog1.getName();
                // 存放进modelmap以便前台获取
                modelMap.put("cata1", catalog1Name);
                modelMap.put("cata2", catalog2Name);
                modelMap.put("title", title);
                modelMap.put("details", details);
                // 道具放在前台判断 用来展示图标
                Byte allhidden = assignment.getAllhidden();
                Byte top = assignment.getTop();
                Byte urgent = assignment.getUrgent();
                modelMap.put("top", top);
                modelMap.put("allhidden", allhidden);
                modelMap.put("urgent", urgent);


                // 拿到结束时间
                String endtime = assignment.getEndtime().substring(0,10);
                modelMap.put("endtime",endtime);

                // 拿到remark以及remarkstatus
                String remark = assignment.getRemark();
                Integer remarkStatus = assignment.getRemarkStatus();
                if (remarkStatus != null) {
                    if (remarkStatus == 1) {
                        // 说明已经备注留言过了
                        modelMap.put("remark",remark);
                    }else {
                        // 还没有备注
                        modelMap.put("remark", 0);
                    }
                }else {
                    // 还没有备注
                    modelMap.put("remark", 0);
                }


                // 后台任务类型一共有五种这里使用0 1 2 3 4
                // taskTypeVal 来代表
                // 0: 投标模式
                // 1: 测试投标
                // 2: 单人模式
                // 3: 多人模式
                // 4: 稿件模式
                Integer tasktype = assignment.getTasktype();
                if (tasktype == 0) {
                    //说明是投标
                    // 看是区间投标还是准确价格投标
                    Integer toubiaoType = assignment.getToubiaoType();
                    if (toubiaoType != null) {
                        if (toubiaoType == 0) {
                            // 准确  拿到投标金额
                            BigDecimal toubiaoPrice = assignment.getToubiaoPrice();
                            modelMap.put("toubiaoPrice",toubiaoPrice);
                        }
                        if (toubiaoType == 1) {
                            // 区间
                            Integer minMoney = assignment.getMinMoney();
                            Integer maxMoney = assignment.getMaxMoney();
                            modelMap.put("toubiaoPrice",minMoney + "-" + maxMoney);
                        }
                    }

                    Byte testStatus = assignment.getTestStatus();
                    if (testStatus == 0) {
                        // 没有
                        modelMap.put("taskTypeVal", 0);
                    }
                    if (testStatus == 1) {
                        // 有
                        modelMap.put("taskTypeVal", 1);
                        // 获得测试题对象
                        TestQuestion testQuestion = publishTaskService.getTestQuestionByAssignmentId(assignmentId);
                        // 测试金额
                        BigDecimal moneyNum = testQuestion.getMoneyNum();
                        modelMap.put("ceshiMoneyNum",moneyNum);
                        String ceshiTitle = testQuestion.getTitle();
                        String ceshiDescribeDetails = testQuestion.getDescribeDetails();
                        modelMap.put("ceshiTitle", ceshiTitle);
                        modelMap.put("ceshiDescribeDetails", ceshiDescribeDetails);
                    }
                }
                if (tasktype == 1){
                    // 说明是悬赏模式
                    Integer xuanshangType = assignment.getXuanshangType();
                    BigDecimal xuanshangPrice = assignment.getXuanshangPrice();
                    modelMap.put("xuanshangPrice",xuanshangPrice);
                    if (xuanshangType == 0) {
                        // 单人
                        modelMap.put("taskTypeVal", 2);
                    }
                    if (xuanshangType == 1) {
                        // 多人
                        modelMap.put("taskTypeVal", 3);
                        // 通过assignmentId 来获取RewardMore对象  下面的数据均在该表中存储
                        RewardMore rewardMore = publishTaskService.getRewareMoreByAssignmentId(assignmentId);
                        Integer peopleNum = rewardMore.getPeopleNum();
                        BigDecimal firstPrize = rewardMore.getFirstPrize();
                        BigDecimal secondPrize = rewardMore.getSecondPrize();
                        Integer secondPrizeNum = rewardMore.getSecondPrizeNum();
                        BigDecimal thirdPrize = rewardMore.getThirdPrize();
                        Integer thirdPrizeNum = rewardMore.getThirdPrizeNum();
                        modelMap.put("peopleNum", peopleNum);
                        modelMap.put("firstPrize", firstPrize);
                        modelMap.put("secondPrize", secondPrize);
                        modelMap.put("secondPrizeNum", secondPrizeNum);
                        modelMap.put("thirdPrize", thirdPrize);
                        modelMap.put("thirdPrizeNum", thirdPrizeNum);
                    }
                    if (xuanshangType == 2) {
                        // 稿件
                        modelMap.put("taskTypeVal", 4);
                    }
                }
                // 拿到稿件发布时间
                String pubtime = assignment.getPubtime().substring(0, 10);
                modelMap.put("pubtime",pubtime);
                // 拿到任务进度
                Integer scheduleStatus = assignment.getScheduleStatus();
                modelMap.put("scheduleStatus",scheduleStatus);
            }
        }
        return "faBaoFangTaskPage";
    }


}


