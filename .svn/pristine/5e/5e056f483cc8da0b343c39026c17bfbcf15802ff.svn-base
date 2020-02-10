package com.wasion.meterreading.service.impl.telecom;

import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.telecom.ServiceData;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;

/**
 * 电信 命令执行上报解析接口
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
public interface CommandServiceResolverI extends ServiceResolveServerI {

	/**
	 * 回调更新本地任务的值
	 * 
	 * @param context
	 *            上下文
	 * @param data
	 *            服务数据
	 * @param localTask
	 *            本地任务
	 * @author w24882 xieningjie
	 * @date 2019年11月18日
	 */
	void callbackLocalTask(ResolveContext context, ServiceData data, RtTaskOnlineV1910 localTask);

}
