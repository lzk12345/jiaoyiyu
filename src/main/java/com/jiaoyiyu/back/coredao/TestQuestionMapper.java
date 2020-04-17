package com.jiaoyiyu.back.coredao;

import com.jiaoyiyu.back.bean.TestQuestion;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface TestQuestionMapper extends Mapper<TestQuestion> {
}
