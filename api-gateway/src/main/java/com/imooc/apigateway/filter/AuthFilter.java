package com.imooc.apigateway.filter;

import com.imooc.apigateway.constant.CookieConstant;
import com.imooc.apigateway.constant.RedisConstant;
import com.imooc.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限过滤器，区分卖家和买家
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 前置过滤
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小优先级越高
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体过滤逻辑
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        /**
         * /order/create 只能买家访问(cookie里有openid)
         * /order/finish 只能卖家访问(cookie里有token，并且redis中有对应的值)
         * /product/list 都可以访问
         */
        /*if ("/order/order/create".equals(request.getRequestURI())) {
            Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
            if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        if ("/order/order/finish".equals(request.getRequestURI())) {
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            if (cookie == null || StringUtils.isBlank(cookie.getValue())
                    || StringUtils.isBlank(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }*/

        return null;
    }
}
