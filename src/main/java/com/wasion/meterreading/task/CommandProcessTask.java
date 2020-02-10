package com.wasion.meterreading.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.config.OnenetProperties;
import com.wasion.meterreading.constant.EcasConstant;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.entity.TRunMeterDocu;
import com.wasion.meterreading.entity.TRunMeterDocuId;
import com.wasion.meterreading.orm.jpa.RtTaskOnlineV1910Repository;
import com.wasion.meterreading.orm.jpa.TCfgProtocolCmdRepository;
import com.wasion.meterreading.orm.jpa.TRunMeterDocuRepository;
import com.wasion.meterreading.service.impl.cache.AppConfigCacheService;
import com.wasion.meterreading.task.context.CommandContextBuilder;
import com.wasion.meterreading.task.context.DefaultCommandContext;
import com.wasion.meterreading.util.CommonUtil;

/**
 * 【水表命令执行】任务
 * 
 * @author w24882
 * @date 2019年11月13日
 */
@ConditionalOnProperty(name = "task.iot.command.process.enable", havingValue = "true")
@EnableConfigurationProperties(value = OnenetProperties.class)
@Component
public class CommandProcessTask {

    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessTask.class);
    /**
     * 未执行标识
     */
    public static final Integer STATE_NOT_EXECUTE = -1;
    /**
     * 成功标识
     */
    public static final Integer STATE_EXECUTE_SUCCESS = 1;
    /**
     * 失败标识
     */
    public static final Integer STATE_EXECUTE_FAILURE = 0;

    @Autowired
    private AppConfigCacheService cacheService;
    @Autowired
    private RtTaskOnlineV1910Repository taskRepo;
    @Autowired
    private TCfgProtocolCmdRepository cmdRepo; // 命令仓库
    @Autowired
    private TRunMeterDocuRepository docuRepo; // 表计拓展参数仓库
    private List<RtTaskOnlineV1910> tasks = new ArrayList<>();
    private List<RtTaskOnlineV1910> successCommands = new ArrayList<>();
    private List<RtTaskOnlineV1910> failCommands = new ArrayList<>();
    private Map<RtTaskOnlineV1910, Future<HttpResponse>> telecomFutureMap = new HashMap<>();
    private Map<RtTaskOnlineV1910, Future<org.json.JSONObject>> mobileFutureMap = new HashMap<>();

    @Scheduled(cron = "30 * * * * *")
    public void scheduled() {
        PageRequest page = PageRequest.of(0, 30);
        tasks = taskRepo.findBySuccessOrderByCommandPriority(STATE_NOT_EXECUTE, page);
        successCommands.clear();
        failCommands.clear();
        telecomFutureMap.clear();
        mobileFutureMap.clear();

        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }
        submitCommand2IotPlatform();
        taskRepo.saveAll(tasks);
        LOG.info("Success num-> {}, Failure num-> {}, Total num-> {}", successCommands.size(), failCommands.size(), tasks.size());
    }

    private void submitCommand2IotPlatform() {
        for (RtTaskOnlineV1910 task : tasks) {
            String traceId = CommonUtil.getUuid();
            task.setTraceId(traceId);
            Integer executedTimes = task.getExecutedTimes() == null ? 1 : task.getExecutedTimes() + 1;
            Integer maxExecuteTimes = task.getMaxExecuteTimes() == null ? 0 : task.getMaxExecuteTimes();
            task.setExecutedTimes(executedTimes);
            task.setMaxExecuteTimes(maxExecuteTimes);

            String appid = task.getWlwAppId();
            String cmdId = task.getSnProtocolCommand();
            String snMeter = task.getSnMeter();

            // 查询设备所属平台
            Optional<TRunMeterDocu> docuPlatformOp = docuRepo.findById(new TRunMeterDocuId(snMeter, EcasConstant.PROTOCOL_NO_PLATFORM));
            if (!docuPlatformOp.isPresent()) {
                fail(task, traceId, "Docu配置Platform不存在");
                continue;
            }
            String platform = docuPlatformOp.get().getParamValue();

            // 查询命令配置
            TCfgAppsConfig appConfig = cacheService.getValue(appid);
            Optional<TCfgProtocolCmd> cmdOp = cmdRepo.findById(cmdId);
            if (!cmdOp.isPresent()) {
                fail(task, traceId, "命令不存在");
                continue;
            }
            TCfgProtocolCmd protocolCmd = cmdOp.get();
            // 查询设备ID
            Optional<TRunMeterDocu> docuDeviceIdOp = docuRepo.findById(new TRunMeterDocuId(snMeter, EcasConstant.PROTOCOL_NO_DEVICE_ID));
            if (!docuDeviceIdOp.isPresent()) {
                fail(task, traceId, "设备ID不存在");
                return;
            }
            String deviceId = docuDeviceIdOp.get().getParamValue();
            // 处理不同平台的命令下发
            CommandContextBuilder build = CommandContextBuilder.build(platform);
            DefaultCommandContext ctx = build.setTask(task).setAppConfig(appConfig).setProtocolCmd(protocolCmd).setPlatform(platform).setSnMeter(snMeter).setDeviceId(deviceId)
                    .setSuccessCommands(successCommands).setFailCommands(failCommands).create();
            ctx.process();
        }
    }

    private void fail(RtTaskOnlineV1910 command, String traceId, String remark) {
        Integer maxExecuteTimes = command.getMaxExecuteTimes();
        Integer executedTimes = command.getExecutedTimes();
        if (executedTimes >= maxExecuteTimes) {
            command.setSuccess(STATE_EXECUTE_FAILURE);
            command.setRemark(remark + ", traceId: " + traceId);
            command.setExceptionCode(500);
        }
        failCommands.add(command);
    }

}
