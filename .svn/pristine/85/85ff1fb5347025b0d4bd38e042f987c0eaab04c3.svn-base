package com.wasion.meterreading.task.context;

import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.task.process.CommandSender;
import lombok.Data;

import java.util.List;

/**
 * 命令下发上下文默认实现
 * 实现了基本字段的存储转发，以及CommandSender的链式调用
 *
 * @author w24882 xieningjie
 * @date 2020年1月4日
 */
@Data
public abstract class DefaultCommandContext implements CommandContext {

    private String snMeter;
    private String platform;
    private String imei;
    private String deviceId;
    private RtTaskOnlineV1910 task;
    private TCfgAppsConfig appConfig;
    private TCfgProtocolCmd protocolCmd;
    private List<RtTaskOnlineV1910> successCommands;
    private List<RtTaskOnlineV1910> failCommands;
    private CommandSender currentSender;
    private String requestMsg;
    private String responseMsg;

    public TCfgProtocolCmd getProtocolCmd() {
        return protocolCmd;
    }

    public void setProtocolCmd(TCfgProtocolCmd protocolCmd) {
        this.protocolCmd = protocolCmd;
    }

    @Override
    public String getSnMeter() {
        return this.snMeter;
    }

    public void setSnMeter(String snMeter) {
        this.snMeter = snMeter;
    }

    @Override
    public String getParameters() {
        return this.task == null ? null : this.task.getParameters();
    }

    @Override
    public String getParamJsonStr() {
        return this.task == null ? null : this.task.getParamJsonStr();
    }

    @Override
    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public RtTaskOnlineV1910 getTask() {
        return this.task;
    }

    public void setTask(RtTaskOnlineV1910 task) {
        this.task = task;
    }

    public TCfgAppsConfig getAppConfig() {
        return this.appConfig;
    }

    public void setAppConfig(TCfgAppsConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public String getCmdId() {
        return this.task == null ? null : this.task.getSnProtocolCommand();
    }

    public List<RtTaskOnlineV1910> getSuccessCommands() {
        return successCommands;
    }

    public void setSuccessCommands(List<RtTaskOnlineV1910> successCommands) {
        this.successCommands = successCommands;
    }

    public List<RtTaskOnlineV1910> getFailCommands() {
        return failCommands;
    }

    public void setFailCommands(List<RtTaskOnlineV1910> failCommands) {
        this.failCommands = failCommands;
    }

    @Override
    public void process() {
        if (null == currentSender) {
            return;
        }
        CommandSender pre = currentSender;
        if (currentSender.hasNext()) {
            currentSender = currentSender.next();
        }
        pre.sendCommand(this);
    }

    public CommandSender getCurrentSender() {
        return currentSender;
    }

    public void setCurrentSender(CommandSender currentSender) {
        this.currentSender = currentSender;
    }

    @Override
    public void fail(RtTaskOnlineV1910 command, String traceId, String remark) {
        CommandContext.super.fail(command, traceId, remark);
        this.failCommands.add(command);
    }

    @Override
    public void fail(RtTaskOnlineV1910 command, String traceId) {
        CommandContext.super.fail(command, traceId);
        this.failCommands.add(command);
    }

    @Override
    public void success(RtTaskOnlineV1910 command, String status, String commandId) {
        CommandContext.super.success(task, status, commandId);
        this.successCommands.add(command);
    }

}
