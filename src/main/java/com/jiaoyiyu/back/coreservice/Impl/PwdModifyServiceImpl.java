package com.jiaoyiyu.back.coreservice.Impl;


import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.coreservice.PwdModifyService;
import com.jiaoyiyu.back.coreservice.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PwdModifyServiceImpl class
 *
 * @author maochaoying
 * @date 2020/2/6
 */
@Service
public class PwdModifyServiceImpl implements PwdModifyService {

    @Autowired
    UserService userService;

    @Override
    public int updateUserPwd(String currentPwd, String newPwd, Integer memberId) {
        // 通过memberId来查询user对象
        User user = new User();
        user.setId(memberId);
        //拿到pwd与curr对比
        User userInfoByMemberId = userService.getUserInfoByMemberId(memberId);
        if (userInfoByMemberId != null) {
            if (userInfoByMemberId.getPassword().equals(currentPwd)) {
                // 如果一样 修改
                int res = userService.updateUserInfoPwd(newPwd,memberId);
                return res;
            }else {
                // 如果不一样 返回错误
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int updatePayPwd(Integer memberId, Integer newPayPwd) {
        int res = userService.updatePayPwd(memberId,newPayPwd);
        return res;
    }
}
