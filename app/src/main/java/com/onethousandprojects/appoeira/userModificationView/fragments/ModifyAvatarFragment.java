package com.onethousandprojects.appoeira.userModificationView.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.getPermissionsView.GetPermissionsActivity;
import com.onethousandprojects.appoeira.serverStuff.uploadPicture.ClientUploadPictureRequest;
import com.onethousandprojects.appoeira.serverStuff.uploadPicture.ServerUploadPictureResponse;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ModifyAvatarFragment extends DialogFragment {

    private Bitmap imageBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ProfileModificationActivity profileModificationActivity = (ProfileModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_modify_avatar, container, false);
        ImageView ivClose = view.findViewById(R.id.modifyAvatarClose);
        Button btnUploadPic = view.findViewById(R.id.modifyAvatarSend);
        Button btnTakePic = view.findViewById(R.id.takePicture);
        Button btnSelectPic = view.findViewById(R.id.selectPicture);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileModificationActivity.killAvatarFragment();
            }
        });
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchPicture();
                } else {
                    Bundle fromGetPermissionsActivity = requireActivity().getIntent().getExtras();
                    if (fromGetPermissionsActivity == null) {
                        Intent toGetPermissionsActivity = new Intent(requireActivity(), GetPermissionsActivity.class);
                        startActivity(toGetPermissionsActivity);
                    } else {
                        ask_camera_permission();
                    }
                }
            }
        });
        btnSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPicture();
                } else {
                    Bundle fromGetPermissionsActivity = requireActivity().getIntent().getExtras();
                    if (fromGetPermissionsActivity == null) {
                        Intent toGetPermissionsActivity = new Intent(requireActivity(), GetPermissionsActivity.class);
                        startActivity(toGetPermissionsActivity);
                    } else {
                        ask_storage_read_permission();
                    }
                }
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageBitmap != null) {
                    Call<ServerUploadPictureResponse> call = null;
                    try {
                        ClientUploadPictureRequest clientUploadPictureRequest = new ClientUploadPictureRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN));
                        call = ((ProfileModificationActivity) requireActivity()).Server.post_picture(clientUploadPictureRequest, CommonMethods.fromBitmapToFile(requireContext(), imageBitmap, "user", "avatar", SharedPreferencesManager.getIntegerValue(Constants.ID), 0));
                        call.enqueue(new Callback<ServerUploadPictureResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ServerUploadPictureResponse> call, @NonNull Response<ServerUploadPictureResponse> response) {
                                if (response.isSuccessful()) {
                                    ServerUploadPictureResponse serverUploadPictureResponse = response.body();
                                    assert serverUploadPictureResponse != null;
                                    if (serverUploadPictureResponse.isOk()){
                                        SharedPreferencesManager.setStringValue(Constants.PIC_URL, serverUploadPictureResponse.getPicUrl());
                                        profileModificationActivity.killAvatarFragment();
                                        Toast.makeText(getContext(), R.string.newImageReady, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ServerUploadPictureResponse> call,
                                                  @NonNull Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProfileModificationActivity activity = (ProfileModificationActivity) getActivity();
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.CAMERA_REQUEST_CODE_INTENT) {
                assert data != null;
                Bundle extras = data.getExtras();
                assert extras != null;
                imageBitmap = (Bitmap) extras.get("data");
                assert activity != null;
                activity.ivUserAvatar.setImageBitmap(imageBitmap);
            }
            if (requestCode == Constants.GALLERY_REQUEST_CODE_INTENT) {
                assert data != null;
                Uri selectedImageUri = data.getData();
                assert selectedImageUri != null;
                InputStream inputStream = null;
                try {
                    inputStream = requireActivity().getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageBitmap = BitmapFactory.decodeStream(inputStream);
                assert activity != null;
                activity.ivUserAvatar.setImageBitmap(imageBitmap);
            }
        }
    }

    private void dispatchPicture() {
        Intent toCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (toCamera.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(toCamera, Constants.CAMERA_REQUEST_CODE_INTENT);
        }
    }
    private void selectPicture() {
        Intent toGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (toGallery.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(toGallery, Constants.GALLERY_REQUEST_CODE_INTENT);
        }
    }

    private void ask_camera_permission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, Constants.CAMERA_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, Constants.CAMERA_REQUEST_CODE);
            }
        }
    }
    private void ask_storage_read_permission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.GALLERY_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.GALLERY_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchPicture();
            }
        }
        if (requestCode == Constants.GALLERY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPicture();
            }
        }
    }
}
