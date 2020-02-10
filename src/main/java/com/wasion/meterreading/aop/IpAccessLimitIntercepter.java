package com.wasion.meterreading.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.annotation.IpAccessLimit;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.service.IpAccessCacheService;
import com.wasion.meterreading.util.ContextProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * IP 限流拦截器
 * 
 * @author w24882
 * @date 2019年11月15日
 */
@ConditionalOnProperty(name = "ipaccess.limit.enable", havingValue = "true")
@Aspect
@Component
@Slf4j
public class IpAccessLimitIntercepter {
    @Autowired
    private IpAccessCacheService cacheService;

    @Pointcut(value = "@within(com.wasion.meterreading.annotation.IpAccessLimit) || @annotation(com.wasion.meterreading.annotation.IpAccessLimit)")
    public void ipAccessLimitPointCut() {
    };

    @Before(value = "ipAccessLimitPointCut() && @annotation(limit)")
    public void requestLimitAround(IpAccessLimit limit) throws Throwable {
        // 限制访问次数
        int count = limit.count();

        // 获取 request ， 然后获取访问 ip
        HttpServletRequest request = ContextProvider.getCurrentRequest();
        String requestIp = IpAccessLimitIntercepter.getRequestIp(request);
        if (StringUtils.isEmpty(requestIp)) {
            throw new MRException(MRExceptionEnum.InSecureIpBlank);
        }

        // 获取request 的请求路径
        String requestURI = request.getRequestURI();
        String key = "_" + requestURI + "_" + requestIp + "_";

        final int realCount = cacheService.count(key);
        if (realCount > count) {
            throw new MRException(MRExceptionEnum.IPAccessExccedUpperLimit);
        }
        // 将访问存进缓存
        cacheService.add(key + System.currentTimeMillis(), "1", limit.timeUnit(), limit.t());
        log.debug("Go through ip access limit intercepter. Real time count for key: {} is {}.", key, realCount);
    }

    public static String getRequestIp(HttpServletRequest request) {
        // 获取请求IP
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equals(ip)) {
            ip = "" + request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equals(ip)) {
            ip = "" + request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equals(ip)) {
            ip = "" + request.getRemoteAddr();
        }
        return ip;
    }

}
