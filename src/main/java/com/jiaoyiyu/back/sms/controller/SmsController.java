package com.jiaoyiyu.back.sms.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jiaoyiyu.util.CodeUtils;
import com.jiaoyiyu.util.MailUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/sms")
@CrossOrigin
public class SmsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAI4FtMzAnwciPRqZzwz7Bw";
    static final String accessKeySecret = "ka3ZMhNDsqdbCH98l1zKlpCSC3HNZZ";

    /**
     * 发送验证码服务
     * @param phonenum
     * @return
     * @throws ClientException
     * @throws InterruptedException
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(@RequestParam("phonenum") String phonenum) throws ClientException, InterruptedException {
        String checkcode = sendMsg(phonenum);
//        Jedis jedis = null;
//        try{
//            jedis = redisUtil.getJedis();
//            // 加缓存 过期时间一分钟
//            jedis.setex("checkcode_" + phonenum, 60, checkcode);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            jedis.close();
//        }
        return checkcode;
    }

    /**
     * 发送邮件
     * @param emailAddress
     * @return
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public int sendEmail(@RequestParam("emailAddress") String emailAddress) {
        String randEnglishCode = CodeUtils.getRandEnglishCode(20);
//        Jedis jedis = null;
//        try{
//            jedis = redisUtil.getJedis();
//            // 加缓存 过期时间30分钟
//            jedis.setex("email_checkcode_" + emailAddress, 60 * 30, randEnglishCode);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            jedis.close();
//        }
        MailUtil mailUtil = new MailUtil(emailAddress,randEnglishCode);
        mailUtil.run();
        return 1;
    }

    @RequestMapping("/checkEmailCode")
    public String checkEmailCode(HttpServletRequest request) {
        String code = request.getParameter("code");
        String emailAddress = request.getParameter("emailAddress");
//        Jedis jedis = null;
//        try{
//            jedis = redisUtil.getJedis();
//            // 加缓存 过期时间一分钟
//            String s = jedis.get("email_checkcode_" + emailAddress);
//            if (StringUtils.isNotBlank(s)) {
//                if (s.equals(code)) {
//                    return "redirect:http://localhost:80/emailAuthentication?sign=1";
//                }
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            jedis.close();
//        }
        return "redirect:http://121.36.85.218:9999/emailAuthentication?sign=0";
    }


    public String sendMsg(String phonenum) throws ClientException, InterruptedException {
            String checkcode = RandomStringUtils.randomNumeric(6);
            //发短信

            SendSmsResponse response = sendSms(phonenum, checkcode);
            logger.info("{}", "短信接口返回的数据----------------");
            logger.info("Code={}" , response.getCode());
            logger.info("Message={}" ,response.getMessage());
            logger.info("RequestId={}" , response.getRequestId());
            logger.info("BizId=" , response.getBizId());

            Thread.sleep(3000L);

            //查明细
            if(response.getCode() != null && response.getCode().equals("OK")) {
                QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
                logger.info("{}" , "短信明细查询接口返回数据----------------");
                logger.info("Code=" , querySendDetailsResponse.getCode());
                logger.info("Message=" , querySendDetailsResponse.getMessage());
                int i = 0;
                for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                {
                    logger.info("SmsSendDetailDTO[{}]:" , i);
                    logger.info("Content=" , smsSendDetailDTO.getContent());
                    logger.info("ErrCode=" , smsSendDetailDTO.getErrCode());
                    logger.info("OutId=" , smsSendDetailDTO.getOutId());
                    logger.info("PhoneNum=" , smsSendDetailDTO.getPhoneNum());
                    logger.info("ReceiveDate=" , smsSendDetailDTO.getReceiveDate());
                    logger.info("SendDate=" , smsSendDetailDTO.getSendDate());
                    logger.info("SendStatus=" , smsSendDetailDTO.getSendStatus());
                    logger.info("Template=" , smsSendDetailDTO.getTemplateCode());
                }
                logger.info("TotalCount=" , querySendDetailsResponse.getTotalCount());
                logger.info("RequestId=" , querySendDetailsResponse.getRequestId());
            }
        return checkcode;
    }

    public static SendSmsResponse sendSms(String phonenum,String checkcode) throws ClientException {



        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phonenum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("交易鱼");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_176938975");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\""+checkcode+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
}
