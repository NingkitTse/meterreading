package com.wasion.meterreading.service;

public interface CacheService<T> {
	/**
	 * 初始化缓存
	 * 
	 * @author w24882
	 */
	public void init();

	public T getValue(String key);

}
