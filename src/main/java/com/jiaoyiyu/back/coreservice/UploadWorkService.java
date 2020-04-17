package com.jiaoyiyu.back.coreservice;

import java.math.BigDecimal;

public interface UploadWorkService {
    int saveWork(Integer memberId, int i, String time, BigDecimal price, String worksTitle, Integer workTypeV, Integer industryV, String workDetail, Integer radioV);

    void uploaderCover(String workId, String url);
}
