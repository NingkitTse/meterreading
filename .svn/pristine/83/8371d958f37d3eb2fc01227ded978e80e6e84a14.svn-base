
package com.wasion.meterreading.service.impl.mobile.ddc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wasion.meterreading.domain.FrameValue;
import com.wasion.meterreading.domain.dto.ResolveContext;
import com.wasion.meterreading.domain.dto.ResolveContext.DataMapKey;
import com.wasion.meterreading.domain.dto.mobile.MobileContext;
import com.wasion.meterreading.entity.EventReportId;
import com.wasion.meterreading.entity.EventReportValue;
import com.wasion.meterreading.entity.EventReportValue.EventCodeEnum;
import com.wasion.meterreading.orm.jpa.EventReportRepository;
import com.wasion.meterreading.service.impl.mobile.MobileResolveServiceI;
import com.wasion.meterreading.util.ParseUtil;

/**
 * 事件上报数据解析器
 * 
 * @author w24882
 * @date 2019年10月15日
 */
@Service
public class EventReportResolver implements MobileResolveServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(EventReportResolver.class);

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private EventReportRepository eventReportRepository;

	@Override
	public void resolveData(ResolveContext context) {
		MobileContext mobileContext = (MobileContext) context;
		FrameValue frameValue = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);

		LOG.debug("Start parse event report data. Data str -> {}", frameValue.peekTo(frameValue.getRawSize()));
		List<EventReportValue> retList = new ArrayList<>();
		EventReportValue eventReportValue = new EventReportValue();
		EventReportId id = new EventReportId();
		String snMeter = mobileContext.getSnMeter();
		id.setSnMeter(snMeter);
		eventReportValue.setId(id);
		String seq = frameValue.next(2);
		eventReportValue.setSeq(seq);
		Integer len = ParseUtil.hexToInt(frameValue.next(2));
		String eventCode = frameValue.next(2);

		for (int i = 0; i < len; ++i) {
			EventReportValue newValue = null;
			try {
				newValue = (EventReportValue) eventReportValue.clone();
			} catch (CloneNotSupportedException e1) {
				LOG.error("Clone failed");
			}
			final String startTimeStr = frameValue.next(14);
			try {
				Date startDateTime = SDF.parse(startTimeStr);
				newValue.getId().setStartDateTime(startDateTime);
			} catch (ParseException e) {
				LOG.error("Parse date failed with value startTimeStr {}.", startTimeStr);
				e.printStackTrace();
			}

			switch (eventCode) {
			case "50": // 磁干扰
				processMagneticDisturbance(frameValue, newValue);
				break;
			case "51":
				newValue.setEventCode(EventCodeEnum.MDayNoWater);
				break;
			case "52":
				newValue.setEventCode(EventCodeEnum.DataAlwaysClear);
				break;
			case "53":
				processValveFailure(frameValue, newValue);
				break;
			case "54":
				newValue.setEventCode(EventCodeEnum.FlowFailure);
				break;
			case "55":
				newValue.setEventCode(EventCodeEnum.LetOut);
				break;
			case "56":
				newValue.setEventCode(EventCodeEnum.UnderVoltage);
				break;
			case "57": // 掉电
				processPowerDown(frameValue, newValue);
				break;
			default:
				break;
			}
			retList.add(newValue);
		}
		eventReportRepository.saveAll(retList);
		LOG.info("Event report parse ret-> {}", retList);
	}

	private void processPowerDown(FrameValue frameValue, EventReportValue newValue) {
		newValue.setEventCode(EventCodeEnum.PowerDown);
		final Double powerDownCumulant = ParseUtil.hexToDoulbe(frameValue.next(8));
		newValue.setCumulant(powerDownCumulant);
	}

	private void processValveFailure(FrameValue frameValue, EventReportValue newValue) {
		newValue.setEventCode(EventCodeEnum.ValveFailure);
		final String valveFailureEndTimeStr = frameValue.next(14);
		try {
			Date endDateTime = SDF.parse(valveFailureEndTimeStr);
			newValue.setEndDateTime(endDateTime);
		} catch (ParseException e) {
			LOG.error("Parse date failed with value endTimeStr {}.", valveFailureEndTimeStr);
			e.printStackTrace();
		}
		final Double vfCumulant = ParseUtil.hexToDoulbe(frameValue.next(8));
		newValue.setCumulant(vfCumulant);
	}

	private void processMagneticDisturbance(FrameValue frameValue, EventReportValue newValue) {
		newValue.setEventCode(EventCodeEnum.MagneticDisturbance);
		final String endTimeStr = frameValue.next(14);
		try {
			Date endDateTime = SDF.parse(endTimeStr);
			newValue.setEndDateTime(endDateTime);
		} catch (ParseException e) {
			LOG.error("Parse date failed with value endTimeStr {}.", endTimeStr);
			e.printStackTrace();
		}
		final Double mdCumulant = ParseUtil.hexToDoulbe(frameValue.next(8));
		newValue.setCumulant(mdCumulant);
	}

}
