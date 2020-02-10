package com.wasion.meterreading.entity.freezedata;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "DT_WTR_A8D_F247")
@DynamicUpdate
public class DayFreezeDataValue extends FreezeDataValue {

	/**
	 * 序列化版本ID
	 */
	private static final long serialVersionUID = 6139515980439295044L;

	@EmbeddedId
	private FreezeDataId id;

	public FreezeDataId getId() {
		return id;
	}

	public void setId(FreezeDataId id) {
		this.id = id;
	}

}
