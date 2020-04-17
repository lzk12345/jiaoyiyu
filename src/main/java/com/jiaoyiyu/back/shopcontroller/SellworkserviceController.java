package com.jiaoyiyu.back.shopcontroller;

import com.jiaoyiyu.back.bean.Catalog1;
import com.jiaoyiyu.back.bean.TbSellservice;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.SellService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.ImageUploadUtil;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/sellworkservice")
@CrossOrigin
public class SellworkserviceController {
    @Autowired
    CatalogService catalogService;
    @Autowired
    SellService sellService;
    @Autowired
    UserService userService;
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        // 生成一个上传作品的id
        int workId = (int) ((Math.random() * 9 + 1) * 100000);
        List<Catalog1> catalog1 = catalogService.getCatalog1();
        modelMap.put("catalog1List", catalog1);
        CookieUtils.setCookie(request, response, "serviceId", workId + "", 60 * 60 * 20, true);
        return "sellworkservice";
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
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

        Map<String,Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data",null);
        return map;
    }

    @RequestMapping("/uploadImg1")
    @ResponseBody
    public Map<String,Object> uploadImg1(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
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

        Map<String,Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data",null);
        return map;
    }


    @RequestMapping("/saveSellService")
    @ResponseBody
    public int saveSellService(HttpServletRequest request,@RequestParam("serviceTitle") String serviceTitle,
                               @RequestParam("serviceType") String serviceType,
                               @RequestParam("price") BigDecimal price,
                               @RequestParam("serviceDetail") String serviceDetail){
        String token = CookieUtils.getCookieValue(request, "oldToken", true);
        User user = new User();
        if (token != null) {
            String key = "26sdasd2sds9848SDASD9484SADASDAS29";
            String salt = "6sds2816asFRFRds814849ASASDASGRGR818HRHT91981";
            Map<String, Object> decode = JwtUtil.decode(token, key, salt);

            if (decode == null) {
                return -1;
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
        return sellService.saveSellService(CookieUtils.getCookieValue(request,"serviceId",true),serviceTitle,serviceType,price,serviceDetail,memberId);
    }

}