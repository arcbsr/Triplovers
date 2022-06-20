package com.arcadio.triplover.acitivies;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.FlightSearchAdapter;
import com.arcadio.triplover.databinding.FragmentFlightOptionsBinding;
import com.arcadio.triplover.fragments.CitySearchFragment;
import com.arcadio.triplover.models.CityModels;
import com.arcadio.triplover.models.search.request.Route;
import com.arcadio.triplover.models.search.request.SearchReq;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.Enums;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.Date;

public class FlightSearchFragment extends BaseFragment {

    private FragmentFlightOptionsBinding binding;
    SearchReq searchReq;
    private View view;

    public static FlightSearchFragment newInstance() {
        return new FlightSearchFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFlightOptionsBinding.inflate(inflater, container, false);
        searchReq = new SearchReq();
        return (view = binding.getRoot());

    }


    private void showCalender(int position, Enums.CalenderType type, Enums.FlightType flightType) {

        long minDate = Calendar.getInstance().getTimeInMillis();
        if (flightType == Enums.FlightType.ROUND && position == 1) {
            minDate = adapter.getRoute(0).getTimeMilisecon();
        }
        new Dialogs().showCalender(getContext(), searchReq.getRoutes().get(position).getTimeMilisecon(),
                0, minDate, getString(R.string.date_format)
                , new Dialogs.DialogListener2() {
                    @Override
                    public void onItemSelected(long miliSecond, String code) {
                        updateDate(position, type, flightType, miliSecond);
                    }


                });
    }


    private void updateDate(int position, Enums.CalenderType type, Enums.FlightType flightType, long miliSecond) {
        //String date = i + "-" + i1 + "-" + i2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliSecond);
//        calendar.set(y, m, d);

        Route route = searchReq.getRoutes().get(position);
        route.setDepartureDate(Utils.getDateString(calendar.getTime()));
        route.setTimeMilisecon(calendar.getTimeInMillis());

        switch (flightType) {
            case ROUND:
                if (type == Enums.CalenderType.DEPART) {
                    //binding.deptTxtDate.setText(route.getDepartureDate());
                    setUpDate(route.getTimeMilisecon(), Enums.CalenderType.DEPART);
                    Route routeRetrn = searchReq.getRoutes().get(1);
                    if (routeRetrn.getTimeMilisecon() < route.getTimeMilisecon()) {
                        routeRetrn.setDepartureDate(Utils.getDateString(calendar.getTime()));
                        routeRetrn.setTimeMilisecon(calendar.getTimeInMillis());
//                        binding.retnTxtDate.setText(routeRetrn.getDepartureDate());
                        setUpDate(route.getTimeMilisecon(), Enums.CalenderType.RETURN);
                    }
                } else if (type == Enums.CalenderType.RETURN) {
//                    binding.retnTxtDate.setText(route.getDepartureDate());
                    setUpDate(route.getTimeMilisecon(), Enums.CalenderType.RETURN);
                }
                break;
            case ONE_WAY:
                //binding.deptTxtDate.setText(route.getDepartureDate());
                setUpDate(route.getTimeMilisecon(), Enums.CalenderType.DEPART);
                break;
            case MULTICITY:

                break;
        }
        updateMultiCityList(flightType);
    }

