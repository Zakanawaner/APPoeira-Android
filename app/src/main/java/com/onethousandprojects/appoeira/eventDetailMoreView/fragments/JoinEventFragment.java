package com.onethousandprojects.appoeira.eventDetailMoreView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.squareup.picasso.Picasso;

public class JoinEventFragment extends DialogFragment {
    private Integer roleId = 2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        assert getArguments() != null;
        String eventImage = getArguments().getString("eventImage");
        boolean member = getArguments().getBoolean("member");
        EventDetailMoreActivity eventDetailMoreActivity = ((EventDetailMoreActivity) requireActivity());

        View view = inflater.inflate(R.layout.fragment_join_group, container, false);
        TextView tvJoinInfo = view.findViewById(R.id.tvJoinGroupInfo);
        ImageView ivClose = view.findViewById(R.id.joinGroupClose);
        ImageView ivAvatar = view.findViewById(R.id.joinGroupUserAvatar);
        ImageView ivEventAvatar = view.findViewById(R.id.joinGroupGroupAvatar);
        Button btnPostComment = view.findViewById(R.id.joinGroupSend);
        ImageView ivLink = view.findViewById(R.id.imageView2);
        Spinner spinner = (Spinner) view.findViewById(R.id.joinGroupSpinner);

        if (member) {
            ivLink.setImageResource(R.drawable.ic_close);
            btnPostComment.setText(R.string.leaveGroup);
            if (getArguments().getBoolean("isOwner")) {
                tvJoinInfo.setText(R.string.leaveEventOwnerExplanation);
            } else {
                tvJoinInfo.setText(R.string.leaveEventExplanation);
            }
        } else {
            ivLink.setImageResource(R.drawable.ic_arrow_right);
            btnPostComment.setText(R.string.joinGroup);
            tvJoinInfo.setText(R.string.joinEventExplanation);
        }

        Picasso.with(getContext()).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).fit().into(ivAvatar);
        Picasso.with(getContext()).load(eventImage).fit().into(ivEventAvatar);
        spinner.setVisibility(View.GONE);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventDetailMoreActivity.killFragment();
            }
        });
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!member) {
                    if (roleId > 0) {
                        eventDetailMoreActivity.eventDetailMoreServer.joinEvent(eventDetailMoreActivity,
                                eventDetailMoreActivity.fromEventDetailActivity.getInt("eventId"),
                                roleId);
                        eventDetailMoreActivity.refreshActivity();
                        eventDetailMoreActivity.killFragment();
                    } else {
                        Toast.makeText(getContext(), R.string.selectRole, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    eventDetailMoreActivity.eventDetailMoreServer.leaveEvent(eventDetailMoreActivity,
                            eventDetailMoreActivity.fromEventDetailActivity.getInt("eventId"));
                }
            }
        });
        return view;
    }
}
