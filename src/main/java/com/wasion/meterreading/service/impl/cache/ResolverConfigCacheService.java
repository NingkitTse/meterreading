package com.wasion.meterreading.service.impl.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wasion.meterreading.entity.TCfgIotResolverValue;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.service.CacheService;
import com.wasion.meterreading.util.ContextProvider;
import com.wasion.meterreading.util.ParseUtil;

/**
 * 电信服务解析配置缓存加载类
 * 
 * @author w24882
 * @date 2019年11月13日
 */
@ConfigurationProperties(prefix = "resolver.config")
@Service
public class ResolverConfigCacheService implements CacheService<TCfgIotResolverValue> {

    private static final Logger LOG = LoggerFactory.getLogger(ResolverConfigCacheService.class);

    private Map<String, TCfgIotResolverValue> map = new HashMap<>();

    public Map<String, TCfgIotResolverValue> getMap() {
        return map;
    }

    public void setMap(Map<String, TCfgIotResolverValue> map) {
        this.map = map;
    }

    @Override
    public void init() {
        LOG.debug("Load config from properties: {}", map);
        // 没必要放数据库了，不够直观，而且切换一套环境还要同步修改数据库的配置，改动点太多了。
        // LOG.debug("Start to init middle platform app config cache.");
        // TCfgTeleServiceRepository repository =
        // ContextProvider.getBean(TCfgTeleServiceRepository.class);
        // Iterable<TCfgIotResolverValue> configs = repository.findAll();
        // 用于补充需要兼容的配置
        Map<String, TCfgIotResolverValue> tmpMap = new HashMap<>();
        for (Entry<String, TCfgIotResolverValue> entry : map.entrySet()) {
            TCfgIotResolverValue config = entry.getValue();
            String resolverId = config.getResolverId();
            String resolverClassPath = config.getResolverClassPath();
            String serviceDataClassPath = config.getServiceDataClassPath();
            try {
                Class<?> resolverClass = Class.forName(resolverClassPath);
                config.setResolverClass(resolverClass);
            } catch (ClassNotFoundException e) {
                LOG.error("Load resolver<{}> failed, for config class path<{}> not found.", resolverId, resolverClassPath);
                throw new MRException(MRExceptionEnum.SystemConfigurationException);
            }

            processDataClass(config, resolverId, serviceDataClassPath);
            // 移动要兼容F300 与 00F3等
            if (resolverId.length() == 4) {
                tmpMap.put(ParseUtil.stringToBcd(resolverId), config);
            }
            // map.put(config.getResolverId(), config);
            LOG.info("Load config successs, config is: {}.", config);
        }
        map.putAll(tmpMap);
    }

    private void processDataClass(TCfgIotResolverValue config, String resolverId, String serviceDataClassPath) {
        if (StringUtils.isEmpty(serviceDataClassPath)) {
            return;
        }
        List<Class<?>> serviceDataClasses = new ArrayList<>();
        if (serviceDataClassPath.indexOf(",") != -1) {
            String[] classPathArr = serviceDataClassPath.split(",");
            for (String classPath : classPathArr) {
                if (StringUtils.isEmpty(classPath)) {
                    continue;
                }
                Class<?> serviceDataClass = loadDataClass(resolverId, classPath);
                config.setServiceDataClass(serviceDataClass);
                serviceDataClasses.add(serviceDataClass);
            }
        } else {
            Class<?> serviceDataClass = loadDataClass(resolverId, serviceDataClassPath);
            serviceDataClasses.add(serviceDataClass);
        }
        config.setServiceDataClasses(serviceDataClasses);
        if (serviceDataClasses.size() > 0) {
            config.setServiceDataClass(serviceDataClasses.get(0));
        }
    }

    private Class<?> loadDataClass(String resolverId, String classPath) {
        Class<?> serviceDataClass = null;
        try {
            serviceDataClass = Class.forName(classPath);
        } catch (ClassNotFoundException e1) {
            LOG.error("Load resolver<{}> failed, for config class path<{}> not found.", resolverId, classPath);
            throw new MRException(MRExceptionEnum.SystemConfigurationException);
        }
        return serviceDataClass;
    }

    /**
     * 通过APP_VALUE查询Config
     */
    @Override
    public TCfgIotResolverValue getValue(String key) {
        return map.get(key);
    }

    /**
     * 获取Resolver
     * 
     * @param id ResolverId
     * @param interfaceClass Resolver实现的接口Class
     * @return 返回接口
     * @author w24882 xieningjie
     * @date 2019年11月21日
     */
    @SuppressWarnings("unchecked")
    public <T> T getResolver(String id, Class<T> interfaceClass) {
        TCfgIotResolverValue tCfgIotResolverValue = map.get(id);
        if (null == tCfgIotResolverValue) {
            LOG.error("Resolver config of resolverId<{}> is not exist.", id);
            throw new MRException(MRExceptionEnum.SystemConfigurationException);
        }
        Class<?> resolverClass = tCfgIotResolverValue.getResolverClass();
        Object bean = ContextProvider.getBean(resolverClass);
        // 如果不是接口的实例则抛出异常
        if (!interfaceClass.isAssignableFrom(bean.getClass())) {
            LOG.error("Target bean is not a instance of the target class: {}", interfaceClass);
            throw new MRException(MRExceptionEnum.SystemConfigurationException);
        }
        return (T) bean;
    }

}
