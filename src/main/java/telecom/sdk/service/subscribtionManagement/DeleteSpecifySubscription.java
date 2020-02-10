package telecom.sdk.service.subscribtionManagement;

import java.util.HashMap;
import java.util.Map;

import telecom.sdk.utils.Constant;
import telecom.sdk.utils.HttpsUtil;
import telecom.sdk.utils.JsonUtil;
import telecom.sdk.utils.StreamClosedHttpResponse;

/**
 * Delete Specify Subscription :
 * This interface is used to delete the configuration information about a subscription by subscription ID on the IoT platform.
 */
public class DeleteSpecifySubscription {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = HttpsUtil.getInstance();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication.get token
        String accessToken = login(httpsUtil);

        //please replace the subscriptionId, when you call this interface.
        String subscriptionId = "a57a5ef1-d770-4bde-b7fd-f8500fb7bf5a";
        
        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlDeleteSpecifySubscription = Constant.DELETE_SPECIFY_SUBSCRIPTION + "/" + subscriptionId;

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseDeleteSpecifySubscription = httpsUtil.doDeleteWithParasGetStatusLine(urlDeleteSpecifySubscription, null, header);

        System.out.println("DeleteSpecifySubscription, response content:");
        System.out.println(responseDeleteSpecifySubscription.getStatusLine());
        System.out.println(responseDeleteSpecifySubscription.getContent());
        System.out.println();
        
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
