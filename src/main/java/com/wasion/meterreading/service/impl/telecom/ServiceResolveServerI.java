package com.wasion.meterreading.service.impl.telecom;

import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.telecom.ServiceData;

/**
 * 电信 数据上报 Service 解析器
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 * @see {@link com.wasion.meterreading.service.impl.telecom.ddc.TelecomDdcResolver.resolve(ResolveContext)}
 */
public interface ServiceResolveServerI {

	/**
	 * 解析服务数据
	 * 
	 * @param context
	 *            解析上下文
	 * @param serviceData
	 *            服务数据
	 * @author w24882 xieningjie
	 * @date 2019年11月25日
	 */
	void resolveServiceData(ResolveContext context, ServiceData serviceData);

}
