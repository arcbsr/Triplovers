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
import com.arcadio.triplover.models.search.request.Route;
import com.arcadio.triplover.models.search.response.Detail;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.Segment;
import com.arcadio.triplover.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FlightsTabAdapter extends RecyclerView.Adapter<FlightsTabAdapter.ViewHolder> {
    // creating a variable for array list and context.
    private List<Route> routes;
    private int selectedPos = 0;
    private AdapterListener adapterListener;
    private Context context;

    // creating a constructor for our variables.
    public FlightsTabAdapter(List<Route> routes, AdapterListener adapterListener, Context context) {
        this.routes = routes;
        this.adapterListener = adapterListener;
        this.context = context;
    }

    public Route getItem(int position) {
        return routes.get(position);
    }

    public boolean isAllFlightSelected() {
        for (Route route : routes) {
            if (route.getDirection() == null) {
                return false;
            }

        }
        return true;
    }

    public List<Direction> getAllDirection() {
        List<Direction> directions = new ArrayList<>();
        for (Route route : routes) {
            directions.add(route.getDirection());
        }
        return directions;
    }

    @NonNull
    @Override
    public FlightsTabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_flight_index_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsTabAdapter.ViewHolder holder, int position) {
        Route route = routes.get(holder.getAdapterPosition());
        holder.item_flight_from.setText(route.getOrigin());
        holder.item_flight_to.setText(route.getDestination());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selectedPos = holder.getAdapterPosition();
//                notifyDataSetChanged();
//                adapterListener.onClickItem(selectedPos, view.getId());
            }
        });
        if (selectedPos == holder.getAdapterPosition()) {
            holder.item_flight_to.setTextColor(Utils.getColorFromResource(context, R.color.colorPrimary));
            holder.item_flight_from.setTextColor(Utils.getColorFromResource(context, R.color.colorPrimary));
            holder.itemView.findViewById(R.id.item_back).setBackgroundResource(R.drawable.round_border_sel);
        } else {
            holder.item_flight_to.setTextColor(Utils.getColorFromResource(context, R.color.rajon));
            holder.item_flight_from.setTextColor(Utils.getColorFromResource(context, R.color.rajon));
            holder.itemView.findViewById(R.id.item_back).setBackgroundResource(R.drawable.round_border);
        }
        if (route.getDirection() == null) {
            holder.itemView.findViewById(R.id.item_flight_empty).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.item_flight_select).setVisibility(View.GONE);
        } else {
            Segment segment = route.getDirection().getSegments().get(0);
            Detail detail = segment.getDetails().get(0);
            holder.airname.setText(segment.getAirline());
            holder.departStartTime.setText(Utils.timeSeperator(segment.getDeparture()));
            holder.departEndTime.setText(Utils.timeSeperator(segment.getArrival()));
            holder.departStartAirCode.setText(segment.getFrom());
            holder.departEndAircode.setText(segment.getTo());
            holder.flightTime.setText(detail.getFlightTime());
            holder.item_dept_date.setText(detail.getDeparture().split(" ")[0]);
            holder.item_dep_stops.setText((route.getDirection().getStops() == 0 ?
                    "Non Stop" : route.getDirection().getStops() + " Stops"));
            holder.itemView.findViewById(R.id.item_flight_select).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.item_flight_empty).setVisibility(View.GONE);
        }
        holder.itemView.findViewById(R.id.item_flight_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return routes.size();
    }

    public void updateItem(Direction direction) {
        routes.get(selectedPos).setDirection(direction);
        notifyDataSetChanged();
    }

    public void clearDirectionItem() {
        if (selectedPos == 0) {
            return;
        }
        routes.get(selectedPos).setDirection(null);
        if (selectedPos > 0)
            selectedPos--;
        notifyDataSetChanged();
    }

    public void reset() {
        for (Route route : routes) {
            route.setDirection(null);
        }
        selectedPos = 0;
        notifyDataSetChanged();
    }

    public void updateItem(int index, Direction direction) {

        routes.get(selectedPos).setDirection(direction);
        if (selectedPos < routes.size() - 1) {
            selectedPos++;
        }
        notifyDataSetChanged();
    }

    public void addItem(Direction direction) {
        Route route = new Route(direction.getFrom(), direction.getTo(), "");
        route.setDirection(direction);
        routes.add(route);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView airname, departStartTime, departEndTime, departStartAirCode, departEndAircode, flightTime, flightName, flightPrice,
                item_dep_stops, flight_refundable, item_dept_date;

        private TextView item_flight_from, item_flight_to;

        public ViewHolder(@NonNull View view) {
            super(view);
            // initializing our views with their ids.
            item_flight_from = view.findViewById(R.id.item_flight_from);
            item_flight_to = view.findViewById(R.id.item_flight_to);
            airname = view.findViewById(R.id.item_dep_airname);
            departStartTime = view.findViewById(R.id.item_dep_stime);
            departEndTime = view.findViewById(R.id.item_dep_etime);
            flightTime = view.findViewById(R.id.item_dep_duration);
            departStartAirCode = view.findViewById(R.id.item_dep_sairport);
            departEndAircode = view.findViewById(R.id.item_dep_eairport);
            flightName = view.findViewById(R.id.flight_aircode);
            flightPrice = view.findViewById(R.id.flight_airprice);
            item_dep_stops = view.findViewById(R.id.item_dep_stops);
            flight_refundable = view.findViewById(R.id.flight_refundable);
            item_dept_date = view.findViewById(R.id.item_dept_date);
        }
    }
}
