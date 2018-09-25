package com.manager.salarymanager.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.manager.salarymanager.mapper")
@EnableTransactionManagement
public class DbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
         return new DruidDataSource() ;
    }


     // druid 后台管理 的 servlet
    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean(){

        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");

        Map<String,String> map = new HashMap<>() ;
        map.put("loginUsername","admin");
        map.put("loginPassword","123") ;

        registrationBean.setInitParameters(map);

        return  registrationBean ;
    }


//       拦截 带有 sql 的请求
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean(){

        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String,String> map = new HashMap<>();
        filterRegistrationBean.setInitParameters(map);
        map.put("exclusions","*.js,*.css,/druid/*") ;
        return filterRegistrationBean ;
    }


}
