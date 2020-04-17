package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.TbHotHomepageData;
import com.jiaoyiyu.back.coredao.TbHotHomepageDataMapper;
import com.jiaoyiyu.back.coreservice.HotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HotDataServiceImpl class
 *
 * @author maochaoying
 * @date 2020/2/6
 */
@Service
public class HotDataServiceImpl implements HotDataService {

    @Autowired
    TbHotHomepageDataMapper tbHotHomepageDataMapper;

    @Override
    public List<TbHotHomepageData> getHotData() {
        return tbHotHomepageDataMapper.getHotData();
    }
}
