package cmcciot.onenet.nbapi.sdk.entity;

import java.util.ArrayList;
import java.util.List;

import cmcciot.onenet.nbapi.sdk.config.Config;

public class GetDeviceDataPoints extends CommonEntity {

	private List<String> deviceIds = new ArrayList<>();

	public GetDeviceDataPoints(List<String> deviceIds) {
		super();
		this.deviceIds.addAll(deviceIds);
	}

	@Override
	public String toUrl() {
		StringBuilder sb = new StringBuilder(Config.getDomainName());
		sb.append("/devices/datapoints");
		if (null != deviceIds && !deviceIds.isEmpty()) {
			sb.append("?devIds=");
			for (String deviceId : deviceIds) {
				sb.append(deviceId + ",");
			}
			return sb.toString().substring(0, sb.length() - 1);
		}
		return sb.toString();
	}

}
