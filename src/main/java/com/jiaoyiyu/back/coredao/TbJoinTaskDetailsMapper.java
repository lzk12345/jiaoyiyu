package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.TbJoinTaskDetails;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * TbJoinTaskDetailsMapper class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

@org.apache.ibatis.annotations.Mapper
public interface TbJoinTaskDetailsMapper extends Mapper<TbJoinTaskDetails> {
    @Select(value = "SELECT count(1) FROM tb_join_task_details WHERE assignment_id=#{assignmentId}")
    int getTaskDetailsCountByAssignmentId(Integer assignmentId);
}
