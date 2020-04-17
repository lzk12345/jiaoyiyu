package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.PropAllHideUser;
import com.jiaoyiyu.back.bean.PropTaskHideUser;
import com.jiaoyiyu.back.bean.PropTaskTopUser;
import com.jiaoyiyu.back.bean.PropTaskUrgentUser;
import com.jiaoyiyu.back.coredao.PropAllHideMapper;
import com.jiaoyiyu.back.coredao.PropTaskHideMapper;
import com.jiaoyiyu.back.coredao.PropTaskTopMapper;
import com.jiaoyiyu.back.coredao.PropTaskUrgentMapper;
import com.jiaoyiyu.back.coreservice.MyPropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MyPropsServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/15
 */
@Service
public class MyPropsServiceImpl implements MyPropsService {

    @Autowired
    PropAllHideMapper propAllHideMapper;
    @Autowired
    PropTaskHideMapper propTaskHideMapper;
    @Autowired
    PropTaskUrgentMapper propTaskUrgentMapper;
    @Autowired
    PropTaskTopMapper propTaskTopMapper;

    @Override
    public PropTaskTopUser selectPropTaskTopByMemberId(Integer memberId) {
        PropTaskTopUser propTaskTopUser = new PropTaskTopUser();
        propTaskTopUser.setUserId(memberId);
        PropTaskTopUser propTaskTopUser1 = propTaskTopMapper.selectOne(propTaskTopUser);
        return propTaskTopUser1;
    }

    @Override
    public PropTaskUrgentUser selectPropTaskUrgentByMemberId(Integer memberId) {
        PropTaskUrgentUser propTaskUrgentUser = new PropTaskUrgentUser();
        propTaskUrgentUser.setUserId(memberId);
        PropTaskUrgentUser propTaskUrgentUser1 = propTaskUrgentMapper.selectOne(propTaskUrgentUser);
        return propTaskUrgentUser1;
    }

    @Override
    public PropTaskHideUser selectPropTaskHideByMemberId(Integer memberId) {
        PropTaskHideUser propTaskHideUser = new PropTaskHideUser();
        propTaskHideUser.setUserId(memberId);
        PropTaskHideUser propTaskHideUser1 = propTaskHideMapper.selectOne(propTaskHideUser);
        return propTaskHideUser1;
    }

    @Override
    public PropAllHideUser selectPropAllHideByMemberId(Integer memberId) {
        PropAllHideUser propAllHideUser = new PropAllHideUser();
        propAllHideUser.setUserId(memberId);
        PropAllHideUser propAllHideUser1 = propAllHideMapper.selectOne(propAllHideUser);
        return propAllHideUser1;
    }

    @Override
    public PropTaskTopUser selectMoneyAndIntegralUserdTop() {
        return propTaskTopMapper.selectMoneyAndIntegralUserdTop();
    }

    @Override
    public PropTaskUrgentUser selectMoneyAndIntegralUserdUrgent() {
        return propTaskUrgentMapper.selectMoneyAndIntegralUserdUrgent();
    }

    @Override
    public PropAllHideUser selectMoneyAndIntegralUserdAllHide() {
        return propAllHideMapper.selectMoneyAndIntegralUserdAllHide();
    }

    @Override
    public PropTaskHideUser selectMoneyAndIntegralUserdHide() {
        return propTaskHideMapper.selectMoneyAndIntegralUserdHide();
    }
}
