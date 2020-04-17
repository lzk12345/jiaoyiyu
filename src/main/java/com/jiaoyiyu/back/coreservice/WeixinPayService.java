package com.jiaoyiyu.back.coreservice;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * WeixinPayService class
 *
 * @author maochaoying
 * @date 2019/11/27
 */
public interface WeixinPayService {

    /**
     * 生成微信支付二维码
     * @param out_trade_no 订单号
     * @param total_fee 金额(分)
     * @return
     */
    public Map<String, String> createNative(String out_trade_no, String total_fee);
}
