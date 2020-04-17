package com.jiaoyiyu.back.publishtask.controller;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.vo.TaskDetailsAndUserInfo;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.MyPropsService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.HttpclientUtil;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * StyListController class
 *
 * @author maochaoying
 * @date 2019/11/29
 */
@Controller
@CrossOrigin
@RequestMapping("/shejishi")
public class SheJiShiController {

    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    CatalogService catalogService;
    @Autowired
    MyPropsService myPropsService;

    @RequestMapping("/index")
    public String taskPage(@RequestParam(value = "assignmentId", defaultValue = "", required = false) String assignmentId, HttpServletRequest request, ModelMap modelMap) {
        if (StringUtils.isBlank(assignmentId)) {
            assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        }
        // 使用token中的数据
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        PropTaskTopUser propTaskTopUser = myPropsService.selectMoneyAndIntegralUserdTop();
        PropTaskUrgentUser propTaskUrgentUser = myPropsService.selectMoneyAndIntegralUserdUrgent();
        PropAllHideUser propAllHideUser = myPropsService.selectMoneyAndIntegralUserdAllHide();
        PropTaskHideUser propTaskHideUser = myPropsService.selectMoneyAndIntegralUserdHide();
        modelMap.put("propTaskTopUser", propTaskTopUser);
        modelMap.put("propTaskUrgentUser", propTaskUrgentUser);
        modelMap.put("propAllHideUser", propAllHideUser);
        modelMap.put("propTaskHideUser", propTaskHideUser);
        // 重点： 每次查询到的数据中都要放置隐藏的assignmentid
        // 而后点击详情将assignmentId存入cookie  该方法统一使用Cookie中的数据
        publishTaskService.AddBrowsePeopleNumAssId(assignmentId);

        Integer memberId = null;
        if (decode != null) {
            String nickname = (String) decode.get("nickname");
            String phonenum = (String) decode.get("phonenum");
            if (nickname != null) {
                modelMap.put("nickname", "尊敬的" + nickname + ",欢迎您回来！");
            } else {
                modelMap.put("nickname", "尊敬的" + phonenum + ",欢迎您回来！");
            }
            memberId = (Integer) decode.get("memberId");
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
            Date lastLoginTime = user.getLastLoginTime();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            if (lastLoginTime == null) {
                lastLoginTime = new Date();
            }
            String format = ft.format(lastLoginTime);
            modelMap.put("lastLoginTime", format);
            if (vipType != null) {
                int lev = 1;
                if (user.getUserLevel() != null) {
                    lev = user.getUserLevel();
                    modelMap.put("lev", lev);
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
                //从数据库中查询该assignmentId所对应的uid

                Integer isOpenStore = user.getIsOpenStore();
                Integer points = user.getPoints();
                modelMap.put("isOpenStore", isOpenStore);
                modelMap.put("points", points);
                String nickName1 = user.getNickName();
                modelMap.put("nickname", nickName1);
                Integer favorableRate = user.getFavorableRate();
                modelMap.put("favorableRate", favorableRate);
                Integer taskHideResidualTimes = user.getTaskHideResidualTimes();

                if (taskHideResidualTimes == 0 || taskHideResidualTimes == null) {
                    // 说明没有全部隐藏道具、
                    modelMap.put("taskHideResidualTimes", "0");
                } else {
                    // 有全部隐藏道具，向前台传道具剩余次数
                    modelMap.put("taskHideResidualTimes", taskHideResidualTimes);
                }
            }

        }
        // 展示assignment的信息
        Assignment assignment = publishTaskService.getAssignmentInfoById(assignmentId);
        Integer gaojianNum = assignment.getGaojianNum();
        Integer gaojianStatus = assignment.getGaojianStatus();
        modelMap.put("gaojianStatus", gaojianStatus);
        modelMap.put("gaojianNum", gaojianNum);
        String submitSourceDate = assignment.getSubmitSourceDate();
        String xuangaoDate = assignment.getXuangaoDate();
        if (xuangaoDate != null) {
            modelMap.put("xuangaoDate", xuangaoDate.substring(0, 10));
        } else {
            modelMap.put("xuangaoDate", null);
        }
        if (submitSourceDate != null) {
            modelMap.put("submitSourceDate", submitSourceDate.substring(0, 10));
        } else {
            modelMap.put("submitSourceDate", null);
        }
        // 根据assignmentId查询reward more表


        int currentGaoJianNum = publishTaskService.getTaskDetailsCountByAssignmentId(assignmentId);
        modelMap.put("currentGaoJianNum", currentGaoJianNum);
        String pic = HttpclientUtil.doGet("/index/getPic?token=" + oldToken);
        modelMap.put("imgself", pic);
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
            String endtime = assignment.getEndtime().substring(0, 10);
            modelMap.put("endtime", endtime);

            // 拿到remark以及remarkstatus
            String remark = assignment.getRemark();
            Integer remarkStatus = assignment.getRemarkStatus();
            if (remarkStatus != null) {
                if (remarkStatus == 1) {
                    // 说明已经备注留言过了
                    modelMap.put("remark", remark);
                } else {
                    // 还没有备注
                    modelMap.put("remark", 0);
                }
            } else {
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
                        modelMap.put("toubiaoPrice", toubiaoPrice);
                    }
                    if (toubiaoType == 1) {
                        // 区间
                        Integer minMoney = assignment.getMinMoney();
                        Integer maxMoney = assignment.getMaxMoney();
                        modelMap.put("toubiaoPrice", minMoney + "-" + maxMoney);
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
                    if (testQuestion != null) {
                        BigDecimal moneyNum = testQuestion.getMoneyNum();
                        modelMap.put("ceshiMoneyNum", moneyNum);
                        String ceshiTitle = testQuestion.getTitle();
                        String ceshiDescribeDetails = testQuestion.getDescribeDetails();
                        modelMap.put("ceshiTitle", ceshiTitle);
                        modelMap.put("ceshiDescribeDetails", ceshiDescribeDetails);
                    }
                }
            }
            if (tasktype == 1) {
                // 说明是悬赏模式
                Integer xuanshangType = assignment.getXuanshangType();
                BigDecimal xuanshangPrice = assignment.getXuanshangPrice();
                modelMap.put("xuanshangPrice", xuanshangPrice);
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
                    Integer firstStatus = rewardMore.getFirstStatus();
                    Integer secondStatus = rewardMore.getSecondStatus();
                    Integer thirdStatus = rewardMore.getThirdStatus();
                    modelMap.put("firstStatus", firstStatus);
                    modelMap.put("secondStatus", secondStatus);
                    modelMap.put("thirdStatus", thirdStatus);

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
            modelMap.put("pubtime", pubtime);
            // 拿到任务进度
            Integer scheduleStatus = assignment.getScheduleStatus();
            modelMap.put("scheduleStatus", scheduleStatus + 1);
        }
        // 根据assignmentId查询join task detail 表
        List<TbJoinTaskDetails> tbJoinTaskDetailsList = publishTaskService.getJoinTaskDetailByAssignment(assignmentId);
        if (tbJoinTaskDetailsList != null) {
            List<TaskDetailsAndUserInfo> taskDetailsAndUserInfoList = publishTaskService.transTaskDetailsToVO(tbJoinTaskDetailsList, memberId);
            modelMap.put("tbJoinTaskDetailsList", taskDetailsAndUserInfoList);
        }

        // 根据assignmentId和uid查询taskdetailbiao   看数据中日期字段是否为空 如果为空则说明可以发布任务  如果不为空 则将按钮设置为disable
        if (memberId != null) {
            TbJoinTaskDetails tbJoinTaskDetails = publishTaskService.getJoinTaskDetailByAssignmentAndUId(assignmentId, memberId);
            if (tbJoinTaskDetails != null) {
                String uploadTime = tbJoinTaskDetails.getUploadTime();
                if (StringUtils.isNotBlank(uploadTime)) {
                    modelMap.put("is_upload", "1");
                } else {
                    modelMap.put("is_upload", "0");
                }
            }
        } else {
            modelMap.put("is_upload", "2");
        }

        // 判断是否是自己发布的任务
        int flag = publishTaskService.doIsSelf(assignmentId, memberId);
        if (flag == 1) {
            return "faBaoFangTaskPage";
        }
        return "stylist";
    }


