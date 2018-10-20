package com.imooc.order.controller;

import com.imooc.order.client.ProductClientDemo;
import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    // ***********RestTemplate的使用示例*********** //

    // 第一种方式：直接使用restTemplate，URL写死
    @GetMapping("/getProductMsg1")
    public String getProductMsg1() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8081/msg", String.class);
        log.info("result = {}", result);
        return result;
    }


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    // 第二种方式：利用loadBalancerClient通过应用名获取URL，然后使用restTemplate
    @GetMapping("/getProductMsg2")
    public String getProductMsg2() {
        // product应用可能有多个实例，而且我们还不知道他的IP
        ServiceInstance productService = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s/msg", productService.getHost(), productService.getPort());
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("result = {}", result);
        return result;
    }


    @Autowired
    private RestTemplate restTemplate;

    // 第三种方式：利用@LoadBalanced，可在restTemplate里使用应用名字
    @GetMapping("/getProductMsg3")
    public String getProductMsg3() {
        String result = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        log.info("result = {}", result);
        return result;
    }


    // ***********Feign的使用示例*********** //

    // 编辑器报错无法自动注入，但是实际上没问题
    @Autowired
    private ProductClientDemo productClientDemo;

    @GetMapping("/getProductMsg4")
    public String getProductMsg4() {
        String result = productClientDemo.productMsg();
        log.info("result = {}", result);
        return result;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfos = productClientDemo.listForOrder(Arrays.asList("164103465734242707"));
        log.info("result = {}", productInfos);
        return "OK";
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock() {
        productClientDemo.decreaseStock(Collections.singletonList(new CartDTO("164103465734242707", 3)));
        return "OK";
    }


}
