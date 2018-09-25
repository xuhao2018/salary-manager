package com.manager.salarymanager.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


     // 自定义  httpMessageConverter 用于解析 请求和响应 中 对象和json的转换
     @Bean
 public HttpMessageConverter httpMessageConverters(){

         MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
         // 解决 spring mvc 默认 的 conversor 无法解析 懒加载 mybatis 查询对象为json
         ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
         objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false) ;
         httpMessageConverter.setObjectMapper(objectMapper);
         return httpMessageConverter ;
     }
}
