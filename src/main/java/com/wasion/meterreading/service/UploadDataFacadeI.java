package com.wasion.meterreading.service;

/**
 * 数据上报接口
 * 
 * 设计模式：Facade， 为子系统中的一组接口提供一个一致的界面，Facade模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 * 
 * @author w24882
 * @date 2019年11月6日
 */
public interface UploadDataFacadeI {

    public void parseData(Object data);

    public void parseCommandRsp(Object data);

}
