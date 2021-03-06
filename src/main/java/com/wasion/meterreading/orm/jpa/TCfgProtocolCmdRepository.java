package com.wasion.meterreading.orm.jpa;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.wasion.meterreading.entity.TCfgProtocolCmd;

/**
 * 命令仓库
 * 
 * @author w24882 xieningjie
 * @date 2019年11月18日
 */
public interface TCfgProtocolCmdRepository extends CrudRepository<TCfgProtocolCmd, String> {

    Page<TCfgProtocolCmd> findAll(Example<TCfgProtocolCmd> example, Pageable pageable);

}
