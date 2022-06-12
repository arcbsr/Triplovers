package com.arcadio.triplover.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.arcadio.triplover.R;
import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.models.usermodel.LoginReq;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.models.usermodel.UserLoginController;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.CountryToPhonePrefix;
import com.arcadio.triplover.utils.Dialogs;
import com.arcadio.triplover.utils.Enums;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class LoginDialogFragment extends DialogFragment {
    public interface Listener {
        void onLogIn(LoginResponse response);

        void onLoginFailed();
    }

    public interface Listenerv2 {
        void onLogIn(LoginResponse response);

        void onLoginFailed();

        void onLoginDismissed();
    }

    private Listener mCallback;
    private Listenerv2 mCallbackv2;

    public LoginDialogFragment(Listener mCallback) {
        this.mCallback = mCallback;
    }

    public LoginDialogFragment(Listenerv2 mCallbackv2) {
        this.mCallbackv2 = mCallbackv2;
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
                if (mCallbackv2 != null)
                    mCallbackv2.onLoginDismissed();
            }
        });
        toolbar.setTitle(getString(R.string.menu_login));
        maniView.findViewById(R.id.login_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (loginValidate(maniView)) {
                    String mail = ((EditText) maniView.findViewById(R.id.login_email)).getText().toString();
                    String password = ((EditText) maniView.findViewById(R.id.login_password)).getText().toString();
                    boolean isRemember = ((CheckBox) maniView.findViewById(R.id.login_remember)).isChecked();
                    PreferencesHelpers.saveBooleanData(getContext(), "isremember", isRemember);
                    PreferencesHelpers.saveStringData(getContext(), Constants.USER_ID, isRemember ? mail : "");
                    PreferencesHelpers.saveStringData(getContext(), Constants.USER_PASS, isRemember ? password : "");
                    final LoginReq loginReq = new LoginReq(mail, password);
                    new UserLoginController().LoginData(getActivity(), loginReq, new UserLoginController.onLoginListener() {
                        @Override
                        public void onSuccess(LoginResponse response) {
                            if (response.getToken() != null) {
                                PreferencesHelpers.setToken(getContext(), response.getToken());
                                if (mCallback != null)
                                    mCallback.onLogIn(response);
                                if (mCallbackv2 != null)
                                    mCallbackv2.onLogIn(response);
                                dismiss();
                            }
                        }

                        @Override
                        public void onFail(int errorCode) {
                            if (mCallback != null)
                                mCallback.onLoginFailed();
                            if (mCallbackv2 != null)
                                mCallbackv2.onLoginFailed();
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
        searchIDPassLocal(maniView);
        return maniView;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
//        if (mCallbackv2 != null)
//            mCallbackv2.onLoginDismissed();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCallbackv2 != null)
            mCallbackv2.onLoginDismissed();
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
//        String[] country = new String[CountryToPhonePrefix.map.size()];
//        int index = 0;
//        for (String ccode : CountryToPhonePrefix.map.keySet()) {
//            country[index] = CountryToPhonePrefix.prefixFor(ccode) + "(" + ccode + ")";
//            index++;
//        }
//        TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String code = manager.getNetworkCountryIso();
//        code = CountryToPhonePrefix.prefixFor(code.toUpperCase());
//        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, country);
//        AutoCompleteTextView countrycode = mainView.findViewById(R.id.signup_contrycode);
//        countrycode.showDropDown();
//        countrycode.setAdapter(aa);
//        countrycode.setText(code);
        ((TextView) mainView.findViewById(R.id.signup_error)).setText("");
        mainView.findViewById(R.id.signup_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(mainView);
            }
        });
        mainView.findViewById(R.id.signup_contrycode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogs().ShowDialog(getString(R.string.phone_contrycode),
                        getActivity(), Enums.CodeSearchType.PhoneCodes,
                        "", new Dialogs.DialogListener() {
                            @Override
                            public void onItemSelected(String code, int position) {
                            }

                            @Override
                            public void onCountrySelected(CountryToPhonePrefix.CountryDetails countryDetails, int position) {

                            }
                        });
            }
        });
    }

    private void signUp(View mainView) {
        String signup_fname = ((EditText) mainView.findViewById(R.id.signup_fname)).getText().toString();
        String signup_lname = ((EditText) mainView.findViewById(R.id.signup_lname)).getText().toString();
        String signup_email = ((EditText) mainView.findViewById(R.id.signup_email)).getText().toString();
        String signup_password = ((EditText) mainView.findViewById(R.id.signup_password)).getText().toString();
        String signup_repassword = ((EditText) mainView.findViewById(R.id.signup_repassword)).getText().toString();
        String signup_contrycode = ((TextView) mainView.findViewById(R.id.signup_contrycode)).getText().toString();
        String signup_phone = ((EditText) mainView.findViewById(R.id.signup_phone)).getText().toString();
        if (signup_fname.isEmpty()) {
            ((EditText) mainView.findViewById(R.id.signup_fname)).setError(getString(R.string.invalid));
            return;
        }
        if (signup_lname.isEmpty()) {
            ((EditText) mainView.findViewById(R.id.signup_lname)).setError(getString(R.string.invalid));
            return;
        }
        if (signup_email.isEmpty()) {
            ((EditText) mainView.findViewById(R.id.signup_email)).setError(getString(R.string.invalid));
            return;
        }
        if (signup_phone.isEmpty()) {
            ((EditText) mainView.findViewById(R.id.signup_phone)).setError(getString(R.string.invalid));
            return;
        }
        if (signup_password.isEmpty()) {
            ((EditText) mainView.findViewById(R.id.signup_password)).setError(getString(R.string.invalid));
            return;
        }
        if (!signup_password.equalsIgnoreCase(signup_repassword)) {
            ((EditText) mainView.findViewById(R.id.signup_password)).setError(getString(R.string.password_not_match));
            return;
        }
        UserData userData = new UserData();
        userData.setFullName(signup_fname + " " + signup_lname);
        userData.setMobile(signup_contrycode + signup_phone);
        userData.setEmail(signup_email);
        userData.setPassword(signup_password);
        userData.setConfirmPassword(signup_repassword);
        new TAsyntask(getActivity(), new TAsyntask.KAsyncListener() {
            RegisterResult response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                try {
                    TAsyntask.ResponseResult result = TAsyntask.postRequest(Utils.getGson().toJson(userData), Constants.ROOT_URL_REGISTER);
                    if (result.code == 200) {
                        response = Utils.getGson().fromJson(result.result, RegisterResult.class);
                        if (response == null) {
                            response = null;
                        }
                    }
                } catch (Exception e) {
                    response = null;
                }
            }

            @Override
            public void onCompleteListener() {
                if (response != null) {
                    if (response.getIsSuccess()) {
                        PreferencesHelpers.saveStringData(getContext(), Constants.USER_ID, signup_email);
                        PreferencesHelpers.saveStringData(getContext(), Constants.USER_PASS, signup_password);
                        searchIDPassLocal(mainView);
                    } else {
                        if (response.getMessage().equalsIgnoreCase("Already Exists")) {
                            ((TextView) mainView.findViewById(R.id.signup_error)).setText(response.getMessage());
                        }
                    }

                }
            }

            @Override
            public void onErrorListener(String msg) {

            }
        }).execute();
    }

    private void searchIDPassLocal(View maniView) {
        ((EditText) maniView.findViewById(R.id.login_email)).setText(PreferencesHelpers.loadStringData(getContext(), Constants.USER_ID, ""));
        ((EditText) maniView.findViewById(R.id.login_password)).setText(PreferencesHelpers.loadStringData(getContext(), Constants.USER_PASS, ""));

        ((CheckBox) maniView.findViewById(R.id.login_remember)).setChecked(
                PreferencesHelpers.loadBooleanData(getContext(), "isremember", false));
        maniView.findViewById(R.id.login_back).performClick();
    }
}

@Generated("jsonschema2pojo")
class UserData {

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirmPassword")
    @Expose
    private String confirmPassword;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

@Generated("jsonschema2pojo")
class RegisterResult {

    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
