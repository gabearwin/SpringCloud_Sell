package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    // @HystrixCommand(fallbackMethod = "fallback")
    /*@HystrixCommand(commandProperties = {
            // 具体配置可以看这个类 -> HystrixCommandProperties  配置可以写到配置文件中

            // 默认执行超时时间是1000毫秒
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            // 服务熔断
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })*/
    @HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        // 1、调用其他服务，其他服务发生异常可以触发降级
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8081/product/listForOrder",
                Arrays.asList("157875196366160022"), String.class);

        // 2、自身服务发生异常也可以触发降级
        // throw new RuntimeException("发生异常了");
    }

    private String fallback() {
        return "太拥挤了，请稍后再试~~";
    }

    // 当前类的默认触发降级后执行的方法
    private String defaultFallback() {
        return "默认提示：太拥挤了，请稍后再试~~";
    }

}
