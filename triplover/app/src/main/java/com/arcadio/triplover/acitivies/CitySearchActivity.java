package com.arcadio.triplover.acitivies;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.adapter.FlightsAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.databinding.ActivityCitySearchBinding;
import com.arcadio.triplover.models.search.request.SearchReq;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.SearchJsModel;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CitySearchActivity extends BaseActivity {

    private ActivityCitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        prepareFlights();
    }

    private void prepareFlights() {
        new TAsyntask(this, new TAsyntask.KAsyncListener() {
            SearchJsModel searchJsModel;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                String searchAuery = getIntent().getStringExtra(Constants.QUERY_FLIGHT_SEARCH);
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
                if (searchJsModel == null || searchJsModel.getItem1() == null) {
                    //NavHostFragment.findNavController(this).popBackStack();
                    return;
                }
                FlightsAdapter adapter = new FlightsAdapter(searchJsModel);
                // Attach the adapter to the recyclerview to populate items
                binding.cityCursor.setAdapter(adapter);
                // Set layout manager to position the items
                binding.cityCursor.setLayoutManager(new LinearLayoutManager(CitySearchActivity.this));

                int airSearchPosition = 0;
                List<Direction> totalDir = searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition).getDirections().get(0);
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

                adapter.addItems(searchJsModel.getItem1().getAirSearchResponses().get(airSearchPosition), totalDir);

            }

            @Override
            public void onErrorListener(String msg) {

            }
        }).execute();
    }
}
