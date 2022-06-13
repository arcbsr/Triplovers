package com.arcadio.triplover.acitivies;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.fragments.TicketViewerFragment;
import com.arcadio.triplover.listeners.TextWatcherListener;
import com.arcadio.triplover.models.passenger.request.ContactInfo;
import com.arcadio.triplover.models.passenger.request.DocumentInfo;
import com.arcadio.triplover.models.passenger.request.NameElement;
import com.arcadio.triplover.models.passenger.request.PassengerInfo;
import com.arcadio.triplover.models.passenger.request.PassengerReq;
import com.arcadio.triplover.models.passenger.response.PreBookingResponse;
import com.arcadio.triplover.models.payments.request.PaymentReq;
import com.arcadio.triplover.models.payments.response.BookingConfirm;
import com.arcadio.triplover.models.reprice.request.RePriceReq;
import com.arcadio.triplover.models.reprice.response.PassengerCounts;
import com.arcadio.triplover.models.reprice.response.RePriceResponse;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.Enums;
import com.arcadio.triplover.utils.KLog;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PassengerEntryActivity extends BaseActivity {
    PassengerReq allPassengers;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_entry);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.passenger_details);
//        PreferencesHelpers.setToken(getContext(), "");
        passDataToSave = PreferencesHelpers.loadPassenger(this);
        gatherInfo(PreferencesHelpers.getToken(getContext()));
    }

    private void gatherInfo(String token) {
        final RePriceReq priceReq = (RePriceReq) getIntent().getSerializableExtra(Constants.PASS_REPRICE_REAUEST);

        KLog.w(getGson().toJson(priceReq));
        if (priceReq == null) {
            finish();
            return;
        }
        KLog.w(getGson().toJson(priceReq));
        new TAsyntask(this, new TAsyntask.KAsyncListener() {
            TAsyntask.ResponseResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                if (token == null || token.isEmpty()) {
                    response = new TAsyntask.ResponseResult();
                    response.code = 401;
                    return;
                }
                response =
                        TAsyntask.postRequestHeader(getGson().toJson(priceReq), Constants.ROOT_REPRICE_REQ,
                                token);

            }

            @Override
            public void onCompleteListener() {
                if (response == null) {

                } else {
                    if (response.code == 200) {
                        RePriceResponse priceResponse = getGson().fromJson(response.result, RePriceResponse.class);
                        if (priceResponse != null && priceResponse.getItem1() != null) {
                            setUpData(priceResponse);
                            return;

                        } else if (priceResponse != null && priceResponse.getItem2() != null) {
                            if (priceResponse.getItem2().getMessage().contains("No eligible fare found")) {
                                new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                        .setCancelable(false)
                                        .setTitle("Failed")
                                        .setMessage("This fare is not available. please select another fare.")
                                        .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                onBackPressed();
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                    .setCancelable(false)
                                    .setTitle("Failed")
                                    .setMessage("Sorry for inconvenience, please try again")
                                    .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            FlightListDataActivity.getInstance().finish();
                                            onBackPressed();
                                        }
                                    })
                                    .show();
                            return;
                        }
                    } else if (response.code == 401) {

                        new LoginDialogFragment(new LoginDialogFragment.Listenerv2() {
                            @Override
                            public void onLogIn(LoginResponse response) {
                                gatherInfo(response.getToken());
                            }

                            @Override
                            public void onLoginFailed() {
                                new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                        .setCancelable(false)
                                        .setTitle("Failed")
                                        .setMessage(getString(R.string.loginFailed))
                                        .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        })
                                        .show();
                            }

                            @Override
                            public void onLoginDismissed() {
                                onBackPressed();
                            }
                        }).show(getSupportFragmentManager(), "LoginFrom");

                    }
                }
            }


            @Override
            public void onErrorListener(String msg) {
                finish();
            }
        }).execute();
    }

    private void initPassengerInfo(int size, Enums.UserAgeType userAgeType, String defaultCountryCode) {
        String phoneCode = CountryToPhonePrefix.CountryCodeBuilder(defaultCountryCode).phoneCode;
        for (int count = 0; count < size; count++) {
            PassengerInfo passengerInfo = new PassengerInfo();
            switch (userAgeType) {
                case ADULT:
                    passengerInfo.title = "Adult# " + (count + 1);
                    passengerInfo.setPassengerType("ADT");
                    break;
                case CHILD:
                    passengerInfo.title = "Child# " + (count + 1);
                    passengerInfo.setPassengerType("CNN");
                    break;
                case INFANT:
                    passengerInfo.title = "Infant# " + (count + 1);
                    passengerInfo.setPassengerType("INF");
                    break;
            }
            passengerInfo.getContactInfo().setCountryCode(defaultCountryCode);
            passengerInfo.getContactInfo().setPhoneCountryCode(phoneCode);
            passengerInfo.getDocumentInfo().setNationality(defaultCountryCode);
            allPassengers.addPassengerInfoes(passengerInfo);
        }

    }

    private void showPassengerDetails(int positionToUpdate) {
        String[] data = new String[passDataToSave.size()];
        int index = 0;
        for (String ss : passDataToSave) {
            PassengerInfo passengerInfo = getGson().fromJson(ss, PassengerInfo.class);
            data[index] = passengerInfo.getNameElement().getFirstName();
            index++;
        }
        new Dialogs().ShowDialogGender("", getActivity(), new Dialogs.DialogListener() {
            @Override
            public void onItemSelected(String code, int position) {
                PassengerInfo passengerInfo = getGson().fromJson(passDataToSave.get(position), PassengerInfo.class);
                allPassengers.getPassengerInfoes().get(positionToUpdate).updatePassengerInfo(passengerInfo);

                notifyPassengerList();
            }

            @Override
            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

            }
        }, "", data, true);
    }

    private void notifyPassengerList() {
        ((RecyclerView) findViewById(R.id.passenger_list)).getAdapter().notifyDataSetChanged();
    }

    List<String> passDataToSave = new ArrayList<>();

    private void setUpData(RePriceResponse response) {


        String countryCode = CountryToPhonePrefix.getLocalCode(getActivity());
        PassengerCounts passengerCounts = response.getItem1().getPassengerCounts();
        allPassengers = new PassengerReq();
        allPassengers.setTotalPrice(response.getItem1().getTotalPrice());
        allPassengers.setItemCodeRef(response.getItem1().getItemCodeRef());
        allPassengers.setPriceCodeRef(response.getItem1().getPriceCodeRef());
        allPassengers.setUniqueTransID(response.getItem1().getUniqueTransID());
        initPassengerInfo(passengerCounts.getAdt(), Enums.UserAgeType.ADULT, countryCode);
        initPassengerInfo(passengerCounts.getCnn(), Enums.UserAgeType.CHILD, countryCode);
        initPassengerInfo(passengerCounts.getInf(), Enums.UserAgeType.INFANT, countryCode);
        RecyclerView passengerRCView = findViewById(R.id.passenger_list);
        passengerRCView.setAdapter(new BasicAdapter(new BasicAdapter.BasicListener() {
            @Override
            public int getItem() {
                return allPassengers.getPassengerInfoes().size();
            }

            @Override
            public int getLayoutId() {
                return R.layout.layout_passenger_item;
            }

            @Override
            public void onBindViewHolder(BasicAdapter.ViewHolder holder, int position) {
                holder.itemView.findViewById(R.id.item_pas_header).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPassengerDetails(position);
                    }
                });
                int pos = holder.getAdapterPosition();
                PassengerInfo passenger = allPassengers.getPassengerInfoes().get(pos);
                setupPassengerInfo(pos, holder.itemView, false);
//                holder.itemView.findViewById(R.id.item_pas_edit).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        passengerEditForm(pos);
//                    }
//                });
                dropDownListview(pos, holder.itemView, true);
            }
        }));
        passengerRCView.setLayoutManager(new LinearLayoutManager(this));

