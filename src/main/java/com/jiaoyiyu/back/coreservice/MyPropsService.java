package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.PropAllHideUser;
import com.jiaoyiyu.back.bean.PropTaskHideUser;
import com.jiaoyiyu.back.bean.PropTaskTopUser;
import com.jiaoyiyu.back.bean.PropTaskUrgentUser;
import org.springframework.stereotype.Service;

public interface MyPropsService {
    PropTaskTopUser selectPropTaskTopByMemberId(Integer memberId);

    PropTaskUrgentUser selectPropTaskUrgentByMemberId(Integer memberId);

    PropTaskHideUser selectPropTaskHideByMemberId(Integer memberId);

    PropAllHideUser selectPropAllHideByMemberId(Integer memberId);

    PropTaskTopUser selectMoneyAndIntegralUserdTop();

    PropTaskUrgentUser selectMoneyAndIntegralUserdUrgent();

    PropAllHideUser selectMoneyAndIntegralUserdAllHide();

    PropTaskHideUser selectMoneyAndIntegralUserdHide();
}
