package com.onethousandprojects.appoeira.commonThings;

import com.onethousandprojects.appoeira.serverStuff.methods.PeriodicalRequests;

public class Constants {
    public static final String API_APPOEIRA_BASE_URL = "https://appoeira.pythonanywhere.com/";
    public static final String PERF_TOKEN = "PERF_TOKEN";
    public static final String ID = "ID";
    public static final String APELHIDO = "APELHIDO";
    public static final String PIC_URL = "PIC_URL";
    public static final String IS_PREMIUM = "Premium";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String RANK = "RANK";
    public static final String EMAIL = "EMAIL";
    public static final String EMAIL_VERIFIED = "EMAIL_VERIFIED";
    public static final Integer LOCATION_REQUEST_CODE = 1001;
    public static final String TAG = "MyLogTag";
    public static final Integer CAMERA_REQUEST_CODE = 1002;
    public static final Integer GALLERY_REQUEST_CODE = 1003;
    public static final int CAMERA_REQUEST_CODE_INTENT = 1012;
    public static final int GALLERY_REQUEST_CODE_INTENT = 1013;

    public static CommonMethods.NewsVariable newsVariable = new CommonMethods.NewsVariable(false);
    public static PeriodicalRequests periodicalRequests = new PeriodicalRequests();

}
