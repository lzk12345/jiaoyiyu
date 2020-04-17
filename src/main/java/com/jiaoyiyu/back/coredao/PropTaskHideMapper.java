package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.PropTaskHideUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface PropTaskHideMapper extends Mapper<PropTaskHideUser> {
    @Select(value = "SELECT  integral_used , money_used, original_cost FROM `prop_task_hide_user` GROUP BY  integral_used , money_used, original_cost LIMIT 1;")
    PropTaskHideUser selectMoneyAndIntegralUserdHide();
}
