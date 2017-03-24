package com.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
//@EnableDiscoveryClient
//必须添加@EnableResourceServer，Zuul才会进行Token Relay。
//(查看各种源码后才发现。文档描述的@EnableOAuth2Sso根本没有什么卵用。只有
//@EnableResourceServer才会加载OAuth2AuthenticationProcessingFilter)
//@EnableResourceServer
//@EnableAuthorizationServer
public class SpringCloudZuulGetwayApplication {

//    @Bean
//    AccessFilter accessFilter() {
//        return new AccessFilter();
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulGetwayApplication.class, args);
    }


}
