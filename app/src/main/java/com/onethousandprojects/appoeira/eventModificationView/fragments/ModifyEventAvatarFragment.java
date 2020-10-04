package com.onethousandprojects.appoeira.eventModificationView.fragments;

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
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;

public class ModifyEventAvatarFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        EventModificationActivity eventModificationActivity = (EventModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_modify_avatar, container, false);
        ImageView ivClose = view.findViewById(R.id.modifyAvatarClose);
        Button btnUploadPic = view.findViewById(R.id.modifyAvatarSend);
        EditText etNewUrl = view.findViewById(R.id.modifyAvatarUrl);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventModificationActivity.killAvatarFragment();
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventModificationActivity.newUrl = String.valueOf(etNewUrl.getText());
                //TODO No carga imagen
                eventModificationActivity.chargeImage();
                eventModificationActivity.killAvatarFragment();
                Toast.makeText(getContext(), R.string.newImageReady, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
