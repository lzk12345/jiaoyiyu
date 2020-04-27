package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.coredao.AllWorksMapper;
import com.jiaoyiyu.back.coreservice.WorkDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public AllWorks getWorkDetails(Integer workId) {
        AllWorks allWorks = allWorksMapper.selectByPrimaryKey(workId);
        return allWorks;
    }
}
