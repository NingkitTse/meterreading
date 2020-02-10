package com.wasion.meterreading.service.impl.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.orm.jpa.TCfgAppsConfigRepository;
import com.wasion.meterreading.service.CacheService;
import com.wasion.meterreading.util.ContextProvider;

/**
 * App配置缓存类
 * 
 * @author w24882
 * @date 2019年11月13日
 */
@Service
public class AppConfigCacheService implements CacheService<TCfgAppsConfig> {

	private static final Logger LOG = LoggerFactory.getLogger(AppConfigCacheService.class);

	private static final Map<String, TCfgAppsConfig> map = new HashMap<>();

	@Override
	public void init() {
		LOG.debug("Start to init middle platform app config cache.");
		TCfgAppsConfigRepository repository = ContextProvider.getBean(TCfgAppsConfigRepository.class);
		Iterable<TCfgAppsConfig> configs = repository.findAll();
		for (TCfgAppsConfig config : configs) {
			map.put(config.getAppid(), config);
		}
	}

	/**
	 * 通过APP_VALUE查询Config
	 */
	@Override
	public TCfgAppsConfig getValue(String key) {
		return map.get(key);
	}

}
