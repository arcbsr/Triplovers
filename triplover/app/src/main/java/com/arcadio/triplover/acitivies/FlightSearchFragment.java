package com.arcadio.triplover.acitivies;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import com.arcadio.triplover.utils.Enums;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.TimeZone;

public class FlightSearchFragment extends BaseFragment {

    private FragmentFlightOptionsBinding binding;
    SearchReq searchReq;
    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFlightOptionsBinding.inflate(inflater, container, false);
        searchReq = new SearchReq();
//        String prevSearch = PreferencesHelpers.loadStringData(getContext(), PreferencesHelpers.SEARCH_QUERY, "null");
//        if (prevSearch != null) {
//            searchReq = new Gson().fromJson(prevSearch, SearchReq.class);
//        }
        return (view = binding.getRoot());

    }


    private void showCalender(int position, Enums.CalenderType type, Enums.FlightType flightType) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(searchReq.getRoutes().get(position).getTimeMilisecon());
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                updateDate(position, type, flightType, i, i1, i2);
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH) + 3);
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();
    }


    private void updateDate(int position, Enums.CalenderType type, Enums.FlightType flightType, int i, int i1, int i2) {
        //String date = i + "-" + i1 + "-" + i2;
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);

        Route route = searchReq.getRoutes().get(position);
        route.setDepartureDate(Utils.getDateString(calendar.getTime()));
        route.setTimeMilisecon(calendar.getTimeInMillis());
        switch (flightType) {
            case ROUND:
                if (type == Enums.CalenderType.DEPART) {
                    binding.deptTxtDate.setText(route.getDepartureDate());
                } else if (type == Enums.CalenderType.RETURN) {
                    binding.retnTxtDate.setText(route.getDepartureDate());
                }
                break;
            case ONE_WAY:
                binding.deptTxtDate.setText(route.getDepartureDate());
                break;
            case MULTICITY:

                break;
        }
        updateMultiCityList(flightType);
    }

    private void TabBarSetup(View view) {
        binding.tabOneway.setBackgroundColor(getColorFromResource(R.color.colorPrimary));
        binding.tabOneway.setTextColor(getColorFromResource(R.color.white));
        binding.tabRoundtip.setBackgroundColor(getColorFromResource(R.color.colorPrimary));
        binding.tabRoundtip.setTextColor(getColorFromResource(R.color.white));
        binding.tabMulticity.setBackgroundColor(getColorFromResource(R.color.colorPrimary));
        binding.tabMulticity.setTextColor(getColorFromResource(R.color.white));
        binding.returnDate.setVisibility(View.INVISIBLE);
        binding.departDate.setVisibility(View.VISIBLE);
        binding.addMore.setVisibility(View.INVISIBLE);
        binding.addMore.setOnClickListener(null);
        binding.datePanel.setVisibility(View.VISIBLE);
        binding.datePanelAdd.setVisibility(View.VISIBLE);
        updateMultiCityList(Enums.FlightType.ONE_WAY);
        if (view != null) {
//            binding.tabOneway.setBackgroundColor(getColorFromResource(R.color.semi_trans));
//            binding.tabOneway.setTextColor(getColorFromResource(R.color.white));
            view.setBackgroundColor(getColorFromResource(R.color.white));
            ((TextView) view).setTextColor(getColorFromResource(R.color.black));
            return;
        }
        binding.tabOneway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabBarSetup(view);
                binding.returnDate.setVisibility(View.INVISIBLE);
                binding.returnDate.setOnClickListener(null);
                binding.datePanelAdd.setVisibility(View.VISIBLE);
                binding.departDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCalender(0, Enums.CalenderType.DEPART, Enums.FlightType.ONE_WAY);
                    }
                });
                updateOneWayList(adapter.getRoute(0));
                updateMultiCityList(Enums.FlightType.ONE_WAY);
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
                binding.datePanel.setVisibility(View.INVISIBLE);
                binding.returnDate.setOnClickListener(null);
                binding.departDate.setOnClickListener(null);
                binding.addMore.setVisibility(View.VISIBLE);
                binding.datePanel.setVisibility(View.GONE);
                binding.addMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateSearchList(new Route());
                    }
                });
                updateMultiCityList(Enums.FlightType.MULTICITY);
            }
        });
    }

    private void ClassTabBarSetup(View view) {
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
        binding.tabOneway.performClick();
        binding.tabEconomy.performClick();

        binding.deptTxtDate.setText(Utils.getDateString(Calendar.getInstance().getTime()));
        binding.retnTxtDate.setText(Utils.getDateString(Calendar.getInstance().getTime()));
        binding.searchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavController controller = NavHostFragment.findNavController(FlightSearchFragment.this);
//                Bundle bundle = new Bundle();

                String data = Utils.getGson().toJson(searchReq);
                PreferencesHelpers.saveStringData(getContext(), PreferencesHelpers.SEARCH_QUERY, data);
//                bundle.putString(Constants.QUERY_FLIGHT_SEARCH, data);
//                controller.navigate(R.id.FlightList, bundle);
                startActivity(new Intent(getContext(), FlightListDataActivity.class).putExtra(Constants.QUERY_FLIGHT_SEARCH, data));
            }
        });


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
        route1.setDepartCityName(route.getDestinationcityname());
        route1.setDestinationcityname(route.getDepartCityName());
        searchReq.getRoutes().add(route1);

        ((FlightSearchAdapter) binding.recycleFlightSearch.getAdapter()).addItems(searchReq.getRoutes());
    }

    private void updateSearchList(Route route) {
        searchReq.getRoutes().add(route);
        if (searchReq.getRoutes().size() == 4) {
            binding.datePanelAdd.setVisibility(View.GONE);
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
                searchReq.setAdults(searchReq.getAdults() + 1);
                passengerCounter();
            }
        });
        binding.adultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchReq.getAdults() == 0) {
                    return;
                }
                searchReq.setAdults(searchReq.getAdults() - 1);
                passengerCounter();
            }
        });
        binding.childPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                searchReq.setInfants(searchReq.getInfants() + 1);
                passengerCounter();
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