package com.arcadio.triplover.acitivies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arcadio.triplover.adapter.FlightsAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.databinding.FragmentFlightListBinding;
import com.arcadio.triplover.models.search.request.SearchReq;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.SearchJsModel;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FlightListFragment extends Fragment {

    private FragmentFlightListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFlightListBinding.inflate(inflater, container, false);
        // That's all!
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                NavHostFragment.findNavController(SecondFragment.this)
////                        .navigate(R.id.action_back_flightsearch);
//                NavHostFragment.findNavController(SeconFlightListFragment.this).popBackStack();
//
//            }
//        });

        prepareFlights();
    }

    private void prepareFlights() {
        new TAsyntask(getActivity(), new TAsyntask.KAsyncListener() {
            SearchJsModel searchJsModel;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                String searchAuery = getArguments().getString(Constants.QUERY_FLIGHT_SEARCH);
                SearchReq searchReq = new SearchReq();
//                Route route = new Route("DAC", "CGP", "2022-06-29");
//                searchReq.getRoutes().add(new Route("DAC", "CGP", "2022-06-29"));
//                //searchReq.getRoutes().add(new Route("CGP", "DAC", "2022-07-7"));
//                searchReq.setAdults(2);
//                searchReq.setChilds(1);
//                searchReq.setCabinClass(1);

//                String jsonData = new Gson().toJson(searchReq);
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
                    String result = response.body().string();
                    searchJsModel = Utils.getGson().fromJson(result, SearchJsModel.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleteListener() {
                if (searchJsModel == null) {
                    NavHostFragment.findNavController(FlightListFragment.this).popBackStack();
                    return;
                }
                FlightsAdapter adapter = new FlightsAdapter(searchJsModel);
                // Attach the adapter to the recyclerview to populate items
                binding.recycleFlightlist.setAdapter(adapter);
                // Set layout manager to position the items
                binding.recycleFlightlist.setLayoutManager(new LinearLayoutManager(getContext()));

                int airSearchPosition = 0;
                List<Direction> totalDir = new ArrayList<>();
//                List<Direction> deptDirections = searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(0);
//                for (Direction direction : deptDirections) {
//                    String ds = new Gson().toJson(direction);
//                    Direction converted = new Gson().fromJson(ds, Direction.class);
//                    List<Direction> retDirections = searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(1);
//                    for (Direction retDirection : retDirections) {
//                        converted.direction = retDirection;
//                        totalDir.add(converted);
//                    }
//                }

                adapter.addItems(searchJsModel.getItem1().getAirSearchResponses().get(3), totalDir);

            }

            @Override
            public void onErrorListener(String msg) {

            }
        }).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}