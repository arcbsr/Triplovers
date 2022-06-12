package com.arcadio.triplover.acitivies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.adapter.FlightsAdapter;
import com.arcadio.triplover.adapter.FlightsTabAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.databinding.ActivityFlightListDataBinding;
import com.arcadio.triplover.listeners.AdapterListener;
import com.arcadio.triplover.models.FilterModule;
import com.arcadio.triplover.models.reprice.request.RePriceReq;
import com.arcadio.triplover.models.search.request.Route;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.PassengerCounts;
import com.arcadio.triplover.models.search.response.SearchJsModel;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.Enums;
import com.arcadio.triplover.utils.FilterDataDialog;
import com.arcadio.triplover.utils.ImageLoader;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class FlightListDataActivity extends BaseActivity {
    private ActivityFlightListDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightListDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prepareFlights();
        setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareFlights() {
        new TAsyntask(this, new TAsyntask.KAsyncListener() {
            String error = "";

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                String searchAuery = getIntent().getStringExtra(Constants.QUERY_FLIGHT_SEARCH);


                TAsyntask.ResponseResult result = TAsyntask.postRequest(searchAuery, Constants.ROOT_URL_SEARCH);
                if (result.code != 200) {
                    return;
                }
                searchJsModel = getGson().fromJson(result.result, SearchJsModel.class);

            }

            @Override
            public void onCompleteListener() {
                if (searchJsModel == null || searchJsModel.getItem1() == null) {
                    new MaterialAlertDialogBuilder(FlightListDataActivity.this)
                            .setTitle(getString(R.string.no_flight))
                            .setMessage(getString(R.string.no_flight_msg))

                            .setNegativeButton(getString(R.string.search_again), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).setCancelable(false)
                            .show();
                    return;
                }
                setUpFlightsTab();

            }

            @Override
            public void onErrorListener(String msg) {
                error = msg;
            }
        }).execute();

        binding.flightSelectedList.setVisibility(View.VISIBLE);
        binding.sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy();
            }
        });
    }

    SearchJsModel searchJsModel;
    int airSearchPosition = 0, airFlightIndex = 0;
    FlightsAdapter adapter = null;

    private void setUpFlightsTab() {
        List<ArrayList<Direction>> directions = searchJsModel.getItem1().getAirSearchResponses().get(0).getDirections();
        final List<Route> routes = new ArrayList<>();
        for (List<Direction> direction : directions) {
            routes.add(new Route(direction.get(0).getFrom(), direction.get(0).getTo(), ""));
        }
        final FlightsTabAdapter adapterIndex = new FlightsTabAdapter(routes, new AdapterListener() {
            @Override
            public void onClickItem(int pos, int viewId) {
            }
        }, this);
        binding.flightSelectedList.setAdapter(adapterIndex);
        binding.flightsNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails(adapterIndex.getAllDirection(), false);
            }
        });

        binding.flightSelectedList.setLayoutManager(new LinearLayoutManager(FlightListDataActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        adapter = new FlightsAdapter(searchJsModel, new FlightsAdapter.FlightSelectedListener() {
            @Override
            public void flightSelected(Direction direction, int viewId) {
                switch (viewId) {
                    case R.id.flight_book:
                        airSearchPosition = direction.searchPosition;
                        adapterIndex.updateItem(airFlightIndex, direction);
                        if (airFlightIndex == routes.size() - 1) {
                            adapter.updateDataSet();
                            binding.flightsNext.setVisibility(View.VISIBLE);
                            showDetails(adapterIndex.getAllDirection(), false);
                            return;
                        }
                        airFlightIndex++;
                        setUpFlightList();
                        break;
                    case R.id.flight_details:
                        ArrayList<Direction> list = new ArrayList<>();
                        list.add(direction);
                        showDetails(list, true);
                        break;
                }
            }

            @Override
            public void onUpdateItemCount(int total) {
                flightCounter(total);
                if (total == 0) {
                    binding.noData.setVisibility(View.VISIBLE);
                } else {
                    binding.noData.setVisibility(View.GONE);
                }
            }

            @Override
            public Context getContext() {
                return FlightListDataActivity.this;
            }
        }, filterModule);
        // Attach the adapter to the recyclerview to populate items
        binding.flightList.setAdapter(adapter);
        // Set layout manager to position the items
        binding.flightList.setLayoutManager(new LinearLayoutManager(FlightListDataActivity.this));
        initFlightListView();
        binding.flightSelectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails(adapterIndex.getAllDirection(), true);
            }
        });
        filterModule = new FilterModule(adapter.getMinPrice(), adapter.getMaxPrice());
    }

    private RePriceReq getPriceReqQuery(final List<Direction> directions) {
        RePriceReq rePriceReq = new RePriceReq();
        rePriceReq.setItemCodeRef(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getItemCodeRef());
        rePriceReq.setUniqueTransID(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getUniqueTransID());

        for (Direction direction : directions) {
            rePriceReq.getSegmentCodeRefs().add(direction.getSegments().get(0).getSegmentCodeRef());
        }
        return rePriceReq;
    }

    private void showDetails(final List<Direction> directions, boolean isViewOnly) {
        if (directions.size() == 0) {
            Toast.makeText(this, getString(R.string.nothing_show), Toast.LENGTH_SHORT).show();
            return;
        }
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_recycleview);
//        ((TextView) bottomSheetDialog.findViewById(R.id.flight_details_price))
//                .setText(directions.get(0).totalPrice.toString());

        if (!isViewOnly && ((FlightsTabAdapter) binding.flightSelectedList.getAdapter()).isAllFlightSelected()) {
            ((TextView) bottomSheetDialog.findViewById(R.id.search_flight_confirm)).setText(getString(R.string.confirm));
            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RePriceReq priceReq = getPriceReqQuery(directions);
                    PassengerCounts passengerCounts = searchJsModel.getItem1().getAirSearchResponses().get(0).getPassengerCounts();
                    Intent pasentry = new Intent(FlightListDataActivity.this, PassengerEntryActivity.class);
                    pasentry.putExtra(Constants.PASS_PASSENGER_COUNTER, passengerCounts);
                    pasentry.putExtra(Constants.PASS_REPRICE_REAUEST, priceReq);
                    startActivity(pasentry);
                    bottomSheetDialog.dismiss();
                }
            });
        } else {
            ((TextView) bottomSheetDialog.findViewById(R.id.search_flight_confirm)).setText(getString(R.string.close));
            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setOnClickListener(view -> bottomSheetDialog.dismiss());
        }
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.rc_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(FlightListDataActivity.this));
        BasicAdapter adapter = new BasicAdapter(new BasicAdapter.BasicListener() {
            @Override
            public int getItem() {
                return directions.size();
            }

            @Override
            public int getLayoutId() {
                return R.layout.layout_flight_details;
            }

            @Override
            public void onBindViewHolder(BasicAdapter.ViewHolder holder, int position) {
                String det1 = directions.get(position).getPlatingCarrierName();
                det1 += "\n" + directions.get(position).getPlatingCarrierCode() + "-" +
                        directions.get(position).getSegments().get(0).getFlightNumber();
                det1 += "," + directions.get(position).getSegments().get(0).getServiceClass();
                ((TextView) holder.itemView.findViewById(R.id.item_details_airline)).setText(det1);

                String det2 = directions.get(position).getFrom() + " - " + directions.get(position).getTo();
                det2 += "\n" + directions.get(position).getSegments().get(0).getDuration().get(0);
                ((TextView) holder.itemView.findViewById(R.id.item_details_codetime)).setText(det2);

                String det3 = directions.get(position).getSegments().get(0).getDeparture().split(" ")[1]
                        + "\n" + directions.get(position).getSegments().get(0).getDeparture().split(" ")[0];
                det3 += "\n[" + directions.get(position).getSegments().get(0).getFrom() + "]";
                if (!directions.get(position).getSegments().get(0).getDetails().get(0).getOriginTerminal().isEmpty())
                    det3 += "\nTerminal: " + directions.get(position).getSegments().get(0).getDetails().get(0).getOriginTerminal();
                ((TextView) holder.itemView.findViewById(R.id.item_details_dep_details)).setText(det3);

                String det4 = directions.get(position).getSegments().get(0).getArrival().split(" ")[1]
                        + "\n" + directions.get(position).getSegments().get(0).getArrival().split(" ")[0];
                det4 += "\n[" + directions.get(position).getSegments().get(0).getTo() + "]";
                if (!directions.get(position).getSegments().get(0).getDetails().get(0).getDestinationTerminal().isEmpty())
                    det4 += "\nTerminal: " + directions.get(position).getSegments().get(0).getDetails().get(0).getDestinationTerminal();
                ((TextView) holder.itemView.findViewById(R.id.item_details_ret_details)).setText(det4);
                ImageLoader.loadImage(directions.get(position).getPlatingCarrierCode(),
                        ((ImageView) holder.itemView.findViewById(R.id.item_details_thumb)), getContext());
            }
        });
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (airFlightIndex == 0) {
            super.onBackPressed();
            return;
        }
        airFlightIndex--;

        if (airFlightIndex == 0) {
            ((FlightsTabAdapter) binding.flightSelectedList.getAdapter()).clearDirectionItem();
            initFlightListView();
            return;
        }
        ((FlightsTabAdapter) binding.flightSelectedList.getAdapter()).clearDirectionItem();
        setUpFlightList();

    }

    private void initFlightListView() {
        airSearchPosition = 0;
        airFlightIndex = 0;
        binding.flightsNext.setVisibility(View.GONE);
        List<Direction> totalDir = new ArrayList<>();
        for (int i = 0; i < searchJsModel.getItem1().getAirSearchResponses().size(); i++) {
            List<ArrayList<Direction>> dirLists = searchJsModel.getItem1().getAirSearchResponses().get(i).getDirections();
            for (int dlists = 0; dlists < dirLists.size(); dlists++) {
                List<Direction> dirlist = dirLists.get(dlists);
                for (Direction direction : dirlist) {
                    direction.searchPosition = i;
                    direction.totalPrice = searchJsModel.getItem1().getAirSearchResponses().get(i).getTotalPrice();
                    if (dlists == 0)
                        totalDir.add(direction);
                }

            }
        }
        adapter.addItems(totalDir);
        flightCounter(totalDir.size());
        //setUpFlightList();
    }

    private void setUpFlightList() {
        //List<Direction> totalDir = searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex);
        //adapter.addItems(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition), totalDir);
        adapter.addItems(airSearchPosition, airFlightIndex);
        //flightCounter(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).size());
    }

    FilterModule filterModule;

    private void showFilterUI() {
        if (filterModule == null) {
            return;
        }
        //adapter.getItemCount()
        new FilterDataDialog().showFilterUI(getContext(), filterModule, adapter.getAllAirLine(), new FilterDataDialog.FilterListener() {
            @Override
            public void onApply(FilterModule filterModule2) {
                filterModule = filterModule2;
                if (filterModule == null) {

                    filterModule = new FilterModule(adapter.getMinPrice(), adapter.getMaxPrice());
                }
                adapter.filterList(filterModule);
            }
        });

    }

    private void flightCounter(int total) {
        if (total == 0) {
            binding.filter.setOnClickListener(null);
        } else {
            binding.filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFilterUI();
                }
            });
        }
        binding.flightTotalFound.setText(total +
                " " + getString(R.string.flight_found));
