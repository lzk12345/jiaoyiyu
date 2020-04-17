package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.vo.ScoreGroupVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MyIntegralMapper {

    @Select("SELECT mi.create_date, mi.remarks, mi.sign, ss.num, ss.source FROM myintegral mi,score_source ss WHERE mi.uid = #{memberId} AND ss.id = mi.source_id;")
    List<ScoreGroupVO> selectScoreInfoByMemberId(Integer memberId);
}
