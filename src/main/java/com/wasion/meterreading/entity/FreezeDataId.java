package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;

import com.wasion.meterreading.domain.BaseValue;

public class FreezeDataId extends BaseValue {

	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = -6449588735877121760L;
	@Column(name = "SN_METER", length = 32)
	private String snMeter;
	@Column(name = "FREEZEN_DATE")
	private Date freezenDate;

	public String getSnMeter() {
		return snMeter;
	}

	public void setSnMeter(String snMeter) {
		this.snMeter = snMeter;
	}

	public Date getFreezenDate() {
		return freezenDate;
	}

	public void setFreezenDate(Date freezenDate) {
		this.freezenDate = freezenDate;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
