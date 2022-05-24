package com.arcadio.triplover.acitivies;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.arcadio.triplover.models.search.request.Route;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.SearchJsModel;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.KLog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                KLog.w(searchAuery);
                MediaType JSON
                        = MediaType.get("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(searchAuery, JSON);
                Request request = new Request.Builder()
                        .url(Constants.ROOT_URL + "api/Search")
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    if (error.equalsIgnoreCase(TAsyntask.ERROR_CANCEL)) {
                        return;
                    }
                    String result = response.body().string();
                    KLog.w(result);
                    searchJsModel = new Gson().fromJson(result, SearchJsModel.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        binding.flightSelectedList.setVisibility(View.GONE);
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
        adapter = new FlightsAdapter(searchJsModel);
        adapter.setListener(new FlightsAdapter.FlightSelectedListener() {
            @Override
            public void flightSelected(Direction direction, int viewId) {
                switch (viewId) {
                    case R.id.flight_book:
                        airSearchPosition = direction.searchPosition;
                        adapterIndex.updateItem(airFlightIndex, direction);
                        if (airFlightIndex == routes.size() - 1) {
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
        });
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
                det3 += "\nTerminal: " + directions.get(position).getSegments().get(0).getDetails().get(0).getOriginTerminal();
                ((TextView) holder.itemView.findViewById(R.id.item_details_dep_details)).setText(det3);

                String det4 = directions.get(position).getSegments().get(0).getArrival().split(" ")[1]
                        + "\n" + directions.get(position).getSegments().get(0).getArrival().split(" ")[0];
                det4 += "\n[" + directions.get(position).getSegments().get(0).getTo() + "]";
                det4 += "\nTerminal: " + directions.get(position).getSegments().get(0).getDetails().get(0).getDestinationTerminal();
                ((TextView) holder.itemView.findViewById(R.id.item_details_ret_details)).setText(det4);
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
        flightCounter(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).size());
        ;
    }

    private void showFilterUI() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_filter_items);
        bottomSheetDialog.show();

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
        setTitle(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).get(0).getFrom()
                + " --> " + searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(airFlightIndex).get(0).getTo());
    }
}