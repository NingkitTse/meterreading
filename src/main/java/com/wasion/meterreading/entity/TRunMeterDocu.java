package com.wasion.meterreading.entity;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TRunMeterDocu entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_RUN_METER_DOCU")
public class TRunMeterDocu implements java.io.Serializable {

	// Fields

	private TRunMeterDocuId id;
	private String paramValue;
	private String operatorId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TRunMeterDocu() {
	}

	/** minimal constructor */
	public TRunMeterDocu(TRunMeterDocuId id) {
		this.id = id;
	}

	/** full constructor */
	public TRunMeterDocu(TRunMeterDocuId id, String paramValue,
			String operatorId, Date createTime) {
		this.id = id;
		this.paramValue = paramValue;
		this.operatorId = operatorId;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "meterGuid", column = @Column(name = "METER_GUID", nullable = false, length = 40)),
			@AttributeOverride(name = "protocolNo", column = @Column(name = "PROTOCOL_NO", nullable = false, length = 12)) })
	public TRunMeterDocuId getId() {
		return this.id;
	}

	public void setId(TRunMeterDocuId id) {
		this.id = id;
	}

	@Column(name = "PARAM_VALUE", length = 20)
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "OPERATOR_ID", length = 40)
	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}