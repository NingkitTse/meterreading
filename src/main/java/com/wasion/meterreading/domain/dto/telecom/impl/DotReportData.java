package com.wasion.meterreading.domain.dto.telecom.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wasion.meterreading.domain.dto.telecom.ServiceDataDetail;

/**
 * 点冻结数据上报数据
 * 
 * @author w24882 xieningjie
 * @date 2019年12月13日
 */
public class DotReportData extends ServiceDataDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8588191713243004278L;
	@JsonProperty(value = "meterEleno")
	private String meterEleno;
	@JsonProperty(value = "totalconsumepower")
	private String totalConsumePower;
	@JsonProperty(value = "maxworkelectricflow")
	private String maxWorkElectricFlow;
	@JsonProperty(value = "minworkelectricflow")
	private String minWorkElectricFlow;
	@JsonProperty(value = "batteryVoltage")
	private String batteryVoltage;
	@JsonProperty(value = "signalStrength")
	private String signalStrength;
	@JsonProperty(value = "SNR")
	private String snr;
	@JsonProperty(value = "state")
	private String state;
	@JsonProperty(value = "currentReading")
	private String currentReading;
	@JsonProperty(value = "dailyActivityTime")
	private String dailyActivityTime;
	@JsonProperty(value = "Dotreporttime")
	private String dotReportTime;
	@JsonProperty(value = "Dotdata01")
	private String dotData01;
	@JsonProperty(value = "Dotdata02")
	private String dotData02;
	@JsonProperty(value = "Dotdata03")
	private String dotData03;
	@JsonProperty(value = "Dotdata04")
	private String dotData04;
	@JsonProperty(value = "Dotdata05")
	private String dotData05;
	@JsonProperty(value = "Dotdata06")
	private String dotData06;
	@JsonProperty(value = "Dotdata07")
	private String dotData07;
	@JsonProperty(value = "Dotdata08")
	private String dotData08;
	@JsonProperty(value = "Dotdata09")
	private String dotData09;
	@JsonProperty(value = "Dotdata10")
	private String dotData10;
	@JsonProperty(value = "Dotdata11")
	private String dotData11;
	@JsonProperty(value = "Dotdata12")
	private String dotData12;
	@JsonProperty(value = "Dotdata13")
	private String dotData13;
	@JsonProperty(value = "Dotdata14")
	private String dotData14;
	@JsonProperty(value = "Dotdata15")
	private String dotData15;
	@JsonProperty(value = "Dotdata16")
	private String dotData16;
	@JsonProperty(value = "Dotdata17")
	private String dotData17;
	@JsonProperty(value = "Dotdata18")
	private String dotData18;
	@JsonProperty(value = "Dotdata19")
	private String dotData19;
	@JsonProperty(value = "Dotdata20")
	private String dotData20;
	@JsonProperty(value = "Dotdata21")
	private String dotData21;
	@JsonProperty(value = "Dotdata22")
	private String dotData22;
	@JsonProperty(value = "Dotdata23")
	private String dotData23;
	@JsonProperty(value = "Dotdata24")
	private String dotData24;
	@JsonProperty(value = "MonthFreezeData")
	private String monthFreezeData;

	public String getMeterEleno() {
		return meterEleno;
	}

	public void setMeterEleno(String meterEleno) {
		this.meterEleno = meterEleno;
	}

	public String getTotalConsumePower() {
		return totalConsumePower;
	}

	public void setTotalConsumePower(String totalConsumePower) {
		this.totalConsumePower = totalConsumePower;
	}

	public String getMaxWorkElectricFlow() {
		return maxWorkElectricFlow;
	}

	public void setMaxWorkElectricFlow(String maxWorkElectricFlow) {
		this.maxWorkElectricFlow = maxWorkElectricFlow;
	}

	public String getMinWorkElectricFlow() {
		return minWorkElectricFlow;
	}

	public void setMinWorkElectricFlow(String minWorkElectricFlow) {
		this.minWorkElectricFlow = minWorkElectricFlow;
	}

	public String getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getSnr() {
		return snr;
	}

	public void setSnr(String snr) {
		this.snr = snr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(String currentReading) {
		this.currentReading = currentReading;
	}

	public String getDailyActivityTime() {
		return dailyActivityTime;
	}

	public void setDailyActivityTime(String dailyActivityTime) {
		this.dailyActivityTime = dailyActivityTime;
	}

	public String getDotReportTime() {
		return dotReportTime;
	}

	public void setDotReportTime(String dotReportTime) {
		this.dotReportTime = dotReportTime;
	}

	public String getDotData01() {
		return dotData01;
	}

	public void setDotData01(String dotData01) {
		this.dotData01 = dotData01;
	}

	public String getDotData02() {
		return dotData02;
	}

	public void setDotData02(String dotData02) {
		this.dotData02 = dotData02;
	}

	public String getDotData03() {
		return dotData03;
	}

	public void setDotData03(String dotData03) {
		this.dotData03 = dotData03;
	}

	public String getDotData04() {
		return dotData04;
	}

	public void setDotData04(String dotData04) {
		this.dotData04 = dotData04;
	}

	public String getDotData05() {
		return dotData05;
	}

	public void setDotData05(String dotData05) {
		this.dotData05 = dotData05;
	}

	public String getDotData06() {
		return dotData06;
	}

	public void setDotData06(String dotData06) {
		this.dotData06 = dotData06;
	}

	public String getDotData07() {
		return dotData07;
	}

	public void setDotData07(String dotData07) {
		this.dotData07 = dotData07;
	}

	public String getDotData08() {
		return dotData08;
	}

	public void setDotData08(String dotData08) {
		this.dotData08 = dotData08;
	}

	public String getDotData09() {
		return dotData09;
	}

	public void setDotData09(String dotData09) {
		this.dotData09 = dotData09;
	}

	public String getDotData10() {
		return dotData10;
	}

	public void setDotData10(String dotData10) {
		this.dotData10 = dotData10;
	}

	public String getDotData11() {
		return dotData11;
	}

	public void setDotData11(String dotData11) {
		this.dotData11 = dotData11;
	}

	public String getDotData12() {
		return dotData12;
	}

	public void setDotData12(String dotData12) {
		this.dotData12 = dotData12;
	}

	public String getDotData13() {
		return dotData13;
	}

	public void setDotData13(String dotData13) {
		this.dotData13 = dotData13;
	}

	public String getDotData14() {
		return dotData14;
	}

	public void setDotData14(String dotData14) {
		this.dotData14 = dotData14;
	}

	public String getDotData15() {
		return dotData15;
	}

	public void setDotData15(String dotData15) {
		this.dotData15 = dotData15;
	}

	public String getDotData16() {
		return dotData16;
	}

	public void setDotData16(String dotData16) {
		this.dotData16 = dotData16;
	}

	public String getDotData17() {
		return dotData17;
	}

	public void setDotData17(String dotData17) {
		this.dotData17 = dotData17;
	}

	public String getDotData18() {
		return dotData18;
	}

	public void setDotData18(String dotData18) {
		this.dotData18 = dotData18;
	}

	public String getDotData19() {
		return dotData19;
	}

	public void setDotData19(String dotData19) {
		this.dotData19 = dotData19;
	}

	public String getDotData20() {
		return dotData20;
	}

	public void setDotData20(String dotData20) {
		this.dotData20 = dotData20;
	}

	public String getDotData21() {
		return dotData21;
	}

	public void setDotData21(String dotData21) {
		this.dotData21 = dotData21;
	}

	public String getDotData22() {
		return dotData22;
	}

	public void setDotData22(String dotData22) {
		this.dotData22 = dotData22;
	}

	public String getDotData23() {
		return dotData23;
	}

	public void setDotData23(String dotData23) {
		this.dotData23 = dotData23;
	}

	public String getDotData24() {
		return dotData24;
	}

	public void setDotData24(String dotData24) {
		this.dotData24 = dotData24;
	}

	public String getMonthFreezeData() {
		return monthFreezeData;
	}

	public void setMonthFreezeData(String monthFreezeData) {
		this.monthFreezeData = monthFreezeData;
	}

}
