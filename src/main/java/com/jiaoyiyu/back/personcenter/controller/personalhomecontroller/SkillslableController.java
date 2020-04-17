package com.jiaoyiyu.back.personcenter.controller.personalhomecontroller;


import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.Catalog1;
import com.jiaoyiyu.back.bean.Catalog2;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.UserAndTag;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SkillslableController class
 *
 * @author maochaoying
 * @date 2019/11/11
 */
@CrossOrigin
@Controller
@RequestMapping("/skillslable")
public class SkillslableController {

    @Autowired
    CatalogService catalogService;
    @Autowired
    UserService userService;

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index(ModelMap modelMap) {
        List<Catalog1> catalog1 = catalogService.getCatalog1();
        modelMap.put("catalog1List", catalog1);
        return "skillslable";
    }

    @RequestMapping("getcatalog2")
    @ResponseBody
    public List<Catalog2> getcatalog2(@RequestParam("catalog1Id") Integer catalog1Id) {
        List<Catalog2> catalog2 = catalogService.getCatalog2(catalog1Id);
        return catalog2;
    }



    @RequestMapping("savecata1ToUserAndTags")
    @ResponseBody
    public int savecata1ToUserAndTags(@RequestParam("id") Integer id, HttpServletRequest request) {
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
        return userService.savecata1ToUserAndTags(id, user.getId());
    }


    @RequestMapping("savebestBusinessToUserAndTags")
    @ResponseBody
    public int savebestBusinessToUserAndTags(@RequestParam("text") String text, HttpServletRequest request) {
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
        return userService.savebestBusinessToUserAndTags(text, user.getId());
    }

    @RequestMapping("saveotherBusinessToUserAndOther")
    @ResponseBody
    public int saveotherBusinessToUserAndOther(@RequestParam("text") String text, HttpServletRequest request) {
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
        return userService.saveotherBusinessToUserAndOther(text, user.getId());
    }

    @RequestMapping("/getFirstAndSecondSkill")
    @ResponseBody
    public Map<String, String> getFirstAndSecondSkill(HttpServletRequest request) {
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
        Map<String, String> map = new HashMap<>();
        UserAndTag userAndTag = userService.getFirstAndSecondSkill(user.getId());
        String theBestBusiness = userAndTag.getTheBestBusiness();
        if (StringUtils.isNotBlank(theBestBusiness)) {
            map.put("second", theBestBusiness);
        }else {
            map.put("second", "");
        }
        if (userAndTag.getCatalogId() != null) {
            Catalog1 catalog1ById = catalogService.getCatalog1ById(userAndTag.getCatalogId());
            String firstName = catalog1ById.getName();
            map.put("first", firstName);
        }else {
            map.put("first", "");
        }
        return map;
    }

    @RequestMapping("/getOtherBusiness")
    @ResponseBody
    public List<String> getOtherBusiness(HttpServletRequest request) {
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
        List<String> list = userService.getOtherBusiness(user.getId());
        return list;
    }

    @RequestMapping("/delFirstCata")
    @ResponseBody
    public int delFirstCata(HttpServletRequest request, @RequestParam("cataName") String cataName) {
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
        return userService.delFirstCata(user.getId(), cataName);
    }

    @RequestMapping("/delSecondCata")
    @ResponseBody
    public int delSecondCata(HttpServletRequest request, @RequestParam("cataName") String cataName) {
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
        return userService.delSecondCata(user.getId(), cataName);
    }

    @RequestMapping("/delThirdCata")
    @ResponseBody
    public int delThirdCata(HttpServletRequest request, @RequestParam("cataName") String cataName) {
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
        return userService.delThirdCata(user.getId(), cataName);
    }
}
