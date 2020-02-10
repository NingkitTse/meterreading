package cmcciot.onenet.nbapi.sdk.entity;

/**
 * 获取单个设备数据点
 * 
 * @author w24882 xieningjie
 * @date 2020年1月6日
 */
public class GetSingleDeviceDP extends CommonEntity {

    public String deviceId;
    public String baseUrl;
    public String start;
    public String end;

    public GetSingleDeviceDP() {
    }

    @Override
    public String toUrl() {
        StringBuilder sb = new StringBuilder(baseUrl);
        String dataStreamId = this.objId + "_" + this.objInstId + "_" + this.resId;
        sb.append("/devices/").append(deviceId).append("/datapoints");
        sb.append("?datastream_id=" + dataStreamId);
        sb.append("&start=" + start);
        sb.append("&end=" + end);
        sb.append("&limit=20");
        return sb.toString();
    }
}
