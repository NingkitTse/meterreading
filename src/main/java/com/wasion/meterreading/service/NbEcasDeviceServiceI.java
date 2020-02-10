package com.wasion.meterreading.service;

/**
 * NB Ecas 项目设备注册、注销接口
 * 
 * @author w24882 xieningjie
 * @date 2019年12月18日
 */
public interface NbEcasDeviceServiceI {

    /**
     * 如果设备已经存在于ECAS项目，注册设备。
     * 
     * @param imei 设备标识码
     * @param deviceId 平台设备ID
     * @author w24882 xieningjie
     * @date 2019年12月18日
     */
    void registerDevice(String imei, String deviceId);

    /**
     * 如果设备已经存在于ECAS项目，注销设备。
     * 
     * @param deviceId 设备ID
     * @author w24882 xieningjie
     * @date 2019年12月18日
     */
    void logoutDevice(String deviceId);

}
