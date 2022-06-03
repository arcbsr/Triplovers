package com.arcadio.triplover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.models.search.response.AirSearchResponse;
import com.arcadio.triplover.models.search.response.Detail;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.SearchJsModel;
import com.arcadio.triplover.models.search.response.Segment;
import com.arcadio.triplover.utils.ImageLoader;
import com.arcadio.triplover.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    private List<Direction> directions = new ArrayList<>();
    //private AirSearchResponse airSearchResponse;
//    int airFlightIndex;
    SearchJsModel searchJsModel;

    public interface FlightSelectedListener {
        void flightSelected(Direction direction, int viewId);

        Context getContext();
    }

    public FlightsAdapter(SearchJsModel searchJsModel, FlightSelectedListener listener) {
        this.searchJsModel = searchJsModel;
        this.listener = listener;
    }

    public FlightsAdapter(SearchJsModel searchJsModel) {
        this.searchJsModel = searchJsModel;

    }

    private FlightSelectedListener listener;

    public void addItems(AirSearchResponse airSearchResponse, List<Direction> directions) {
        if (airSearchResponse == null) {
            directions.clear();
            return;
        }
        this.directions = directions;//airSearchResponse.getDirections().get(0);
        //this.airSearchResponse = airSearchResponse;
        notifyDataSetChanged();
    }

    public void addItems(List<Direction> directions) {
//        if (airSearchResponse == null) {
//            directions.clear();
//            return;
//        }
        this.directions = directions;//airSearchResponse.getDirections().get(0);
        //this.airSearchResponse = airSearchResponse;
        notifyDataSetChanged();
    }

    public void addItems(int searchPosition, int airFlightIndex) {
//        this.airFlightIndex = airFlightIndex;
//        if (airSearchResponse == null) {
//            directions.clear();
//            return;
//        }
        //this.airSearchResponse = searchJsModel.getItem1().getAirSearchResponses().get(searchPosition);
        this.directions = searchJsModel.getItem1().getAirSearchResponses().get(searchPosition).getDirections().get(airFlightIndex);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_flightlist_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //viewHolder.getTextView().setText(localDataSet[position]);
        Direction direction = directions.get(position);
        Segment segment = direction.getSegments().get(0);
        Detail detail = segment.getDetails().get(0);

        holder.airname.setText(segment.getAirline());
        holder.departStartTime.setText(Utils.timeSeperator(segment.getDeparture()));
        holder.departEndTime.setText(Utils.timeSeperator(segment.getArrival()));
        holder.departStartAirCode.setText(segment.getFrom());
        holder.departEndAircode.setText(segment.getTo());
        holder.flightTime.setText(detail.getFlightTime());
        holder.item_dep_stops.setText((direction.getStops() == 0 ? "Non Stop" : direction.getStops() + " Stops"));

        holder.flightName.setText("BDT");
        holder.flightPrice.setText(searchJsModel.getItem1().getAirSearchResponses().get(direction.searchPosition).getTotalPrice() + "");
        holder.flight_refundable.setText(searchJsModel.getItem1().getAirSearchResponses().get(direction.searchPosition)
                .getRefundable() ? "Refundable" : "Non-Refundable");
//        if (directionsr.size() > 0) {
//            holder.itemView.findViewById(R.id.item_return_layout).setVisibility(View.VISIBLE);
//            Direction rDirection = directionsr.get(position);
//            Segment rsegment = rDirection.getSegments().get(0);
//            Detail rdetail = rsegment.getDetails().get(0);
//            holder.item_ret_airname.setText(rsegment.getAirline());
//            holder.item_ret_stime.setText(timeSeperator(rsegment.getDeparture()));
//            holder.item_ret_etime.setText(timeSeperator(rsegment.getArrival()));
//            holder.item_ret_sairport.setText(rsegment.getFrom());
//            holder.item_ret_eairport.setText(rsegment.getTo());
//            holder.item_ret_duration.setText(rdetail.getFlightTime());
//            holder.item_ret_stops.setText((rDirection.getStops() == 0 ? "Non Stop" : rDirection.getStops() + " Stops"));
//        } else {        }
        holder.itemView.findViewById(R.id.item_return_layout).setVisibility(View.GONE);
        if (direction.isSeleced) {
            ((Button) holder.itemView.findViewById(R.id.flight_book)).setText(listener.getContext().getString(R.string.next) + ">>");
            holder.itemView.findViewById(R.id.item_main_layout).setBackgroundColor(Utils.getColorFromResource(listener.getContext(),
                    R.color.orange));

        } else {
            holder.itemView.findViewById(R.id.item_main_layout).setBackgroundColor(Utils.getColorFromResource(listener.getContext(),
                    R.color.semi_trans));
            ((Button) holder.itemView.findViewById(R.id.flight_book)).setText(listener.getContext().getString(R.string.select));
        }
        holder.itemView.findViewById(R.id.flight_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    for (Direction direction1 : directions) {
                        direction1.isSeleced = false;
                    }
                    direction.isSeleced = true;
                    listener.flightSelected(directions.get(holder.getAdapterPosition()), view.getId());
                }
            }
        });
        holder.itemView.findViewById(R.id.flight_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.flightSelected(directions.get(holder.getAdapterPosition()), view.getId());
                }
            }
        });
        if (listener != null) {
            ImageLoader.loadImage(direction.getPlatingCarrierCode(), holder.item_dep_airthumb, listener.getContext());
        }
    }


    @Override
    public int getItemCount() {
        return directions.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView airname, departStartTime, departEndTime, departStartAirCode, departEndAircode, flightTime, flightName, flightPrice,
                item_dep_stops, flight_refundable;
        public TextView item_ret_stime, item_ret_etime, item_ret_sairport, item_ret_eairport, item_ret_duration, item_ret_stops, item_ret_airname;
        public ImageView item_dep_airthumb;

        public ViewHolder(View view) {
            super(view);
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
            //

            item_ret_stime = view.findViewById(R.id.item_ret_stime);
            item_ret_etime = view.findViewById(R.id.item_ret_etime);
            item_ret_sairport = view.findViewById(R.id.item_ret_sairport);
            item_ret_eairport = view.findViewById(R.id.item_ret_eairport);
            item_ret_duration = view.findViewById(R.id.item_ret_duration);
            item_ret_stops = view.findViewById(R.id.item_ret_stops);
            item_ret_airname = view.findViewById(R.id.item_ret_airname);
            //image thumb

            item_dep_airthumb = view.findViewById(R.id.item_dep_airthumb);
        }

    }
}
