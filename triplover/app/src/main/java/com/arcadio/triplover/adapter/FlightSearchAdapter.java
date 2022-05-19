package com.arcadio.triplover.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.models.CityModels;
import com.arcadio.triplover.models.search.request.Route;
import com.arcadio.triplover.utils.Enums;

import java.util.ArrayList;
import java.util.List;

public class FlightSearchAdapter extends RecyclerView.Adapter<FlightSearchAdapter.ViewHolder> {

    private List<Route> routes = new ArrayList<>();
    private Enums.FlightType flightType = Enums.FlightType.ONE_WAY;

    public Enums.FlightType getFLightType() {
        return flightType;
    }

    public interface AdapterListener {
        void removeItem(int pos);

        void departItem(int pos);

        void returnItem(int pos);

        void departDate(int pos);
    }

    private AdapterListener listener;

    public FlightSearchAdapter(AdapterListener listener) {
        this.listener = listener;
    }

    public void addItems(List<Route> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    public Route getRoute(int pos) {
        if (routes.size() > 0) {
            return routes.get(pos);
        } else {
            return new Route("DAC","CGP","Wed, May 18, 2022");


        }
    }

    public void setFlightType(Enums.FlightType flightType) {
        this.flightType = flightType;
        notifyDataSetChanged();
    }

    public void updateData(int position, CityModels cityModels, Enums.CalenderType calenderType) {
        if (calenderType == Enums.CalenderType.DEPART) {
            routes.get(position).setOrigin(cityModels.getIata());
            routes.get(position).setDepartCityName(cityModels.getName()+", "+ cityModels.getCountry());
        } else {
            routes.get(position).setDestination(cityModels.getIata());
            routes.get(position).setDestinationcityname(cityModels.getName()+", "+ cityModels.getCountry());
        }
        if (flightType == Enums.FlightType.ROUND) {
            Route routeauto = position == 0 ? routes.get(1) : routes.get(0);
            Route routeori = position == 0 ? routes.get(0) : routes.get(1);
            routeauto.setOrigin(routeori.getDestination());
            routeauto.setDepartCityName(routeori.getDestinationcityname());
            routeauto.setDestination(routeori.getOrigin());
            routeauto.setDestinationcityname(routeori.getDepartCityName());

        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_flightsearch_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (flightType == Enums.FlightType.MULTICITY) {
            if (routes.size() > 2) {
                holder.itemView.findViewById(R.id.item_delete).setVisibility(View.VISIBLE);
            } else {
                holder.itemView.findViewById(R.id.item_delete).setVisibility(View.GONE);
            }
            holder.itemView.findViewById(R.id.item_depart_date).setVisibility(View.VISIBLE);
        } else {
            holder.itemView.findViewById(R.id.item_depart_date).setVisibility(View.GONE);

        }
        holder.itemView.findViewById(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.removeItem(holder.getAdapterPosition());
            }
        });
        holder.itemView.findViewById(R.id.item_dept_date_layer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.departDate(holder.getAdapterPosition());
            }
        });
        holder.itemView.findViewById(R.id.city_dept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.departItem(holder.getAdapterPosition());
            }
        });
        holder.itemView.findViewById(R.id.city_destination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.returnItem(holder.getAdapterPosition());
            }
        });
        Route route = routes.get(holder.getAdapterPosition());
        holder.item_dept_code.setText(route.getOrigin());
        holder.item_return_code.setText(route.getDestination());
        holder.item_dept_date.setText(route.getDepartureDate());
        holder.item_dept_city.setText(route.getDepartCityName());
        holder.item_return_city.setText(route.getDestinationcityname());
    }

    private String timeSeperator(String totalTime) {
        String time = totalTime.split(" ")[1];
        String[] timehhmm = time.split(":");
        return timehhmm[0] + ":" + timehhmm[1];

    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_dept_code, item_dept_city, item_return_code, item_return_city,
                item_dept_date;

        public ViewHolder(View view) {
            super(view);
            item_dept_code = view.findViewById(R.id.item_dept_code);
            item_dept_city = view.findViewById(R.id.item_dept_city);
            item_return_code = view.findViewById(R.id.item_return_code);
            item_return_city = view.findViewById(R.id.item_return_city);
            item_dept_date = view.findViewById(R.id.item_dept_date);


        }

    }
}
