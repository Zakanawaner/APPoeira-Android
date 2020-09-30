package com.onethousandprojects.appoeira.groupDetailMoreView.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.squareup.picasso.Picasso;

public class NewCommentFragment extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_new_comment, container, false);
        ImageView ivClose = view.findViewById(R.id.newCommentClose);
        ImageView ivAvatar = view.findViewById(R.id.groupCommentUserAvatar);
        Button btnPostComment = view.findViewById(R.id.newCommentSend);
        EditText etComment = view.findViewById(R.id.newCommentText);
        Picasso.with(getContext()).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).fit().into(ivAvatar);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GroupDetailMoreActivity) requireActivity()).killFragment();
            }
        });
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GroupDetailMoreActivity) requireActivity()).postComment(String.valueOf(etComment.getText()));
                ((GroupDetailMoreActivity) requireActivity()).refreshActivity();
                ((GroupDetailMoreActivity) requireActivity()).killFragment();
            }
        });
        return view;
    }
}
