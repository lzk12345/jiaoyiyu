package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.bean.UploaderImg;
import com.jiaoyiyu.back.bean.UploaderVideo;
import com.jiaoyiyu.back.bean.UploaderZip;
import com.jiaoyiyu.back.coredao.AllWorksMapper;
import com.jiaoyiyu.back.coredao.UploaderImgMapper;
import com.jiaoyiyu.back.coredao.UploaderUrlMapper;
import com.jiaoyiyu.back.coredao.UploaderVideoMapper;
import com.jiaoyiyu.back.coreservice.UploadWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

/**
 * UploadWorkServiceImpl class
 *
 * @author maochaoying
 * @date 2020/4/6
 */
@Service
public class UploadWorkServiceImpl implements UploadWorkService {

    @Autowired
    AllWorksMapper allWorksMapper;
    @Autowired
    UploaderUrlMapper uploaderUrlMapper;
    @Autowired
    UploaderVideoMapper uploaderVideoMapper;
    @Autowired
    UploaderImgMapper uploaderImgMapper;


    @Override
    public int saveWork(Integer memberId, int i, String time, BigDecimal price
            , String worksTitle, Integer workTypeV, Integer industryV,
                        String workDetail, Integer radioV) {
        AllWorks allWorks1 = new AllWorks();
        allWorks1.setId(i);
        AllWorks allWorks2 = allWorksMapper.selectOne(allWorks1);
        if (allWorks2 == null) {
            AllWorks allWorks = new AllWorks();
            allWorks.setId(i);
            allWorks.setCataId(industryV);
            allWorks.setClassOfIndustryId(workTypeV);
            allWorks.setCreatDate(time);
            allWorks.setDetail(workDetail);
            allWorks.setPrice(price);
            allWorks.setSellState(radioV);
            allWorks.setuId(memberId);
            allWorks.setTitle(worksTitle);
            return allWorksMapper.insertSelective(allWorks);
        } else {
            AllWorks allWorks = new AllWorks();
            allWorks.setCataId(industryV);
            allWorks.setClassOfIndustryId(workTypeV);
            allWorks.setCreatDate(time);
            allWorks.setDetail(workDetail);
            allWorks.setPrice(price);
            allWorks.setSellState(radioV);
            allWorks.setuId(memberId);
            allWorks.setTitle(worksTitle);
            Example example = new Example(AllWorks.class);
            example.createCriteria().andEqualTo("id", i);
            return allWorksMapper.updateByExampleSelective(allWorks, example);
        }
    }

    @Override
    public int uploaderCover(String workId, String url) {
        AllWorks allWorks1 = new AllWorks();
        allWorks1.setId(Integer.parseInt(workId));
        AllWorks allWorks2 = allWorksMapper.selectOne(allWorks1);
        int res = 0;
        if (allWorks2 == null) {
            allWorks1.setCoverUrl(url);
            res = allWorksMapper.insertSelective(allWorks1);
        } else {
            AllWorks allWorks = new AllWorks();
            allWorks.setCoverUrl(url);
            Example example = new Example(AllWorks.class);
            example.createCriteria().andEqualTo("id", workId);
            res = allWorksMapper.updateByExampleSelective(allWorks, example);
        }
        return res;
    }

    @Override
    public int uploaderZip(String url, String workId) {
        UploaderZip uploaderZip = new UploaderZip();
        uploaderZip.setWorkId(Integer.parseInt(workId));
        uploaderZip.setZipUrl(url);
        int res = uploaderUrlMapper.insertSelective(uploaderZip);
        return res;
    }

    @Override
    public int uploadImg(String workId, String url) {
        UploaderImg uploaderImg = new UploaderImg();
        uploaderImg.setWorkId(Integer.parseInt(workId));
        uploaderImg.setUrl(url);
        int res = uploaderImgMapper.insertSelective(uploaderImg);
        return res;
    }

    @Override
    public int uploaderAvi(String workId, String url) {
        UploaderVideo uploaderVideo = new UploaderVideo();
        uploaderVideo.setWorkId(Integer.parseInt(workId));
        uploaderVideo.setUrl(url);
        int res = uploaderVideoMapper.insertSelective(uploaderVideo);
        return res;
    }
}
