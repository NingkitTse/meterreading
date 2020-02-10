package com.wasion.meterreading.orm.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wasion.meterreading.entity.EventReportValue;

public interface EventReportRepository extends JpaRepository<EventReportValue, Long> {

}
