package com.wasion.meterreading.orm.jpa.freezedata;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wasion.meterreading.entity.freezedata.DayFreezeDataValue;
import com.wasion.meterreading.entity.freezedata.FreezeDataId;

public interface DayFreezeDataRepository extends JpaRepository<DayFreezeDataValue, FreezeDataId> {

    @Modifying
    @Query(value = "update DT_WTR_A8D_F247 set freezen_value=:freezenValue, register_date = sysdate where freezen_date=:freezenDate and sn_meter=:snMeter", nativeQuery = true)
    public void updateFreezenValueById(@Param(value = "snMeter") String snMeter,
            @Param(value = "freezenDate") Date freezenDate, @Param(value = "freezenValue") String freezenValue);

}
