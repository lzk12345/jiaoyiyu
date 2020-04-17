package com.jiaoyiyu.back.personcenter.controller.accountmanagementcontroller;

import com.jiaoyiyu.back.annotation.LoginRequired;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * AutonymController class
 * 实名认证
 * @author maochaoying
 * @date 2019/11/12
 */
@CrossOrigin
@Controller
@RequestMapping("/autonym")
public class AutonymController {

    @RequestMapping
    @LoginRequired(loginSuccess = true)
    public String index() {
        return "autonym";
    }


    @RequestMapping("/checkIdCardAndHead")
    @ResponseBody
    public String checkIdCardAndHead(@RequestParam("idcard") String idcard, @RequestParam("name") String name, @RequestParam("image") String image) throws IOException {
        String url = "https://faceidcardb.shumaidata.com/getfaceidb";
        String appCode = "3423464d4a1f487ba6844e1589bbe1ff";
        Map<String, String> params = new HashMap<>();
        params.put("idcard", idcard);
        params.put("name", name);
        params.put("image", image);
        String result = postForm(appCode, url, params);
        return result;
    }


    public String postForm(String appCode, String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            formbuilder.add(key, params.get(key));
        }
        FormBody body = formbuilder.build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
        Response response = client.newCall(request).execute();
        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
        String result = response.body().string();
        return result;
    }

}
