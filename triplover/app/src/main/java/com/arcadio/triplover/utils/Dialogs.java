package com.arcadio.triplover.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;

import com.arcadio.triplover.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

public class Dialogs {


    public interface DialogListener {
        void onItemSelected(String code, int position);

        void onCountrySelected(CountryToPhonePrefix.CountryDetails code, int position);
    }

    public interface DialogListener2 {
        void onItemSelected(long miliSecond, String code);

    }

    public void ShowDialog(Activity activity, Enums.CodeSearchType codeSearchType, String defaultCode, DialogListener listener) {
        ShowDialog("", activity, codeSearchType, defaultCode, listener);
    }

    public void ShowDialog(String title, Activity activity, Enums.CodeSearchType codeSearchType, String defaultCode, DialogListener listener) {

        LinkedHashMap<String, CountryToPhonePrefix.CountryDetails> countries =
                CountryToPhonePrefix.CountryCodesBuilder(defaultCode);

        ArrayList<CountryToPhonePrefix.CountryDetails> allCountries = new ArrayList<>();

        String[] dataList = new String[countries.size()];
        //CountryToPhonePrefix.CountryDetails[] dataListCode = new CountryToPhonePrefix.CountryDetails[countries.size()];
        int index = 0;
        AtomicInteger selected = new AtomicInteger(-1);
        for (String code : countries.keySet()) {
            if (countries.get(code).isSelected) {
                selected.set(index);
            }
            allCountries.add(countries.get(code));
            switch (codeSearchType) {
                case Countries:
                    if (countries.get(code).countryName.equalsIgnoreCase(defaultCode)) {
                        selected.set(index);
                    }
                    dataList[index] = countries.get(code).countryName + " (" + countries.get(code).countryCode + ")";
                    //dataListCode[index] = countries.get(code);
                    break;
                case PhoneCodes:
                    if (countries.get(code).phoneCode.equalsIgnoreCase(defaultCode)) {
                        selected.set(index);
                    }
                    dataList[index] = countries.get(code).countryName + " (" + countries.get(code).phoneCode + ")";
                    //dataListCode[index] = countries.get(code);
                    break;
                case CountryCodes:
                    if (countries.get(code).countryCode.equalsIgnoreCase(defaultCode)) {
                        selected.set(index);
                    }
                    dataList[index] = countries.get(code).countryName + " (" + countries.get(code).countryCode + ")";
                    //dataListCode[index] = countries.get(code);
                    break;
            }
            index++;
        }
        new MaterialAlertDialogBuilder(activity).setTitle(title)
                .setNeutralButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
//                .setPositiveButton(activity.getString(R.string.done), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (selected.get() == -1) {
//                            dialogInterface.dismiss();
//                            return;
//                        }
//                        listener.onCountrySelected(allCountries.get(selected.get()));
//                        dialogInterface.dismiss();
//                    }
//                })
                .setSingleChoiceItems(dataList, selected.get(), (dialogInterface, i) -> {
                    selected.set(i);
                    if (selected.get() == -1) {
                        dialogInterface.dismiss();
                        return;
                    }
                    listener.onCountrySelected(allCountries.get(i), i);
                    dialogInterface.dismiss();
                }).show();
    }

    public void ShowDialogGender(String title, Activity activity, DialogListener listener, String defdam, String[] dataList) {

        ShowDialogGender(title, activity, listener, defdam, dataList, false);
    }

    public void ShowDialogGender(String title, Activity activity, DialogListener listener, String defdam, String[] dataList, boolean forceShow) {
        if (!forceShow) {
            if (dataList.length < 2) {
                KLog.w("Nodata for list");
                return;
            }
        }
        if (defdam == null) {
            defdam = "";
        }
        int selectedIndex = -1;
        for (int i = 0; i < dataList.length; i++) {
            if (defdam.equalsIgnoreCase(dataList[i])) {
                selectedIndex = i;
            }
        }
        new MaterialAlertDialogBuilder(activity).setTitle(title)
                .setNegativeButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })

                .setSingleChoiceItems(dataList, selectedIndex, (dialogInterface, i) -> {
                    listener.onItemSelected(dataList[i], i);
                    dialogInterface.dismiss();
                }).show();
    }

    public void showCalender(Context context, long defTimeMili, long maxTimeMili, long minTimeMili, String dateFormat, DialogListener2 listener) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(defTimeMili);
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(i, i1, i2);
                listener.onItemSelected(calendar2.getTimeInMillis(), Utils.getDateString(calendar2.getTime(), dateFormat));
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (maxTimeMili != 0)
            dialog.getDatePicker().setMaxDate(maxTimeMili);
        if (minTimeMili != 0)
            dialog.getDatePicker().setMinDate(minTimeMili);
        dialog.show();
    }
}
