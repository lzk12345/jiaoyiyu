package com.jiaoyiyu.back.coreservice;


import com.jiaoyiyu.back.bean.Catalog2;
import com.jiaoyiyu.back.bean.TbVisitor;
import com.jiaoyiyu.back.bean.User;
import com.jiaoyiyu.back.bean.UserAndTag;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
public interface UserService {

    User queryFromCache(String username, String password);

    User queryUserOne(String username, String password);

    void saveUserInfoToCache(User user);

    User savePhoneNumToDB(String phonenum);

    String saveUserAndPassToDB(String username, String password, String phonenum);

    User getUserInfoByMemberId(Integer memberId);

    User getUserInfoByPhoneNum(String phonenum);

    List<User> getFavorableUserInfoToHomePageLimitSix();

    int updateHeadPicByMemberId(Integer memberId, String url);

    int updateUserInfo(Integer memberId, String nickname, String select1, String detailAddr, String weixinnum, Integer sexVal, String select2, String phonenum, String selfdetail, String birthday, String select3, String qqnum);

    int updateUserInfoPwd(String newPwd, Integer memberId);

    int updatePayPwd(Integer memberId, Integer newPayPwd);

    List<Catalog2> getTagsByUserId(Integer id);

    int addVisitor(Integer visitorId, String uid, String stringDate);

    List<TbVisitor> getVisitors(Integer id) throws ParseException;

    int savecata1ToUserAndTags(Integer id, Integer id1);

    int savebestBusinessToUserAndTags(String text, Integer id);

    int saveotherBusinessToUserAndOther(String text, Integer id);

    UserAndTag getFirstAndSecondSkill(Integer id);

    List<String> getOtherBusiness(Integer id);

    int delFirstCata(Integer id, String cataName);

    int delSecondCata(Integer id, String cataName);

    int delThirdCata(Integer id, String cataName);
}
