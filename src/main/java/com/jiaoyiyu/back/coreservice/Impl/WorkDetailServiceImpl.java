package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.bean.UploaderImg;
import com.jiaoyiyu.back.bean.UploaderVideo;
import com.jiaoyiyu.back.bean.UploaderZip;
import com.jiaoyiyu.back.coredao.AllWorksMapper;
import com.jiaoyiyu.back.coredao.UploaderImgMapper;
import com.jiaoyiyu.back.coredao.UploaderUrlMapper;
import com.jiaoyiyu.back.coredao.UploaderVideoMapper;
import com.jiaoyiyu.back.coreservice.WorkDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WorkDetailServiceImpl class
 *
 * @author maochaoying
 * @date 2020/4/27
 */
@Service
public class WorkDetailServiceImpl implements WorkDetailService {

    @Autowired
    AllWorksMapper allWorksMapper;
    @Autowired
    UploaderImgMapper uploaderImgMapper;
    @Autowired
    UploaderVideoMapper uploaderVideoMapper;
    @Autowired
    UploaderUrlMapper uploaderUrlMapper;

    @Override
    public AllWorks getWorkDetails(Integer workId) {
        AllWorks allWorks = allWorksMapper.selectByPrimaryKey(workId);
        return allWorks;
    }

    @Override
    public List<UploaderImg> getImgsByWorkId(Integer workId) {
        UploaderImg uploaderImg = new UploaderImg();
        uploaderImg.setWorkId(workId);
        return uploaderImgMapper.select(uploaderImg);
    }

    @Override
    public List<UploaderVideo> getVideosByWorkId(Integer workId) {
        UploaderVideo uploaderVideo = new UploaderVideo();
        uploaderVideo.setWorkId(workId);
        return uploaderVideoMapper.select(uploaderVideo);
    }

    @Override
    public List<UploaderZip> getZipByWorkId(Integer workId) {
        UploaderZip uploaderZip = new UploaderZip();
        uploaderZip.setWorkId(workId);
        return uploaderUrlMapper.select(uploaderZip);
    }
}
