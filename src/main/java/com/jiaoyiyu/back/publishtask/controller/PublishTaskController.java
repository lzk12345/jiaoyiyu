package com.jiaoyiyu.back.publishtask.controller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.es.AssignmentES;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.MyPropsService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.IdWorker;
import com.jiaoyiyu.util.ImageUploadUtil;
import com.jiaoyiyu.util.JwtUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * PublishTaskController class
 * 发布任务
 *
 * @author maochaoying
 * @date 2019/11/6
 */
@Controller
@RequestMapping("/publish")
@CrossOrigin
public class PublishTaskController {

    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    MyPropsService myPropsService;
    @Autowired
    IdWorker idWorker;
    @Autowired
    CatalogService catalogService;
    @Autowired
    JestClient jestClient;

    /**
     * 携带assignment返回页面 方便后期存储其他数据
     *
     * @param ineed
     * @param phonenum
     * @param request
     * @param modelMap
     * @param token
     * @return
     */
    @RequestMapping("/taskDetails")
    @ResponseBody
//    @LoginRequired(loginSuccess = true)
    public int toubiaoIndex(@RequestParam(value = "ineed",defaultValue = "",required = false) String ineed, @RequestParam(value = "phonenum",defaultValue = "",required = false) String phonenum,
                            HttpServletRequest request, ModelMap modelMap, @RequestParam("token") String token, HttpServletResponse response) {
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        // 任务的id
        int assignmentId = -1;
        if (token != null && !token.equals("")) {
            Map<String, Object> decode = JwtUtil.decode(token, key, salt);
            if (decode == null) {
                return -1;
            } else {
                Integer memberId = (Integer) decode.get("memberId");
                // 存入数据库
                // TODO
                if (memberId != null) {
                    assignmentId = publishTaskService.saveIneedAndPhoneNumToDB(ineed, phonenum, memberId);
                    if (assignmentId < 0) {
                        return -1;
                    }
                } else {
//                phonenum
                    assignmentId = publishTaskService.saveIneedAndPhoneNumToDBWithOutId(ineed, phonenum);
                    if (assignmentId < 0) {
                        return -1;
                    }
                }
            }
        }
        CookieUtils.setCookie(request, response, "assignmentId",assignmentId+"",60*60*3,true);
        return assignmentId;
    }

