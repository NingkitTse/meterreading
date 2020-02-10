package cmcciot.onenet.nbapi.sdk.entity.builder;

import cmcciot.onenet.nbapi.sdk.entity.CommonEntity;
import cmcciot.onenet.nbapi.sdk.entity.GetSingleDeviceDP;

public class SingleDeviceDpBuilder extends CommonEntityBuilder<SingleDeviceDpBuilder> {

    private GetSingleDeviceDP instance;

    public static SingleDeviceDpBuilder build() {
        final SingleDeviceDpBuilder builder = new SingleDeviceDpBuilder();
        builder.instance = new GetSingleDeviceDP();
        return builder;
    }

    public GetSingleDeviceDP create() {
        return this.instance;
    }

    public SingleDeviceDpBuilder setDeviceId(String deviceId) {
        instance.deviceId = deviceId;
        return this;
    }

    public SingleDeviceDpBuilder setBaseUrl(String baseUrl) {
        instance.baseUrl = baseUrl;
        return this;
    }

    public SingleDeviceDpBuilder setStart(String start) {
        instance.start = start;
        return this;
    }

    public SingleDeviceDpBuilder setEnd(String end) {
        instance.end = end;
        return this;
    }

    @Override
    protected CommonEntity getInstance() {
        return this.instance;
    }
}