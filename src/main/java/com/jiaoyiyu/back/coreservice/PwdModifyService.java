package com.jiaoyiyu.back.coreservice;

import org.springframework.stereotype.Service;

public interface PwdModifyService {
    int updateUserPwd(String currentPwd, String newPwd, Integer memberId);

    int updatePayPwd(Integer memberId, Integer newPayPwd);
}
