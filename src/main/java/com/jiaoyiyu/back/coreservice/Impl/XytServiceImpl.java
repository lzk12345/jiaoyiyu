package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.bean.Catalog1;
import com.jiaoyiyu.back.bean.Catalog2;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.vo.XytVO;
import com.jiaoyiyu.back.coredao.AllWorksMapper;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.UserService;
import com.jiaoyiyu.back.coreservice.XytService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * XytServiceImpl class
 *
 * @author maochaoying
 * @date 2020/4/27
 */
@Service
public class XytServiceImpl implements XytService {

    @Autowired
    AllWorksMapper allWorksMapper;
    @Autowired
    UserService userService;
    @Autowired
    CatalogService catalogService;
    @Override
    public List<XytVO> getXytVO(Integer pageNum) throws ParseException {

        List<AllWorks> allWorksList = allWorksMapper.getXytVO((pageNum - 1) * 12);
        List<XytVO> list = new ArrayList<>();
        if (allWorksList != null) {
            for (AllWorks allWorks : allWorksList) {
                XytVO xytVO = new XytVO();
                Integer integer = allWorks.getuId();
                User userInfoByMemberId = userService.getUserInfoByMemberId(integer);
                Integer cataId = allWorks.getCataId();
                Integer classOfIndustryId = allWorks.getClassOfIndustryId();
                Catalog1 catalog1ById = catalogService.getCatalog1ById(classOfIndustryId);
                Catalog2 catalog2ById = catalogService.getCatalog2ById(cataId);
                xytVO.setTitle(allWorks.getTitle());
                xytVO.setCata1Name(catalog2ById.getName());
                xytVO.setCata2Name(catalog1ById.getName());
                xytVO.setCoverUrl(allWorks.getCoverUrl());
                xytVO.setComments(allWorks.getComments());
                xytVO.setLikeNum(allWorks.getLikeNum());
                xytVO.setPageView(allWorks.getPageView());
                xytVO.setId(allWorks.getId());
                String creatDate = allWorks.getCreatDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = simpleDateFormat.parse(creatDate);
                int nDay = (int) ((new Date().getTime() - parse.getTime()) / (24 * 60 * 60 * 1000));
                xytVO.setDays(nDay);
                xytVO.setNickName(userInfoByMemberId.getNickName());
                xytVO.setHeadPic(userInfoByMemberId.getHeadPic());
                xytVO.setUid(userInfoByMemberId.getId());
                list.add(xytVO);
            }
        }
        return list;
    }

    @Override
    public List<XytVO> getXytVO1() throws ParseException {
        List<AllWorks> allWorksList = allWorksMapper.getXytVO(0);
        List<XytVO> list = new ArrayList<>();
        if (allWorksList != null) {
            for (AllWorks allWorks : allWorksList) {
                XytVO xytVO = new XytVO();
                Integer integer = allWorks.getuId();
                User userInfoByMemberId = userService.getUserInfoByMemberId(integer);
                Integer cataId = allWorks.getCataId();
                Integer classOfIndustryId = allWorks.getClassOfIndustryId();
                Catalog1 catalog1ById = catalogService.getCatalog1ById(classOfIndustryId);
                Catalog2 catalog2ById = catalogService.getCatalog2ById(cataId);
                xytVO.setTitle(allWorks.getTitle());
                xytVO.setCata1Name(catalog2ById.getName());
                xytVO.setCata2Name(catalog1ById.getName());
                xytVO.setCoverUrl(allWorks.getCoverUrl());
                xytVO.setUid(userInfoByMemberId.getId());
                xytVO.setComments(allWorks.getComments());
                xytVO.setLikeNum(allWorks.getLikeNum());
                xytVO.setPageView(allWorks.getPageView());
                xytVO.setId(allWorks.getId());
                String creatDate = allWorks.getCreatDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = simpleDateFormat.parse(creatDate);
                int nDay = (int) ((new Date().getTime() - parse.getTime()) / (24 * 60 * 60 * 1000));
                xytVO.setDays(nDay);
                xytVO.setNickName(userInfoByMemberId.getNickName());
                xytVO.setHeadPic(userInfoByMemberId.getHeadPic());
                list.add(xytVO);
            }
        }
        return list;
    }

    @Override
    public int getTotalCount() {
        AllWorks allWorks3 = new AllWorks();
        int i = allWorksMapper.selectCount(allWorks3);
        return i;
    }
}
