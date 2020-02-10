package telecom.sdk.service;

import java.util.HashMap;
import java.util.Map;

import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSON;

import telecom.sdk.utils.Constant;
import telecom.sdk.utils.HttpsUtil;
import telecom.sdk.utils.JsonUtil;
import telecom.sdk.utils.StreamClosedHttpResponse;

/**
 * 电信API基础对象，最大程度上减少重复代码
 * 
 * @author w24882 xieningjie
 * @date 2020年1月7日
 */
public abstract class BaseApiEntity {

    public String deviceId;
    public String gatewayId;
    public String baseUrl;
    public String appid;
    public String secret;

    public class Builder<T> {

        private BaseApiEntity instance;

        public Builder(BaseApiEntity instance) {
            super();
            this.instance = instance;
        }

        @SuppressWarnings("unchecked")
        public T setDeviceId(String deviceId) {
            instance.deviceId = deviceId;
            instance.gatewayId = deviceId;
            return (T) this;
        }

        /*@SuppressWarnings("unchecked")
        public T setGatewayId(String gatewayId) {
            instance.deviceId = gatewayId;
            instance.gatewayId = gatewayId;
            return (T) this;
        }*/

        @SuppressWarnings("unchecked")
        public T setBaseUrl(String baseUrl) {
            instance.baseUrl = baseUrl;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T setAppid(String appid) {
            instance.appid = appid;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T setSecret(String secret) {
            instance.secret = secret;
            return (T) this;
        }
    }

    /**
     * 返回电信平台返回的JsonString
     * 
     * @return JsonString
     * @throws Exception
     * @author w24882 xieningjie
     * @date 2020年1月7日
     */
    public abstract HttpResponse process() throws Exception;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * Authentication.get token
     */
    @SuppressWarnings("unchecked")
    public String login(HttpsUtil httpsUtil) throws Exception {

        String appId = this.appid;
        String secret = this.secret;
        if (StringUtils.isEmpty(this.baseUrl)) {
            throw new MRException(MRExceptionEnum.CommandBaseUrlEmpty);
        }
        String urlLogin = this.baseUrl + Constant.APP_AUTH_WITHOUT_BASE;

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
