package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.TbSellservice;

import java.math.BigDecimal;
import java.util.List;

public interface SellService {
    int saveSellService(String id,String serviceTitle, String serviceType, BigDecimal price, String serviceDetail,Integer memberId);


    TbSellservice getSellService(Integer memberId, String serviceId);
}
