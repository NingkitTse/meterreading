package com.wasion.meterreading.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConstant {
    public static final String PROFILE_PRO = "pro";
    /**
     * 移动平台
     */
    public static final String PLATFORM_MOBILE = "MOBILE";
    /**
     * 电信平台
     */
    public static final String PLATFORM_TELECOM = "TELECOM";

    public static final String TASK_RETURN_DATA_SEPERATOR = ";";

    private static String activeProfile;

    @Value(value = "${spring.profiles.active}")
    public void setActiveProfile(String activeProfile) {
        SystemConstant.activeProfile = activeProfile;
    }

    public static boolean isProEnv() {
        return PROFILE_PRO.equals(activeProfile);
    }

}