//        setTitle(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).get(0).getFrom()
//                + " --> " + searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).get(0).getTo());
    }

    private void sortBy() {
        String[] sortingBy = new String[4];
        sortingBy[0] = Enums.Sorting.PRICEASC.getText();
        sortingBy[1] = Enums.Sorting.PRICEDESC.getText();
        sortingBy[2] = Enums.Sorting.TIMEASC.getText();
        sortingBy[3] = Enums.Sorting.TIMEDESC.getText();
        new Dialogs().ShowDialogGender("Sort By", getActivity(), new Dialogs.DialogListener() {
            @Override
            public void onItemSelected(String code, int position) {
                if (code.equalsIgnoreCase(Enums.Sorting.PRICEASC.getText())) {
                    adapter.sorting(Enums.Sorting.PRICEASC);

                } else if (code.equalsIgnoreCase(Enums.Sorting.PRICEDESC.getText())) {
                    adapter.sorting(Enums.Sorting.PRICEDESC);

                } else if (code.equalsIgnoreCase(Enums.Sorting.TIMEASC.getText())) {
                    adapter.sorting(Enums.Sorting.TIMEASC);

                } else if (code.equalsIgnoreCase(Enums.Sorting.TIMEDESC.getText())) {
                    adapter.sorting(Enums.Sorting.TIMEDESC);

                }
            }

            @Override
            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

            }
        }, adapter.getSortType().getText(), sortingBy, true);
    }
}