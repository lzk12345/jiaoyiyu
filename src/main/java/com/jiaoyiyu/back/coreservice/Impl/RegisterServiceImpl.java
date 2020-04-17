package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.coreservice.RegisterService;
import org.springframework.stereotype.Service;

/**
 * RegisterServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/8
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Override
    public String registerByMsg(String phonenum, String messageCode) {
//        Jedis jedis = null;
        String result = "";
//        try {
//            jedis = redisUtil.getJedis();
//            String code = jedis.get("checkcode_" + phonenum);
//            if (code == null) {
//                result = "验证码已过期";
//            }else {
//                if(!code.equals(messageCode)) {
//                    result = "验证码不正确";
//                }else {
//                    result = "success";
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }finally {
//            jedis.close();
//        }
        return result;
    }

    @Override
    public void delCodeCache(String phonenum, String messageCode) {
//        Jedis jedis = null;
//        try {
//            jedis = redisUtil.getJedis();
//            jedis.del("checkcode_" + phonenum);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            jedis.close();
//        }
    }
}
