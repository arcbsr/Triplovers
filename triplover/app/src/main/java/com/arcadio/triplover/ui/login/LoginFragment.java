package com.arcadio.triplover.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arcadio.triplover.databinding.FragmentLoginBinding;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}