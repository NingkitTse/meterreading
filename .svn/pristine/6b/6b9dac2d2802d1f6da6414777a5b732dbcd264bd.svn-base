package com.wasion.meterreading.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.service.CacheService;
import com.wasion.meterreading.util.ContextProvider;

/**
 * 启动加载类
 * 
 * @author w24882
 * @date 2019年11月13日
 */
@Component
public class StartUpListener implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		ApplicationContext context = ContextProvider.getContext();
		// 初始化缓存
		String[] beanNamesForType = context.getBeanNamesForType(CacheService.class);
		for (String beanName : beanNamesForType) {
			CacheService<?> bean = (CacheService<?>) context.getBean(beanName);
			bean.init();
		}
	}

}
