package com.jiaoyiyu.back.config;

import com.jiaoyiyu.back.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注意一定不要加@Configuration注解
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/css/**","/img/**","/js/**","/vide/**","/laydate/theme/default/**"
        ,"/layui/css/modules/laydate/default/**","/layui/css/modules/layer/default/**","/layui/css/modules/**","/layui/css/**","/layui/font/**"
        ,"/layui/images/face/**","/layui/lay/modules/**","/layui/**","/login_img/**","/laydate/**","/laydate/*.js","/laydate/theme/default/*.css","/editor/**");
    }
}