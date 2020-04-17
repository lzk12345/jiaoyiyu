package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.vo.ScoreGroupVO;
import com.jiaoyiyu.back.coredao.MyIntegralMapper;
import com.jiaoyiyu.back.coreservice.MyIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MyIntegralServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/12
 */
@Service
public class MyIntegralServiceImpl implements MyIntegralService {

    @Autowired
    MyIntegralMapper myIntegralMapper;

    @Override
    public List<ScoreGroupVO> selectScoreInfoByMemberId(Integer memberId) {
        return myIntegralMapper.selectScoreInfoByMemberId(memberId);
    }
}
