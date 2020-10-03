package com.onethousandprojects.appoeira.onlineModificationView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;

public class ModifyOnlineAvatarFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        OnlineModificationActivity onlineModificationActivity = (OnlineModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_modify_avatar, container, false);
        ImageView ivClose = view.findViewById(R.id.modifyAvatarClose);
        Button btnUploadPic = view.findViewById(R.id.modifyAvatarSend);
        EditText etNewUrl = view.findViewById(R.id.modifyAvatarUrl);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineModificationActivity.killAvatarFragment();
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineModificationActivity.newUrl = String.valueOf(etNewUrl.getText());
                //TODO No carga imagen
                onlineModificationActivity.chargeImage();
                onlineModificationActivity.killAvatarFragment();
                Toast.makeText(getContext(),"Nueva imagen lista para actualizar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
