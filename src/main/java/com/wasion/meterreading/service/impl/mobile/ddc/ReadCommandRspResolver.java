package com.wasion.meterreading.service.impl.mobile.ddc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.domain.FrameValue;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.ResolveContext.DataMapKey;
import com.wasion.meterreading.domain.dto.mobile.MobileContext;
import com.wasion.meterreading.service.impl.mobile.MobileResolveServiceI;

/**
 * 抄表指令响应解析类
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
@Component
public class ReadCommandRspResolver implements MobileResolveServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(ReadCommandRspResolver.class);

	/**
	 * 样例数据：0101004816 <br>
	 * 序号：01<br>
	 * 数据类型：01 日冻结 02月冻结 03点冻结<br>
	 * 冻结 冻结数据长度：00 <br>
	 * 校验和：48 <br>
	 * 结束符：16 <br>
	 */
	@Override
	public void resolveData(ResolveContext context) {
	    MobileContext mobileContext = (MobileContext) context;
		FrameValue frameValue = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);
		String seq = frameValue.next(2);
		LOG.info("Receive read command response {}.", seq);
	}

}
