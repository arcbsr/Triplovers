package com.arcadio.triplover.acitivies;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.adapter.BasicAdapter;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.models.passenger.request.ContactInfo;
import com.arcadio.triplover.models.passenger.request.DocumentInfo;
import com.arcadio.triplover.models.passenger.request.NameElement;
import com.arcadio.triplover.models.passenger.request.PassengerInfo;
import com.arcadio.triplover.models.passenger.request.PassengerReq;
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

        findViewById(R.id.pas_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginDialogFragment(new LoginDialogFragment.Listener() {
                    @Override
                    public void onLogIn(LoginResponse response) {
                        KLog.w(response == null ? "Error in data" : getGson().toJson(response));
                        gatherInfo(response.getToken());
                    }

                    @Override
                    public void onLoginFailed() {
                        new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                .setTitle("Falied")
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
        });
        gatherInfo(PreferencesHelpers.loadStringData(this, Constants.USER_TOKEN, ""));
    }

    private void gatherInfo(String token) {
        final RePriceReq priceReq = (RePriceReq) getIntent().getSerializableExtra(Constants.PASS_REPRICE_REAUEST);
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
                response =
                        TAsyntask.postRequestHeader(getGson().toJson(priceReq), Constants.ROOT_REPRICE_REQ,
                                token);

            }

            @Override
            public void onCompleteListener() {
                if (response == null) {
                    finish();
                    return;
                } else {
                    if (response.code == 200) {
                        RePriceResponse priceResponse = getGson().fromJson(response.result, RePriceResponse.class);
                        if (priceResponse != null && priceResponse.getItem1() != null) {
                            setUpData(priceResponse);
                        } else {
                            finish();
                            return;
                        }
                    } else if (response.code == 401) {
                        new LoginDialogFragment(new LoginDialogFragment.Listener() {
                            @Override
                            public void onLogIn(LoginResponse response) {
                                gatherInfo(response.getToken());
                            }

                            @Override
                            public void onLoginFailed() {
                                new MaterialAlertDialogBuilder(PassengerEntryActivity.this)
                                        .setTitle("Falied")
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

    private void setUpData(RePriceResponse response) {
        String countryCode = CountryToPhonePrefix.getLocalCode(getActivity());
        PassengerCounts passengerCounts = response.getItem1().getPassengerCounts();
        allPassengers = new PassengerReq();
        allPassengers.setItemCodeRef(response.getItem1().getItemCodeRef());
        allPassengers.setPriceCodeRef(response.getItem1().getPriceCodeRef());
        allPassengers.setUniqueTransID(response.getItem1().getUniqueTransID());
        initPassengerInfo(passengerCounts.getAdt(), Enums.UserAgeType.ADULT, countryCode);
        initPassengerInfo(passengerCounts.getCnn(), Enums.UserAgeType.CHILD, countryCode);
        initPassengerInfo(passengerCounts.getInf(), Enums.UserAgeType.INFANT, countryCode);

//        String countryCode = CountryToPhonePrefix.SearchCountryCodeBuilder
//                (CountryToPhonePrefix.getLocalCode(getActivity()), Enums.CodeSearchType.CountryCodes);
//
//        String phoneCode = CountryToPhonePrefix.SearchCountryCodeBuilder
//                (CountryToPhonePrefix.getLocalCode(getActivity()), Enums.CodeSearchType.PhoneCodes);
//        for (int adult = 0; adult < passengerCounts.getAdt(); adult++) {
//            PassengerInfo passengerInfo = new PassengerInfo();
//            passengerInfo.title = "Adult# " + (adult + 1);
//            passengerInfo.setPassengerType("ADT");
//            passengerInfo.getContactInfo().setCountryCode(countryCode);
//            passengerInfo.getContactInfo().setPhoneCountryCode(phoneCode);
//            allPassengers.addPassengerInfoes(passengerInfo);
//        }
//        for (int child = 0; child < passengerCounts.getCnn(); child++) {
//            PassengerInfo passengerInfo = new PassengerInfo();
//            passengerInfo.title = "Child# " + (child + 1);
//            passengerInfo.setPassengerType("CNN");
//            passengerInfo.getContactInfo().setCountryCode(countryCode);
//            passengerInfo.getContactInfo().setPhoneCountryCode(phoneCode);
//            allPassengers.addPassengerInfoes(passengerInfo);
//        }
//        for (int infant = 0; infant < passengerCounts.getInf(); infant++) {
//            PassengerInfo passengerInfo = new PassengerInfo();
//            passengerInfo.title = "Infant# " + (infant + 1);
//            passengerInfo.setPassengerType("INF");
//            passengerInfo.getContactInfo().setCountryCode(countryCode);
//            passengerInfo.getContactInfo().setPhoneCountryCode(phoneCode);
//            allPassengers.addPassengerInfoes(passengerInfo);
//        }
//        for (int ins = 0; ins < passengerCounts.getIns(); ins++) {
//            PassengerInfo passengerInfo = new PassengerInfo();
//            passengerInfo.title = "Ins# " + (ins + 1);
//            passengerInfo.setPassengerType("INS");
//            passengerInfo.getContactInfo().setCountryCode(countryCode);
//            passengerInfo.getContactInfo().setPhoneCountryCode(phoneCode);
//            allPassengers.addPassengerInfoes(passengerInfo);
//        }
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
                int pos = holder.getAdapterPosition();
                PassengerInfo passenger = allPassengers.getPassengerInfoes().get(pos);
                setupPassengerInfo(pos, holder.itemView, false);
                holder.itemView.findViewById(R.id.item_pas_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passengerEditForm(pos);
                    }
                });
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
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
                bookingTicket(response);
            }
        });
    }

    private void bookingTicket(RePriceResponse response) {
        KLog.w(getGson().toJson(allPassengers));
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
        view.findViewById(R.id.item_pas_nationality).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().ShowDialog(getString(R.string.nationality),
                        getActivity(), Enums.CodeSearchType.CountryCodes,
                        documentInfo.getNationality(), new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code) {
                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails) {

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
                    public void onItemSelected(String code) {
                        passenger.setGender(code);
                        getTextView(R.id.item_pas_gender, view, isEnable).setText(passenger.getGender());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

                    }
                }, passenger.getGender(), getResources().getStringArray(R.array.gender));
            }
        });
        view.findViewById(R.id.item_pas_title).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                new Dialogs().ShowDialogGender(getString(R.string.gender), getActivity(), new Dialogs.DialogListener() {
                    @Override
                    public void onItemSelected(String code) {
                        passenger.getNameElement().setTitle(code);
                        getTextView(R.id.item_pas_title, view, isEnable).setText(passenger.getNameElement().getTitle());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

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
                            public void onItemSelected(String code) {

                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails) {
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
                    public void onItemSelected(String code) {
                        documentInfo.setDocumentType(code);
                        getTextView(R.id.item_pas_doctype, view, isEnable).setText(documentInfo.getDocumentType());
                    }

                    @Override
                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

                    }
                }, documentInfo.getDocumentType(), getResources().getStringArray(R.array.doc_type));
            }
        });
        view.findViewById(R.id.item_pas_dob).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().showCalender(getContext(), pos,
                        Utils.stringToMilliseconds(passenger.getDateOfBirth(), Utils.DATE_FORMAT)
                        , new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code) {
                                passenger.setDateOfBirth(code);
                                getTextView(R.id.item_pas_dob, view).setText(passenger.getDateOfBirth());
                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

                            }
                        });
            }
        });
        view.findViewById(R.id.item_pas_docexpiry).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                new Dialogs().showCalender(getContext(), pos,
                        Utils.stringToMilliseconds(documentInfo.getExpireDate(), Utils.DATE_FORMAT)
                        , new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code) {
                                documentInfo.setExpireDate(code);
                                getTextView(R.id.item_pas_docexpiry, view).setText(documentInfo.getExpireDate());
                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {

                            }
                        });
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
        getTextView(R.id.item_pas_header, view).setText(passenger.title);

        getEditTextView(R.id.item_pas_docno, view, isEnable).setText(passenger.getDocumentInfo().getDocumentNumber());
        getTextView(R.id.item_pas_doctype, view, isEnable).setText(passenger.getDocumentInfo().getDocumentType());
        getEditTextView(R.id.item_pas_docissuein, view, isEnable).setText(passenger.getDocumentInfo().getIssuingCountry());
        getTextView(R.id.item_pas_docexpiry, view, isEnable).setText(passenger.getDocumentInfo().getExpireDate());
        //getEditTextView(R.id.item_pas_nationality, view, isEnable).setText(passenger.getDocumentInfo().getNationality());
        getTextView(R.id.item_pas_nationality, view).setText(passenger.getDocumentInfo().getNationality());
