package com.wasion.meterreading.entity.freezedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.wasion.meterreading.domain.UploadData;
import com.wasion.meterreading.util.CommonUtil;

/**
 * 冻结数据对象
 * 
 * @MappedSuperclass 使用该注解，子类可以继承父类的字段
 * @author w24882
 * @date 2019年10月15日
 */
@MappedSuperclass
public abstract class FreezeDataValue extends UploadData implements Cloneable {
    /**
     * 序列化版本ID
     */
    private static final long serialVersionUID = -6421377686106924420L;

    @Column(name = "SN_DEVICE")
    private String snDevice;
    @Transient
    private transient String controlCode;
    @Transient
    private transient String seq;
    @Column(name = "SN")
    private String sn;

    @Column(name = "FREEZEN_VALUE")
    private String freezenValue;
    private String statusWord;

    @Column(name = "BATTERY")
    private Double battery;
    @Transient
    private Double voltage;

    @Column(name = "STATUS_VALUE")
    private String statusValue;
    private transient boolean notExist;
    private Date registerDate;
    /**
     * 信号强度, 兼容老库，将值存在状态值当中
     */
    @Transient
    private Integer rsrp;
    /**
     * Signal Noise Ratio 信噪比, 兼容老库，将值存在状态值当中
     */
    @Transient
    private Integer snr;
    @Column(name = "IS_VALID_DATA")
    private Boolean validData = true;

    public abstract FreezeDataId getId();

    public abstract void setId(FreezeDataId id);

    public String getSnDevice() {
        return snDevice;
    }

    public void setSnDevice(String snDevice) {
        this.snDevice = snDevice;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getFreezenValue() {
        return freezenValue;
    }

    public void setFreezenValue(String freezenValue) {
        this.freezenValue = freezenValue;
    }

    public String getStatusWord() {
        return statusWord;
    }

    public void setStatusWord(String statusWord) {
        this.statusWord = statusWord;
    }

    public Double getBattery() {
        return battery;
    }

    public void setBattery(Double battery) {
        this.battery = battery;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public boolean isNotExist() {
        return notExist;
    }

    public void setNotExist(boolean notExist) {
        this.notExist = notExist;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        String snMeter = this.getId().getSnMeter();
        Date freezenDate = this.getId().getFreezenDate();
        final FreezeDataValue clone = (FreezeDataValue) super.clone();
        clone.setSn(CommonUtil.getUuid());
        FreezeDataId clonedId = new FreezeDataId();
        clonedId.setSnMeter(snMeter);
        if (null != freezenDate) {
            clonedId.setFreezenDate(new Date(freezenDate.getTime()));
        }
        clone.setId(clonedId);
        return clone;
    }

    public Integer getRsrp() {
        return rsrp;
    }

    public void setRsrp(Integer rsrp) {
        this.rsrp = rsrp;
    }

    public Integer getSnr() {
        return snr;
    }

    public void setSnr(Integer snr) {
        this.snr = snr;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Boolean getValidData() {
        return validData;
    }

    public void setValidData(Boolean validData) {
        this.validData = validData;
    }

}
