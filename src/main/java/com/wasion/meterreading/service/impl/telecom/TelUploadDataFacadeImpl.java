package com.wasion.meterreading.service.impl.telecom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wasion.meterreading.domain.dto.telecom.TelecomContext;
import com.wasion.meterreading.service.NotifyResolveServiceI;
import com.wasion.meterreading.service.UploadDataFacadeI;
import com.wasion.meterreading.service.impl.cache.ResolverConfigCacheService;

/**
 * 移动上传数据解析实现类<br>
 * 层级关系： 上报数据：【notifyType：【{serviceType， serviceData}】】
 * 
 * @author w24882
 * @date 2019年10月28日
 */
@Service(value = "telecomUploadDataServiceImpl")
public class TelUploadDataFacadeImpl implements UploadDataFacadeI {

    private final static Logger LOG = LoggerFactory.getLogger(TelUploadDataFacadeImpl.class);
    @Autowired
    ResolverConfigCacheService resolverConfigCache;

    @Override
    public void parseData(Object data) {
        JSONObject requestBody = (JSONObject) data;
        String notifyType = requestBody.getString("notifyType");
        LOG.info("Telecom nofify type is {}.", notifyType);

        TelecomContext context = new TelecomContext();
        context.setData(requestBody);

        try {
            NotifyResolveServiceI resolver = resolverConfigCache.getResolver(notifyType, NotifyResolveServiceI.class);
            resolver.resolve(context);
        } catch (Exception e) {
            LOG.error("Parse upload data failed,", e);
        }
    }

    /**
     * { "deviceId": "d2187a76-1a6c-404f-b1e7-2fe7a60fa984", "commandId":
     * "f67384a07a5b4581a87b9c93f29824a5", "result": { "resultCode":
     * "SUCCESSFUL", "resultDetail": { "result": { "result": 1,
     * "getdayfreezingstarttimedata": [ "1912040000", 100, "FFFFFFFFFF",
     * 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295,
     * "FFFFFFFFFF", 4294967295, "FFFFFFFFFF", 4294967295, "FFFFFFFFFF",
     * 4294967295 ], "getdayfreezingnumber": 7, "meterEleno": "00113037027741" }
     * } } }
     */
    @Override
    public void parseCommandRsp(Object data) {
        try {
            JSONObject requestBody = (JSONObject) data;

            TelecomContext context = new TelecomContext();
            context.setData(requestBody);
            NotifyResolveServiceI resolver = resolverConfigCache.getResolver("commandRsp", NotifyResolveServiceI.class);
            resolver.resolve(context);
        } catch (Exception e) {
            LOG.error("Parse command response failed,", e);
        }
    }

}
