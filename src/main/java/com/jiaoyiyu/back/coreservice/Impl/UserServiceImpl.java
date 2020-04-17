package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.coredao.TbVisitorMapper;
import com.jiaoyiyu.back.coredao.UserAndOtherBusinessMapper;
import com.jiaoyiyu.back.coredao.UserAndTagMapper;
import com.jiaoyiyu.back.coredao.UserMapper;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.sql.Types.NULL;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserAndTagMapper userAndTagMapper;
    @Autowired
    UserAndOtherBusinessMapper userAndOtherBusinessMapper;
    @Autowired
    CatalogService catalogService;
    @Autowired
    TbVisitorMapper tbVisitorMapper;

    @Override
    public User queryFromCache(String username, String password) {
        User user = null;
//        Jedis jedis = null;
//        try {
//            jedis = redisUtil.getJedis();
//            String passwordCache = jedis.get("user:" + username + password + ":password");
//            if (StringUtils.isNotBlank(passwordCache)) {
//                user = JSON.parseObject(passwordCache, User.class);
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            jedis.close();
//        }
        return user;
    }

    @Override
    public User queryUserOne(String username, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        User user1 = userMapper.selectOne(user);
        return user1;
    }

    @Override
    public void saveUserInfoToCache(User user) {
//        String username = user.getUsername();
//        String password = user.getPassword();
//        Jedis jedis = null;
//        try {
//            jedis = redisUtil.getJedis();
//            jedis.setex("user:" + username + ":password", 60 * 60 * 60 * 24, password);
//            jedis.setex("user:" + password + ":info", 60 * 60 * 60 * 24, JSON.toJSONString(user));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            jedis.close();
//        }
    }

    @Override
    public User savePhoneNumToDB(String phonenum) {
        User user = new User();
        user.setPhone(phonenum);
        List<User> select = userMapper.select(user);
        if (select.size() > 0) {
            return select.get(0);
        } else {
            user.setId(Integer.parseInt(RandomStringUtils.randomNumeric(6)));
            userMapper.insertSelective(user);
            return user;
        }

    }

    @Override
    public String saveUserAndPassToDB(String username, String password, String phonenum) {

        // 根据username查询数据库
//        如果为空说明username没有用过 可以注册

        User user2 = new User();
        user2.setUsername(username);
        User user3 = userMapper.selectOne(user2);
        if (user3 != null) {
            return "用户名已经存在";
        }

        User user = new User();
        user.setPhone(phonenum);
        User user1 = userMapper.selectOne(user);
        String result = "";
        if (user1 != null) {
            // 查phone,返回User对象，如果此时查到了且username和password存在，返回  “账号已经存在，请直接登陆”
            if (user1.getUsername() != null && user1.getPassword() != null) {
                result = "账号已经存在，请直接登陆";
            } else {
                //如果查到了对象，但是username和password不存在，就将现在的username和password存入数据库
                user1.setUsername(username);
                user1.setPassword(password);
                Example example = new Example(User.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("phone", phonenum);
                int i = userMapper.updateByExampleSelective(user1, example);
                if (i != -1) {
                    result = "success";
                } else {
                    result = "failed";
                }
            }
        } else {


            //如果没有查到对象，就重新将三个数据存入数据库
            user.setUsername(username);
            user.setPassword(password);
            int i = userMapper.insertSelective(user);
            if (i != -1) {
                result = "success";
            } else {
                result = "failed";
            }
        }
        return result;
    }

    @Override
    public User getUserInfoByMemberId(Integer memberId) {
        User user = new User();
        user.setId(memberId);
        User user1 = userMapper.selectOne(user);
        return user1;
    }

    @Override
    public User getUserInfoByPhoneNum(String phonenum) {
        User user = new User();
        user.setPhone(phonenum);
        User user1 = userMapper.selectOne(user);
        return user1;
    }

    @Override
    public List<User> getFavorableUserInfoToHomePageLimitSix() {
        return userMapper.getFavorableUserInfoToHomePageLimitSix();
    }

    @Override
    public int updateHeadPicByMemberId(Integer memberId, String url) {
        User user = new User();
        user.setHeadPic(url);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", memberId);
        int i = userMapper.updateByExampleSelective(user, example);
        return i;
    }

    @Override
    public int updateUserInfo(Integer memberId, String nickname, String select1, String detailAddr, String weixinnum, Integer sexVal, String select2, String phonenum, String selfdetail, String birthday, String select3, String qqnum) {
        // 更新user表
        User user = new User();
        user.setNickName(nickname);
        user.setFirstAddr(select1);
        user.setSecondAddr(select2);
        user.setThirdAddr(select3);
        user.setDetailAddr(detailAddr);
        user.setWeixin(weixinnum);
        user.setSex(sexVal + "");
        user.setQq(qqnum);
        user.setPhone(phonenum);
        user.setAboutTheAuthor(selfdetail);
        user.setBirthday(birthday);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", memberId);
        int i = userMapper.updateByExampleSelective(user, example);
        return i;
    }

    @Override
    public int updateUserInfoPwd(String newPwd, Integer memberId) {
        User user1 = new User();
        user1.setPassword(newPwd);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", memberId);
        int i = userMapper.updateByExampleSelective(user1, example);
        return i;
    }

    @Override
    public int updatePayPwd(Integer memberId, Integer newPayPwd) {
        User user = new User();
        user.setPayPassword(newPayPwd);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", memberId);
        int i = userMapper.updateByExampleSelective(user, example);
        return i;
    }

    @Override
    public List<Catalog2> getTagsByUserId(Integer id) {
        UserAndTag userAndTag = new UserAndTag();
        userAndTag.setuId(id);
        List<UserAndTag> select = userAndTagMapper.select(userAndTag);
        List<Catalog2> list = new ArrayList<>();
        for (UserAndTag andTag : select) {
            Integer catalogId = andTag.getCatalogId();
            Catalog2 catalog2ById = catalogService.getCatalog2ById(catalogId);
            list.add(catalog2ById);
        }
        return list;
    }

    @Override
    public int addVisitor(Integer visitorId, String uid, String stringDate) {
        if (!uid.equals(visitorId + "")) {
            TbVisitor tbVisitor1 = new TbVisitor();
            tbVisitor1.setVisitorUid(visitorId);
            tbVisitor1.setOriginUid(Integer.parseInt(uid));
            TbVisitor tbVisitor2 = tbVisitorMapper.selectOne(tbVisitor1);
            int i = 0;
            if (tbVisitor2 == null) {
                TbVisitor tbVisitor = new TbVisitor();
                tbVisitor.setOriginUid(Integer.parseInt(uid));
                tbVisitor.setVisitorUid(visitorId);
                tbVisitor.setVisitTime(stringDate);
                i = tbVisitorMapper.insert(tbVisitor);
            }else {
                tbVisitor1.setVisitTime(stringDate);
                Example example = new Example(TbVisitor.class);
                example.createCriteria().andEqualTo("visitorUid", visitorId).andEqualTo("originUid",uid);
                i = tbVisitorMapper.updateByExample(tbVisitor1, example);
            }

            return i;
        }
      return -1;
    }

    @Override
    public List<TbVisitor> getVisitors(Integer id) throws ParseException {
        TbVisitor tbVisitor = new TbVisitor();
        tbVisitor.setOriginUid(id);
        List<TbVisitor> select = tbVisitorMapper.select(tbVisitor);
        for (TbVisitor visitor : select) {
            Integer visitorUid = visitor.getVisitorUid();
            User userInfoByMemberId = getUserInfoByMemberId(visitorUid);
            String headPic = userInfoByMemberId.getHeadPic();
            String nickName = userInfoByMemberId.getNickName();
            visitor.setHeadPic(headPic);
            visitor.setNickName(nickName);
            String visitTime = visitor.getVisitTime();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = dateFormat.parse(visitTime);
            long l = (date.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
            visitor.setDayage(l);
        }
        return select;
    }

    @Override
    public int savecata1ToUserAndTags(Integer id, Integer id1) {

        UserAndTag u = new UserAndTag();
        u.setuId(id1);
        UserAndTag userAndTag1 = userAndTagMapper.selectOne(u);
        if (userAndTag1 == null) {
            UserAndTag userAndTag = new UserAndTag();
            userAndTag.setuId(id1);
            userAndTag.setCatalogId(id);
            return userAndTagMapper.insertSelective(userAndTag);
        }else {
            Example example = new Example(UserAndTag.class);
            example.createCriteria().andEqualTo("uId", id1);
            UserAndTag userAndTag = new UserAndTag();
            userAndTag.setCatalogId(id);
            return userAndTagMapper.updateByExampleSelective(userAndTag, example);
        }
    }

    @Override
    public int savebestBusinessToUserAndTags(String text, Integer id) {

        UserAndTag u = new UserAndTag();
        u.setuId(id);
        UserAndTag userAndTag1 = userAndTagMapper.selectOne(u);
        if (userAndTag1 == null) {
            UserAndTag userAndTag = new UserAndTag();
            userAndTag.setuId(id);
            userAndTag.setTheBestBusiness(text);
            return userAndTagMapper.insertSelective(userAndTag);
        }else {
            Example example = new Example(UserAndTag.class);
            example.createCriteria().andEqualTo("uId", id);
            UserAndTag userAndTag = new UserAndTag();
            userAndTag.setTheBestBusiness(text);
            return userAndTagMapper.updateByExampleSelective(userAndTag, example);
        }
    }

    @Override
    public int saveotherBusinessToUserAndOther(String text, Integer id) {
        UserAndOtherBusiness userAndOtherBusiness = new UserAndOtherBusiness();
        userAndOtherBusiness.setOtherBusiness(text);
        userAndOtherBusiness.setuId(id);
        return userAndOtherBusinessMapper.insert(userAndOtherBusiness);
    }

    @Override
    public UserAndTag getFirstAndSecondSkill(Integer id) {
        UserAndTag userAndTag = new UserAndTag();
        userAndTag.setuId(id);
        return userAndTagMapper.selectOne(userAndTag);
    }

    @Override
    public List<String> getOtherBusiness(Integer id) {
        List<String> list = new ArrayList<>();
        UserAndOtherBusiness userAndOtherBusiness = new UserAndOtherBusiness();
        userAndOtherBusiness.setuId(id);
        List<UserAndOtherBusiness> select = userAndOtherBusinessMapper.select(userAndOtherBusiness);
        for (UserAndOtherBusiness andOtherBusiness : select) {
            list.add(andOtherBusiness.getOtherBusiness());
        }
        return list;
    }

    @Override
    public int delFirstCata(Integer id, String cataName) {
        UserAndTag userAndTag = new UserAndTag();
        userAndTag.setCatalogId(NULL);
        Example example = new Example(UserAndTag.class);
        example.createCriteria().andEqualTo("uId", id);
        return userAndTagMapper.updateByExampleSelective(userAndTag,example);
    }

    @Override
    public int delSecondCata(Integer id, String cataName) {
        UserAndTag userAndTag = new UserAndTag();
        userAndTag.setTheBestBusiness("");
        Example example = new Example(UserAndTag.class);
        example.createCriteria().andEqualTo("uId", id);
        return userAndTagMapper.updateByExampleSelective(userAndTag,example);
    }

    @Override
    public int delThirdCata(Integer id, String cataName) {
        UserAndOtherBusiness userAndOtherBusiness = new UserAndOtherBusiness();
        userAndOtherBusiness.setOtherBusiness(cataName);
        userAndOtherBusiness.setuId(id);
        int delete = userAndOtherBusinessMapper.delete(userAndOtherBusiness);
        return delete;
    }
}
