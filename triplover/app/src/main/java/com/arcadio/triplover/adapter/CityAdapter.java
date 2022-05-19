package com.arcadio.triplover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.listeners.AdapterListener;
import com.arcadio.triplover.models.CityModels;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    // creating a variable for array list and context.
    private List<CityModels> courseModalArrayList;
    private Context context;
    private AdapterListener adapterListener;

    // creating a constructor for our variables.
    public CityAdapter(Context context, AdapterListener adapterListener) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        this.adapterListener = adapterListener;
    }

    public CityModels getItem(int position) {
        return courseModalArrayList.get(position);
    }

    // method for filtering our recyclerview items.
    public void filterList(List<CityModels> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        courseModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        CityModels modal = courseModalArrayList.get(position);
        holder.cityName.setText(modal.getName() + "( " + modal.getIata() + " )");
        holder.cityDescription.setText(modal.getCity() + ", " + modal.getCountry());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.onClickItem(holder.getAdapterPosition(), view.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView cityName, cityDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            cityName = itemView.findViewById(R.id.item_cityname);
            cityDescription = itemView.findViewById(R.id.item_citydes);
        }
    }
}
