package com.arcadio.triplover.models;

import android.app.Activity;

import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SingleToneData {
    public interface CityListListener {
        void getCity(List<CityModels> cityModelsList);

        void onError(int code);
    }

    private static SingleToneData toneData = null;
    private List<CityModels> cityList = new ArrayList<>();

    public static SingleToneData getInstance() {
        if (toneData == null) {
            toneData = new SingleToneData();
        }
        return toneData;
    }

    public void createCityList(Activity activity, CityListListener listener) {
        new TAsyntask(activity, new TAsyntask.KAsyncListener() {
            @Override
            public void onPreListener() {
                String jsonFileString = "{\"data\" : " + Utils.getJsonFromAssets(activity, "airports.json") + "}";
                cityList = new Gson().fromJson(jsonFileString, CityList.class).getData();
                listener.getCity(cityList);
            }

            @Override
            public void onThreadListener(String data) {

            }

            @Override
            public void onCompleteListener() {

            }

            @Override
            public void onErrorListener(String msg) {

            }
        }).execute();
        this.cityList = cityList;
    }

    public void getCityList(Activity activity, CityListListener listener) {
        if (cityList.size() == 0) {
            createCityList(activity, listener);
            return;
        }
        listener.getCity(cityList);
    }
}
