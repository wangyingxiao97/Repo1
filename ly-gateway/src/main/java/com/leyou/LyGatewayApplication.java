package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */

@EnableZuulProxy
@SpringCloudApplication
public class LyGatewayApplication {

    public static void main(String[] args){
        SpringApplication.run(LyGatewayApplication.class,args);
    }

}