    /**
     * 发布任务首页
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    @LoginRequired(loginSuccess = true)
    public String index(HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) {
        PropTaskTopUser propTaskTopUser = myPropsService.selectMoneyAndIntegralUserdTop();
        PropTaskUrgentUser propTaskUrgentUser = myPropsService.selectMoneyAndIntegralUserdUrgent();
        PropAllHideUser propAllHideUser = myPropsService.selectMoneyAndIntegralUserdAllHide();
        modelMap.put("propTaskTopUser", propTaskTopUser);
        modelMap.put("propTaskUrgentUser", propTaskUrgentUser);
        modelMap.put("propAllHideUser", propAllHideUser);
        String token = request.getParameter("token");
        String assignmentId = request.getParameter("assignmentId");
        if (assignmentId == null || assignmentId == "") {
            long l = idWorker.nextId();
            String s = l + "";
            String substring = s.substring(0, 8);
            assignmentId = substring;
        }
        String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
        String key = "26sdasd2sds9848SDASD9484SADASDAS29";
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
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
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
                //查询道具

                Integer allHideResidualTimes = user.getAllHideResidualTimes();

                Integer taskTopResidualTimes = user.getTaskTopResidualTimes();

                Integer taskUrgentResidualTimes = user.getTaskUrgentResidualTimes();

                if (allHideResidualTimes == 0 || allHideResidualTimes == null) {
                    // 说明没有全部隐藏道具、
                    modelMap.put("allHideResidualTimes", "0");
                } else {
                    // 有全部隐藏道具，向前台传道具剩余次数
                    modelMap.put("allHideResidualTimes", allHideResidualTimes);
                }


                if (taskTopResidualTimes == null || taskTopResidualTimes == 0) {
                    // 没有置顶的剩余道具
                    modelMap.put("taskTopResidualTimes", "0");
                } else {
                    // 有置顶的剩余道具，向前台传道具剩余次数
                    modelMap.put("taskTopResidualTimes", taskTopResidualTimes);
                }


                if (taskUrgentResidualTimes == null || taskUrgentResidualTimes == 0) {
                    // 没有加急的剩余道具
                    modelMap.put("taskUrgentResidualTimes", "0");
                } else {
                    // 有加急的剩余道具，向前台传道具剩余次数
                    modelMap.put("taskUrgentResidualTimes", taskUrgentResidualTimes);
                }

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
            // 展示assignment的信息
            Assignment assignment = publishTaskService.getAssignmentInfoById(assignmentId);
            if (assignment != null) {
                String title = assignment.getTitle();
                String details = assignment.getDetails();
            }
        }
        return "taskDetails";
    }

    /**
     * 校验token
     *
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
        }
        return "success";
    }

    @RequestMapping(value = "/removeFile",method = RequestMethod.POST)
    @ResponseBody
    public int removeFile(HttpServletRequest request, @RequestParam("filename") String filename, @RequestParam(value = "assignmentId",required = false) String assignmentId) {
        if (StringUtils.isBlank(assignmentId)) {
            // 从cookie中获取assignmentId
            assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        }

        return  publishTaskService.removeFile(filename,assignmentId);
    }
    @RequestMapping(value = "/removeFile1",method = RequestMethod.POST)
    @ResponseBody
    public int removeFile1(HttpServletRequest request, @RequestParam("filename") String filename, @RequestParam(value = "assignmentId",required = false) String assignmentId) {
        if (StringUtils.isBlank(assignmentId)) {
            // 从cookie中获取assignmentId
            assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        }
        // 从cookie中获取testId；
        String testId = CookieUtils.getCookieValue(request, "testId", true);
        return  publishTaskService.removeFile1(filename,assignmentId,testId);
    }

    /**
     * 上传文件
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // 从cookie中获取assignmentId
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float)size/1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i1 + 1);
        int res = publishTaskService.saveIncludeFileUrl(url, originalFilename, extName, assignmentId, sizeBig);
        return url;
    }

    @RequestMapping(value = "/uploadFileBymanuscriptIdGaoJian/{sign}", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileBymanuscriptIdGaoJian(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, @PathVariable(name = "sign") Integer sign) throws IOException {
        // 从cookie中获取assignmentId
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float)size/1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i1 + 1);
        int res = publishTaskService.savemanuscriptIdUrlGaoJian(sign,url, originalFilename, extName, assignmentId, sizeBig,manuscriptId);
        return url;
    }


    @RequestMapping(value = "/upload111")
    @ResponseBody
    public String upload111(@RequestParam("img") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String url = ImageUploadUtil.imgUpload(file);
        System.out.println(url);
        return "上传成功";
    }

    @RequestMapping(value = "/uploadFileBymanuscriptId", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileBymanuscriptId(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // 从cookie中获取assignmentId
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float)size/1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i1 + 1);
        int res = publishTaskService.savemanuscriptIdUrl(url, originalFilename, extName, assignmentId, sizeBig,manuscriptId);
        return url;
    }

    @RequestMapping(value = "/uploadFileBymanuscriptIdss", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileBymanuscriptIdss(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // 从cookie中获取assignmentId
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float)size/1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i1 + 1);
        int res = publishTaskService.savemanuscriptIdUrlss(url, originalFilename, extName, assignmentId, sizeBig,manuscriptId);
        return url;
    }
    /**
     * 上传文件(测试题附件的上传)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/uploadFile1", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload1(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // 从cookie中获取assignmentId
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float)size/1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        // 从cookie中获取testId；
        String testId = CookieUtils.getCookieValue(request, "testId", true);
        String extName = originalFilename.substring(i1 + 1);
        int res = publishTaskService.saveIncludeFileUrl1(url, originalFilename, extName, assignmentId, testId, sizeBig);
        return url;
    }

    @RequestMapping("/removeFileBymanuscriptId")
    @ResponseBody
    public int removeFileBymanuscriptId(HttpServletRequest request, @RequestParam("filename") String filename) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.removeFileBymanuscriptId(filename,manuscriptId);
    }

    @RequestMapping("/removeFileBymanuscriptIdss")
    @ResponseBody
    public int removeFileBymanuscriptIdss(HttpServletRequest request, @RequestParam("filename") String filename) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.removeFileBymanuscriptIdss(filename,manuscriptId);
    }

    @RequestMapping("/removeFileBymanuscriptIdGaoJian/{sign}")
    @ResponseBody
    public int removeFileBymanuscriptIdGaoJian(HttpServletRequest request, @RequestParam("filename") String filename, @PathVariable(name = "sign") Integer sign) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.removeFileBymanuscriptIdGaoJian(sign,filename,manuscriptId);
    }

    /**
     * 保存发布页面第一个模态框的内容
     *
     * @param title    标题
     * @param details  需求详细描述
     * @param cata3val 该需求的分类Id
     * @return
     */
    @RequestMapping("/saveFirstPage")
    @ResponseBody
    @LoginRequired(loginSuccess = true)
    public String saveFirstPage(@RequestParam("title") String title, @RequestParam("details") String details, @RequestParam("cata3val") Integer cata3val, HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveFirstPageInfoByAssignmentId(title, details, cata3val, assignmentId);
        // 根据res来做判断
        // TODO
        return "success";
    }

