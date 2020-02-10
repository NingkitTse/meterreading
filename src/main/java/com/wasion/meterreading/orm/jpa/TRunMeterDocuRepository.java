package com.wasion.meterreading.orm.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wasion.meterreading.entity.TRunMeterDocu;
import com.wasion.meterreading.entity.TRunMeterDocuId;

@Repository
public interface TRunMeterDocuRepository extends CrudRepository<TRunMeterDocu, TRunMeterDocuId> {

    Optional<TRunMeterDocu> findByIdAndParamValue(TRunMeterDocuId id, String paramValue);

}
