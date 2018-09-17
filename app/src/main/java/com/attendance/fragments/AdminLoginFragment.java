package com.attendance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.activities.AddDetailsActivity;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomTextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminLoginFragment extends Fragment implements View.OnClickListener {

    CustomTextInputLayout til_username, til_password;
    CustomInputEditText et_username, et_password;
    Button btn_submit;

    public static AdminLoginFragment newInstance() {
        return new AdminLoginFragment();
    }

    public AdminLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        initViews(view);

        btn_submit.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        til_username = (CustomTextInputLayout) view.findViewById(R.id.til_username);
        til_password = (CustomTextInputLayout) view.findViewById(R.id.til_password);

        et_username = (CustomInputEditText) view.findViewById(R.id.et_username);
        et_password = (CustomInputEditText) view.findViewById(R.id.et_password);

        btn_submit = (Button) view.findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_submit:
                if(et_username.getText().toString().trim().equalsIgnoreCase("admin") &&
                        et_password.getText().toString().trim().equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(getActivity(), AddDetailsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