    /**
     * 使用idworker生成id
     *
     * @return
     */
    @RequestMapping("/getIdByIdWorker")
    @ResponseBody
    public long getIdByIdWorker() {
        return idWorker.nextId();
    }

    /**
     * @param request
     * @param yusuanmoney
     * @param closingDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveTouBiaoWithoutCeshiMoney")
    public int saveTouBiaoWithoutCeshiMoney(HttpServletRequest request, @RequestParam("toubiaoPrice") BigDecimal yusuanmoney, @RequestParam("closingDate") String closingDate) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveTouBiaoWithoutCeshiMoney(yusuanmoney, closingDate, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param yusuanmoney
     * @param closingDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveTouBiaoWithCeshiMoney")
    public int saveTouBiaoWithCeshiMoney(HttpServletRequest request, @RequestParam("toubiaoPrice") BigDecimal yusuanmoney, @RequestParam("closingDate") String closingDate) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveTouBiaoWithCeshiMoney(yusuanmoney, closingDate, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param ceshititle
     * @param describe
     * @param moneynum
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCeShiTi")
    public int saveCeShiTi(HttpServletRequest request, @RequestParam("ceshititle") String ceshititle, @RequestParam("describe") String describe,
                           @RequestParam("moneynum") BigDecimal moneynum) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveCeShiTi(ceshititle, describe, moneynum, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param yusuanvalmin
     * @param yusuanvalmax
     * @param closingDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePriceRangeWithoutCeShiTi")
    public int savePriceRangeWithoutCeShiTi(HttpServletRequest request, @RequestParam("yusuanvalmin") Integer yusuanvalmin, @RequestParam("yusuanvalmax") Integer yusuanvalmax,
                                            @RequestParam("closingDate") String closingDate) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.savePriceRangeWithoutCeShiTi(yusuanvalmin, yusuanvalmax, closingDate, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param yusuanvalmin
     * @param yusuanvalmax
     * @param closingDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePriceRangeWithCeShiTi")
    public int savePriceRangeWithCeShiTi(HttpServletRequest request, @RequestParam("yusuanvalmin") Integer yusuanvalmin, @RequestParam("yusuanvalmax") Integer yusuanvalmax,
                                         @RequestParam("closingDate") String closingDate) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.savePriceRangeWithCeShiTi(yusuanvalmin, yusuanvalmax, closingDate, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param closingDate
     * @param renwumoney
     * @return
     */
    @RequestMapping("/saveSingleReward")
    @ResponseBody
    public int saveSingleReward(HttpServletRequest request, @RequestParam("closingDate") String closingDate, @RequestParam("renwumoney") BigDecimal renwumoney) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveSingleReward(closingDate, renwumoney, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param closingDate
     * @param renwumoney
     * @return
     */
    @RequestMapping("/saveMoreRewardDateAndMoney")
    @ResponseBody
    public int saveMoreRewardDateAndMoney(HttpServletRequest request, @RequestParam("closingDate") String closingDate, @RequestParam("renwumoney") BigDecimal renwumoney) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveMoreRewardDateAndMoney(closingDate, renwumoney, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param firstprizemoney
     * @param zhanbi
     * @param secondprizemoney
     * @return
     */
    @RequestMapping("/saveMoreRewardWhenNumEqualsTwo")
    @ResponseBody
    public int saveMoreRewardWhenNumEqualsTwo(HttpServletRequest request, @RequestParam("firstprizemoney") BigDecimal firstprizemoney,
                                              @RequestParam("zhanbi") BigDecimal zhanbi,
                                              @RequestParam("secondprizemoney") BigDecimal secondprizemoney) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveMoreRewardWhenNumEqualsTwo(firstprizemoney, zhanbi, secondprizemoney, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param thirdprizenum
     * @param secondprizenum
     * @param peoplenum
     * @param firstprizemoney
     * @param zhanbi
     * @param secondprizemoney
     * @param thirdprizemoney
     * @return
     */
    @RequestMapping("/saveMoreRewardWhenNumGreaterThanTwo")
    @ResponseBody
    public int saveMoreRewardWhenNumGreaterThanTwo(HttpServletRequest request, @RequestParam("thirdprizenum") Integer thirdprizenum,
                                                   @RequestParam("secondprizenum") Integer secondprizenum,
                                                   @RequestParam("peoplenum") Integer peoplenum,
                                                   @RequestParam("firstprizemoney") BigDecimal firstprizemoney,
                                                   @RequestParam("zhanbi") BigDecimal zhanbi,
                                                   @RequestParam("secondprizemoney") BigDecimal secondprizemoney,
                                                   @RequestParam("thirdprizemoney") BigDecimal thirdprizemoney) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveMoreRewardWhenNumGreaterThanTwo(thirdprizenum, assignmentId, secondprizenum, peoplenum, firstprizemoney, zhanbi, secondprizemoney, thirdprizemoney);
        return res;
    }

    /**
     * @param request
     * @param gaojianSinglePrice
     * @param gaojiannum
     * @return
     */
    @RequestMapping("/saveGaoJian")
    @ResponseBody
    public int saveGaoJian(HttpServletRequest request, @RequestParam("gaojianSinglePrice") BigDecimal gaojianSinglePrice, @RequestParam("gaojiannum") Integer gaojiannum) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveGaoJian(gaojianSinglePrice, gaojiannum, assignmentId);
        return res;
    }

    /**
     * @param request
     * @param closingDate
     * @param renwumoney
     * @return
     */
    @RequestMapping("/saveGaoJianDateAndMoney")
    @ResponseBody
    public int saveGaoJianDateAndMoney(HttpServletRequest request, @RequestParam("closingDate") String closingDate, @RequestParam("renwumoney") BigDecimal renwumoney) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveGaoJianDateAndMoney(closingDate, renwumoney, assignmentId);
        return res;
    }

