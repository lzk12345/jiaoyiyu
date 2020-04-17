package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.AssignDifferTimeHour;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface AssignDifferTimeHourMapper extends Mapper<AssignDifferTimeHour> {

    @Insert(value = "INSERT INTO assign_differ_time_hour(assignment, differ_time_hour) VALUES (#{assignmentId}, (SELECT\n" +
            "\n" +
            "TIMESTAMPDIFF(HOUR,\n" +
            "\n" +
            "(DATE_FORMAT((SELECT pubtime FROM assignment WHERE id=#{assignmentId}),'%Y-%m-%d %H:%i')),\n" +
            "\n" +
            "(DATE_FORMAT((SELECT endtime FROM assignment WHERE id=#{assignmentId}), '%Y-%m-%d %H:%i'))\n" +
            ") AS timediffer\n" +
            "FROM\n" +
            "\n" +
            "DUAL))")
    int saveDifferTime(Integer assignmentId);
}
