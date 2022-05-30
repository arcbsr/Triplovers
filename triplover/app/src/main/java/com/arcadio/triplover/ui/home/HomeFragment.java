package com.arcadio.triplover.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arcadio.triplover.acitivies.FlightSearchActivity;
import com.arcadio.triplover.databinding.FragmentHomeBinding;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.Enums;

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
                new Dialogs().ShowDialog(getActivity(), Enums.CodeSearchType.Countries,
                        CountryToPhonePrefix.getLocalCode(getActivity()), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code) {

                    }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

                            }
                        });

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