package com.arcadio.triplover.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arcadio.triplover.R;
import com.arcadio.triplover.acitivies.FlightSearchActivity;
import com.arcadio.triplover.databinding.FragmentHomeBinding;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.models.SingleToneData;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.utils.ImageLoader;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.Utils;
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
                comingSoonMsg(getString(R.string.menu_hotel));

            }
        });
        binding.btnTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comingSoonMsg(getString(R.string.menu_tour));
            }
        });
        binding.btnVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comingSoonMsg(getString(R.string.menu_visa));
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
        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getFlightIcon()
                , binding.btnFlight, getContext(), R.drawable.menu_flight);
        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getHotelIcon()
                , binding.btnHotel, getContext(), R.drawable.menu_hotels);
        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getTourIcon()
                , binding.btnTour, getContext(), R.drawable.menu_tour);
        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getVisaIcon()
                , binding.btnVisa, getContext(), R.drawable.menu_visa);

        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getHomeOfferPlace1().getImage()
                , binding.offerPlace1, getContext(), R.drawable.image_1);

        ImageLoader.loadImageUrl(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getHomeOfferPlace2().getImage()
                , binding.offerPlace2, getContext(), R.drawable.image_2);
        binding.offerPlace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openExternalLink(getContext(), SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getHomeOfferPlace1().
                        getLink());
            }
        });
        binding.offerPlace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openExternalLink(getContext(), SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getHomeOfferPlace2().
                        getLink());
            }
        });
        ImageLoader.loadImageBackground(binding.homeBackground, getContext());
        return root;
    }

    private void comingSoonMsg(String title) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(title)
                .setMessage(getString(R.string.comming_soon))

                .setNegativeButton(getString(R.string.thanks), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}