    /**
     * @param top
     * @param request
     * @return
     */
    @RequestMapping("/updateTop")
    @ResponseBody
    public int updateTop(@RequestParam("top") Integer top, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.updateTop(assignmentId, top);
        return res;
    }

    /**
     * @param urgent
     * @param request
     * @return
     */
    @RequestMapping("/updateUrgent")
    @ResponseBody
    public int updateUrgent(@RequestParam("urgent") Integer urgent, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.updateUrgent(assignmentId, urgent);
        return res;
    }

    /**
     * @param allHide
     * @param request
     * @return
     */
    @RequestMapping("/updateAllHide")
    @ResponseBody
    public int updateAllHide(@RequestParam("allHide") Integer allHide, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.updateAllHide(assignmentId, allHide);
        return res;
    }

    /**
     * @param money
     * @return
     */
    @RequestMapping("/saveTuoGuanMoney")
    @ResponseBody
    public int saveTuoGuanMoney(@RequestParam("tuoguanmoney") BigDecimal money, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveTuoGuanMoney(money, assignmentId);
        return res;
    }

    /**
     * @param money
     * @return
     */
    @RequestMapping("/saveTopMoney")
    @ResponseBody
    public int saveTopMoney(@RequestParam("topmoney") BigDecimal money, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveTopMoney(money, assignmentId);
        return res;
    }

