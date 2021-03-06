package com.wasion.meterreading.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wasion.meterreading.domain.BaseValue;

/**
 * 电信平台Service处理配置 配置类
 * 
 * @author w24882
 * @date 2019年11月15日
 */
@Entity
@Table(name = "T_CFG_IOT_RESOLVER")
public class TCfgIotResolverValue extends BaseValue {

    /**
     * 
     */
    private static final long serialVersionUID = 1838574916228388368L;
    @Id
    private String sn;
    private String resolverId;
    private String serviceType;
    private String serviceDataClassPath;
    private String resolverClassPath;
    /**
     * 通知类型 deviceDatasChanged, commandRsp等
     */
    private String notifyType;
    private transient Class<?> resolverClass;
    private transient Class<?> serviceDataClass;
    /**
     * 由于嵌入式开发人员的不规范，有的ServiceData存在多种数据类型，这里只能由软件来兼容一下，比如：
     * <link>com.wasion.meterreading.domain.dto.telecom.impl.DotReportData</link>
     * <link>com.wasion.meterreading.domain.dto.telecom.impl.DotReportDataDelta</link>
     */
    private transient List<Class<?>> serviceDataClasses = new ArrayList<>();
    private String remark;

    @Column(name = "SN", length = 40)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "RESOLVER_ID", length = 40)
    public String getResolverId() {
        return resolverId;
    }

    public void setResolverId(String resolverId) {
        this.resolverId = resolverId;
    }

    @Column(name = "SERVICE_TYPE", length = 40)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "DATA_CLASS_PATH", length = 255)
    public String getServiceDataClassPath() {
        return serviceDataClassPath;
    }

    public void setServiceDataClassPath(String serviceDataClassPath) {
        this.serviceDataClassPath = serviceDataClassPath;
    }

    @Column(name = "RESOLVER_CLASS_PATH", length = 255)
    public String getResolverClassPath() {
        return resolverClassPath;
    }

    public void setResolverClassPath(String resolverClassPath) {
        this.resolverClassPath = resolverClassPath;
    }

    public Class<?> getResolverClass() {
        return resolverClass;
    }

    public void setResolverClass(Class<?> resolverClass) {
        this.resolverClass = resolverClass;
    }

    public Class<?> getServiceDataClass() {
        return serviceDataClass;
    }

    public void setServiceDataClass(Class<?> serviceDataClass) {
        this.serviceDataClass = serviceDataClass;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    @Column(name = "REMARK", length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Class<?>> getServiceDataClasses() {
        return serviceDataClasses;
    }

    public void setServiceDataClasses(List<Class<?>> serviceDataClasses) {
        this.serviceDataClasses = serviceDataClasses;
    }

}
