package com.wasion.meterreading.task.context;

import com.wasion.meterreading.constant.SystemConstant;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.task.process.CommandSender;
import com.wasion.meterreading.task.process.mobile.MobileQueryHisSender;
import com.wasion.meterreading.task.process.telecom.TelecomQueryHisSender;
import com.wasion.meterreading.util.ContextProvider;

import java.util.List;


/**
 * 命令上下文建造者，使用建造者赋值比构造函数要清晰
 *
 * @author w24882 xieningjie
 * @date 2020年1月4日
 */
public class CommandContextBuilder {

    private DefaultCommandContext instance = null;

    public static CommandContextBuilder build(String type) {
        CommandContextBuilder builder = new CommandContextBuilder();
        DefaultCommandContext ctx = null;
        CommandSender sender = null;
        switch (type) {
            case SystemConstant.PLATFORM_MOBILE:
                ctx = new MobileCommandContext();
                sender = ContextProvider.getBean(MobileQueryHisSender.class);
                ctx.setCurrentSender(sender);
                break;
            case SystemConstant.PLATFORM_TELECOM:
                ctx = new TelecomCommandContext();
                sender = ContextProvider.getBean(TelecomQueryHisSender.class);
                ctx.setCurrentSender(sender);
                break;
            default:
                break;
        }
        builder.instance = ctx;
        return builder;
    }

    public DefaultCommandContext create() {
        return this.instance;
    }

    public CommandContextBuilder setTask(RtTaskOnlineV1910 task) {
        this.instance.setTask(task);
        return this;
    }

    public CommandContextBuilder setAppConfig(TCfgAppsConfig config) {
        this.instance.setAppConfig(config);
        return this;
    }

    public CommandContextBuilder setProtocolCmd(TCfgProtocolCmd cmd) {
        this.instance.setProtocolCmd(cmd);
        return this;
    }

    public CommandContextBuilder setPlatform(String platform) {
        this.instance.setPlatform(platform);
        return this;
    }

    public CommandContextBuilder setSnMeter(String snMeter) {
        this.instance.setSnMeter(snMeter);
        return this;
    }

    public CommandContextBuilder setDeviceId(String deviceId) {
        this.instance.setDeviceId(deviceId);
        return this;
    }

    public CommandContextBuilder setSuccessCommands(List<RtTaskOnlineV1910> successCommands) {
        this.instance.setSuccessCommands(successCommands);
        return this;
    }

    public CommandContextBuilder setFailCommands(List<RtTaskOnlineV1910> failCommands) {
        this.instance.setFailCommands(failCommands);
        return this;
    }

}
