package com.wasion.meterreading.service;

import com.wasion.meterreading.domain.dto.ResolveContext;

/**
 * 通知解析接口，如解析数据上报，事件上报
 * 
 * @author w24882
 * @date 2019年11月13日
 */
public interface NotifyResolveServiceI {

	/**
	 * 解析电信上报
	 * 
	 * @param context
	 * @author w24882
	 */
	void resolve(ResolveContext context);

}
