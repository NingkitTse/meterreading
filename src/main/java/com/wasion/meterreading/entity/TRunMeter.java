package com.wasion.meterreading.entity;
 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * TRunMeter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RUN_METER")
@SuppressWarnings("serial")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TRunMeter implements java.io.Serializable {

	// Fields

	private String meterGuid;
	private Integer meterId;
	private String meterNo;
	private String meterModelNo;
	private String meterStatus;
	private String closeFlag;
	private String barcode;
	private String kdc;
	private String assetNo;
	private String secondRest;
	private Integer ctNumerator;
	private Integer ctDenominator;
	private Integer ct;
	private Integer ptNumerator;
	private Integer ptDenominator;
	private Integer pt;
	private Integer ctpt;
	private String ctptChangeFlag;
	private String priceGuid;
	private String paraChangeFlag;
	private Integer initPreSellTimes;
	private String mediumGuid;
	private String preGuid;
	private String curLeval;
	private String volLeval;
	private String buyNodeList;
	private String sortGuid;
	private String remark;
	private String operatorId;
	private Date createTime;
	private String cstId;
	private String isReadMeter;
	private String zbbz;
	private String remoteControl;
	private String sfzdb;
	private String factoryNo;
	private String meterSort;
	private String parentMeterId;
	private Double give;
	private String lastState;
	private String houseId;
	private String sysId;
	private String startDate;
	private Double startValue;
	private String jldGuid;
	private String deptId;
	private String colAddr;
	private String cpuRandom;
	private String giveId;
	private Double JStartValue;
	private Double FStartValue;
	private Double PStartValue;
	private Double GStartValue; 
	// Constructors

	/** default constructor */
	public TRunMeter() {
	}

	/** minimal constructor */
	public TRunMeter(String meterGuid, Integer meterId, String meterNo,
			String meterModelNo, String meterStatus, String closeFlag,
			Double startValue, Double JStartValue, Double FStartValue,
			Double PStartValue, Double GStartValue) {
		this.meterGuid = meterGuid;
		this.meterId = meterId;
		this.meterNo = meterNo;
		this.meterModelNo = meterModelNo;
		this.meterStatus = meterStatus;
		this.closeFlag = closeFlag;
		this.startValue = startValue;
		this.JStartValue = JStartValue;
		this.FStartValue = FStartValue;
		this.PStartValue = PStartValue;
		this.GStartValue = GStartValue;
	}

	/** full constructor */
	public TRunMeter(String meterGuid, Integer meterId, String meterNo,
			String meterModelNo, String meterStatus, String closeFlag,
			String barcode, String kdc, String assetNo, String secondRest,
			Integer ctNumerator, Integer ctDenominator, Integer ct,
			Integer ptNumerator, Integer ptDenominator, Integer pt,
			Integer ctpt, String ctptChangeFlag, String priceGuid,
			String paraChangeFlag, Integer initPreSellTimes,
			String mediumGuid, String preGuid, String curLeval,
			String volLeval, String buyNodeList, String sortGuid,
			String remark, String operatorId, Date createTime, String cstId,
			String isReadMeter, String zbbz, String remoteControl,
			String sfzdb, String factoryNo, String meterSort,
			String parentMeterId, Double give, String lastState,
			String houseId, String sysId, String startDate, Double startValue,
			String jldGuid, String deptId, String colAddr, String cpuRandom,
			String giveId, Double JStartValue, Double FStartValue,
			Double PStartValue, Double GStartValue) {
		this.meterGuid = meterGuid;
		this.meterId = meterId;
		this.meterNo = meterNo;
		this.meterModelNo = meterModelNo;
		this.meterStatus = meterStatus;
		this.closeFlag = closeFlag;
		this.barcode = barcode;
		this.kdc = kdc;
		this.assetNo = assetNo;
		this.secondRest = secondRest;
		this.ctNumerator = ctNumerator;
		this.ctDenominator = ctDenominator;
		this.ct = ct;
		this.ptNumerator = ptNumerator;
		this.ptDenominator = ptDenominator;
		this.pt = pt;
		this.ctpt = ctpt;
		this.ctptChangeFlag = ctptChangeFlag;
		this.priceGuid = priceGuid;
		this.paraChangeFlag = paraChangeFlag;
		this.initPreSellTimes = initPreSellTimes;
		this.mediumGuid = mediumGuid;
		this.preGuid = preGuid;
		this.curLeval = curLeval;
		this.volLeval = volLeval;
		this.buyNodeList = buyNodeList;
		this.sortGuid = sortGuid;
		this.remark = remark;
		this.operatorId = operatorId;
		this.createTime = createTime;
		this.cstId = cstId;
		this.isReadMeter = isReadMeter;
		this.zbbz = zbbz;
		this.remoteControl = remoteControl;
		this.sfzdb = sfzdb;
		this.factoryNo = factoryNo;
		this.meterSort = meterSort;
		this.parentMeterId = parentMeterId;
		this.give = give;
		this.lastState = lastState;
		this.houseId = houseId;
		this.sysId = sysId;
		this.startDate = startDate;
		this.startValue = startValue;
		this.jldGuid = jldGuid;
		this.deptId = deptId;
		this.colAddr = colAddr;
		this.cpuRandom = cpuRandom;
		this.giveId = giveId;
		this.JStartValue = JStartValue;
		this.FStartValue = FStartValue;
		this.PStartValue = PStartValue;
		this.GStartValue = GStartValue;
	}

	// Property accessors
	@Id
	@Column(name = "METER_GUID", unique = true, nullable = false, length = 40)
	public String getMeterGuid() {
		return this.meterGuid;
	}

	public void setMeterGuid(String meterGuid) {
		this.meterGuid = meterGuid;
	}

	@Column(name = "METER_ID", nullable = false, precision = 22, scale = 0)
	public Integer getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
	}

	@Column(name = "METER_NO", nullable = false, length = 20)
	public String getMeterNo() {
		return this.meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	@Column(name = "METER_MODEL_NO", nullable = false, length = 4)
	public String getMeterModelNo() {
		return this.meterModelNo;
	}

	public void setMeterModelNo(String meterModelNo) {
		this.meterModelNo = meterModelNo;
	}

	@Column(name = "METER_STATUS", nullable = false, length = 1)
	public String getMeterStatus() {
		return this.meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	@Column(name = "CLOSE_FLAG", nullable = false, length = 1)
	public String getCloseFlag() {
		return this.closeFlag;
	}

	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	@Column(name = "BARCODE", length = 30)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "KDC", length = 20)
	public String getKdc() {
		return this.kdc;
	}

	public void setKdc(String kdc) {
		this.kdc = kdc;
	}

	@Column(name = "ASSET_NO", length = 20)
	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	@Column(name = "SECOND_REST", length = 2)
	public String getSecondRest() {
		return this.secondRest;
	}

	public void setSecondRest(String secondRest) {
		this.secondRest = secondRest;
	}

	@Column(name = "CT_NUMERATOR", precision = 22, scale = 0)
	public Integer getCtNumerator() {
		return this.ctNumerator;
	}

	public void setCtNumerator(Integer ctNumerator) {
		this.ctNumerator = ctNumerator;
	}

	@Column(name = "CT_DENOMINATOR", precision = 22, scale = 0)
	public Integer getCtDenominator() {
		return this.ctDenominator;
	}

	public void setCtDenominator(Integer ctDenominator) {
		this.ctDenominator = ctDenominator;
	}

	@Column(name = "CT", precision = 22, scale = 0)
	public Integer getCt() {
		return this.ct;
	}

	public void setCt(Integer ct) {
		this.ct = ct;
	}

	@Column(name = "PT_NUMERATOR", precision = 22, scale = 0)
	public Integer getPtNumerator() {
		return this.ptNumerator;
	}

	public void setPtNumerator(Integer ptNumerator) {
		this.ptNumerator = ptNumerator;
	}

	@Column(name = "PT_DENOMINATOR", precision = 22, scale = 0)
	public Integer getPtDenominator() {
		return this.ptDenominator;
	}

	public void setPtDenominator(Integer ptDenominator) {
		this.ptDenominator = ptDenominator;
	}

	@Column(name = "PT", precision = 22, scale = 0)
	public Integer getPt() {
		return this.pt;
	}

	public void setPt(Integer pt) {
		this.pt = pt;
	}

	@Column(name = "CTPT", precision = 22, scale = 0)
	public Integer getCtpt() {
		return this.ctpt;
	}

	public void setCtpt(Integer ctpt) {
		this.ctpt = ctpt;
	}

	@Column(name = "CTPT_CHANGE_FLAG", length = 1)
	public String getCtptChangeFlag() {
		return this.ctptChangeFlag;
	}

	public void setCtptChangeFlag(String ctptChangeFlag) {
		this.ctptChangeFlag = ctptChangeFlag;
	}

	@Column(name = "PRICE_GUID", length = 40)
	public String getPriceGuid() {
		return this.priceGuid;
	}

	public void setPriceGuid(String priceGuid) {
		this.priceGuid = priceGuid;
	}

	@Column(name = "PARA_CHANGE_FLAG", length = 1)
	public String getParaChangeFlag() {
		return this.paraChangeFlag;
	}

	public void setParaChangeFlag(String paraChangeFlag) {
		this.paraChangeFlag = paraChangeFlag;
	}

	@Column(name = "INIT_PRE_SELL_TIMES", precision = 22, scale = 0)
	public Integer getInitPreSellTimes() {
		return this.initPreSellTimes;
	}

	public void setInitPreSellTimes(Integer initPreSellTimes) {
		this.initPreSellTimes = initPreSellTimes;
	}

	@Column(name = "MEDIUM_GUID", length = 40)
	public String getMediumGuid() {
		return this.mediumGuid;
	}

	public void setMediumGuid(String mediumGuid) {
		this.mediumGuid = mediumGuid;
	}

	@Column(name = "PRE_GUID", length = 40)
	public String getPreGuid() {
		return this.preGuid;
	}

	public void setPreGuid(String preGuid) {
		this.preGuid = preGuid;
	}

	@Column(name = "CUR_LEVAL", length = 20)
	public String getCurLeval() {
		return this.curLeval;
	}

	public void setCurLeval(String curLeval) {
		this.curLeval = curLeval;
	}

	@Column(name = "VOL_LEVAL", length = 20)
	public String getVolLeval() {
		return this.volLeval;
	}

	public void setVolLeval(String volLeval) {
		this.volLeval = volLeval;
	}

	@Column(name = "BUY_NODE_LIST", length = 100)
	public String getBuyNodeList() {
		return this.buyNodeList;
	}

	public void setBuyNodeList(String buyNodeList) {
		this.buyNodeList = buyNodeList;
	}

	@Column(name = "SORT_GUID", length = 40)
	public String getSortGuid() {
		return this.sortGuid;
	}

	public void setSortGuid(String sortGuid) {
		this.sortGuid = sortGuid;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "CST_ID", length = 32)
	public String getCstId() {
		return this.cstId;
	}

	public void setCstId(String cstId) {
		this.cstId = cstId;
	}

	@Column(name = "IS_READ_METER", length = 20)
	public String getIsReadMeter() {
		return this.isReadMeter;
	}

	public void setIsReadMeter(String isReadMeter) {
		this.isReadMeter = isReadMeter;
	}

	@Column(name = "ZBBZ", length = 1)
	public String getZbbz() {
		return this.zbbz;
	}

	public void setZbbz(String zbbz) {
		this.zbbz = zbbz;
	}

	@Column(name = "REMOTE_CONTROL", length = 10)
	public String getRemoteControl() {
		return this.remoteControl;
	}

	public void setRemoteControl(String remoteControl) {
		this.remoteControl = remoteControl;
	}

	@Column(name = "SFZDB", length = 1)
	public String getSfzdb() {
		return this.sfzdb;
	}

	public void setSfzdb(String sfzdb) {
		this.sfzdb = sfzdb;
	}

	@Column(name = "FACTORY_NO", length = 20)
	public String getFactoryNo() {
		return this.factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	@Column(name = "METER_SORT", length = 32)
	public String getMeterSort() {
		return this.meterSort;
	}

	public void setMeterSort(String meterSort) {
		this.meterSort = meterSort;
	}

	@Column(name = "PARENT_METER_ID", length = 32)
	public String getParentMeterId() {
		return this.parentMeterId;
	}

	public void setParentMeterId(String parentMeterId) {
		this.parentMeterId = parentMeterId;
	}

	@Column(name = "GIVE", precision = 15, scale = 6)
	public Double getGive() {
		return this.give;
	}

	public void setGive(Double give) {
		this.give = give;
	}

	@Column(name = "LAST_STATE", length = 2)
	public String getLastState() {
		return this.lastState;
	}

	public void setLastState(String lastState) {
		this.lastState = lastState;
	}

	@Column(name = "HOUSE_ID", length = 32)
	public String getHouseId() {
		return this.houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Column(name = "SYS_ID", length = 10)
	public String getSysId() {
		return this.sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	@Column(name = "START_DATE", length = 10)
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name = "START_VALUE", nullable = false, precision = 15, scale = 4)
	public Double getStartValue() {
		return this.startValue;
	}

	public void setStartValue(Double startValue) {
		this.startValue = startValue;
	}

	@Column(name = "JLD_GUID", length = 32)
	public String getJldGuid() {
		return this.jldGuid;
	}

	public void setJldGuid(String jldGuid) {
		this.jldGuid = jldGuid;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "COL_ADDR", length = 60)
	public String getColAddr() {
		return this.colAddr;
	}

	public void setColAddr(String colAddr) {
		this.colAddr = colAddr;
	}

	@Column(name = "CPU_RANDOM", length = 30)
	public String getCpuRandom() {
		return this.cpuRandom;
	}

	public void setCpuRandom(String cpuRandom) {
		this.cpuRandom = cpuRandom;
	}

	@Column(name = "GIVE_ID", length = 60)
	public String getGiveId() {
		return this.giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	@Column(name = "J_START_VALUE", nullable = false, precision = 15, scale = 4)
	public Double getJStartValue() {
		return this.JStartValue;
	}

	public void setJStartValue(Double JStartValue) {
		this.JStartValue = JStartValue;
	}

	@Column(name = "F_START_VALUE", nullable = false, precision = 15, scale = 4)
	public Double getFStartValue() {
		return this.FStartValue;
	}

	public void setFStartValue(Double FStartValue) {
		this.FStartValue = FStartValue;
	}

	@Column(name = "P_START_VALUE", nullable = false, precision = 15, scale = 4)
	public Double getPStartValue() {
		return this.PStartValue;
	}

	public void setPStartValue(Double PStartValue) {
		this.PStartValue = PStartValue;
	}

	@Column(name = "G_START_VALUE", nullable = false, precision = 15, scale = 4)
	public Double getGStartValue() {
		return this.GStartValue;
	}

	public void setGStartValue(Double GStartValue) {
		this.GStartValue = GStartValue;
	} 

}