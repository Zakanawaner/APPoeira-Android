package com.onethousandprojects.appoeira.userModificationView.fragments;

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
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;

public class ModifyPasswordFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_modify_password, container, false);
        ImageView ivClose = view.findViewById(R.id.modifyPasswordClose);
        Button btnUploadPic = view.findViewById(R.id.modifyPasswordSend);
        EditText etOldPass = view.findViewById(R.id.modifyPasswordOld);
        EditText etNewPass = view.findViewById(R.id.modifyPasswordNew);
        EditText etNewPass2 = view.findViewById(R.id.modifyPasswordNew2);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProfileModificationActivity) requireActivity()).killPasswordFragment();
            }
        });
        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(etNewPass.getText()).equals(String.valueOf(etNewPass2.getText())) && !String.valueOf(etNewPass.getText()).equals("")) {
                    ((ProfileModificationActivity) requireActivity()).pass = String.valueOf(etOldPass.getText());
                    ((ProfileModificationActivity) requireActivity()).newPass = String.valueOf(etNewPass.getText());
                    ((ProfileModificationActivity) requireActivity()).killPasswordFragment();
                    Toast.makeText(getContext(), "Contraseña guardada. Envía los cambios para terminar", Toast.LENGTH_SHORT).show();
                } else { Toast.makeText(getContext(), R.string.signupIncorrectPassword, Toast.LENGTH_SHORT).show(); }

            }
        });
        return view;
    }
}
