# 服务注册中心 eureka
- 启动类注解`@EnableEurekaServer`
- 配置文件
    ```text
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
            register-with-eureka: false
          server:
            enable-self-preservation: false
        spring:
          application:
            name: eureka
        server:
          port: 8761
    ```

# 全局配置中心 config
- 启动类注解`@EnableDiscoveryClient` 和 `@EnableConfigServer`
- 配置文件
    ```text
        spring:
          application:
            name: config
          cloud:
            config:
              server:
                git:
                  # Github上配置webhooks，地址http://10.5.155.0:8080/monitor
                  uri: https://github.com/gabearwin/config-repo
                  #存放到本地地址
                  basedir: /GitProject/SpringCloud_Sell/config-repo
          rabbitmq:
            host: 10.57.211.134
            port: 5672
            username: guest
            password: guest
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
        
        #暴露出接口，类似: [/actuator/refresh],methods=[POST]
        management:
          endpoints:
            web:
              exposure:
                include: '*'
    ```

# 商品服务 product
- 启动类注解
- 配置文件
    ```text
        spring:
          application:
            name: product
          cloud:
            config:
              discovery:
                enabled: true
                service-id: CONFIG
              profile: dev
            stream:
              bindings:
                myMessage:
                  #对消息进行分组：当前项目部署多个实例时，只会有一个实例处理消息
                  group: product
                  content-type: application/json
          datasource:
            driver-class-name: com.mysql.jdbc.Driver
            username: root
            password: root
            url: jdbc:mysql://127.0.0.1:3306/sell?characterEncoding=utf-8&useSSL=false
          rabbitmq:
            host: 10.57.211.134
            port: 5672
            username: guest
            password: guest
          zipkin:
            base-url: http://10.57.211.134:9411/
            sender:
              type: web
          sleuth:
            sampler:
              probability: 1
        
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
        
        env:
          dev
    ```

# 订单服务 order
- 启动类注解
- 配置文件
    ```text
        spring:
          application:
            name: order
          cloud:
            config:
              discovery:
                enabled: true
                service-id: CONFIG
              profile: dev
            stream:
              bindings:
                myMessage:
                  #对消息进行分组：当前项目部署多个实例时，只会有一个实例处理消息
                  group: order
                  content-type: application/json
          datasource:
            driver-class-name: com.mysql.jdbc.Driver
            username: root
            password: root
            url: jdbc:mysql://127.0.0.1:3306/sell?characterEncoding=utf-8&useSSL=false
          rabbitmq:
            host: 10.57.211.134
            port: 5672
            username: guest
            password: guest
          zipkin:
            base-url: http://10.57.211.134:9411/
            sender:
              type: web
          sleuth:
            sampler:
              probability: 1
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
        #先要注册到eureka上，再通过config找到配置服务，然后通过order和test找到配置
        
        hystrix:
          command:
            default:
              execution:
                isolation:
                  thread:
                    timeoutInMilliseconds: 3000
            getProductInfoList:
              execution:
                isolation:
                  thread:
                    timeoutInMilliseconds: 3000
        feign:
          hystrix:
            enabled: true
          client:
            config:
              default:
                connectTimeout: 5000
                readTimeout: 5000
                loggerLevel: basic
        
        logging:
          level:
            org.springframework.cloud.openfeign: debug
            com.imooc.order.controller.HystrixController: debug
        
        management:
          endpoints:
            web:
              exposure:
                include: '*'
        
        env:
          dev
        girl:
          name: yan
          age: 25
    ```

# 用户服务 user
- 启动类注解
- 配置文件
    ```text
        spring:
          application:
            name: user
          cloud:
            config:
              discovery:
                enabled: true
                service-id: CONFIG
              profile: dev
            stream:
              bindings:
                myMessage:
                  #对消息进行分组：当前项目部署多个实例时，只会有一个实例处理消息
                  group: user
                  content-type: application/json
          datasource:
            driver-class-name: com.mysql.jdbc.Driver
            username: root
            password: root
            url: jdbc:mysql://127.0.0.1:3306/sell?characterEncoding=utf-8&useSSL=false
          rabbitmq:
            host: 10.57.211.134
            port: 5672
            username: guest
            password: guest
          redis:
            host: 127.0.0.1
            port: 6379
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
    ```

## 网关服务 api-gateway
- 启动类注解
- 配置文件
    ```text
        spring:
          application:
            name: api-gateway
          cloud:
            config:
              discovery:
                enabled: true
                service-id: CONFIG
              profile: dev
        eureka:
          client:
            service-url:
              defaultZone: http://localhost:8761/eureka/
        zuul:
          # 设置全部服务的敏感头(都可以传递cookie)
          sensitive-headers:
          routes:
            # 自定义某些路由 /myProduct/product/list -> /product/product/list
            aaaaaa:
              path: /myProduct/**
              serviceId: product
              # 默认路由转发之后没有cookie、Authorization等，将sensitiveHeaders设置为空后请求转发就会带上原有的信息
              sensitiveHeaders:
              # 简洁写法
          # product: /myProduct/**
        
          # 排除某些路由
          ignored-patterns:
          - /**/product/listForOrder
        
        ribbon:
          ReadTimeout: 5000
          SocketTimeout: 5000
    ```