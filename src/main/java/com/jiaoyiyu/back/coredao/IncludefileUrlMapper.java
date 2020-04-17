package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.IncludefileUrl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface IncludefileUrlMapper extends Mapper<IncludefileUrl> {
    @Select(value = "SELECT dev.test_id FROM (SELECT test_id,assignment_id FROM includefile_url  WHERE assignment_id=#{assignmentId}) dev GROUP BY test_id")
    List<String> selectTestIdExistsByAssignmentId(Integer assignmentId);
}
