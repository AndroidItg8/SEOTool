package itg8.com.seotoolapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.BaseActivity;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.Prefs;
import itg8.com.seotoolapp.common.SecretGenerator;
import itg8.com.seotoolapp.common.UtilSnackbar;
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.login.mvp.LoginMVP;
import itg8.com.seotoolapp.login.mvp.LoginPresenterImp;
import itg8.com.seotoolapp.passcode.ChangePassCodeActivity;
import me.philio.pinentry.PinEntryView;

import static itg8.com.seotoolapp.common.CommonMethod.getMyPhoneNO;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginMVP.LoginView {


    private static final String TAG = "LoginActivity";
    @BindView(R.id.pin_entry_colors)
    PinEntryView pinEntryColors;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.inputPhoneNumber)
    TextInputLayout inputPhoneNumber;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    LoginMVP.LoginPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new LoginPresenterImp(this);
        checkPermissionFirst();
        fab.setOnClickListener(this);
        pinEntryColors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 3) {
                    validateUser();
                }
            }
        });

    }


    private void checkPermissionFirst() {

        askForPermission(new GrantPhonePermissionListener() {
            @Override
            public void grantedPhonePermission() {
                getPhoneNumber();
            }

            @Override
            public void notGrantedPhonePermission() {
                showSnackbar(getString(R.string.rationale_permission_message));
            }
        });
    }

    private void getPhoneNumber() {
        if (getMyPhoneNO(this) == null) {
            showPhoneNumberEdittext();
        } else {
            verifyPhoneNumberOverServer(getMyPhoneNO(this));
        }
    }

    private void verifyPhoneNumberOverServer(String myPhoneNO) {
        Log.d(TAG, "verifyPhoneNumberOverServer: " + myPhoneNO);
    }

    private void showPhoneNumberEdittext() {

    }


    private void showSnackbar(String msg) {

    }

    private void validateUser() {

    }

    @Override
    public void onClick(View view) {
        presenter.onLoginClicked(view);
//        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public String getUsername() {
        return edtPhoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return pinEntryColors.getText().toString();
    }

    @Override
    public void onSuccess() {
        try {
            SecretKey key = SecretGenerator.generateKey(getString(R.string.app_name));
            Prefs.putString(CommonMethod.SECRET_KEY, key.toString());
            byte[] value = SecretGenerator.encryptMsg(getPassword(), key);
            Prefs.putString(CommonMethod.P_KEY, new String(value, "UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        finish();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onError(Object t) {

    }

    @Override
    public void onUsernameInvalid(String err) {
        inputPhoneNumber.setError(err);
    }

    @Override
    public void onPasswordInvalid(String err) {
        UtilSnackbar.showSnakbarRedColor(inputPhoneNumber, err);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFirstTimeLogin() {

        finish();
        startActivity(new Intent(LoginActivity.this, ChangePassCodeActivity.class));
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);

    }
}
