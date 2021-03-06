package com.wasion.meterreading.task.process.telecom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.entity.RtTaskOnlineV1910;
import com.wasion.meterreading.entity.TCfgAppsConfig;
import com.wasion.meterreading.entity.TCfgProtocolCmd;
import com.wasion.meterreading.task.context.CommandContext;
import com.wasion.meterreading.task.process.CommandSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import telecom.sdk.service.commandDelivery.CreateDeviceCommand;
import telecom.sdk.utils.HttpsUtil;

/**
 * 电信通用命令下发Sender
 *
 * @author w24882 xieningjie
 * @date 2020年1月6日
 */
@Component
@Slf4j
public class TelecomCmdSender implements CommandSender {

    @Override
    public void sendCommand(CommandContext context) {
        String paramJsonStr = context.getParamJsonStr();
        TCfgAppsConfig appConfig = context.getAppConfig();
        String appid = appConfig.getAppid();
        RtTaskOnlineV1910 task = context.getTask();
        String traceId = task.getTraceId();

        TCfgProtocolCmd protocolCmd = context.getProtocolCmd();
        String serviceId = protocolCmd.getServiceId();
        String method = protocolCmd.getMethod();
        String deviceId = context.getDeviceId();
        HttpResponse response = null;
        CreateDeviceCommand command = null;
        String responseBody = null;

        try {
            String baseUrl = appConfig.getBaseUrl();
            String secret = appConfig.getSecret();
            log.info("Send command to telecom platform, request serviceId is {}, method is {}, parameters is {}.",
                    serviceId, method, paramJsonStr);
            command = new CreateDeviceCommand().build().setAppid(appid).setBaseUrl(baseUrl).setSecret(secret)
                    .setDeviceId(deviceId).setServiceId(serviceId).setMethod(method)
                    .setParas(JSON.parseObject(paramJsonStr)).create();
            response = command.process();

            if (null == response) {
                log.error("Publish command to platform failed");
                context.fail(task, traceId, "未获取到平台响应");
                return;
            }
            final int statusCode = response.getStatusLine().getStatusCode();
            responseBody = HttpsUtil.getInstance().getHttpResponseBody(response);
            JSONObject rspObj = JSON.parseObject(responseBody);
            if (statusCode == HttpStatus.CREATED.value()) {
                String status = rspObj.getString("status");
                String commandId = rspObj.getString("commandId");
                context.success(task, status, commandId);
            } else {
                // DeviceCommand- ResponseBody:
                // {"error_code":"100612","error_desc":"Device is zombie."}
                String error_code = rspObj.getString("error_code");
                String errorDesc = rspObj.getString("error_desc");
                context.fail(task, traceId, "平台未返回命令创建成功, " + error_code + "," + errorDesc);
            }
        } catch (Exception e1) {
            log.error("traceId: {}", traceId, e1);
            context.fail(task, traceId, "未知异常，请跟踪日志.");
        } finally {
            log.info("命令下发完成, 请求: {}, 结果: {}.", command, responseBody);
        }
    }

}
