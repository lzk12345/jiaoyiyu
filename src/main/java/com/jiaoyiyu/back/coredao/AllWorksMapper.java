package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.AllWorks;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AllWorksMapper extends Mapper<AllWorks> {
    @Select(value = "select * from all_works limit #{pageNum},12;")
    List<AllWorks> getXytVO(@Param("pageNum") Integer pageNum);
}