    @RequestMapping("/getManuScriptId")
    @ResponseBody
    public int getManuScriptId(@RequestParam("assignmentId") String assignmentId, HttpServletRequest request) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        int manuScriptId = publishTaskService.getManuScriptId(assignmentId, memberId);
        return manuScriptId;
    }


    @RequestMapping("/saveTouBiaoModeDetails")
    @ResponseBody
    public int saveTouBiaoModeDetails(HttpServletRequest request, @RequestParam("assignmentId") Integer assignmentId,
                                      @RequestParam("workPeriod") Integer workPeriod,
                                      @RequestParam("projectQuotation") BigDecimal projectQuotation,
                                      @RequestParam("editor2html") String editor2html,
                                      @RequestParam("checkStatus") Integer checkStatus,
                                      @RequestParam("ensuremoneyinput") BigDecimal ensuremoneyinput) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.saveTouBiaoModeDetails(manuscriptId, memberId, assignmentId, workPeriod, projectQuotation, editor2html, checkStatus, ensuremoneyinput);
    }

    @RequestMapping("/saveCeShiTouBiaoModeDetails")
    @ResponseBody
    public int saveCeShiTouBiaoModeDetails(HttpServletRequest request, @RequestParam("assignmentId") Integer assignmentId,
                                           @RequestParam("workPeriod") Integer workPeriod,
                                           @RequestParam("projectQuotation") BigDecimal projectQuotation,
                                           @RequestParam("editor2html") String editor2html,
                                           @RequestParam("checkStatus") Integer checkStatus,
                                           @RequestParam("ensuremoneyinput") BigDecimal ensuremoneyinput, @RequestParam("editor3html") String editor3html) {

        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.saveCeShiTouBiaoModeDetails(manuscriptId, memberId, assignmentId, workPeriod, projectQuotation, editor2html, checkStatus, ensuremoneyinput, editor3html);
    }


    @RequestMapping("/saveSingleXuanShangModeDetails")
    @ResponseBody
    public int saveSingleXuanShangModeDetails(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId, @RequestParam("editor4html") String editor4html, @RequestParam("checkStatus") Integer checkStatus) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.saveSingleXuanShangModeDetails(manuscriptId, memberId, assignmentId, checkStatus, editor4html);
    }

    @RequestMapping("/saveMoreXuanShangModeDetails")
    @ResponseBody
    public int saveMoreXuanShangModeDetails(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId, @RequestParam("editor4html") String editor4html, @RequestParam("checkStatus") Integer checkStatus) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.saveSingleXuanShangModeDetails2(manuscriptId, memberId, assignmentId, checkStatus, editor4html);
    }

    @RequestMapping("/saveGaoJianXuanShangModeDetails")
    @ResponseBody
    public int saveGaoJianXuanShangModeDetails(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId, @RequestParam("checkStatus") Integer checkStatus) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.saveSingleXuanShangModeDetails1(manuscriptId, memberId, assignmentId, checkStatus);
    }


    @RequestMapping("/updateBianHao")
    @ResponseBody
    public int updateBianHao(@RequestParam("bianhao") Integer bianhao) {
        return publishTaskService.updateBianHao(bianhao);
    }

    @RequestMapping("/clickcollectToDB")
    @ResponseBody
    public int clickcollectToDB(@RequestParam("assignmentId") Integer assignmentId, HttpServletRequest request) {

        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }

        return publishTaskService.clickcollectToDB(assignmentId,memberId);
    }


    @RequestMapping("/canclecollectToDB")
    @ResponseBody
    public int canclecollectToDB(@RequestParam("assignmentId") Integer assignmentId, HttpServletRequest request) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        return publishTaskService.canclecollectToDB(assignmentId,memberId);
    }


    @RequestMapping("/getTaskCollect")
    @ResponseBody
    public int getTaskCollect(@RequestParam("assignmentId") Integer assignmentId, HttpServletRequest request) {
        Map<String, Object> decode = null;
        String oldToken = CookieUtils.getCookieValue(request, "oldToken", true);
        if (StringUtils.isNotBlank(oldToken)) {
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            decode = JwtUtil.decode(oldToken, key, salt);
        }
        Integer memberId = null;
        if (decode != null) {
            memberId = (Integer) decode.get("memberId");
            String phonenum = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        return publishTaskService.getTaskCollect(assignmentId,memberId);
    }

}