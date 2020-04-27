package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.bean.UploaderImg;
import com.jiaoyiyu.back.bean.UploaderVideo;
import com.jiaoyiyu.back.bean.UploaderZip;

import java.util.List;

public interface WorkDetailService {
    AllWorks getWorkDetails(Integer workId);

    List<UploaderImg> getImgsByWorkId(Integer workId);

    List<UploaderVideo> getVideosByWorkId(Integer workId);

    List<UploaderZip> getZipByWorkId(Integer workId);
}
