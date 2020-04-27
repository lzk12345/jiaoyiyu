package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.vo.XytVO;

import java.text.ParseException;
import java.util.List;

public interface XytService {
    List<XytVO> getXytVO(Integer pageNum) throws ParseException;
}
