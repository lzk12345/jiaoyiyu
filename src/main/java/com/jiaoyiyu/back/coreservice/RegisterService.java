package com.jiaoyiyu.back.coreservice;

import org.springframework.stereotype.Service;

public interface RegisterService {
    String registerByMsg(String phonenum, String messageCode);

    void delCodeCache(String phonenum, String messageCode);
}
