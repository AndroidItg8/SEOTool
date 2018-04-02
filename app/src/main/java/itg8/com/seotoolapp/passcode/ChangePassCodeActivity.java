package itg8.com.seotoolapp.passcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.common.Prefs;
import itg8.com.seotoolapp.common.UtilSnackbar;
import itg8.com.seotoolapp.home.HomeActivity;
import me.philio.pinentry.PinEntryView;

public class ChangePassCodeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pin_entry_colors)
    PinEntryView pinEntryColors;
    @BindView(R.id.pin_entry_confirm)
    PinEntryView pinEntryConfirm;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_code);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();


    }

    private void init() {

        fab.setOnClickListener(this);
    }

    private boolean validateUser() {
        boolean isValid = true;
        if (pinEntryColors.getText().length() != 4) {
            isValid = false;
            UtilSnackbar.showSnakbarRedColor(fab, getString(R.string.invalid_pass));
        }
        if (pinEntryConfirm.getText().length() != 4) {
            isValid = false;
            UtilSnackbar.showSnakbarRedColor(fab, getString(R.string.invalid_pass));
        }
        return isValid;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            progressbar.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            boolean isTrue = validateUser();
            if (isTrue) {
                if (pinEntryColors.getText().toString().equals(pinEntryConfirm.getText().toString())) {
                    changePasscodeToServer();
                } else {
                    progressbar.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);

                    Toast.makeText(this, getString(R.string.pass_not_match), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void changePasscodeToServer() {

        new NetworkUtility.NetworkBuilder().build().changePasscode(getString(R.string.url_change_passcode),
                Prefs.getString(CommonMethod.USER_ID), pinEntryColors.getText().toString(), pinEntryConfirm.getText().toString(),
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        progressbar.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);

                        finish();
                        startActivity(new Intent(ChangePassCodeActivity.this, HomeActivity.class));
//                        UtilSnackbar.showSnakbarRedColor(fab, message.toString());


                    }

                    @Override
                    public void onFailure(Object err) {
                        progressbar.setVisibility(View.GONE);

                        UtilSnackbar.showSnakbarRedColor(fab, err.toString());


                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }
}
