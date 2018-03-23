package itg8.com.seotoolapp.traffic;

import android.graphics.YuvImage;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.time.Year;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;

public class TrafficDetailsActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnItemClickedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    OnItemDateListener listener;
    private String[]    months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();


    }

    private void init() {
        setOnClickListener();
        callFragment();
    }

    private void setOnClickListener() {
        lblDate.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void callFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.frameLayout, TrafficDetailsFragment.newInstance("", ""), TrafficDetailsFragment.class.getSimpleName());
        ft.commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbl_date:
                openDialogueSelectDateFragment();
                // selectDate();
                break;
        }
    }

    private void openDialogueSelectDateFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        DatePickerFragment pf = new DatePickerFragment();
        pf.show(ft, DatePickerFragment.class.getSimpleName());
    }


    @Override
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        lblDate.setText(String.valueOf(selectWeek) +"Week");
        listener.onItemSelected(selectWeek, months, years);

    }

    @Override
    public void onItemSelect(int selectedMonth, Integer selectedYear) {
        getMonthFromIndex(selectedMonth);
        lblDate.setText(String.valueOf(selectedMonth) +"M "+ String.valueOf(selectedYear)+" "+" Y");
        listener.onItemSelected(selectedMonth, selectedYear);

    }

    private void getMonthFromIndex(int selectedMonth) {
        Arrays.asList(months).indexOf(selectedMonth);

    }

    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear)+" "+" Year");
        listener.onItemSelect(selectedYear);

    }

    public void setyearListner(OnItemDateListener yearListner) {
        this.listener = yearListner;
    }

    public interface OnItemDateListener {
        void onItemSelected(int month, Integer selectedYear);

        void onItemSelected(CommonMethod.WeekList list, int month, Integer selectedYear);

        void onItemSelect(Integer selectedYear);
    }


}
