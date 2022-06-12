package com.arcadio.triplover.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arcadio.triplover.R;
import com.arcadio.triplover.acitivies.FlightSearchActivity;
import com.arcadio.triplover.databinding.FragmentHomeBinding;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.utils.KLog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FlightSearchActivity.class));
            }
        });
        binding.btnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();

            }
        });
        binding.btnTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginDialogFragment(new LoginDialogFragment.Listener() {
                    @Override
                    public void onLogIn(LoginResponse response) {
                        KLog.w(response == null ? "Error in data" : new Gson().toJson(response));
                    }

                    @Override
                    public void onLoginFailed() {
                        new MaterialAlertDialogBuilder(getContext())
                                .setTitle("Falied")
                                .setMessage(getString(R.string.loginFailed))
                                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }
                }).show(getParentFragmentManager(), "LoginFrom");
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