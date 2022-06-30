package com.arcadio.triplover.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.CityAdapter;
import com.arcadio.triplover.listeners.AdapterListener;
import com.arcadio.triplover.models.CityModels;
import com.arcadio.triplover.models.SingleToneData;
import com.arcadio.triplover.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CitySearchFragment extends BaseDialog {
    public interface Listener {
        void onCitySelected(CityModels cityModels);
    }

    private Listener mCallback;

    public CitySearchFragment(Listener mCallback) {
        this.mCallback = mCallback;
    }


    Toolbar toolbar;
    // creating variables for
    // our ui components.
    private RecyclerView cityRV;

    // variable for our adapter
    // class and array list
    private CityAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_city_search, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        toolbar.setTitle(getString(R.string.back));
        Menu menu = toolbar.getMenu();
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        cityRV = view.findViewById(R.id.city_cursor);
        // DO whatever you need with menu items
        buildRecyclerView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        ImageLoader.loadImageBackground(view.findViewById(R.id.homebackground), getContext());
        return view;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<CityModels> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        SingleToneData.getInstance().getCityList(getActivity(), new SingleToneData.CityListListener() {
            @Override
            public void getCity(List<CityModels> cityModelsList) {
                for (CityModels item : cityModelsList) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                            item.getCity().toLowerCase().contains(text.toLowerCase()) ||
                            item.getCountry().toLowerCase().contains(text.toLowerCase()) ||
                            item.getIata().toLowerCase().contains(text.toLowerCase())) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    // if no item is added in filtered list we are
                    // displaying a toast message as no data found.
                    //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
                } else {
                    // at last we are passing that filtered
                    // list to our adapter class.
                    adapter.filterList(filteredlist);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void buildRecyclerView() {
        adapter = new CityAdapter(getContext(), new AdapterListener() {
            @Override
            public void onClickItem(int pos, int viewId) {
                if (mCallback == null) {
                    return;
                }
                CityModels models = adapter.getItem(pos);
                mCallback.onCitySelected(models);
                dismiss();
            }
        });
        SingleToneData.getInstance().getCityList(getActivity(), new SingleToneData.CityListListener() {
            @Override
            public void getCity(List<CityModels> cityModelsList) {
                adapter.filterList(cityModelsList.subList(0, 50));
            }

            @Override
            public void onError(int code) {

            }
        });

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        cityRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        cityRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        cityRV.setAdapter(adapter);
    }
}
