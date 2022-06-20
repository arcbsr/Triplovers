package com.arcadio.triplover.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.arcadio.triplover.R;
import com.arcadio.triplover.models.payments.response.Adt;
import com.arcadio.triplover.models.payments.response.BookingComponent;
import com.arcadio.triplover.models.payments.response.Detail;
import com.arcadio.triplover.models.payments.response.Direction;
import com.arcadio.triplover.models.payments.response.Item1;
import com.arcadio.triplover.models.payments.response.NameElement;
import com.arcadio.triplover.models.payments.response.PassengerCounts;
import com.arcadio.triplover.models.payments.response.PassengerFares;
import com.arcadio.triplover.models.payments.response.Segment;
import com.arcadio.triplover.models.payments.response.TicketInfo;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.ImageLoader;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.Utils;

import java.util.List;

public class TicketViewerFragment extends BaseDialog {
    public interface Listener {
        void onCloseListener();
    }

    private Listener mCallback;
    private Item1 item1;

    public TicketViewerFragment(Listener mCallback, Item1 item1) {
        this.mCallback = mCallback;
        this.item1 = item1;
    }

    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_ticketviewer, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setUpPassengerInfo(view, item1.getTicketInfoes());
        for (List<Direction> directions : item1.getFlightInfo().getDirections()) {
            flightDetailsSetup(view, directions,
                    item1.getPnr());
        }
        fareDetailsSetup(view, item1.getFlightInfo().getPassengerFares(), item1.getFlightInfo().getPassengerCounts(),
                item1.getFlightInfo().getBookingComponents().get(0));
        if (item1.issueDate.isEmpty()) {
            view.findViewById(R.id.ticv_issue_p).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.ticv_issue).setVisibility(View.INVISIBLE);
        }
        ((TextView) view.findViewById(R.id.ticv_issue_p)).setText(item1.issueDate);
        //((TextView) view.findViewById(R.id.ticv_bref_p)).setText(item1.getBookingCodeRef());
        view.findViewById(R.id.ticv_bref_p).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.ticv_bref).setVisibility(View.INVISIBLE);

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

    private void setUpPassengerInfo(View view, List<TicketInfo> ticketInfos) {
        KLog.w(Utils.getGson().toJson(ticketInfos));
        LinearLayout rootView = view.findViewById(R.id.ticv_pas_list);
        int index = 0;
        for (TicketInfo ticketInfo : ticketInfos) {
            View child = getLayoutInflater().inflate(R.layout.layout_ticketviewer_passenger, null);
            NameElement nameElement = ticketInfo.getPassengerInfo().getNameElement();
            String name = nameElement.getFirstName() + " " + nameElement.getMiddleName() + " " + nameElement.getLastName();
            ((TextView) child.findViewById(R.id.item_pass_name)).setText(name);
            ((TextView) child.findViewById(R.id.item_pass_name)).setTypeface(null, Typeface.ITALIC);
            ((TextView) child.findViewById(R.id.item_pass_type)).setText(ticketInfo.getPassengerInfo().getPassengerType());
            ((TextView) child.findViewById(R.id.item_pass_type)).setTypeface(null, Typeface.ITALIC);
            ((TextView) child.findViewById(R.id.item_pass_tcknumber)).setText(ticketInfo.getTicketNumbers().get(0));
            ((TextView) child.findViewById(R.id.item_pass_tcknumber)).setTypeface(null, Typeface.ITALIC);
            rootView.addView(child);
            index++;
        }
    }

    private void flightDetailsSetup(View view, List<Direction> directions, String pnr) {
        KLog.w(Utils.getGson().toJson(directions));
        LinearLayout rootView = view.findViewById(R.id.ticv_flight_list);
        int index = 0;
        for (Direction direction : directions) {
            Segment segment = direction.getSegments().get(0);
            Detail detail = segment.getDetails().get(0);
            View child = getLayoutInflater().inflate(R.layout.layout_ticketviewer_flights, null);
            ImageLoader.loadImage(direction.getPlatingCarrierCode(), (ImageView) child.findViewById(R.id.tct_item_thumb), getContext());
            ((TextView) child.findViewById(R.id.tct_item_airname)).setText(direction.getPlatingCarrierName());
            ((TextView) child.findViewById(R.id.tct_item_from)).setText(direction.getFrom());
            ((TextView) child.findViewById(R.id.tct_item_from_det)).setText(dateFormat(segment.getDeparture()));
            ((TextView) child.findViewById(R.id.tct_item_to)).setText(direction.getTo());
            ((TextView) child.findViewById(R.id.tct_item_to_det)).setText(dateFormat(segment.getArrival()));
            ((TextView) child.findViewById(R.id.tct_item_flightno)).setText(direction.getPlatingCarrierCode() + " "
                    + segment.getFlightNumber());
            ((TextView) child.findViewById(R.id.tct_item_class)).setText(segment.getBookingClass());
            ((TextView) child.findViewById(R.id.tct_item_depart_det)).setText(segment.getFromAirport());
            ((TextView) child.findViewById(R.id.tct_item_arrives_det)).setText(segment.getToAirport());
            ((TextView) child.findViewById(R.id.tct_item_pnr_det)).setText(pnr);
            rootView.addView(child);
            index++;
        }
    }

    private void fareDetailsSetup(View view, PassengerFares passengerFares, PassengerCounts passengerCounts, BookingComponent bookingComponent) {
        KLog.w(Utils.getGson().toJson(passengerFares));
        LinearLayout rootView = view.findViewById(R.id.ticv_fare_list);

        int index = 0;
        if (passengerFares.getAdt() != null) {
            rootView.addView(setUpData("Adult", passengerFares.getAdt(), passengerCounts.getAdt()));

        }
        if (passengerFares.getCnn() != null) {
            rootView.addView(setUpData("Child", passengerFares.getCnn(), passengerCounts.getCnn()));

        }
        if (passengerFares.getInf() != null) {
            rootView.addView(setUpData("Child", passengerFares.getInf(), passengerCounts.getInf()));

        }
        if (passengerFares.getIns() != null) {

            rootView.addView(setUpData("Ins", passengerFares.getIns(), passengerCounts.getIns()));
        }
        getTxtView(R.id.item_tct_totalp, view).setText(bookingComponent.getTotalPrice() + "");
        getTxtView(R.id.item_tct_ait, view).setText(bookingComponent.getAit() + "");
        getTxtView(R.id.item_tct_discount, view).setText(bookingComponent.getDiscountPrice() + "");
        getTxtView(R.id.item_tct_grandtotal, view).setText(bookingComponent.getBasePrice() + "");
    }

    private View setUpData(String title, Adt data, int count) {
        View child = getLayoutInflater().inflate(R.layout.layout_ticketviewer_fare, null);
        getTxtView(R.id.item_tct_pax, child).setText(title);
        getTxtView(R.id.item_tct_base, child).setText(data.getBasePrice() + "");
        getTxtView(R.id.item_tct_tax, child).setText(data.getTaxes() + "");
        getTxtView(R.id.item_tct_fees, child).setText("0");
        getTxtView(R.id.item_tct_person, child).setText(count + "");
        getTxtView(R.id.item_tct_total, child).setText(data.getTotalPrice() + "");
        return child;
    }

    private TextView getTxtView(int id, View view) {
        return view.findViewById(id);

    }

    public static String dateFormat(String dateTime) {
        try {
            String date = Utils.timeSeperator(dateTime);
            String[] dateTimes = dateTime.split(" ");
            date += "\n" + Utils.getDate(Utils.stringToMilliseconds(dateTimes[0], "yyyy-MM-dd"), Constants.DATE_FORMAT_NORMAL);

            return date;
        } catch (Exception e) {

        }
        return dateTime;
    }
}
