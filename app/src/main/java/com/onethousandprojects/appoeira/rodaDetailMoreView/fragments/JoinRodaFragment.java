package com.onethousandprojects.appoeira.rodaDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.squareup.picasso.Picasso;

public class JoinRodaFragment extends DialogFragment {
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
        String rodaImage = getArguments().getString("rodaImage");
        boolean member = getArguments().getBoolean("member");
        RodaDetailMoreActivity rodaDetailMoreActivity = ((RodaDetailMoreActivity) requireActivity());

        View view = inflater.inflate(R.layout.fragment_join_group, container, false);
        TextView tvJoinInfo = view.findViewById(R.id.tvJoinGroupInfo);
        ImageView ivClose = view.findViewById(R.id.joinGroupClose);
        ImageView ivAvatar = view.findViewById(R.id.joinGroupUserAvatar);
        ImageView ivRodaAvatar = view.findViewById(R.id.joinGroupGroupAvatar);
        Button btnPostComment = view.findViewById(R.id.joinGroupSend);
        ImageView ivLink = view.findViewById(R.id.imageView2);
        Spinner spinner = (Spinner) view.findViewById(R.id.joinGroupSpinner);

        if (member) {
            ivLink.setImageResource(R.drawable.ic_close);
            btnPostComment.setText(R.string.leaveGroup);
            if (getArguments().getBoolean("isOwner")) {
                tvJoinInfo.setText(R.string.leaveRodaOwnerExplanation);
            } else {
                tvJoinInfo.setText(R.string.leaveRodaExplanation);
            }
        } else {
            ivLink.setImageResource(R.drawable.ic_arrow_right);
            btnPostComment.setText(R.string.joinGroup);
            tvJoinInfo.setText(R.string.joinRodaExplanation);
        }

        Picasso.with(getContext()).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).fit().into(ivAvatar);
        Picasso.with(getContext()).load(rodaImage).fit().into(ivRodaAvatar);
        spinner.setVisibility(View.GONE);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rodaDetailMoreActivity.killFragment();
            }
        });
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!member) {
                    if (roleId > 0) {
                        rodaDetailMoreActivity.rodaDetailMoreServer.joinRoda(rodaDetailMoreActivity,
                                rodaDetailMoreActivity.fromRodaDetailActivity.getInt("rodaId"),
                                roleId);
                        rodaDetailMoreActivity.refreshActivity();
                        rodaDetailMoreActivity.killFragment();
                    } else {
                        Toast.makeText(getContext(), R.string.selectRole, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    rodaDetailMoreActivity.rodaDetailMoreServer.leaveRoda(rodaDetailMoreActivity,
                            rodaDetailMoreActivity.fromRodaDetailActivity.getInt("rodaId"));
                }
            }
        });
        return view;
    }
}
