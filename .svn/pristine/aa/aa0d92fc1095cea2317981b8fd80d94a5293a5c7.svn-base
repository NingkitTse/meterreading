package telecom.sdk.service.dataCollection;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import telecom.sdk.service.BaseApiEntity;
import telecom.sdk.utils.Constant;
import telecom.sdk.utils.HttpsUtil;
import telecom.sdk.utils.StreamClosedHttpResponse;

/**
 * 电信设备历史记录查询对象，使用建造者模式
 * 
 * @author w24882 xieningjie
 * @date 2020年1月7日
 */
public class QueryDeviceHistoryData extends BaseApiEntity {

    private String startTime;
    private String endTime;
    private String pageNo = "0";
    private String pageSize = "10";
    
    public Builder build() {
        return new Builder(this);
    }

    public class Builder extends BaseApiEntity.Builder<Builder> {

        private QueryDeviceHistoryData instance;

        public Builder(QueryDeviceHistoryData instance) {
            super(instance);
            this.instance = instance;
        }

        public QueryDeviceHistoryData create() {
            return instance;
        }

        public Builder setStartTime(String startTime) {
            instance.startTime = startTime;
            return this;
        }

        public Builder setEndTime(String endTime) {
            instance.endTime = endTime;
            return this;
        }

        public Builder setPageNo(String pageNo) {
            instance.pageNo = pageNo;
            return this;
        }

        public Builder setPageSize(String pageSize) {
            instance.pageSize = pageSize;
            return this;
        }
    }

    public HttpResponse process() throws Exception {
        // Two-Way Authentication
        HttpsUtil httpsUtil = HttpsUtil.getInstance();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication.get token
        String accessToken = login(httpsUtil);

        // Please make sure that the following parameter values have been
        // modified in the Constant file.
        String appId = this.appid;
        String urlQueryDeviceHistoryData = this.baseUrl + Constant.QUERY_DEVICE_HISTORY_DATA_WITHOUT_BASE;

        Map<String, String> paramQueryDeviceHistoryData = new HashMap<>();
        paramQueryDeviceHistoryData.put("deviceId", this.deviceId);
        paramQueryDeviceHistoryData.put("gatewayId", this.gatewayId);
        paramQueryDeviceHistoryData.put("startTime", this.startTime);
        paramQueryDeviceHistoryData.put("endTime", this.endTime);
        paramQueryDeviceHistoryData.put("pageNo", this.pageNo);
        paramQueryDeviceHistoryData.put("pageSize", this.pageSize);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse bodyQueryDeviceHistoryData = httpsUtil.doGetWithParasGetStatusLine(urlQueryDeviceHistoryData, paramQueryDeviceHistoryData, header);

        System.out.println("QueryDeviceHistoryData, response content:");
        System.out.println(bodyQueryDeviceHistoryData.getStatusLine());
        System.out.println(bodyQueryDeviceHistoryData.getContent());
        System.out.println();
        return bodyQueryDeviceHistoryData;
    }

}
