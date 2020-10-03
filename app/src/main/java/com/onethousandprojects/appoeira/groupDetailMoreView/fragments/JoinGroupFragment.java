package com.onethousandprojects.appoeira.groupDetailMoreView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.squareup.picasso.Picasso;

public class JoinGroupFragment extends DialogFragment {
    private Integer roleId;
    private TextView tvNotSoFast;
    AdapterView.OnItemSelectedListener spinnerRank = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            roleId = i;
            if (roleId > 0 && roleId < 4) {
                tvNotSoFast.setVisibility(View.VISIBLE);
            } else {
                tvNotSoFast.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            roleId = 0;
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        assert getArguments() != null;
        String groupImage = getArguments().getString("groupImage");
        GroupDetailMoreActivity groupDetailMoreActivity = ((GroupDetailMoreActivity) requireActivity());

        View view = inflater.inflate(R.layout.fragment_join_group, container, false);
        ImageView ivClose = view.findViewById(R.id.joinGroupClose);
        ImageView ivAvatar = view.findViewById(R.id.joinGroupUserAvatar);
        ImageView ivGroupAvatar = view.findViewById(R.id.joinGroupGroupAvatar);
        Button btnPostComment = view.findViewById(R.id.joinGroupSend);
        Spinner spinner = (Spinner) view.findViewById(R.id.joinGroupSpinner);
        tvNotSoFast = view.findViewById(R.id.tvNotSoFastBuddy);

        Picasso.with(getContext()).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).fit().into(ivAvatar);
        Picasso.with(getContext()).load(groupImage).fit().into(ivGroupAvatar);
        spinner.setOnItemSelectedListener(spinnerRank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.group_rank_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupDetailMoreActivity.killFragment();
            }
        });
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roleId > 0) {
                    groupDetailMoreActivity.groupDetailMoreServer.joinGroup(groupDetailMoreActivity,
                            groupDetailMoreActivity.fromGroupDetailActivity.getInt("id"),
                            roleId);
                    groupDetailMoreActivity.refreshActivity();
                    groupDetailMoreActivity.killFragment();
                } else {
                    Toast.makeText(getContext(),"Selecciona un rol", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
