package com.jiaoyiyu.back.shopcontroller;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.SellService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.back.coreservice.WorkDetailService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/worksdetails")
@CrossOrigin
public class WorksDetailsController {

    @Autowired
    WorkDetailService workDetailService;
    @Autowired
    UserService userService;
    @Autowired
    CatalogService catalogService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap) throws ParseException {
        if (request.getParameter("workId") != null) {
            Integer workId = Integer.parseInt(request.getParameter("workId"));
            AllWorks allWorks = workDetailService.getWorkDetails(workId);
            Integer uId = allWorks.getuId();
            User user= userService.getUserInfoByMemberId(uId);
            modelMap.put("user", user);
            modelMap.put("allworks",allWorks);
            Integer cataId = allWorks.getCataId();
            Integer classOfIndustryId = allWorks.getClassOfIndustryId();
            Catalog1 catalog1ById = catalogService.getCatalog1ById(classOfIndustryId);
            Catalog2 catalog2ById = catalogService.getCatalog2ById(cataId);
            modelMap.put("calalog1",catalog1ById.getName());
            modelMap.put("calalog2",catalog2ById.getName());
            String creatDate = allWorks.getCreatDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = simpleDateFormat.parse(creatDate);
            int nDay = (int) ((parse.getTime() - new Date().getTime() ) / (24 * 60 * 60 * 1000));
            modelMap.put("days", nDay);
        }


        return "worksdetails";
    }
}