    private void TabBarSetup(View view) {
        binding.tabOneway.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_filled_icon, 0, 0, 0);
        binding.tabRoundtip.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_filled_icon, 0, 0, 0);
        binding.tabMulticity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_filled_icon, 0, 0, 0);
        binding.returnDate.setVisibility(View.INVISIBLE);
        binding.departDate.setVisibility(View.VISIBLE);
        binding.addMore.setVisibility(View.INVISIBLE);
        binding.addMore.setOnClickListener(null);
        binding.datePanel.setVisibility(View.VISIBLE);
        binding.datePanelAdd.setVisibility(View.VISIBLE);
        updateMultiCityList(Enums.FlightType.ONE_WAY);

        if (view != null) {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_filled_icon_orange, 0, 0, 0);
            return;
        }
        binding.tabOneway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabBarSetup(view);

                binding.returnDate.setVisibility(View.VISIBLE);
                binding.returnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.tabRoundtip.performClick();
                    }
                });
                binding.datePanelAdd.setVisibility(View.VISIBLE);
                binding.departDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCalender(0, Enums.CalenderType.DEPART, Enums.FlightType.ONE_WAY);
                    }
                });
                updateOneWayList(adapter.getRoute(0));
                updateMultiCityList(Enums.FlightType.ONE_WAY);

                binding.retnTxtDate.setText(getString(R.string.round_trip_msg));
                binding.retnTxtDayn.setText("");
                binding.retnTxtDay.setText("");
            }
        });
        binding.tabRoundtip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabBarSetup(view);
                binding.returnDate.setVisibility(View.VISIBLE);
                binding.datePanelAdd.setVisibility(View.VISIBLE);
                binding.returnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCalender(1, Enums.CalenderType.RETURN, Enums.FlightType.ROUND);
                    }
                });
                binding.departDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCalender(0, Enums.CalenderType.DEPART, Enums.FlightType.ROUND);
                    }
                });

                updateRoundTripList(adapter.getRoute(0));
                updateMultiCityList(Enums.FlightType.ROUND);
            }
        });
        binding.tabMulticity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabBarSetup(view);
                binding.returnDate.setOnClickListener(null);
                binding.departDate.setOnClickListener(null);
                binding.addMore.setVisibility(View.VISIBLE);
                binding.datePanel.setVisibility(View.GONE);
                binding.datePanelAdd.setVisibility(View.GONE);
                binding.addMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (searchReq.getRoutes().size() == 4) {
                            return;
                        }
                        updateSearchList(new Route("Select", "Select", Utils.getDate(System.currentTimeMillis(),
                                Constants.DATE_FORMAT_SEARCH)));
                    }
                });
                updateMultiCityList(Enums.FlightType.MULTICITY);
            }
        });
    }

    private void ClassTabBarSetup(View view) {


        if (true) {
            return;
        }
        binding.tabEconomy.setBackgroundColor(getColorFromResource(R.color.semi_trans));
        binding.tabPremEconomy.setBackgroundColor(0);
        binding.tabBusiness.setBackgroundColor(0);
        binding.tabFirstclass.setBackgroundColor(0);
        if (view != null) {
            binding.tabEconomy.setBackgroundColor(0);
            view.setBackgroundColor(getColorFromResource(R.color.semi_trans));
            return;
        }
        binding.tabEconomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchReq.setCabinClass(1);
                ClassTabBarSetup(view);
            }
        });
        binding.tabPremEconomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchReq.setCabinClass(2);
                ClassTabBarSetup(view);

            }
        });
        binding.tabBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchReq.setCabinClass(3);
                ClassTabBarSetup(view);

            }
        });
        binding.tabFirstclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchReq.setCabinClass(4);
                ClassTabBarSetup(view);

            }
        });
    }

    FlightSearchAdapter adapter;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                NavHostFragment.findNavController(FirstFragment.this)
////                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                NavHostFragment.findNavController(FlightSearchFragment.this).navigate(R.id.SecondFragment);
//            }
//        });
        changeSearchProperty();
        adapter = new FlightSearchAdapter(new FlightSearchAdapter.AdapterListener() {
            @Override
            public void removeItem(final int pos) {
                searchReq.getRoutes().remove(pos);
                binding.tabMulticity.performClick();
            }

            @Override
            public void departItem(int pos) {
//                startActivityForResult(new Intent(getContext(), CitySearchActivity.class).putExtra(Constants.RESULT_CITY_ACTION, Constants.RESULT_CITY_START),
//                        1000);
                new CitySearchFragment(new CitySearchFragment.Listener() {
                    @Override
                    public void onCitySelected(CityModels cityModels) {
                        adapter.updateData(pos, cityModels, Enums.CalenderType.DEPART);
                    }
                }).show(getFragmentManager(), "citysearch");
            }

            @Override
            public void returnItem(int pos) {
                new CitySearchFragment(new CitySearchFragment.Listener() {
                    @Override
                    public void onCitySelected(CityModels cityModels) {
                        adapter.updateData(pos, cityModels, Enums.CalenderType.RETURN);
                    }
                }).show(getFragmentManager(), "citysearch");
            }

            @Override
            public void departDate(int pos) {
                showCalender(pos, Enums.CalenderType.DEPART, Enums.FlightType.MULTICITY);
            }
        });
        binding.recycleFlightSearch.setAdapter(adapter);
        // Set layout manager to position the items
        binding.recycleFlightSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.addItems(searchReq.getRoutes());


        TabBarSetup(null);
        ClassTabBarSetup(null);

