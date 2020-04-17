package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    @Select(value = "SELECT * FROM `user` ORDER BY favorable_rate DESC LIMIT 6;")
    List<User> getFavorableUserInfoToHomePageLimitSix();
}
