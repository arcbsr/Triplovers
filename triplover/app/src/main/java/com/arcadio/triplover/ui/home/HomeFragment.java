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
import com.arcadio.triplover.models.passenger.request.PassengerInfo;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;

import java.util.List;

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

                showPassengerDetails(-1);
            }
        });
        return root;
    }

    private void showPassengerDetails(int postionToUpdate) {

        List<String> passDataToSave = PreferencesHelpers.loadPassenger(getContext());
        String[] data = new String[passDataToSave.size()];
        int index = 0;
        for (String ss : passDataToSave) {
            PassengerInfo passengerInfo = Utils.getGson().fromJson(ss, PassengerInfo.class);
            data[index] = passengerInfo.getNameElement().getFirstName();
            index++;
        }
        new Dialogs().ShowDialogGender("", getActivity(), new Dialogs.DialogListener() {
            @Override
            public void onItemSelected(String code, int position) {

            }

            @Override
            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

            }
        }, "", data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}