package com.wasion.meterreading.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wasion.meterreading.config.OnenetProperties;
import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.entity.TMeterDevice;
import com.wasion.meterreading.entity.view.VMeterDevice;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.TMeterDeviceRepository;
import com.wasion.meterreading.orm.jpa.VMeterDeviceRepository;
import com.wasion.meterreading.service.CrudServiceI;
import com.wasion.meterreading.util.CommonUtil;
import com.wasion.meterreading.util.ContextProvider;

import cmcciot.onenet.nbapi.sdk.api.online.CreateDeviceOpe;
import cmcciot.onenet.nbapi.sdk.api.online.DeleteOpe;
import cmcciot.onenet.nbapi.sdk.api.online.ReadOpe;
import cmcciot.onenet.nbapi.sdk.entity.Device;
import telecom.sdk.service.deviceManagement.RegisterDirectConnectedDevice;

/**
 * 水表设备服务类，主要完成本地插入水表设备记录以及在平台注册相关工作
 * 
 * @author w24882
 * @date 2019年11月6日
 */
@Service
@EnableConfigurationProperties(value = OnenetProperties.class)
@ConfigurationProperties(prefix = "10086.iot")
public class MeterServiceImpl implements CrudServiceI<TMeterDevice> {

	private static final Logger LOG = LoggerFactory.getLogger(MeterServiceImpl.class);
	@Autowired
	private VMeterDeviceRepository vMeterRepository;
	@Autowired
	private TMeterDeviceRepository tMeterRepository;
	@Autowired
	private OnenetProperties properties;
	@Value(value = "${spring.profiles.active}")
	private String activeProfile;

	public String getActiveProfile() {
		return activeProfile;
	}

	public void setActiveProfile(String activeProfile) {
		this.activeProfile = activeProfile;
	}

	public Page<VMeterDevice> findAllMobileMeter(VMeterDevice record, Pageable page) {
		Example<VMeterDevice> example = Example.of(record);
		Page<VMeterDevice> findAll = vMeterRepository.findAll(example, page);
		return findAll;
	}

	@Transactional
	@Override
	public TMeterDevice insert(TMeterDevice record) {
		String imei = record.getImei();
		String imsi = record.getImsi();
		String platform = record.getPlatform();

		if (SystemConstant.PLATFORM_MOBILE.equals(platform)) { // 移动平台新增
			// Create device
			CreateDeviceOpe deviceOpe = new CreateDeviceOpe(properties.getApiKey());
			Device device = new Device(imei, imei, imsi);

			final JSONObject operationRet = deviceOpe.operation(device, device.toJsonObject());
			LOG.info(operationRet.toString());
			try {
				final int errno = operationRet.getInt("errno");
				if (errno == 0) {
					record.setSn(CommonUtil.getUuid());
					String deviceId = operationRet.getJSONObject("data").getString("device_id");
					record.setDeviceId(deviceId);
					return tMeterRepository.save(record);
				} else {
					String errorMsg = operationRet.getString("error");
					LOG.error(errorMsg);
					throw new MRException(errorMsg, errorMsg);
				}
			} catch (JSONException e) {
				LOG.error("Json exception.", e);
				throw new MRException(MRExceptionEnum.CommunicationException);
			}
		} else { // 电信平台新增
			RegisterDirectConnectedDevice bean = ContextProvider.getBean(RegisterDirectConnectedDevice.class);
			try {
				String registerDevice = bean.registerDevice(imei);
				com.alibaba.fastjson.JSONObject parseObject = JSON.parseObject(registerDevice);
				String deviceId = parseObject.getString("deviceId");

				record.setSn(CommonUtil.getUuid());
				record.setDeviceId(deviceId);
				return tMeterRepository.save(record);
			} catch (Exception e) {
				LOG.error("Create device failed, Json exception.", e);
				throw new MRException(MRExceptionEnum.CommunicationException);
			}
		}
	}

	@Transactional
	@Override
	public void delete(TMeterDevice record) {
		Optional<TMeterDevice> recordInDb = tMeterRepository.findById(record.getImei());
		if (!recordInDb.isPresent()) {
			LOG.error("Device is not exist!");
			throw new MRException(MRExceptionEnum.DeviceExsit);
		}
		TMeterDevice tMeterDevice = recordInDb.get();
		String platform = tMeterDevice.getPlatform();
		if (SystemConstant.PLATFORM_MOBILE.equals(platform)) {

		}
		// Create delete device ope
		DeleteOpe deviceOpe = new DeleteOpe(properties.getApiKey());
		String deviceId = tMeterDevice.getDeviceId();
		Device device = Device.deleteDevice(deviceId);
		JSONObject retObj = deviceOpe.operation(device, null);
		LOG.info("Delete device ret is {}.", retObj);
		tMeterRepository.delete(tMeterDevice);
	}

	@Override
	public TMeterDevice update(TMeterDevice record) {
		return tMeterRepository.save(record);
	}

	/**
	 * 依照DeviceId从OneNET平台查询设备信息
	 * 
	 * @param deviceIds
	 *            设备ID列表
	 */
	public void queryVMobileMetersFromIot(Collection<String> deviceIds) {
		LOG.debug("Start to query mobile meter device from one net platform.");
		if (CollectionUtils.isEmpty(deviceIds)) {
			LOG.info("There is no devices need query.");
			return;
		}
		for (String deviceId : deviceIds) {
			queryVMobileMeterFromIot(deviceId);
		}
		LOG.debug("End to query mobile meter device from one net platform.");
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public VMeterDevice queryVMobileMeterFromIot(String deviceId) {
		ReadOpe deviceOpe = new ReadOpe(properties.getApiKey());
		Device device = Device.selectDevice(deviceId);
		JSONObject retObj = deviceOpe.operation(device, null);
		LOG.info("Query device info ret is {}.", retObj);
		try {
			int errno = retObj.getInt("errno");
			// 成功标识：0
			if (errno != 0) {
				return null;
			}
			JSONObject data = retObj.getJSONObject("data");
			JSONObject authInfo = data.getJSONObject("auth_info");
			@SuppressWarnings("unchecked")
			Iterator<String> imeis = authInfo.keys();
			if (imeis.hasNext()) {
				String imei = imeis.next();
				String imsi = authInfo.getString(imei);
				TMeterDevice tMobileMeter = TMeterDevice.of(deviceId, imei, imsi, SystemConstant.PLATFORM_MOBILE);
				tMeterRepository.save(tMobileMeter);
				LOG.info("Save meter mobile success. {}.", tMobileMeter);
			}
		} catch (JSONException e) {
			LOG.error("Parse deviec info failed.", retObj);
		}
		return vMeterRepository.findByDeviceId(deviceId);
	}

	@Override
	public Page<TMeterDevice> findAll(TMeterDevice record, Pageable page) {
		return null;
	}

}
