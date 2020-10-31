package com.onethousandprojects.appoeira.eventModificationView.fragments;

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
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;

public class DeleteEventFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        EventModificationActivity eventModificationActivity = (EventModificationActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        ImageView ivClose = view.findViewById(R.id.deleteClose);
        Button btnUploadPic = view.findViewById(R.id.deleteCancel);
        Button btnDelete = view.findViewById(R.id.delete);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventModificationActivity.killAvatarFragment();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventModificationActivity.eventModificationServer.deleteEvent(eventModificationActivity, eventModificationActivity.fromEventDetail.getInt("eventId"));
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventModificationActivity.killAvatarFragment();
            }
        });
        return view;
    }
}
