package com.jiaoyiyu.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class OcrUtil {
    // Access_Token获取
    private static final String ACCESS_TOKEN_HOST = "https://aip.baidubce.com/oauth/2.0/token?";
    // 身份证识别请求URL
    private static final String OCR_HOST = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?";
    // apiKey,secretKey
    private static final String API_KEY ="1YX4IbZzImg6f3zBxsL0TX5G";
    private static final String SECRET_KEY = "zaWdG9lFSW79Elz1twpPenrdhx1bohrq";

    // 获取百度云OCR的授权access_token
    public static String getAccessToken() {
        return getAccessToken(API_KEY, SECRET_KEY);
    }
 
    /**
     * 获取百度云OCR的授权access_token
     * @param apiKey
     * @param secretKey
     * @return
     */
    public static String getAccessToken(String apiKey, String secretKey) {
        String accessTokenURL = ACCESS_TOKEN_HOST
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + apiKey
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + secretKey;
 
        try {
            URL url = new URL(accessTokenURL);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
 
            // 获取响应头
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                System.out.println(key + "---->" + map.get(key));
            }
 
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                result.append(inputLine);
            }
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            return jsonObject.getString("access_token");
 
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("获取access_token失败");
        }
        return null;
    }


    public static void main(String[] args) {
        String accessToken = getAccessToken(API_KEY, SECRET_KEY);
        System.out.println(accessToken);
    }

}