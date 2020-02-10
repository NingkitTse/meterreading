package com.wasion.meterreading.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCfgProtocolCmdParam entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CFG_PROTOCOL_CMD_PARAM")
public class TCfgProtocolCmdParam implements java.io.Serializable {

    // Fields

    private String paramId;
    private String cmdId;
    private Integer paramNo;
    private String paramName;
    private String paramKey;
    private String minValue;
    private String maxValue;
    private String dataType;
    private String emuList;
    private String dataFormat;
    private String unit;
    private String defaultValue;
    private Integer byteLen;
    /**
     * 1:输入 0:输出
     */
    private String paramType;
    private String isShow;
    private String paramFrameType;

    // Constructors

    /** default constructor */
    public TCfgProtocolCmdParam() {
    }

    /** minimal constructor */
    public TCfgProtocolCmdParam(String paramId, String cmdId, String dataType, String paramType, String isShow) {
        this.paramId = paramId;
        this.cmdId = cmdId;
        this.dataType = dataType;
        this.paramType = paramType;
        this.isShow = isShow;
    }

    /** full constructor */
    public TCfgProtocolCmdParam(String paramId, String cmdId, Integer paramNo, String paramName, String minValue, String maxValue, String dataType, String emuList, String dataFormat, String unit,
            String defaultValue, Integer byteLen, String paramType, String isShow) {
        this.paramId = paramId;
        this.cmdId = cmdId;
        this.paramNo = paramNo;
        this.paramName = paramName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.dataType = dataType;
        this.emuList = emuList;
        this.dataFormat = dataFormat;
        this.unit = unit;
        this.defaultValue = defaultValue;
        this.byteLen = byteLen;
        this.paramType = paramType;
        this.isShow = isShow;
    }

    // Property accessors
    @Id
    @Column(name = "PARAM_ID", unique = true, nullable = false, length = 40)
    public String getParamId() {
        return this.paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    @Column(name = "CMD_ID", nullable = false, length = 50)
    public String getCmdId() {
        return this.cmdId;
    }

    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    @Column(name = "PARAM_NO", precision = 22, scale = 0)
    public Integer getParamNo() {
        return this.paramNo;
    }

    public void setParamNo(Integer paramNo) {
        this.paramNo = paramNo;
    }

    @Column(name = "PARAM_NAME", length = 60)
    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name = "MIN_VALUE", length = 50)
    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    @Column(name = "MAX_VALUE", length = 50)
    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    @Column(name = "DATA_TYPE", nullable = false, length = 1)
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "EMU_LIST", length = 1000)
    public String getEmuList() {
        return this.emuList;
    }

    public void setEmuList(String emuList) {
        this.emuList = emuList;
    }

    @Column(name = "DATA_FORMAT", length = 50)
    public String getDataFormat() {
        return this.dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Column(name = "UNIT", length = 100)
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "DEFAULT_VALUE", length = 200)
    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Column(name = "BYTE_LEN", precision = 22, scale = 0)
    public Integer getByteLen() {
        return this.byteLen;
    }

    public void setByteLen(Integer byteLen) {
        this.byteLen = byteLen;
    }

    @Column(name = "PARAM_TYPE", nullable = false, length = 1)
    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Column(name = "IS_SHOW", nullable = false, length = 1)
    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Column(name = "PARAM_KEY", nullable = true, length = 50)
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    @Column(name = "PARAM_FRAME_TYPE", nullable = true, length = 10)
    public String getParamFrameType() {
        return paramFrameType;
    }

    public void setParamFrameType(String paramFrameType) {
        this.paramFrameType = paramFrameType;
    }

}