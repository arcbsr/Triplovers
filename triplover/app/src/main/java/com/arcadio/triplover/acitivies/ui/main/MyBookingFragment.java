package com.arcadio.triplover.acitivies.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.fragments.TicketViewerFragment;
import com.arcadio.triplover.models.mybookings.MyBookingReq;
import com.arcadio.triplover.models.mybookings.MyBookingRes;
import com.arcadio.triplover.models.mybookings.MyBookings;
import com.arcadio.triplover.models.payments.response.Item1;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.ImageLoader;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class MyBookingFragment extends Fragment {

    public static MyBookingFragment newInstance() {
        return new MyBookingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        myBookingSetup(view);

        ImageLoader.loadImageBackground(view.findViewById(R.id.homebackground), getContext());
        return view;
    }

    private void myBookingSetup(View view) {
        new TAsyntask(getActivity(), new TAsyntask.KAsyncListener() {
            TAsyntask.ResponseResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                String token = PreferencesHelpers.getToken(getContext());
                if (token == null || token.isEmpty()) {
                    response = new TAsyntask.ResponseResult();
                    response.code = 401;
                    return;
                }
                MyBookingReq myBookingReq = new MyBookingReq();
                response =
                        TAsyntask.postRequestHeader(Utils.getGson().toJson(myBookingReq), Constants.ROOT_MY_BOOKINGS,
                                token);

            }

            @Override
            public void onCompleteListener() {
                if (response == null) {
                    view.findViewById(R.id.booking_no_found).setVisibility(View.VISIBLE);
                } else {
                    if (response.code == 200) {
                        MyBookings myBookings = Utils.getGson().fromJson("{\"data\":" + response.result + "}", MyBookings.class);
                        setUpList(view, myBookings.getData());
                        return;

                    } else if (response.code == 401) {

                        new LoginDialogFragment(new LoginDialogFragment.Listener() {
                            @Override
                            public void onLogIn(LoginResponse response) {

                                myBookingSetup(view);
                            }

                            @Override
                            public void onLoginFailed() {
                                new MaterialAlertDialogBuilder(getContext())
                                        .setTitle("Failed")
                                        .setMessage(getString(R.string.loginFailed))
                                        .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                getActivity().onBackPressed();
                                            }
                                        })
                                        .show();
                            }
                        }).show(getParentFragmentManager(), "LoginFrom");

                    } else {
                        view.findViewById(R.id.booking_no_found).setVisibility(View.VISIBLE);
                    }
                }
            }


            @Override
            public void onErrorListener(String msg) {
            }
        }).customExecute(true);
    }


    private void setUpList(View view, List<MyBookingRes> myBookings) {
        if (myBookings.size() == 0) {
            view.findViewById(R.id.booking_no_found).setVisibility(View.VISIBLE);
        }
        RecyclerView bookingList = view.findViewById(R.id.mybooking_list);
        bookingList.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingList.setAdapter(new BasicAdapter(new BasicAdapter.BasicListener() {
            @Override
            public int getItem() {
                return myBookings.size();
            }

            @Override
            public int getLayoutId() {
                return R.layout.layout_mybooking_item;
            }

            @Override
            public void onBindViewHolder(BasicAdapter.ViewHolder holder, int position) {
                MyBookingRes bookingRes = myBookings.get(position);
                ((TextView) holder.itemView.findViewById(R.id.item_paxname)).setText(bookingRes.getPaxName());
                String details = "Issue: " + bookingRes.getIssueDate();
                details += "\nDepart: " + bookingRes.getTravellDate();
                //details += "\nTicket:" + bookingRes.getTicketNumber();
                details += "\nPnr: " + bookingRes.getPnr();
                details += "\nStatus: " + bookingRes.getStatus();
                ((TextView) holder.itemView.findViewById(R.id.item_paxdes)).setText(details);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTicketDetail(bookingRes);
                    }
                });
            }
        }));

    }

    private void showTicketDetail(MyBookingRes bookingRes) {

        new TAsyntask(getActivity(), new TAsyntask.KAsyncListener() {
            TAsyntask.ResponseResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                response =
                        TAsyntask.getRequestHeader(Constants.ROOT_MY_BOOKING_DETAIL + bookingRes.getUniqueTransID(),
                                PreferencesHelpers.getToken(getContext()));
            }

            @Override
            public void onCompleteListener() {
                if (response.code == 200) {
                    Item1 item1 = null;
                    try {
                        item1 = Utils.getGson().fromJson(response.result, Item1.class);
                        item1.issueDate = bookingRes.getIssueDate();
                    } catch (Exception e) {

                    }
                    if (item1 != null) {
                        new TicketViewerFragment(new TicketViewerFragment.Listener() {
                            @Override
                            public void onCloseListener() {

                            }
                        }, item1).show(getParentFragmentManager(), "TicketViewer");
                    }
                    return;
                }
                Toast.makeText(getContext(), getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onErrorListener(String msg) {

            }
        }).execute();
    }
}