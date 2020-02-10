package com.wasion.meterreading.service.impl.ecas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wasion.meterreading.constant.EcasConstant;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.orm.jpa.TCfgProtocolCmdRepository;
import com.wasion.meterreading.service.CrudServiceI;

@Service
public class CmdServiceImpl implements CrudServiceI<TCfgProtocolCmd> {

    @Autowired
    private TCfgProtocolCmdRepository repo;

    @Override
    public Page<TCfgProtocolCmd> findAll(TCfgProtocolCmd record, Pageable page) {
        record.setProtocolNo(EcasConstant.CMD_NB_PROTOCOL_NO);
        Example<TCfgProtocolCmd> example = Example.of(record);
        Page<TCfgProtocolCmd> findAll = repo.findAll(example, page);
        return findAll;
    }

    @Override
    public TCfgProtocolCmd insert(TCfgProtocolCmd record) {
        return repo.save(record);
    }

    @Override
    public void delete(TCfgProtocolCmd record) {
        repo.delete(record);
    }

    @Override
    public TCfgProtocolCmd update(TCfgProtocolCmd record) {
        return repo.save(record);
    }

}
