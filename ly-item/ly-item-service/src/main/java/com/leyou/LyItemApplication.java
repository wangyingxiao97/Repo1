package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */

@EnableEurekaClient
@SpringBootApplication
public class LyItemApplication {

    public static void main(String[] args){
        SpringApplication.run(LyItemApplication.class);
    }
}
