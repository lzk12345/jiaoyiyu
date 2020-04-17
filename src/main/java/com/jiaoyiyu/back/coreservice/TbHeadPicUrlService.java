package com.jiaoyiyu.back.coreservice;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
public interface TbHeadPicUrlService {
    int savePicUrl(String url, String originalFilename, String extName, BigDecimal sizeBig, Integer memberId);
}