    /**
     * @param money
     * @return
     */
    @RequestMapping("/saveUrgentMoney")
    @ResponseBody
    public int saveUrgentMoney(@RequestParam("urgentmoney") BigDecimal money, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveUrgentMoney(money, assignmentId);
        return res;
    }

    /**
     * @param money
     * @return
     */
    @RequestMapping("/saveHideMoney")
    @ResponseBody
    public int saveHideMoney(@RequestParam("hidemoney") BigDecimal money, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveHideMoney(money, assignmentId);
        return res;
    }

    /**
     * @param money
     * @return
     */
    @RequestMapping("/saveTotalMoney")
    @ResponseBody
    public int saveTotalMoney(@RequestParam("totalmoney") BigDecimal money, HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveTotalMoney(money, assignmentId);
        return res;
    }

    @ResponseBody
    @RequestMapping("/makeSettlementNull")
    public int makeSettlementNull(HttpServletRequest request) {
        // 保存到数据库
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.makeSettlementNull(assignmentId);
        return res;
    }


    @ResponseBody
    @RequestMapping("/getReferenceFileWithoutTestId")
    public List<IncludefileUrl> getReferenceFileWithoutTestId(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId) {
        List<IncludefileUrl> res = publishTaskService.getReferenceFileWithoutTestId(assignmentId);
        return res;
    }


    @ResponseBody
    @RequestMapping("/getReferenceFileWithTestId")
    public List<IncludefileUrl> getReferenceFileWithTestId(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId) {
        // 从cookie中获取testId；
        String testId = CookieUtils.getCookieValue(request, "testId", true);
        List<IncludefileUrl> res = publishTaskService.getReferenceFileWithTestId(assignmentId, testId);
        return res;
    }

    @RequestMapping("/submitOrder")
    @LoginRequired(loginSuccess = true)
    public String submitOrder(HttpServletRequest request,HttpServletResponse response) {
        String assignmentId = request.getParameter("assignmentId");
        if (assignmentId == null || assignmentId == "") {
            assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        }
        // 通过assignmentId查询并创建order表
        Settlement settlement = publishTaskService.getSettlementInfoByAssId(assignmentId);
        if (settlement != null) {
            String orderCodeSn = getOrderCodeSn();
            BigDecimal totalAmount = settlement.getTotalAmount();
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            String str = "redirect:http://121.36.85.218:9999/payment/index?orderSn=" + orderCodeSn +"&assignmentId=" + assignmentId;
            // 创建订单，并填写相应的数据

            int res = publishTaskService.saveorder(totalAmount, assignmentId, orderCodeSn);

            // TODO

//            String str = "redirect:/fabao/taskPage?orderSn=" + orderCodeSn;

            return str;
        }else {
            throw new RuntimeException("界面发生异常");
        }
    }

    @RequestMapping("/saveorder")
    @ResponseBody
    public String saveorder(@RequestParam("totalMoney") BigDecimal totalMoney, HttpServletRequest request) {
        String assignmentId = request.getParameter("assignmentId");
        if (assignmentId == null || assignmentId == "") {
            assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        }
        String orderCodeSn = getOrderCodeSn();
        int res = publishTaskService.saveorder(totalMoney, assignmentId, orderCodeSn);
        if (res == 1) {

        }
        return orderCodeSn;
    }

    //获得订单号
    private String getOrderCodeSn() {
        long currentTimeMillis = System.currentTimeMillis();
        String orderCodeSn = "20190925" + currentTimeMillis;
        return orderCodeSn;
    }

    @RequestMapping("/getSettlementByAssIdToMap")
    @ResponseBody
    public Map<String,BigDecimal> getSettlementByAssIdToMap(@RequestParam("assignmentId")Integer assignmentId) {
        Map<String,BigDecimal> map = publishTaskService.getSettlementByAssIdToMap(assignmentId);
        // 使用fastjson工具类把map转化为Sting传给前台解析遍历
        return map;
    }

