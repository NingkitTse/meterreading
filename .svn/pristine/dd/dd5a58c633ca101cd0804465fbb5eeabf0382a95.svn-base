package telecom.sdk.service.commandDelivery;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import telecom.sdk.utils.Constant;
import telecom.sdk.utils.HttpsUtil;
import telecom.sdk.utils.JsonUtil;
import telecom.sdk.utils.StreamClosedHttpResponse;

/**
 * Create a Device Command Cancel Task :
 * 
 * This interface is used to cancel all device commands under the specified device ID.
 */
public class CreateDeviceCmdCancelTask {

	private static final Logger LOG = LoggerFactory.getLogger(CreateDeviceCommand.class);

	public static void main(String[] args) throws Exception {
		String appId = Constant.APPID;
		String secret = Constant.SECRET;
		// String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;
		// please replace the following parameter values, when you call this
		// interface.
		// And those parameter values must be consistent with the content of
		// profile that have been preset to IoT platform.
		// The following parameter values of this demo are use the watermeter
		// profile that already initialized to IoT platform.
		String serviceId = "WaterMeter";
		String method = "CHANGE_STATUS";
		// please replace the deviceId, when you call this interface.
		String deviceId = "de71ad4a-211e-4e53-b48c-80cb2d5c88c8";
		JSONObject paras = JSON.parseObject("{\"value\":\"1\"}");

		submitCachedDeviceCommand(Constant.BASE_URL, appId, secret, serviceId, method, deviceId, paras);
	}

	public static HttpResponse submitCachedDeviceCommand(String baseUrl, String appId, String secret, String serviceId,
			String method, String deviceId, JSONObject paras) throws Exception, IOException {
		// Two-Way Authentication
		HttpsUtil httpsUtil = HttpsUtil.getInstance();
		httpsUtil.initSSLConfigForTwoWay();

		// Authentication.get token
		String accessToken = login(httpsUtil, baseUrl, appId, secret);

		// Please make sure that the following parameter values have been
		// modified in the Constant file.
		String urlCreateDeviceCommand = baseUrl + Constant.CREATE_DEVICECMD_CANCEL_TASK_WITHOUT_BASE;

		// please replace the following parameter values as required, when you
		// call this interface.
		Integer expireTime = 0;
		Integer maxRetransmit = 3;

		Map<String, Object> paramCommand = new HashMap<>();
		paramCommand.put("serviceId", serviceId);
		paramCommand.put("method", method);
		paramCommand.put("paras", paras);

		Map<String, Object> paramCreateDeviceCommand = new HashMap<>();
		paramCreateDeviceCommand.put("deviceId", deviceId);
		paramCreateDeviceCommand.put("command", paramCommand);
		// paramCreateDeviceCommand.put("callbackUrl", callbackUrl);
		paramCreateDeviceCommand.put("expireTime", expireTime);
		paramCreateDeviceCommand.put("maxRetransmit", maxRetransmit);

		String jsonRequest = JsonUtil.jsonObj2Sting(paramCreateDeviceCommand);

		Map<String, String> header = new HashMap<>();
		header.put(Constant.HEADER_APP_KEY, appId);
		header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

		HttpResponse responseCreateDeviceCommand = httpsUtil.doPostJson(urlCreateDeviceCommand, header, jsonRequest);

		String responseBody = httpsUtil.getHttpResponseBody(responseCreateDeviceCommand);
		LOG.info("CreateDeviceCommand, response content:");
		LOG.info("StatusLine: {}", responseCreateDeviceCommand.getStatusLine());
		LOG.info("ResponseBody: {}", responseBody);
		return responseCreateDeviceCommand;
	}

	/**
	 * Authentication.get token
	 */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil, String baseUrl, String appId, String secret) throws Exception {
		String urlLogin = baseUrl + Constant.APP_AUTH_WITHOUT_BASE;

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
