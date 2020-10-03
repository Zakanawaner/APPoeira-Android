package com.onethousandprojects.appoeira.rodaModificationView.fragments;

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
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;

public class ModifyRodaAvatarFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        RodaModificationActivity rodaModificationActivity = (RodaModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_modify_avatar, container, false);
        ImageView ivClose = view.findViewById(R.id.modifyAvatarClose);
        Button btnUploadPic = view.findViewById(R.id.modifyAvatarSend);
        EditText etNewUrl = view.findViewById(R.id.modifyAvatarUrl);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaModificationActivity.killAvatarFragment();
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaModificationActivity.newUrl = String.valueOf(etNewUrl.getText());
                //TODO No carga imagen
                rodaModificationActivity.chargeImage();
                rodaModificationActivity.killAvatarFragment();
                Toast.makeText(getContext(),"Nueva imagen lista para actualizar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
