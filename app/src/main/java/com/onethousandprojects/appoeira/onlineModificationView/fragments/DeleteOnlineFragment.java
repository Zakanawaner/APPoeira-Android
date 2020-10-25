package com.onethousandprojects.appoeira.onlineModificationView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;

public class DeleteOnlineFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        OnlineModificationActivity onlineModificationActivity = (OnlineModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        ImageView ivClose = view.findViewById(R.id.deleteClose);
        Button btnUploadPic = view.findViewById(R.id.deleteCancel);
        Button btnDelete = view.findViewById(R.id.delete);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineModificationActivity.killAvatarFragment();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineModificationActivity.onlineModificationServer.deleteOnline(onlineModificationActivity, onlineModificationActivity.fromOnlineDetail.getInt("onlineId"));
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineModificationActivity.killAvatarFragment();
            }
        });
        return view;
    }
}
