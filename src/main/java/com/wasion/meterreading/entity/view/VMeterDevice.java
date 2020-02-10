package com.wasion.meterreading.entity.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wasion.meterreading.domain.BaseValue;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "V_METER_DEVICE")
@EqualsAndHashCode(callSuper = false)
@Data
public class VMeterDevice extends BaseValue {
    /**
     * 
     */
    private static final long serialVersionUID = -7510434082608705333L;
    @Column(name = "DEVICE_ID", length = 32)
    private String deviceId;
    @Id
    @Column(name = "IMEI", length = 32)
    private String imei;
    @Column(name = "IMSI", length = 32)
    private String imsi;
    @Column(name = "REGISTER_DATE")
    private Date registerDate;
    @Column(name = "METER_NO", nullable = true)
    private String meterNo;
    private String snDevice;
    private String snMeter;
    private String platform;

    public VMeterDevice() {
    }

    @Column(name = "SN_DEVICE", length = 40)
    public String getSnDevice() {
        return snDevice;
    }

    public void setSnDevice(String snDevice) {
        this.snDevice = snDevice;
    }

    @Column(name = "SN_METER", length = 40)
    public String getSnMeter() {
        return snMeter;
    }

    public void setSnMeter(String snMeter) {
        this.snMeter = snMeter;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
