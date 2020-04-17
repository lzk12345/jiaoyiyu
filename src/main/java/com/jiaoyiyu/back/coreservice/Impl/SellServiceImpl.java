package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.TbSellservice;
import com.jiaoyiyu.back.coredao.TbSellserviceMapper;
import com.jiaoyiyu.back.coreservice.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SellServiceImpl implements SellService {
    @Autowired
    TbSellserviceMapper tbSellserviceMapper;

    @Override
    public int saveSellService(String id,String serviceTitle, String serviceType, BigDecimal price, String serviceDetail,Integer memberId) {
        TbSellservice tbSellservice = new TbSellservice();
        tbSellservice.setPrice(price);
        tbSellservice.setCatalog1Id(Integer.parseInt(serviceType));
        tbSellservice.setServiceDetails(serviceDetail);
        tbSellservice.setServiceName(serviceTitle);
        tbSellservice.setUploadTime(new Date());
        tbSellservice.setUid(memberId);
        tbSellservice.setId(Integer.parseInt(id));
        return tbSellserviceMapper.insertSelective(tbSellservice);
    }

    @Override
    public TbSellservice getSellService(Integer memberId, String serviceId) {
        TbSellservice tbSellservice = new TbSellservice();
        tbSellservice.setUid(memberId);
        tbSellservice.setId(Integer.parseInt(serviceId));
        return tbSellserviceMapper.selectOne(tbSellservice);
    }


}
