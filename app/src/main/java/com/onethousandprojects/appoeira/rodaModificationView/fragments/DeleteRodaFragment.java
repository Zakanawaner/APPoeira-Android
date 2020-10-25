package com.onethousandprojects.appoeira.rodaModificationView.fragments;

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
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.getPermissionsView.GetPermissionsActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class DeleteRodaFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        RodaModificationActivity rodaModificationActivity = (RodaModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        ImageView ivClose = view.findViewById(R.id.deleteClose);
        Button btnUploadPic = view.findViewById(R.id.deleteCancel);
        Button btnDelete = view.findViewById(R.id.delete);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaModificationActivity.killAvatarFragment();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaModificationActivity.rodaModificationServer.deleteRoda(rodaModificationActivity, rodaModificationActivity.fromRodaDetail.getInt("rodaId"));
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaModificationActivity.killAvatarFragment();
            }
        });
        return view;
    }
}
