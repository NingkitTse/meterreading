package cmcciot.onenet.nbapi.sdk.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cmcciot.onenet.nbapi.sdk.config.Config;

/**
 * 移动19年10月通用协议缓存写操作
 * 
 * @author w24882 xieningjie
 * @date 2019年12月12日
 */
public class WriteOfflineV1910 extends CommonEntity {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private Date expiredTime;
	private Integer mode;

	/**
	 * @param imei
	 *            设备IMEI
	 * @param objId
	 *            写对象ID
	 * @param objInstId
	 *            写实例ID
	 * @param mode
	 *            写的模式（1：replace，2：partial update）
	 */
	public WriteOfflineV1910(String imei, Integer objId, Integer objInstId, Integer mode, Date expiredTime) {
		this.imei = imei;
		this.objId = objId;
		this.objInstId = objInstId;
		this.mode = mode;
		this.expiredTime = expiredTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String toUrl() {
		StringBuilder url = new StringBuilder(Config.getDomainName());
		url.append("/nbiot/offline?imei=").append(this.imei);
		url.append("&obj_id=").append(this.objId);
		url.append("&obj_inst_id=").append(this.objInstId);
		url.append("&mode=").append(this.mode);
		url.append("&trigger_msg=6"); // 7 容易出现错误请求
		url.append("&expired_time=").append(sdf.format(this.expiredTime));
		return url.toString();
	}
}