//        view.findViewById(R.id.item_pas_nationality).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
//            @Override
//            public void onClick(View view2) {
//
//                new Dialogs().ShowDialog(getString(R.string.nationality),
//                        getActivity(), Enums.CodeSearchType.CountryCodes,
//                        CountryToPhonePrefix.getLocalCode(getActivity()), new Dialogs.DialogListener() {
//                            @Override
//                            public void onItemSelected(String code) {
//                            }
//
//                            @Override
//                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails) {
//
//                                documentInfo.setNationality(countryDetails.countryCode);
//                                getTextView(R.id.item_pas_nationality, view).setText(passenger.getDocumentInfo().getNationality());
//                            }
//                        });
//            }
//        });

        getTextView(R.id.item_pas_dob, view, isEnable).setText(passenger.getDateOfBirth());
        getTextView(R.id.item_pas_gender, view, isEnable).setText(passenger.getGender());
//        view.findViewById(R.id.item_pas_gender).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view2) {
//                new Dialogs().ShowDialogGender(getActivity(), new Dialogs.DialogListener() {
//                    @Override
//                    public void onItemSelected(String code) {
//                        passenger.setGender(code);
//                        getTextView(R.id.item_pas_gender, view, isEnable).setText(passenger.getGender());
//                    }
//
//                    @Override
//                    public void onCountrySelected(CountryToPhonePrefix.CountryDetails code) {
//
//                    }
//                });
//            }
//        });
        getEditTextView(R.id.item_pas_email, view, isEnable).setText(contactInfo.getEmail());
        getEditTextView(R.id.item_pas_cityname, view, isEnable).setText(contactInfo.getCityName());
        getTextView(R.id.item_pas_contrycode, view, isEnable).setText(contactInfo.getCountryCode());
        getEditTextView(R.id.item_pas_phone, view, isEnable).setText(contactInfo.getPhone());
