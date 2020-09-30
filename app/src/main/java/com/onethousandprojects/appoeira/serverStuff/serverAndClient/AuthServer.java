package com.onethousandprojects.appoeira.serverStuff.serverAndClient;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ClientUserDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ServerUserDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthServer {
    @POST("/user-detail")
    Call<ServerUserDetailResponse> post_user_detail(@Body ClientUserDetailRequest clientUserDetailRequest);

}
