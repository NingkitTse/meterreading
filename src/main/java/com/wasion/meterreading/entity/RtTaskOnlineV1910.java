package com.wasion.meterreading.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wasion.meterreading.domain.BaseValue;

@Entity
@Table(name = "RT_TASK_ONLINE_V1910")
public class RtTaskOnlineV1910 extends BaseValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 795944866763534120L;

	private String sn;
	private String snDevice;
	private String snMeter;
	private String snProtocolCommand;
	private Date timeCreate;
	private Date timeCompletion;
	private Integer success;
	private String parameters;
	private String paramJsonStr;
	private String remark;
	private Integer commandPriority;
	/**
	 * 已执行次数
	 */
	private Integer executedTimes;
	private Integer maxExecuteTimes;
	private String returnData;
	private Integer funcType;
	private String groupId;
	private Integer exceptionCode;
	private String commandId;
	private String wlwAppId;
	private String wlwCmdStatus;
	private Integer seq;
	private transient String traceId;

	@Id
	@Column(name = "SN", length = 50)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "SN_DEVICE", length = 50)
	public String getSnDevice() {
		return snDevice;
	}

	public void setSnDevice(String snDevice) {
		this.snDevice = snDevice;
	}

	@Column(name = "SN_METER", length = 50)
	public String getSnMeter() {
		return snMeter;
	}

	public void setSnMeter(String snMeter) {
		this.snMeter = snMeter;
	}

	@Column(name = "SN_PROTOCOL_COMMAND", length = 50)
	public String getSnProtocolCommand() {
		return snProtocolCommand;
	}

	public void setSnProtocolCommand(String snProtocolCommand) {
		this.snProtocolCommand = snProtocolCommand;
	}

	@Column(name = "TIME_CREATE", length = 7)
	public Date getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}

	@Column(name = "TIME_COMPLETION", nullable = true, length = 7)
	public Date getTimeCompletion() {
		return timeCompletion;
	}

	public void setTimeCompletion(Date timeCompletion) {
		this.timeCompletion = timeCompletion;
	}

	@Column(name = "IS_SUCCESS")
	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	@Column(name = "PARAMETERS", length = 255)
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Column(name = "REMARK", length = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COMMAND_PRIORITY")
	public Integer getCommandPriority() {
		return commandPriority;
	}

	public void setCommandPriority(Integer commandPriority) {
		this.commandPriority = commandPriority;
	}

	@Column(name = "RETURN_DATA", length = 255)
	public String getReturnData() {
		return returnData;
	}

	public void setReturnData(String returnData) {
		this.returnData = returnData;
	}

	@Column(name = "FUNC_TYPE")
	public Integer getFuncType() {
		return funcType;
	}

	public void setFuncType(Integer funcType) {
		this.funcType = funcType;
	}

	@Column(name = "GROUP_ID", length = 50)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "EXCEPTION_CODE")
	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Column(name = "COMMAND_ID", length = 50)
	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	@Column(name = "WLW_APP_ID", length = 50)
	public String getWlwAppId() {
		return wlwAppId;
	}

	public void setWlwAppId(String wlwAppId) {
		this.wlwAppId = wlwAppId;
	}

	@Column(name = "MAX_EXECUTE_TIMES")
	public Integer getMaxExecuteTimes() {
		return maxExecuteTimes;
	}

	public void setMaxExecuteTimes(Integer maxExecuteTimes) {
		this.maxExecuteTimes = maxExecuteTimes;
	}

	@Column(name = "EXECUTED_TIMES")
	public Integer getExecutedTimes() {
		return executedTimes;
	}

	public void setExecutedTimes(Integer executedTimes) {
		this.executedTimes = executedTimes;
	}

	@Column(name = "WLW_CMD_STATUS")
	public String getWlwCmdStatus() {
		return wlwCmdStatus;
	}

	public void setWlwCmdStatus(String wlwCmdStatus) {
		this.wlwCmdStatus = wlwCmdStatus;
	}

	@Column(name = "PARAM_JSON_STR")
	public String getParamJsonStr() {
		return paramJsonStr;
	}

	public void setParamJsonStr(String paramJsonStr) {
		this.paramJsonStr = paramJsonStr;
	}

	@Column(name = "SEQ")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}
