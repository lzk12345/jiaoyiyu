package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.PropTaskTopUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface PropTaskTopMapper extends Mapper<PropTaskTopUser> {
    @Select(value = "SELECT  integral_used , money_used, original_cost FROM `prop_task_top_user` GROUP BY  integral_used , money_used, original_cost LIMIT 1;")
    PropTaskTopUser selectMoneyAndIntegralUserdTop();
}
