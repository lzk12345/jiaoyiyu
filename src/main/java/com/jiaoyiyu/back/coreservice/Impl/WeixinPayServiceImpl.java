package com.jiaoyiyu.back.coreservice.Impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.jiaoyiyu.back.coreservice.WeixinPayService;

import com.jiaoyiyu.util.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * WeixinPayServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/27
 */

@Service
@PropertySource("classpath:weixinpay.properties")
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;
    /**
     * 生成二维码
     * @return
     */
    @Override
    public Map<String, String> createNative(String out_trade_no, String total_fee) {
        //1.创建参数
        Map<String,String> param=new HashMap();//创建参数
        param.put("appid", appid);//公众号
        param.put("mch_id", partner);//商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", "交易鱼");//商品描述
        param.put("out_trade_no", out_trade_no);//商户订单号
        param.put("total_fee",total_fee);//总金额（分）
        param.put("spbill_create_ip", "127.0.0.1");//IP
        param.put("notify_url", "http://test.itcast.cn");//回调地址(随便写)
        param.put("trade_type", "NATIVE");//交易类型
        try {
            //2.生成要发送的 xml
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println(xmlParam);
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            StringEntity entity = new StringEntity(xmlParam);
            String result = HttpClientUtils.post(url, null, null, entity);
            System.out.println(result);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            Map<String, String> map=new HashMap<>();
            map.put("code_url", resultMap.get("code_url"));//支付地址
            map.put("total_fee", total_fee);//总金额
            map.put("out_trade_no",out_trade_no);//订单号
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
