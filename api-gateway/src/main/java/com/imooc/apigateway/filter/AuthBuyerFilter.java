package com.imooc.apigateway.filter;

import com.imooc.apigateway.constant.CookieConstant;
import com.imooc.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 买家权限过滤器
 */
@Component
public class AuthBuyerFilter extends ZuulFilter {

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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 这里具体的地址配置可选的方式：
        // 1、调用user服务读取用户数据库，得到权限
        // 2、user服务将权限放入Redis中，API-gateway读取Redis
        if ("/order/order/create".equals(request.getRequestURI())) {
            return true;
        }
        return false;
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
         */
        Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
        if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
