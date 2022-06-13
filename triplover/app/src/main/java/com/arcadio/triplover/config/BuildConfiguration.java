package com.arcadio.triplover.config;

import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;

public class BuildConfiguration {
    /*Configure jni/keys.c file first for stage and production build*/
    enum BuildType {
        STAGE,
        PRODUCTION
    }

    static BuildType buildType = BuildType.STAGE;

    //Production url
    private static String PROD_URL = "http://52.221.202.198:83/";
    //Stage url
    private static String STG_URL = "http://52.221.202.198:83/";
    public static final String ROOT_URL_THUMB = "https://tbbd-flight.s3.ap-southeast-1.amazonaws.com/airlines-logo/";

    public static String getBaseURL() {
        if (buildType == BuildType.PRODUCTION) {
            return PROD_URL;
        } else {
            return STG_URL;
        }
    }

    public static String getThumbURL() {
        if (buildType == BuildType.PRODUCTION) {
            return ROOT_URL_THUMB;
        } else {
            return ROOT_URL_THUMB;
        }
    }

    public static String getSSLBuildType() {
        if (buildType == BuildType.PRODUCTION) {
            return SSLCSdkType.LIVE;
        } else {
            return SSLCSdkType.TESTBOX;
        }
    }

    public static boolean showLog() {
        if (buildType == BuildType.PRODUCTION) {
            return false;
        } else {
            return false;
        }
    }

    public static String getPaymentValidationURL() {
        if (buildType == BuildType.PRODUCTION) {
            return "";
        } else {
            return "";
        }
    }
}
