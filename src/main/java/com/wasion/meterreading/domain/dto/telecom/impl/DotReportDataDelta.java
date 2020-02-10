package com.wasion.meterreading.domain.dto.telecom.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wasion.meterreading.domain.dto.telecom.ServiceDataDetail;

/**
 * 点冻结数据上报数据, 增量版本 数据样例：
 * 
 * <pre>
{
    "notifyType": "deviceDatasChanged",
    "requestId": null,
    "deviceId": "4dba963e-61d3-42a2-89f7-7c3e4550f9a7",
    "gatewayId": "4dba963e-61d3-42a2-89f7-7c3e4550f9a7",
    "services": [
        {
            "serviceId": "DotReport",
            "serviceType": "DotReport",
            "data": {
                "meterEleno": "00113005677280",
                "Dotfreezingperiod": 40,
                "freezingnumber": 36,
                "Dotreporttime": "1912220040",
                "Dotdata": [
                    1000,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                ]
            },
            "eventTime": "20191223T013619Z"
        }
    ]
}
 * </pre>
 * 
 * @author w24882 xieningjie
 * @date 2019年12月13日
 */
public class DotReportDataDelta extends ServiceDataDetail {
    /**
     * 
     */
    private static final long serialVersionUID = -8588191713243004278L;
    @JsonProperty(value = "meterEleno")
    private String meterEleno;
    @JsonProperty(value = "Dotfreezingperiod")
    private Integer dotFreezingPeriod;
    @JsonProperty(value = "freezingnumber")
    private Integer freezingNumber;
    @JsonProperty(value = "Dotreporttime")
    private String dotReportTime;
    @JsonProperty(value = "Dotdata")
    private List<Integer> deltaDotDatas;

    public String getMeterEleno() {
        return meterEleno;
    }

    public void setMeterEleno(String meterEleno) {
        this.meterEleno = meterEleno;
    }

    public String getDotReportTime() {
        return dotReportTime;
    }

    public void setDotReportTime(String dotReportTime) {
        this.dotReportTime = dotReportTime;
    }

    public List<Integer> getDeltaDotDatas() {
        return deltaDotDatas;
    }

    public void setDeltaDotDatas(List<Integer> deltaDotDatas) {
        this.deltaDotDatas = deltaDotDatas;
    }

    public Integer getDotFreezingPeriod() {
        return dotFreezingPeriod;
    }

    public void setDotFreezingPeriod(Integer dotFreezingPeriod) {
        this.dotFreezingPeriod = dotFreezingPeriod;
    }

    public Integer getFreezingNumber() {
        return freezingNumber;
    }

    public void setFreezingNumber(Integer freezingNumber) {
        this.freezingNumber = freezingNumber;
    }

}
