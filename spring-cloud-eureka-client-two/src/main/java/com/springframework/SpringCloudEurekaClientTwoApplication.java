package com.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class SpringCloudEurekaClientTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaClientTwoApplication.class, args);
    }

//    @Bean
//    LoadBalancerInterceptor loadBalancerInterceptor(LoadBalancerClient loadBalance){
//        return new LoadBalancerInterceptor(loadBalance);
//    }
}