//        getEditTextView(R.id.item_pas_phnccode, view, isEnable).setText(contactInfo.getPhoneCountryCode());

        getTextView(R.id.item_pas_phnccode, view).setText(contactInfo.getPhoneCountryCode());
//        view.findViewById(R.id.item_pas_phnccode).setOnClickListener(!isEnable ? null : new View.OnClickListener() {
//            @Override
//            public void onClick(View view2) {
//
//                new Dialogs().ShowDialog(getString(R.string.phone_contrycode),
//                        getActivity(), Enums.CodeSearchType.PhoneCodes,
//                        CountryToPhonePrefix.getLocalCode(getActivity()), new Dialogs.DialogListener() {
//                            @Override
//                            public void onItemSelected(String code) {
//
//                            }
//
//                            @Override
//                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails) {
//                                contactInfo.setPhoneCountryCode(countryDetails.phoneCode);
//                                contactInfo.setCountryCode(countryDetails.countryCode);
//                                getTextView(R.id.item_pas_phnccode, view).setText(contactInfo.getPhoneCountryCode());
//                                getTextView(R.id.item_pas_contrycode, view).setText(contactInfo.getCountryCode());
//                            }
//                        });
//            }
//        });
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
        editText.setEnabled(isEnable);

        if (!isEnable) {
//            editText.setInputType(InputType.TYPE_NULL);
//            editText.setKeyListener(null);
//            editText.setCursorVisible(false);
//            editText.setFocusable(false);
//            editText.setClickable(false);
//            editText.setOnClickListener(null);
//            editText.setFocusableInTouchMode(false);
        }
        return editText;
    }
}