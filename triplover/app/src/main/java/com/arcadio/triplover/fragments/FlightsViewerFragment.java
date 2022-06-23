package com.arcadio.triplover.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.models.search.response.Baggage;
import com.arcadio.triplover.models.search.response.Direction;
import com.arcadio.triplover.models.search.response.PassengerFares;

import java.util.ArrayList;
import java.util.List;

public class FlightsViewerFragment extends BaseDialog {
    public interface Listener {
        void onCloseListener();

        void onConfirmListener();
    }

    private Listener mCallback;

    public FlightsViewerFragment(final List<Direction> directions, boolean isAllFlightSelected, Listener mCallback) {
        this.mCallback = mCallback;
        this.directions = directions;
        this.isAllFlightSelected = isAllFlightSelected;
    }

    List<Direction> directions = new ArrayList<>();
    boolean isAllFlightSelected;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_recycleview, container, false);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        showDetails(view, directions, isAllFlightSelected);
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mCallback.onCloseListener();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mCallback.onCloseListener();
    }

    private void showDetails(View bottomSheetDialog, final List<Direction> directions, boolean isAllFlightSelected) {
        if (directions.size() == 0) {
            Toast.makeText(getContext(), getString(R.string.nothing_show), Toast.LENGTH_SHORT).show();
            return;
        }


        PassengerFares passengerFares = directions.get(0).passengerFares;
        if (passengerFares.getAdt() != null) {
            ((TextView) bottomSheetDialog.findViewById(R.id.baseprice)).setText(passengerFares.getAdt().getBasePrice() + "");
            ((TextView) bottomSheetDialog.findViewById(R.id.tax)).setText(passengerFares.getAdt().getTaxes() + "");
            ((TextView) bottomSheetDialog.findViewById(R.id.discount)).setText(passengerFares.getAdt().getDiscountPrice() + "");
            ((TextView) bottomSheetDialog.findViewById(R.id.process_fee)).setText(0 + "");
            ((TextView) bottomSheetDialog.findViewById(R.id.total_price)).setText(passengerFares.getAdt().getTotalPrice() + "");
        }
        if (isAllFlightSelected) {
            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setVisibility(View.VISIBLE);
            ((TextView) bottomSheetDialog.findViewById(R.id.search_flight_confirm)).setText(getString(R.string.confirm));
            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onConfirmListener();
                    dismiss();
                }
            });
        } else {
//            ((TextView) bottomSheetDialog.findViewById(R.id.search_flight_confirm)).setText(getString(R.string.close));
//            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setOnClickListener(view -> dismiss());
            bottomSheetDialog.findViewById(R.id.search_flight_confirm).setVisibility(View.GONE);
        }
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.rc_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                ((TextView) holder.itemView.findViewById(R.id.item_flight_info)).setText(directions.get(position).getFrom() + " - " + directions.get(position).getTo());
                ((TextView) holder.itemView.findViewById(R.id.item_flight_planename)).setText(directions.get(position).getPlatingCarrierCode() + "-" +
                        directions.get(position).getSegments().get(0).getFlightNumber());
                ((TextView) holder.itemView.findViewById(R.id.item_flight_duration)).setText(directions.get(position).getSegments().get(0).getDuration().get(0));
                ((TextView) holder.itemView.findViewById(R.id.item_flight_class)).setText(directions.get(position).getSegments().get(0).getServiceClass());
                if (directions.get(position).getSegments().get(0).getBaggage() == null ||
                        directions.get(position).getSegments().get(0).getBaggage().size() == 0) {
//                    ((TextView) holder.itemView.findViewById(R.id.item_flight_baggeg)).setText( );
                } else {
                    Baggage baggage = directions.get(position).getSegments().get(0).
                            getBaggage().get(0);
                    ((TextView) holder.itemView.findViewById(R.id.item_flight_baggeg)).setText(baggage.getAmount() +
                            baggage.getUnits());
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
