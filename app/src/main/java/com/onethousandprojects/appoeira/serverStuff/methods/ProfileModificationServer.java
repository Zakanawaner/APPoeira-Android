package com.onethousandprojects.appoeira.serverStuff.methods;

import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.userModification.ClientUserModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.userModification.ServerUserModificationResponse;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModificationServer {
    private ServerUserModificationResponse serverUserModificationResponse;
    Client Client;
    public Server Server;

    public ProfileModificationServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendModificationsToServer(ProfileModificationActivity ProfileModificationActivity,
                                          String name, String lastName, String apelhido,
                                          String email, String pass, String newPass, Integer roleId,
                                          Bitmap imageBitmap) throws IOException {
        ClientUserModificationRequest clientUserModificationRequest = new ClientUserModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID), name, lastName, apelhido, email, pass, newPass, roleId);
        Call<ServerUserModificationResponse> call = Server.post_user_update_profile(clientUserModificationRequest, CommonMethods.fromBitmapToFile(ProfileModificationActivity, imageBitmap, "user", "avatar", SharedPreferencesManager.getIntegerValue(Constants.ID), 0));
        call.enqueue(new Callback<ServerUserModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerUserModificationResponse> call, @NonNull Response<ServerUserModificationResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    serverUserModificationResponse = response.body();
                    ProfileModificationActivity.manageResponse();
                    ProfileModificationActivity.refreshActivity();
                } else {
                    Toast.makeText(ProfileModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerUserModificationResponse> call, @NonNull Throwable t) {
                Toast.makeText(ProfileModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public ServerUserModificationResponse getServerUserModificationResponse() {
        return serverUserModificationResponse;
    }
}
