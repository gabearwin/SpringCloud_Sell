package com.imooc.order.client;

import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 这个类只是个示例，实际上使用的接口应写在product服务中
/**
 * Feign使用步骤：
 * 1.加入依赖(spring-cloud-starter-feign)，启动主类加上注解(@EnableFeignClients)
 * 2.申明客户端，就像下面这样
 * 3.用的时候直接自动注入，调用接口方法就行
 */
@FeignClient(name = "product")
public interface ProductClientDemo {

    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
