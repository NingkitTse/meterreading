package cmcciot.onenet.nbapi.sdk.api.online;

import org.json.JSONObject;

import cmcciot.onenet.nbapi.sdk.entity.CommonEntity;
import cmcciot.onenet.nbapi.sdk.utils.HttpSendCenter;
import okhttp3.Callback;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 * apiKey: the product of api-key which can be found on OneNET
 */
public class ReadOpe extends BasicOpe{
    public ReadOpe(String apiKey) {
        super(apiKey);
    }
    @Override
    public JSONObject operation(CommonEntity commonEntity, JSONObject body) {
        return HttpSendCenter.get(apiKey, commonEntity.toUrl());
    }
    @Override
    public void operation(CommonEntity commonEntity, JSONObject body, Callback callback) {
        HttpSendCenter.getAsync(apiKey, commonEntity.toUrl(), callback);
    }

}
