package com.imooc.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imooc.order.utils.JsonUtil;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductInfoReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // 1、message =>> ProductInfoOutput
        /*ProductInfoOutput productInfoOutput = JsonUtil.toBean(message, ProductInfoOutput.class);
        log.info("从队列接收到消息：{}", productInfoOutput);
        // 2、存储到Redis中
        stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                String.valueOf(productInfoOutput.getProductStock()));*/

        List<ProductInfoOutput> productInfoOutputs = JsonUtil.toList(message, new TypeReference<List<ProductInfoOutput>>() {});
        log.info("从队列接收到消息：{}", productInfoOutputs);
        // 存储到Redis中
        for (ProductInfoOutput infoOutput : productInfoOutputs) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, infoOutput.getProductId()),
                    String.valueOf(infoOutput.getProductStock()));
        }
    }
}
