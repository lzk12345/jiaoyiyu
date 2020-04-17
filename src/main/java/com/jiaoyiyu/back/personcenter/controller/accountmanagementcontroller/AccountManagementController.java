package com.jiaoyiyu.back.personcenter.controller.accountmanagementcontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.back.coreservice.TbHeadPicUrlService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.ImageUploadUtil;
import com.jiaoyiyu.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * AccountManagementController class
 * 账号管理首页（基本资料）
 *
 * @author maochaoying
 * @date 2019/11/12
 */

@Controller
@RequestMapping("/accountManagemet")
@CrossOrigin
public class AccountManagementController {

    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    TbHeadPicUrlService tbHeadPicUrlService;
    @Autowired
    UserService userService;


    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index(ModelMap modelMap, HttpServletRequest request) {
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
                String headPic = user.getHeadPic();
                modelMap.put("headPic", headPic);
                modelMap.put("user", user);
            }
        }

        return "AccountManagement";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("the_file") MultipartFile multipartFile, HttpServletRequest request, ModelMap modelMap) {
        // 从cookie中获取assignmentId
        String url = ImageUploadUtil.imgUpload(multipartFile);
        System.out.println(url);
        long size = multipartFile.getSize();
        DecimalFormat df = new DecimalFormat("0.00");
        String num = df.format((float) size / 1024);//返回的是String类型
        System.out.println("文件大小为" + num + "KB");
        BigDecimal sizeBig = new BigDecimal(num);
        // 把url存入数据库  status设置为0  以后用户删除附件后将status设置为1 方便查询
        // 拿到文件名并存入数据库
        String originalFilename = multipartFile.getOriginalFilename();
        int i1 = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(i1 + 1);
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
        int res = tbHeadPicUrlService.savePicUrl(url, originalFilename, extName, sizeBig, memberId);
        return url;
    }


    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public int updateUserInfo(HttpServletRequest request, @RequestParam("nickname") String nickname, @RequestParam("sexVal") Integer sexVal, @RequestParam("birthday") String birthday
            , @RequestParam("select1") String select1, @RequestParam("select2") String select2, @RequestParam("select3") String select3
            , @RequestParam("detailAddr") String detailAddr, @RequestParam("phonenum") String phonenum, @RequestParam("qqnum") String qqnum
            , @RequestParam("weixinnum") String weixinnum, @RequestParam("selfdetail") String selfdetail) {
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
            String phonenum1 = (String) decode.get("phonenum");
//            viptypeandlevel
            User user = null;
            if (memberId == null) {
                // 说明用手机登录
                user = publishTaskService.getUserInfoByPhoneNum(phonenum1);
            } else {
                user = publishTaskService.getUserInfoByMemberId(memberId);
            }
            if (user != null) {
                // 拿到userId
                memberId = user.getId();
            }
        }
        int res = userService.updateUserInfo(memberId,nickname,select1,detailAddr,weixinnum,sexVal,select2,phonenum,selfdetail,birthday,select3,qqnum);
        return res;
    }
}
