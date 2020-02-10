package telecom.sdk.service.dataCollection;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import telecom.sdk.utils.Constant;
import telecom.sdk.utils.HttpsUtil;
import telecom.sdk.utils.JsonUtil;
import telecom.sdk.utils.StreamClosedHttpResponse;

/**
 * Query Specify Device : This interface is used to query information of a
 * specified device based on the device ID on the IoT platform.
 * 
 */
public class QuerySpecifyDevice {

	private static Logger LOG = LoggerFactory.getLogger(QuerySpecifyDevice.class);

	public static void main(String args[]) throws Exception {
		querySpecifyDevice("ec19305e-e051-4bb9-aa67-63932324a01b");
	}

	public static String querySpecifyDevice(String deviceId) throws Exception {
		// Two-Way Authentication
		HttpsUtil httpsUtil = HttpsUtil.getInstance();
		httpsUtil.initSSLConfigForTwoWay();

		// Authentication.get token
		String accessToken = login(httpsUtil);

		// Please make sure that the following parameter values have been
		// modified in the Constant file.
		String appId = Constant.APPID;

		// please replace the deviceId, when you call this interface.
		String urlQuerySpecifyDevice = Constant.QUERY_SPECIFY_DEVICE + "/" + deviceId;

		Map<String, String> paramQuerySpecifyDevice = new HashMap<>();
		paramQuerySpecifyDevice.put("appId", appId);

		Map<String, String> header = new HashMap<>();
		header.put(Constant.HEADER_APP_KEY, appId);
		header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

		StreamClosedHttpResponse bodyQuerySpecifyDevice = httpsUtil.doGetWithParasGetStatusLine(urlQuerySpecifyDevice,
				paramQuerySpecifyDevice, header);

		LOG.info("QuerySpecifyDevice, response content:");
		LOG.info("" + bodyQuerySpecifyDevice.getStatusLine());
		LOG.info(bodyQuerySpecifyDevice.getContent());
		return bodyQuerySpecifyDevice.getContent();
	}

	/**
	 * Authentication.get token
	 */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = Constant.APPID;
		String secret = Constant.SECRET;
		String urlLogin = Constant.APP_AUTH;

		Map<String, String> paramLogin = new HashMap<>();
		paramLogin.put("appId", appId);
		paramLogin.put("secret", secret);

		StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

		System.out.println("app auth success,return accessToken:");
		System.out.println(responseLogin.getStatusLine());
		System.out.println(responseLogin.getContent());
		System.out.println();

		Map<String, String> data = new HashMap<>();
		data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
		return data.get("accessToken");
	}

}
