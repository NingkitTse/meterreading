package cmcciot.onenet.nbapi.sdk.entity;

import cmcciot.onenet.nbapi.sdk.config.Config;

public class GetDeviceByImei extends CommonEntity {

	private String imei;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public GetDeviceByImei(String imei) {
		super();
		this.imei = imei;
	}

	@Override
	public String toUrl() {
		StringBuilder url = new StringBuilder(Config.getDomainName());
		url.append("/devices/getbyimei?");
		url.append("imei=" + this.imei);
		return url.toString();
	}

}
