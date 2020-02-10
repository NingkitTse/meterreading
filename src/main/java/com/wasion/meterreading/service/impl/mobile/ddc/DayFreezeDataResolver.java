package com.wasion.meterreading.service.impl.mobile.ddc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.FreezeDataId;
import com.wasion.meterreading.orm.jpa.freezedata.DayFreezeDataRepository;
import com.wasion.meterreading.service.impl.mobile.MobileResolveServiceI;
import com.wasion.meterreading.util.CommonUtil;
import com.wasion.meterreading.util.DateUtil;
import com.wasion.meterreading.util.ParseUtil;

/**
 * 冻结数据解析类
 * 
 * @author w24882
 * @date 2019年11月6日
 */
@Service
public class DayFreezeDataResolver implements MobileResolveServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(DayFreezeDataResolver.class);
	@Autowired
	private DayFreezeDataRepository freezeDataRepository;

	@SuppressWarnings("unused")
	@Override
	public void resolveData(ResolveContext context) {
		MobileContext mobileContext = (MobileContext) context;
		FrameValue frameValue = (FrameValue) mobileContext.get(DataMapKey.FRAME_VALUE_KEY);

		DayFreezeDataValue freezeDataValue = new DayFreezeDataValue();
		String snMeter = context.getSnMeter();
		String snDevice = context.getSnDevice();
		freezeDataValue.setSnDevice(snDevice);
		FreezeDataId freezeDataId = new FreezeDataId();
		freezeDataId.setSnMeter(snMeter);
		freezeDataValue.setId(freezeDataId);
		freezeDataValue.setValidData(true);

		String seq = frameValue.next(2);
		double instantFlux = ParseUtil.hexToDoulbe(frameValue.next(8)); // 当前累积流量
		double monthFreezeData = ParseUtil.hexToDoulbe(frameValue.next(8)); // 月冻结数据

		String dayFreezeDateStr = ParseUtil.bcdToString(frameValue.next(10)); // 最近一次日冻结数据时间
		Date dayFreezenDate = null;
		try {
			dayFreezenDate = DateUtil.parseDataYYMMDDHHMM(dayFreezeDateStr);
		} catch (ParseException e) {
			LOG.error("Parse day freezen date failed with value {}.", dayFreezeDateStr);
			dayFreezenDate = new Date(0);
			freezeDataValue.setValidData(false);
		}

		List<Double> dataList = new ArrayList<>();
		dataList.add(instantFlux);
		Integer len = 7;
		while (len-- > 0) {
			String freezeFlux = frameValue.next(8);
			if ("FFFFFFFF".equals(freezeFlux)) {
				continue;
			}
			// 冻结数据暂不使用
			// dataList.add(ParseUtil.hexToDoulbe(freezeFlux));
		}
		Double battery = ParseUtil.bcdToDoulbe(frameValue.next(4), 100);
		Integer rsrp = ParseUtil.bcdToSignedInt(frameValue.next(4)); // 信号强度
		Integer snr = ParseUtil.bcdToSignedInt(frameValue.next(4)); // 信噪比
		Integer lastPowerOnTime = ParseUtil.hexToInt(frameValue.next(2)); // 上一次NB模块上电时间
		double totalUsedBattery = ParseUtil.hexToDoulbe(frameValue.next(8)); // 电池总用电量
		double maxCurrent = ParseUtil.hexToDoulbe(frameValue.next(4)); // 最大电流
		double minCurrent = ParseUtil.hexToDoulbe(frameValue.next(4)); // 最大电流
		String statusWord = frameValue.next(4);

		freezeDataValue.setRsrp(rsrp);
		freezeDataValue.setSnr(snr);
		freezeDataValue.setStatusValue(snr + ";" + rsrp);
		freezeDataValue.setBattery(battery);
		freezeDataValue.setStatusWord(statusWord);
		resolveStatusWord(freezeDataValue, snr, rsrp, statusWord);
		freezeDataValue.setSeq(seq);

		Date registerDate = new Date();
		freezeDataValue.setRegisterDate(registerDate);

		List<DayFreezeDataValue> retList = new ArrayList<>();
		Calendar c = Calendar.getInstance();

		for (Double data : dataList) {
			DayFreezeDataValue meter = null;
			try {
				meter = (DayFreezeDataValue) freezeDataValue.clone();
			} catch (CloneNotSupportedException e) {
				LOG.error("Clone freezeDataValue failed.");
			}
			meter.setSn(CommonUtil.getUuid());
			meter.setFreezenValue(data + "");

			FreezeDataId id = meter.getId();
			id.setFreezenDate(dayFreezenDate);

			c.setTime(dayFreezenDate);
			c.add(Calendar.DAY_OF_MONTH, -1);
			dayFreezenDate = c.getTime();
			retList.add(meter);
		}
		context.put(DataMapKey.FREEZE_DATA_KEY, retList.get(0));
		context.put(DataMapKey.FREEZE_DATA_LIST_KEY, retList);
		freezeDataRepository.saveAll(retList);
		LOG.info("Save freezen date succeed: {}.", retList);
	}

	/**
	 * 对照老代码取值情况，状态值使用到的字段有 第0位的valveFlag 与 第2位的信号强度
	 * 解析后的状态值,用分号隔开；顺序是阀门状态，电池欠压标志，CSQ，走气，过流，远程阀控，磁干扰，远程开关阀，泄漏，厂内状态，失联，持续走气泄漏
	 */
	@SuppressWarnings("unused")
	private void resolveStatusWord(DayFreezeDataValue freezeDataValue, Integer snr, Integer rsrp, String statusWord) {
		Integer state = ParseUtil.hexToInt(statusWord);
		// 表计阀门状态标志
		byte valveFlag = (byte) ((0x0300 & state) >> 8);
		String valveTempStr = "00" + Integer.toHexString(valveFlag);
		String valveFlagHex = valveTempStr.substring(valveTempStr.length() - 2);
		// M天不走气
		byte mDayNoGas = (byte) ((0x0080 & state) >> 7);
		// 过流
		byte flowOut = (byte) ((0x0040 & state) >> 6);
		// 强制阀控标志
		byte forcedValveControlFlag = (byte) ((0x0020 & state) >> 5);
		// 磁干扰标志
		byte magneticInterferenceFlag = (byte) ((0x0008 & state) >> 3);
		// 远程阀控标志
		byte remoteValveControlFlag = (byte) ((0x0004 & state) >> 2);
		// 泄露
		byte leakage = (byte) ((0x0002 & state) >> 1);
		// 厂内状态
		byte inPlantStatus = (byte) (0x0001 & state);
		StringBuffer sb = new StringBuffer(valveFlagHex + ";");
		sb.append(0 + ";");
		sb.append(rsrp + ";");
		sb.append(snr + ";");
		freezeDataValue.setStatusValue(sb.toString());
	}

}
