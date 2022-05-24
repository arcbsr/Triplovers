package com.arcadio.triplover.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.databinding.FragmentLoginBinding;
import com.arcadio.triplover.models.usermodel.LoginReq;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.models.usermodel.UserLoginController;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.layoutSignup.setVisibility(View.GONE);
        binding.layoutLogin.setVisibility(View.VISIBLE);
        binding.createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutLogin.setVisibility(View.GONE);
                binding.layoutSignup.setVisibility(View.VISIBLE);
            }
        });
        binding.loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutSignup.setVisibility(View.GONE);
                binding.layoutLogin.setVisibility(View.VISIBLE);
            }
        });
        binding.loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();
                final LoginReq loginReq = new LoginReq(mail, password);
//                new TAsyntask(getActivity(), new TAsyntask.KAsyncListener() {
//                    String error = "";
//
//                    @Override
//                    public void onPreListener() {
//
//                    }
//
//                    @Override
//                    public void onThreadListener(String data) {
//                        String result = TAsyntask.postRequest(new Gson().toJson(loginReq), Constants.ROOT_URL_AUTH);
//                        if (result != null) {
//                            PreferencesHelpers.saveStringData(getContext(), result, Constants.RESULT_USER_AUTH);
//                        }
//                    }
//
//                    @Override
//                    public void onCompleteListener() {
//                        if (error.equalsIgnoreCase(TAsyntask.ERROR_CANCEL)) {
//                            return;
//                        }
//
//                    }
//
//                    @Override
//                    public void onErrorListener(String msg) {
//                        error = msg;
//                    }
//                }).execute();


            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}