package com.shisj.study.zuul.gateway.filter;

import com.netflix.discovery.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 类型 有四种： pre -> route -> post [error 发生错误时]
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() { // 是否应该进入过滤器
        RequestContext ctx = RequestContext.getCurrentContext();
        String uri = ctx.getRequest().getRequestURI();
        System.out.println("uri = " +ctx);
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            // 不发送请求
            ctx.setSendZuulResponse(false); //
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            // set service
//            ctx.put(FilterConstants.SERVICE_ID_KEY, "${serviceid}");
        }
        return null;
    }
}
