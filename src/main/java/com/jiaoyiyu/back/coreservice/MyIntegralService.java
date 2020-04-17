package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.vo.ScoreGroupVO;
import org.springframework.stereotype.Service;

import java.util.List;
public interface MyIntegralService {
    List<ScoreGroupVO> selectScoreInfoByMemberId(Integer memberId);
}
