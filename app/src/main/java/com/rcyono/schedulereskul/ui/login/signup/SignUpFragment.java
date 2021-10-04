package com.rcyono.schedulereskul.ui.login.signup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.ui.login.LoginViewModel;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private Button btnRegistered;
    private TextInputEditText edtUsername, edtEmail, edtPassword;
    private LoginViewModel loginViewModel;
    private ProgressBar progressBar;

    private SweetAlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnRegistered = view.findViewById(R.id.btn_register);
        edtUsername = view.findViewById(R.id.edt_username);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegistered.setOnClickListener(this);

        loginViewModel = new LoginViewModel();
        loginViewModel.getUserResponse().observe(requireActivity(), response -> {
            if (response.getSuccess() == 1) {
                alertDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitleText("Register Success!");
                alertDialog.setContentText("Next to continue");
            } else {
                alertDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText("Register Failed!");
                alertDialog.setContentText("Unable to retrieve any data from server");
            }
            alertDialog.show();
            alertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
        });

        setAlertDialog();

        loginViewModel.isLoading().observe(requireActivity(), load -> {
            if (load) {
                progressBar.setVisibility(View.VISIBLE);
            }else  {
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register) {
            String username = Objects.requireNonNull(edtUsername.getText()).toString();
            String password = Objects.requireNonNull(edtPassword.getText()).toString();
            String email = Objects.requireNonNull(edtEmail.getText()).toString();
            boolean isEmptyField = false;
            if (username.isEmpty()) {
                edtUsername.setError(getString(R.string.field_not_empty));
                isEmptyField = true;
            } else if (password.isEmpty()) {
                edtPassword.setError(getString(R.string.field_not_empty));
                isEmptyField = true;
            } else if (email.isEmpty()) {
                edtEmail.setError(getString(R.string.field_not_empty));
                isEmptyField = true;
            }
            if (!isEmptyField) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                loginViewModel.registLogin(user);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    private void setAlertDialog() {
        alertDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_main));
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
    }

}