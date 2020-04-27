package com.jiaoyiyu.back.coreservice;

import java.math.BigDecimal;

public interface UploadWorkService {
    int saveWork(Integer memberId, int i, String time, BigDecimal price, String worksTitle, Integer workTypeV, Integer industryV, String workDetail, Integer radioV);

    int uploaderCover(String workId, String url);

    int uploaderZip(String url, String workId);

    int uploadImg(String workId, String url);

    int uploaderAvi(String workId, String url);
}
