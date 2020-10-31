package com.onethousandprojects.appoeira.searchView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.searchView.SearchActivity;

public class VerifyEmailFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SearchActivity searchActivity = (SearchActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_verify_mail, container, false);
        ImageView ivClose = view.findViewById(R.id.verifyClose);
        Button btnCancel = view.findViewById(R.id.verifyCancel);
        TextView tvSendEmailExplanation = view.findViewById(R.id.buttonSendEmailExplanation);
        Button btnDelete = view.findViewById(R.id.verify);

        tvSendEmailExplanation.setText(getResources().getString(R.string.sendEmail) + SharedPreferencesManager.getStringValue(Constants.EMAIL));

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActivity.finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActivity.sendVerificationEmail();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActivity.finish();
            }
        });
        return view;
    }
}