//        ((CheckBox)findViewById(R.id.pas_agree)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                findViewById(R.id.book_continue).setEnabled(b);
//            }
//        });
        findViewById(R.id.book_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validationMsg = userFormValidation();
                if (!validationMsg.isEmpty()) {
                    new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                            .setTitle("Invalid Data")
                            .setMessage(validationMsg)
                            .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                    return;
                }
                if (((CheckBox) findViewById(R.id.pas_agree)).isChecked())
                    bookingTicket(response);
                else {
                    ((CheckBox) findViewById(R.id.pas_agree)).setChecked(true);
                    ((CheckBox) findViewById(R.id.pas_agree)).setChecked(false);
                }
            }
        });
        findViewById(R.id.book_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openTermAndCondition(getContext());
            }
        });
    }

    private String userFormValidation() {
        String error = "";
        for (PassengerInfo passengerInfo : allPassengers.getPassengerInfoes()) {
            NameElement nameElement = passengerInfo.getNameElement();
            if (nameElement.getFirstName().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Fast Name";
                break;
            }
            if (nameElement.getLastName().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Last Name";
                break;
            }
            DocumentInfo documentInfo = passengerInfo.getDocumentInfo();
            if (documentInfo.getDocumentNumber().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Document Number";
                break;
            }
            if (documentInfo.getIssuingCountry().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Issued In";
                break;
            }
            ContactInfo contactInfo = passengerInfo.getContactInfo();
            if (contactInfo.getCityName().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " City Name";
                break;
            }
            if (contactInfo.getEmail().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Email Id";
                break;
            }
            if (contactInfo.getPhone().isEmpty()) {
                error = "Invalid " + passengerInfo.title + " Phone number";
                break;
            }
        }
        return error;
    }

    private void processForPayment(String uniqueTransID) {
        processPayment(uniqueTransID, allPassengers.getTotalPrice(), new SSLCTransactionResponseListener() {
            @Override
            public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
                confirmBookingTask(sslcTransactionInfoModel);
            }

            @Override
            public void transactionFail(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void merchantValidationError(String s) {

            }
        });
    }

    private void confirmBookingTask(SSLCTransactionInfoModel sslcTransactionInfoModel) {
        new TAsyntask(this, new TAsyntask.KAsyncListener() {
            TAsyntask.ResponseResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                PaymentReq paymentReq = new PaymentReq();
                paymentReq.setUniqueTransID(sslcTransactionInfoModel.getTranId());
                paymentReq.setSslResponse(sslcTransactionInfoModel);
                String request = getGson().toJson(paymentReq);
                KLog.w(request);
                response =
                        TAsyntask.postRequestHeader(request, Constants.ROOT_CONFIRM_BOOKING,
                                PreferencesHelpers.getToken(getContext()));

            }

            @Override
            public void onCompleteListener() {
                if (response == null) {
                    Toast.makeText(getContext(), getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code == 200) {
                        BookingConfirm bookingConfirm = getGson().fromJson(response.result, BookingConfirm.class);
                        PreferencesHelpers.saveStringData(getContext(), "book2", response.result);
                        if (bookingConfirm != null && bookingConfirm.getIsSuccess()) {
                            new TicketViewerFragment(new TicketViewerFragment.Listener() {
                                @Override
                                public void onCloseListener() {
                                    finish();
                                }
                            }, bookingConfirm.getData().getItem1()).show(getSupportFragmentManager(), "TicketViewer");
                            return;

                        } else {

                            new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                    .setTitle("Failed")
                                    .setMessage(response.result)
                                    .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();
                            return;
                        }

                    } else if (response.code == 401) {

                        new LoginDialogFragment(new LoginDialogFragment.Listener() {
                            @Override
                            public void onLogIn(LoginResponse response) {
                                confirmBookingTask(sslcTransactionInfoModel);
                            }

                            @Override
                            public void onLoginFailed() {
                                new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                        .setTitle("Failed")
                                        .setMessage(getString(R.string.loginFailed))
                                        .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();
                            }
                        }).show(getSupportFragmentManager(), "LoginFrom");

                    }
                }
            }


            @Override
            public void onErrorListener(String msg) {
                finish();
            }
        }).execute();

    }

    private void preBookingTask() {
        new TAsyntask(this, new TAsyntask.KAsyncListener() {
            TAsyntask.ResponseResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                response =
                        TAsyntask.postRequestHeader(getGson().toJson(allPassengers), Constants.ROOT_PREPARE_BOOKING,
                                PreferencesHelpers.getToken(getContext()));

            }

            @Override
            public void onCompleteListener() {
                if (response == null) {

                } else {
                    if (response.code == 200) {
                        PreBookingResponse preBookingResponse = getGson().fromJson(response.result, PreBookingResponse.class);
                        if (preBookingResponse != null && preBookingResponse.getIsSuccess()) {
                            processForPayment(preBookingResponse.getUniqueTransID());

                            return;

                        } else {
                            finish();
                            return;
                        }
                    } else if (response.code == 401) {

                        new LoginDialogFragment(new LoginDialogFragment.Listener() {
                            @Override
                            public void onLogIn(LoginResponse response) {
                                preBookingTask();
                            }

                            @Override
                            public void onLoginFailed() {
                                new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                        .setTitle("Failed")
                                        .setMessage(getString(R.string.loginFailed))
                                        .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();
                            }
                        }).show(getSupportFragmentManager(), "LoginFrom");

                    }
                }
            }


            @Override
            public void onErrorListener(String msg) {
                finish();
            }
        }).execute();

    }

    private void bookingTicket(RePriceResponse response) {
        //TODO: add Validation....
        PreferencesHelpers.savePassemger(passDataToSave, this);
        preBookingTask();

    }

    private void passengerEditForm(int position) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_passenger_item);
        bottomSheetDialog.findViewById(R.id.item_pas_edit).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.item_pas_save).setVisibility(View.VISIBLE);

        bottomSheetDialog.findViewById(R.id.item_pas_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassengerInfo(position, bottomSheetDialog.getWindow().getDecorView());
                bottomSheetDialog.dismiss();
            }
        });
        setupPassengerInfo(position, bottomSheetDialog.getWindow().getDecorView(), true);
        bottomSheetDialog.show();
    }

    private void savePassengerInfo(int pos, View view) {
        PassengerInfo passengerInfo = allPassengers.getPassengerInfoes().get(pos);
        NameElement nameElement = passengerInfo.getNameElement();
        DocumentInfo documentInfo = passengerInfo.getDocumentInfo();
        ContactInfo contactInfo = passengerInfo.getContactInfo();

        nameElement.setTitle(getTextView(R.id.item_pas_title, view).getText().toString());
        nameElement.setFirstName(getEditTextView(R.id.item_pas_fname, view).getText().toString());
        nameElement.setLastName(getEditTextView(R.id.item_pas_lname, view).getText().toString());

        documentInfo.setDocumentNumber(getEditTextView(R.id.item_pas_docno, view).getText().toString());
        documentInfo.setDocumentType(getTextView(R.id.item_pas_doctype, view).getText().toString());
        documentInfo.setIssuingCountry(getEditTextView(R.id.item_pas_docissuein, view).getText().toString());
        documentInfo.setExpireDate(getTextView(R.id.item_pas_docexpiry, view).getText().toString());
//        documentInfo.setNationality(getEditTextView(R.id.item_pas_nationality, view).getText().toString());

        documentInfo.setNationality(getTextView(R.id.item_pas_nationality, view).getText().toString());

        passengerInfo.setDateOfBirth(getTextView(R.id.item_pas_dob, view).getText().toString());
        passengerInfo.setGender(getTextView(R.id.item_pas_gender, view).getText().toString());

        contactInfo.setEmail(getEditTextView(R.id.item_pas_email, view).getText().toString());
        contactInfo.setCityName(getEditTextView(R.id.item_pas_cityname, view).getText().toString());
        contactInfo.setCountryCode(getTextView(R.id.item_pas_contrycode, view).getText().toString());
        contactInfo.setPhoneCountryCode(getTextView(R.id.item_pas_phnccode, view).getText().toString());
        contactInfo.setPhone(getEditTextView(R.id.item_pas_phone, view).getText().toString());

        (((RecyclerView) findViewById(R.id.passenger_list)).getAdapter()).notifyDataSetChanged();
    }

    private void setupPassengerInfo(int pos, View view) {
        setupPassengerInfo(pos, view, true);
    }

    private void dropDownListview(int pos, View view, boolean isEnable) {
        PassengerInfo passenger = allPassengers.getPassengerInfoes().get(pos);
        ContactInfo contactInfo = passenger.getContactInfo();
        DocumentInfo documentInfo = passenger.getDocumentInfo();
        NameElement nameElement = passenger.getNameElement();
        view.findViewById(R.id.item_pas_nationality).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().ShowDialog(getString(R.string.nationality),
                        getActivity(), Enums.CodeSearchType.Countries,
                        documentInfo.getNationality(), new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code, int position) {
                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails, int position) {

                                documentInfo.setNationality(countryDetails.countryCode);
                                getTextView(R.id.item_pas_nationality, view).setText(passenger.getDocumentInfo().getNationality());
                            }
                        });
            }
        });
        view.findViewById(R.id.item_pas_gender).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                new Dialogs().ShowDialogGender(getString(R.string.gender), getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        passenger.setGender(code);
                        getTextView(R.id.item_pas_gender, view, isEnable).setText(passenger.getGender());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, passenger.getGender(), getResources().getStringArray(R.array.gender));
            }
        });
        view.findViewById(R.id.item_pas_title).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                new Dialogs().ShowDialogGender(getString(R.string.gender), getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        passenger.getNameElement().setTitle(code);
                        getTextView(R.id.item_pas_title, view, isEnable).setText(passenger.getNameElement().getTitle());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, passenger.getNameElement().getTitle(), getResources().getStringArray(R.array.passenger_title));
            }
        });
        view.findViewById(R.id.item_pas_phnccode).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().ShowDialog(getString(R.string.phone_contrycode),
                        getActivity(), Enums.CodeSearchType.PhoneCodes,
                        contactInfo.getPhoneCountryCode(), new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code, int position) {

                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails, int position) {
                                contactInfo.setPhoneCountryCode(countryDetails.phoneCode);
                                contactInfo.setCountryCode(countryDetails.countryCode);
                                getTextView(R.id.item_pas_phnccode, view).setText(contactInfo.getPhoneCountryCode());
                                getTextView(R.id.item_pas_contrycode, view).setText(contactInfo.getCountryCode());
                            }
                        });
            }
        });
        view.findViewById(R.id.item_pas_doctype).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().ShowDialogGender(getString(R.string.doc_type), getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code, int position) {
                        documentInfo.setDocumentType(code);
                        getTextView(R.id.item_pas_doctype, view, isEnable).setText(documentInfo.getDocumentType());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position) {

                    }
                }, documentInfo.getDocumentType(), getResources().getStringArray(R.array.doc_type));
            }
        });
        view.findViewById(R.id.item_pas_dob).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().showCalender(getContext(),
                        Utils.stringToMilliseconds(passenger.getDateOfBirth(), getString(R.string.date_format)),
                        Calendar.getInstance().getTimeInMillis(), 0, getString(R.string.date_format)
                        , new Dialogs.DialogListener2() {
                            @Override
                            public void onItemSelected(long miliSecond, String code) {
                                passenger.setDateOfBirth(Utils.getDate(miliSecond, getString(R.string.date_format)));
                                getTextView(R.id.item_pas_dob, view).setText(passenger.getDateOfBirth());
                            }
                        });
            }
        });
        view.findViewById(R.id.item_pas_docexpiry).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().showCalender(getContext(),
                        Utils.stringToMilliseconds(documentInfo.getExpireDate(), getString(R.string.date_format)),
                        0, 0, getString(R.string.date_format)
                        , new Dialogs.DialogListener2() {
                            @Override
                            public void onItemSelected(long miliSecond, String code) {
                                documentInfo.setExpireDate(Utils.getDate(miliSecond, getString(R.string.date_format)));
                                getTextView(R.id.item_pas_docexpiry, view).setText(documentInfo.getExpireDate());
                            }


                        });
            }
        });
        //Edittext update..
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_fname, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                nameElement.setFirstName(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_lname, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                nameElement.setLastName(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_docno, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                documentInfo.setDocumentNumber(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_docissuein, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                documentInfo.setIssuingCountry(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_email, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                contactInfo.setEmail(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_phone, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                contactInfo.setPhone(text);
            }
        });
        TextWatcherListener.onTextListener(getEditTextView(R.id.item_pas_cityname, view, isEnable), new TextWatcherListener.onTextedChangedListener() {
            @Override
            public void onChanged(String text) {
                contactInfo.setCityName(text);
            }
        });
    }

    private void setupPassengerInfo(int pos, View view, boolean isEnable) {
        PassengerInfo passenger = allPassengers.getPassengerInfoes().get(pos);
        ContactInfo contactInfo = passenger.getContactInfo();
        DocumentInfo documentInfo = passenger.getDocumentInfo();
        NameElement nameElement = passenger.getNameElement();

        getTextView(R.id.item_pas_title, view, isEnable).setText(nameElement.getTitle());
        getEditTextView(R.id.item_pas_fname, view, isEnable).setText(nameElement.getFirstName());

        getEditTextView(R.id.item_pas_lname, view, isEnable).setText(nameElement.getLastName());
        getTextView(R.id.item_pas_header, view).setText(passenger.title.toUpperCase());

        getEditTextView(R.id.item_pas_docno, view, isEnable).setText(passenger.getDocumentInfo().getDocumentNumber());
        getTextView(R.id.item_pas_doctype, view, isEnable).setText(passenger.getDocumentInfo().getDocumentType());
        getEditTextView(R.id.item_pas_docissuein, view, isEnable).setText(passenger.getDocumentInfo().getIssuingCountry());
        getTextView(R.id.item_pas_docexpiry, view, isEnable).setText(passenger.getDocumentInfo().getExpireDate());
        getTextView(R.id.item_pas_docexpiry, view, isEnable).setHint(getTextView(R.id.item_pas_docexpiry, view, isEnable).getHint().toString().toUpperCase());
        getTextView(R.id.item_pas_nationality, view).setText(passenger.getDocumentInfo().getNationality());


        getTextView(R.id.item_pas_dob, view, isEnable).setText(passenger.getDateOfBirth());
        getTextView(R.id.item_pas_dob, view, isEnable).setHint(getTextView(R.id.item_pas_dob, view, isEnable).getHint().toString().toUpperCase());
        getTextView(R.id.item_pas_gender, view, isEnable).setText(passenger.getGender());

        getEditTextView(R.id.item_pas_email, view, isEnable).setText(contactInfo.getEmail());
        getEditTextView(R.id.item_pas_cityname, view, isEnable).setText(contactInfo.getCityName());
        getTextView(R.id.item_pas_contrycode, view, isEnable).setText(contactInfo.getCountryCode());
        getEditTextView(R.id.item_pas_phone, view, isEnable).setText(contactInfo.getPhone());

        getTextView(R.id.item_pas_phnccode, view).setText(contactInfo.getPhoneCountryCode());

        dropDownListview(pos, view, isEnable);
    }

    private TextView getTextView(int id, View view) {
        return view.findViewById(id);
    }

    private TextView getTextView(int id, View view, boolean isEnable) {
        return view.findViewById(id);
    }

    private EditText getEditTextView(int id, View view) {
        return getEditTextView(id, view, false);
    }

    private EditText getEditTextView(int id, View view, boolean isEnable) {

        EditText editText = view.findViewById(id);
        editText.setEnabled(true);

        if (!isEnable) {
        }
        return editText;
    }
}
