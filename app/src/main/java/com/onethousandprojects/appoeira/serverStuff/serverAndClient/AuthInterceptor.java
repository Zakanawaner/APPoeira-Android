package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //With this class I add the header to the request and form the JWT authorization method
        String token = SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN);
        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
        return chain.proceed(request);
    }
}
