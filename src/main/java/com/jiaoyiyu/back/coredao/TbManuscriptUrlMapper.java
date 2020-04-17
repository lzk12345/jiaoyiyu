package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.TbManuscriptUrl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface TbManuscriptUrlMapper extends Mapper<TbManuscriptUrl> {
    @Select(value = "select max(gaojian_sign) from tb_manuscript_url WHERE manuscript_id=#{id};")
    Integer getMaxValue(Integer id);
}
