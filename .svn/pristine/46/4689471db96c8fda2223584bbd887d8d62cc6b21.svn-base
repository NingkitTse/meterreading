package cmcciot.onenet.nbapi.sdk.entity;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhuocongbin date 2018/3/16
 */
public abstract class CommonEntity {
    // 设备imei号，平台唯一，必填参数
    public String imei;
    // ISPO标准中的Object ID
    public Integer objId;
    // ISPO标准中的Object Instance ID
    public Integer objInstId;
    // ISPO标准中的Resource ID
    public Integer resId;

    public JSONObject toJsonObject() {
        return null;
    }

    public abstract String toUrl();

    @Override
    public String toString() {
        return "url: " + toUrl() + "," + JSON.toJSONString(this);
    }

}
