package com.arcadio.triplover.fragments;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.arcadio.triplover.R;
import com.arcadio.triplover.models.usermodel.LoginReq;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.models.usermodel.UserLoginController;
import com.arcadio.triplover.utils.CountryToPhonePrefix;

public class LoginDialogFragment extends DialogFragment {
    public interface Listener {
        void onLogIn(LoginResponse response);

        void onLoginFailed();
    }

    private Listener mCallback;

    public LoginDialogFragment(Listener mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View maniView = inflater.inflate(R.layout.fragment_login, container, false);
        toolbar = maniView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        toolbar.setTitle(getString(R.string.menu_login));
        maniView.findViewById(R.id.login_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (loginValidate(maniView)) {
                    String mail = ((EditText) maniView.findViewById(R.id.login_email)).getText().toString();
                    String password = ((EditText) maniView.findViewById(R.id.login_password)).getText().toString();
                    final LoginReq loginReq = new LoginReq(mail, password);
                    new UserLoginController().LoginData(getActivity(), loginReq, new UserLoginController.onLoginListener() {
                        @Override
                        public void onSuccess(LoginResponse response) {

                            mCallback.onLogIn(response);
                            dismiss();
                        }

                        @Override
                        public void onFail(int errorCode) {
                            mCallback.onLoginFailed();
                        }
                    });
                }
            }
        });
        maniView.findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maniView.findViewById(R.id.layout_login).setVisibility(View.GONE);
                maniView.findViewById(R.id.layout_signup).setVisibility(View.VISIBLE);
            }
        });
        maniView.findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maniView.findViewById(R.id.layout_login).setVisibility(View.VISIBLE);
                maniView.findViewById(R.id.layout_signup).setVisibility(View.GONE);
            }
        });
        maniView.findViewById(R.id.layout_login).setVisibility(View.VISIBLE);
        maniView.findViewById(R.id.layout_signup).setVisibility(View.GONE);
        forSignUP(maniView);
        return maniView;
    }

    private boolean loginValidate(View mainView) {
        boolean isValide = true;
        if (((EditText) mainView.findViewById(R.id.login_email)).getText().toString().trim().isEmpty()) {
            ((EditText) mainView.findViewById(R.id.login_email)).setError(getString(R.string.invalid));
            isValide = false;
        }
        if (((EditText) mainView.findViewById(R.id.login_password)).getText().toString().trim().isEmpty()) {
            ((EditText) mainView.findViewById(R.id.login_password)).setError(getString(R.string.invalid));
            isValide = false;
        }
        return isValide;
    }

    private void forSignUP(View mainView) {
        String[] country = new String[CountryToPhonePrefix.map.size()];
        int index = 0;
        for (String ccode : CountryToPhonePrefix.map.keySet()) {
            country[index] = CountryToPhonePrefix.prefixFor(ccode) + "(" + ccode + ")";
            index++;
        }
        TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String code = manager.getNetworkCountryIso();
        code = CountryToPhonePrefix.prefixFor(code.toUpperCase());
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, country);
        AutoCompleteTextView countrycode = mainView.findViewById(R.id.signup_contrycode);
        countrycode.showDropDown();
        countrycode.setAdapter(aa);
        countrycode.setText(code);

    }

}