//        binding.deptTxtDate.setText(Utils.getDateString(Calendar.getInstance().getTime()));
//        binding.retnTxtDate.setText(Utils.getDateString(Calendar.getInstance().getTime()));
        setUpDate(Calendar.getInstance().getTime(), Enums.CalenderType.BOTH);
        binding.searchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = Utils.getGson().toJson(searchReq);
                boolean isDomestic = true;
                for (Route route : searchReq.getRoutes()) {
                    if (!route.departCountryName.equalsIgnoreCase(route.desCountryName)) {
                        isDomestic = false;
                        break;
                    }
                }
                PreferencesHelpers.saveStringData(getContext(), PreferencesHelpers.SEARCH_QUERY, data);

                KLog.w("isDomestic>>" + isDomestic);
                startActivity(new Intent(getContext(), FlightListDataActivity.class).putExtra(Constants.QUERY_FLIGHT_SEARCH, data)
                        .putExtra(Constants.IS_DOMESTIC, isDomestic));
            }
        });
        binding.travelerClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTravelClassDialog();
            }
        });
        binding.tabOneway.performClick();
        binding.tabEconomy.performClick();

    }

    private void showTravelClassDialog() {
        final int[] classIndex = {searchReq.getCabinClass()};
        int maxPassenger = 10;
        final int[] maxAdult = {searchReq.getAdults()};
        final int[] maxChild = {searchReq.getChilds()};
        final int[] maxInf = {searchReq.getInfants()};
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.layout_traveler_class_dialog);
        bottomSheetDialog.findViewById(R.id.close_dlg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        final String[] className = {(Enums.Classes.fromString(
                classIndex[0]).toString())};
        if (className[0].equals(Enums.Classes.Fast.toString())) {
            className[0] = "Fast Class";
        }
        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_class)).setText(className[0]);
        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_adult)).setText(maxAdult[0] + " Traveler");
        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_child)).setText(maxChild[0] + " Traveler");
        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_inf)).setText(maxInf[0] + " Traveler");
        bottomSheetDialog.findViewById(R.id.dlg_class_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogs().ShowDialogGender("Class", getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        classIndex[0] = position + 1;
                        className[0] = code;
                        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_class)).setText(className[0]);
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, className[0], new String[]{"Economy", "Premium", "Business", "Fast Class"});
            }
        });
        bottomSheetDialog.findViewById(R.id.dlg_adultlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxVal = maxPassenger - maxChild[0];
                int[] values = new int[maxVal];
                String[] stringValues = new String[maxVal];
                for (int i = 0; i < values.length; i++) {
                    values[i] = i;
                    stringValues[i] = Utils.convertNumberOrS(values[i], "Adult", "s");
                }
                new Dialogs().ShowDialogGender("Adult", getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        maxAdult[0] = position;
                        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_adult)).setText(
                                Utils.convertNumberOrS(maxAdult[0], "Traveler", "s"));
                        if (maxAdult[0] < maxInf[0]) {
                            maxInf[0] = maxAdult[0];
                            ((TextView) bottomSheetDialog.findViewById(R.id.dlg_inf)).setText(
                                    Utils.convertNumberOrS(maxInf[0], "Traveler", "s"));
                        }
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, stringValues[maxAdult[0]], stringValues);

            }
        });
        bottomSheetDialog.findViewById(R.id.dlg_child_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxVal = maxPassenger - maxAdult[0];
                int[] values = new int[maxVal];
                String[] stringValues = new String[maxVal];
                for (int i = 0; i < values.length; i++) {
                    values[i] = i;
                    stringValues[i] = Utils.convertNumberOrS(values[i], "Child", "ren");
                }
                new Dialogs().ShowDialogGender("Child", getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        maxChild[0] = position;
                        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_child)).setText(
                                Utils.convertNumberOrS(maxChild[0], "Traveler", "s"));
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, stringValues[maxChild[0]], stringValues);

            }
        });
        bottomSheetDialog.findViewById(R.id.dlg_inf_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxVal = maxAdult[0] + 1;
                if (maxVal == 0) {
                    return;
                }
                int[] values = new int[maxVal];
                String[] stringValues = new String[maxVal];
                for (int i = 0; i < values.length; i++) {
                    values[i] = i;
                    stringValues[i] = Utils.convertNumberOrS(values[i], "Infant", "s");
                }
                new Dialogs().ShowDialogGender("Infant", getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        maxInf[0] = position;
                        ((TextView) bottomSheetDialog.findViewById(R.id.dlg_inf)).setText(
                                Utils.convertNumberOrS(maxInf[0], "Traveler", "s"));
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, stringValues[maxInf[0]], stringValues, true);

            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.findViewById(R.id.dlg_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchReq.setCabinClass(classIndex[0]);
                binding.classes.setText(className[0]);
                searchReq.setInfants(maxInf[0]);
                searchReq.setAdults(maxAdult[0]);
                searchReq.setChilds(maxChild[0]);
                binding.traveler.setText(
                        Utils.convertNumberOrS((searchReq.getAdults() + searchReq.getChilds() + searchReq.getInfants()), "Traveler", "s"));
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void setUpDate(Date date, Enums.CalenderType type) {
        long timeMili = date.getTime();
        if (type == Enums.CalenderType.BOTH) {
            setUpDate(timeMili, Enums.CalenderType.DEPART);
            setUpDate(timeMili, Enums.CalenderType.RETURN);
            return;
        }
        setUpDate(timeMili, type);

    }

    private void setUpDate(Long date, Enums.CalenderType type) {
        String datee = Utils.getDate(date, Constants.DATE_FORMAT_MMYY);
        String[] dats = datee.split(" ");
        if (type == Enums.CalenderType.DEPART) {
            binding.deptTxtDate.setText(dats[2].replace(",", "'"));
            binding.deptTxtDayn.setText(dats[1] + "  ");
            binding.deptTxtDay.setText(dats[0]);
        }
        if (type == Enums.CalenderType.RETURN) {
            binding.retnTxtDate.setText(dats[2].replace(",", "'"));
            binding.retnTxtDayn.setText(dats[1] + "  ");
            binding.retnTxtDay.setText(dats[0]);
        }
    }

    private void updateOneWayList(Route route) {
        searchReq.getRoutes().clear();
        searchReq.getRoutes().add(route);
        ((FlightSearchAdapter) binding.recycleFlightSearch.getAdapter()).addItems(searchReq.getRoutes());
    }

    private void updateRoundTripList(Route route) {
        searchReq.getRoutes().clear();
        searchReq.getRoutes().add(route);
        Route route1 = new Route();
        route1.setOrigin(route.getDestination());
        route1.setDestination(route.getOrigin());
        route1.departCountryName = route.departCountryName;
        route1.desCountryName = route.desCountryName;
        route1.setDepartCityName(route.getDestinationcityname());
        route1.setDestinationcityname(route.getDepartCityName());
        route1.setTimeMilisecon(route.getTimeMilisecon());
        route1.setDepartureDate(route.getDepartureDate());
        searchReq.getRoutes().add(route1);
        //binding.retnTxtDate.setText(route1.getDepartureDate());
        setUpDate(route1.getTimeMilisecon(), Enums.CalenderType.RETURN);
        ((FlightSearchAdapter) binding.recycleFlightSearch.getAdapter()).addItems(searchReq.getRoutes());
    }

    private void updateSearchList(Route route) {
        searchReq.getRoutes().add(route);
        if (searchReq.getRoutes().size() == 4) {
            binding.datePanelAdd.setVisibility(View.GONE);
            binding.addMore.setVisibility(View.INVISIBLE);

        }
        ((FlightSearchAdapter) binding.recycleFlightSearch.getAdapter()).addItems(searchReq.getRoutes());
    }

    private void updateMultiCityList(Enums.FlightType flightType) {
        if (searchReq.getRoutes().size() == 4) {
            binding.datePanelAdd.setVisibility(View.GONE);
        }
        ((FlightSearchAdapter) binding.recycleFlightSearch.getAdapter()).setFlightType(flightType);
    }

    private void changeSearchProperty() {
        searchReq.setAdults(1);
        binding.adultPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalPassenger = searchReq.getAdults() + searchReq.getChilds();
                if (totalPassenger == 9) {
                    return;
                }
                searchReq.setAdults(searchReq.getAdults() + 1);
                passengerCounter();
            }
        });
        binding.adultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searchReq.getAdults() == 1) {
                    return;
                }
                searchReq.setAdults(searchReq.getAdults() - 1);
                if (searchReq.getInfants() > searchReq.getAdults()) {
                    searchReq.setInfants(searchReq.getAdults());
                }
                passengerCounter();
            }
        });
        binding.childPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalPassenger = searchReq.getAdults() + searchReq.getChilds();
                if (totalPassenger == 9) {
                    return;
                }
                searchReq.setChilds(searchReq.getChilds() + 1);
                passengerCounter();
            }
        });
        binding.childMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchReq.getChilds() == 0) {
                    return;
                }
                searchReq.setChilds(searchReq.getChilds() - 1);
                passengerCounter();
            }
        });
        binding.infantPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchReq.getInfants() < searchReq.getAdults()) {
                    searchReq.setInfants(searchReq.getInfants() + 1);
                    passengerCounter();
                }
            }
        });
        binding.infantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchReq.getInfants() == 0) {
                    return;
                }
                searchReq.setInfants(searchReq.getInfants() - 1);
                passengerCounter();
            }
        });
        passengerCounter();
    }

    private void passengerCounter() {
        binding.adultCounter.setText(searchReq.getAdults() + "");
        binding.childCounter.setText(searchReq.getChilds() + "");
        binding.infantCounter.setText(searchReq.getInfants() + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}