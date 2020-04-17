package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.TbHotHomepageData;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface TbHotHomepageDataMapper extends Mapper<TbHotHomepageData> {
    @Select(value = "SELECT * FROM tb_hot_homepage_data LIMIT 5;")
    List<TbHotHomepageData> getHotData();
}
