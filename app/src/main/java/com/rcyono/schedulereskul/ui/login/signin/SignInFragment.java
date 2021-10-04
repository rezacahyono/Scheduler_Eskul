package com.rcyono.schedulereskul.ui.login.signin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.preferences.AppPreferences;
import com.rcyono.schedulereskul.ui.login.LoginViewModel;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignInFragment extends Fragment implements View.OnClickListener {
    private TextView btnSignUp;
    private TextInputEditText edtUsername, edtPassword;
    private Button btnLogin, btnCancel;
    private ProgressBar progressBar;

    private User user;
    private LoginViewModel loginViewModel;
    private AppPreferences preferences;

    private SweetAlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        edtUsername = view.findViewById(R.id.edt_username);
        edtPassword = view.findViewById(R.id.edt_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSignUp = view.findViewById(R.id.btn_signup);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        preferences = new AppPreferences(requireActivity());

        loginViewModel = new LoginViewModel();
        loginViewModel.getUserResponse().observe(requireActivity(), response -> {
            if (response.getSuccess() == 1) {
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_bottomNavFragment);
                user = response.getUser().get(0);
                preferences.setUser(user);
            } else {
                alertDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText("Login Failed!");
                alertDialog.setContentText("Unable to retrieve any data from server");
                alertDialog.show();
                alertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
            }
        });

        loginViewModel.isLoading().observe(requireActivity(), load -> {
            alertDialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_main));
            alertDialog.setTitleText("Loading");
            alertDialog.setCancelable(false);
            if (load) {
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            String username = Objects.requireNonNull(edtUsername.getText()).toString();
            String password = Objects.requireNonNull(edtPassword.getText()).toString();
            boolean isEmptyField = false;
            if (username.isEmpty()) {
                edtUsername.setText(getString(R.string.field_not_empty));
                isEmptyField = true;
            } else if (password.isEmpty()) {
                edtPassword.setText(getString(R.string.field_not_empty));
                isEmptyField = true;
            }
            if (!isEmptyField) {
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                loginViewModel.getLogin(user);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else if (view.getId() == R.id.btn_cancel) {
            edtUsername.setText("");
            edtPassword.setText("");
        } else if (view.getId() == R.id.btn_signup) {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
        }
    }
}