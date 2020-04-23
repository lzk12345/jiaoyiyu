package com.jiaoyiyu.back.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jiaoyiyu.back.annotation.LoginRequired;
import com.jiaoyiyu.back.bean.Settlement;
import com.jiaoyiyu.back.bean.Taskorder;
import com.jiaoyiyu.back.config.AlipayConfig;
import com.jiaoyiyu.back.coreservice.Impl.WeixinPayServiceImpl;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.CookieUtils;
import com.jiaoyiyu.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * PaymentController class
 *
 * @author maochaoying
 * @date 2019/9/28
 */

@Controller
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    AlipayClient alipayClient;
    @Autowired
    PublishTaskService publishTaskService;
    @Autowired
    WeixinPayServiceImpl weixinPayServiceImpl;
    @Autowired
    IdWorker idWorker;

    /**
     * 进入选择支付方式的页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    @LoginRequired(loginSuccess = true)
    public String index(HttpServletRequest request, ModelMap modelMap) {
        String orderSn = request.getParameter("orderSn");
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        if (assignmentId == null) {
            assignmentId = request.getParameter("assignmentId");
        }
        Settlement settlement = publishTaskService.getSettlementInfoByAssId(assignmentId);
        if (settlement != null) {
            BigDecimal totalAmount = settlement.getTotalAmount();
            modelMap.put("totalAmount", totalAmount);
        }
        modelMap.put("orderId", orderSn);
        return "paymentindex";
    }

    @RequestMapping("/TouBiaoindex")
    @LoginRequired(loginSuccess = true)
    public String TouBiaoindex(HttpServletRequest request, ModelMap modelMap) {
        String orderSn = request.getParameter("orderSn");
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        Taskorder taskorder = publishTaskService.getTaskPriceFromTaskOrderByOrderSn(orderSn);
        modelMap.put("totalAmount", taskorder.getOrderprice());
        modelMap.put("orderId", orderSn);
        return "ZhongBiaoPay";
    }

    /**
     * 微信回调
     *
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    @RequestMapping("/mx/submit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public Map<String, String> mxSubmit(String outTradeNo, String totalAmount) {
        return weixinPayServiceImpl.createNative(outTradeNo, totalAmount);
    }

    /**
     * 支付宝页面的生成
     *
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    @RequestMapping("/alipay/submit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String alipaySubmit(String outTradeNo, String totalAmount) throws IOException {
        String form = null;
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        Map<String, Object> map = new HashMap<>();
        alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        map.put("out_trade_no", outTradeNo);
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", 0.01);
        map.put("subject", "交易鱼");
        String param = JSON.toJSONString(map);
        alipayTradePagePayRequest.setBizContent(param);
        try {
            form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 支付宝页面的生成
     *
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    @RequestMapping("/alipay/zhongbiaosubmit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String alipayZhongBiaoSubmit(String outTradeNo, String totalAmount) throws IOException {
        String form = null;
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        Map<String, Object> map = new HashMap<>();
        alipayTradePagePayRequest.setReturnUrl("http://121.36.85.218:80/payment/alipay/callback/return/zhongbiao");
        alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        map.put("out_trade_no", outTradeNo);
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", 0.01);
        map.put("subject", "交易鱼");
        String param = JSON.toJSONString(map);
        alipayTradePagePayRequest.setBizContent(param);
        try {
            form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 支付宝同步回调
     *
     * @return
     */
    @RequestMapping("/alipay/callback/return")
//    @LoginRequired(loginSuccess = true)
    public String AlipaycallBackReturn() {
        //跳转到支付成功的页面。

//        return "redirect:/fabao/taskPage";
        return "redirect:http://localhost:80/shejishi/index";
    }

    /**
     * 支付宝同步回调
     *
     * @return
     */
    @RequestMapping("/alipay/callback/return/zhongbiao")
//    @LoginRequired(loginSuccess = true)
    public String AlipaycallBackReturnZhongBiao(HttpServletRequest request) {
        //跳转到支付成功的页面。
        // 设置该任务中标
        String manuscriptId = CookieUtils.getCookieValue(request, "manuscriptId", true);
        publishTaskService.setIsBiddingByManuScriptId(manuscriptId);
        String assignmentId = CookieUtils.getCookieValue(request, "assignmentId", true);
        int res = publishTaskService.setAssignmentScheduleStatus(assignmentId);
        return "redirect:http://localhost:80/shejishi/index?sign=1";
    }

    /**
     * 支付宝异步通知
     *
     * @return
     */
    @RequestMapping("alipay/callback/notify")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String AlipaycallBackNotify() {

        return "success";
    }


    @RequestMapping("/error")
    public String errorPage() {
        return "error/error";
    }

}