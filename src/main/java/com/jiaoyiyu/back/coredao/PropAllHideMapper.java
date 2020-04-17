package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.PropAllHideUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface PropAllHideMapper extends Mapper<PropAllHideUser> {
    @Select(value = "SELECT  integral_used , money_used, original_cost FROM `prop_all_hide_user` GROUP BY  integral_used , money_used, original_cost LIMIT 1;")
    PropAllHideUser selectMoneyAndIntegralUserdAllHide();

}
