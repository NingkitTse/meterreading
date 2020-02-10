package com.wasion.meterreading.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 增删改查服务接口
 * 
 * @author w24882
 * @date 2019年11月6日
 * @param <T>
 */
public interface CrudServiceI<T> {

	public Page<T> findAll(T record, Pageable page);

	public T insert(T record);

	public void delete(T record);

	public T update(T record);
}