    /**
     * 保存留言
     * @return
     */
    @RequestMapping("/saveRemark")
    @ResponseBody
    public int saveRemark(@RequestParam("remark") String remark, HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveRemark(remark, assignmentId);
        return res;
    }

    /**
     *  查询该任务的状态  如果不是0 说明已经发布，不让重新发布
     *  如果是0 则更改其状态 并且让其发布。
     * @param request
     * @return
     */
    @RequestMapping("/catAndUpdateTaskStatus")
    @ResponseBody
    public int catAndUpdateTaskStatus(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);

        int res = publishTaskService.catAndUpdateTaskStatus(assignmentId);
        return res;
    }

    @RequestMapping("/saveDifferTime")
    @ResponseBody
    public int saveDifferTime(HttpServletRequest request){
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.saveDifferTime(assignmentId);
        return res;
    }

    @RequestMapping("/getUrlByManuscriptId")
    @ResponseBody
    public List<TbManuscriptUrl> getUrlByManuscriptId(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.getUrlByManuscriptId(manuscriptId);
    }

    @RequestMapping("/IsBiddingByManuScriptIdAndScheduleStatus")
    @ResponseBody
    public int IsBiddingByManuScriptIdAndScheduleStatus(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.IsBiddingByManuScriptIdAndScheduleStatus(assignmentId,manuscriptId);
    }

    @RequestMapping("/IsBiddingByManuScriptIdAndScheduleStatus2")
    @ResponseBody
    public int IsBiddingByManuScriptIdAndScheduleStatus2(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.IsBiddingByManuScriptIdAndScheduleStatus2(assignmentId,manuscriptId);
    }
    @RequestMapping("/updateXuangaoTime")
    @ResponseBody
    public int updateXuangaoTime(@RequestParam("assignmentId") String assignmentId) {
        return publishTaskService.updateXuangaoTime(assignmentId);
    }

    @RequestMapping("/setAssignmentScheduleStatus1")
    @ResponseBody
    public int setAssignmentScheduleStatus1(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        return publishTaskService.setAssignmentScheduleStatus1(assignmentId);
    }

    @RequestMapping("/updateFirstStatus")
    @ResponseBody
    public int updateFirstStatus(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        return publishTaskService.updateFirstStatus(assignmentId);
    }

    @RequestMapping("/setTaskStatus")
    @ResponseBody
    public int setTaskStatus(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.setTaskStatus(manuscriptId);
    }

    @RequestMapping("/updateFirstStatus2")
    @ResponseBody
    public int updateFirstStatus2(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        return publishTaskService.updateFirstStatus2(assignmentId);
    }

    @RequestMapping("/setTaskStatus2")
    @ResponseBody
    public int setTaskStatus2(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.setTaskStatus2(manuscriptId);
    }

    @RequestMapping("/updateFirstStatus3")
    @ResponseBody
    public int updateFirstStatus3(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        return publishTaskService.updateFirstStatus3(assignmentId);
    }

    @RequestMapping("/setTaskStatus3")
    @ResponseBody
    public int setTaskStatus3(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        return publishTaskService.setTaskStatus3(manuscriptId);
    }

    @ResponseBody
    @RequestMapping("/updateGaoJianStatus")
    public int updateGaoJianStatus(HttpServletRequest request) {
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        return publishTaskService.updateGaoJianStatus(assignmentId);
    }

    @RequestMapping("/updateUrlStatus")
    @ResponseBody
    public int updateUrlStatus(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        String gaojianSign = CookieUtils.getCookieValue(request, "gaojianSign", true);
        return publishTaskService.updateUrlStatus(manuscriptId,gaojianSign);
    }

    @ResponseBody
    @RequestMapping("/updateJijianHegeNum")
    public int updateJijianHegeNum(HttpServletRequest request) {
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        String gaojianSign = CookieUtils.getCookieValue(request, "gaojianSign", true);
        return publishTaskService.updateJijianHegeNum(manuscriptId,gaojianSign);
    }

}
