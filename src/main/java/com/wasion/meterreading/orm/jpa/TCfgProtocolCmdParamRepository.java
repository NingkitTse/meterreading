package com.wasion.meterreading.orm.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wasion.meterreading.entity.TCfgProtocolCmdParam;

/**
 * 命令参数仓库
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
public interface TCfgProtocolCmdParamRepository extends CrudRepository<TCfgProtocolCmdParam, String> {

	List<TCfgProtocolCmdParam> findByCmdIdAndParamTypeOrderByParamNo(String cmdId, String paramType);

}
