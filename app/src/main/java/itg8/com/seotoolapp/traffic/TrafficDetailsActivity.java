package itg8.com.seotoolapp.traffic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;

public class TrafficDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
          getWindow().setLayout(width, height);
         }

    private void callFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.frameLayout, TrafficDetailsFragment.newInstance("", ""), TrafficDetailsFragment.class.getSimpleName());
        ft.commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.lbl_date:
openDialogueSelectDateFragment();
               // selectDate();
                break;
        }
    }

    private void openDialogueSelectDateFragment() {
      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction ft =fm.beginTransaction();

       DatePickerFragment pf = new DatePickerFragment();
        pf.show(ft,DatePickerFragment.class.getSimpleName());
    }

    private void selectDate()
    {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        lblDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showMonthPopUp() {

        PopupMenu popup = new PopupMenu(this, lblDate);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_month_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        TrafficDetailsActivity.this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
//                listener.onSelectedMonth( item.getTitle().toString());
                lblDate.setText(" "+item.getTitle().toString());
                return true;
            }
        });

        popup.show(); //showing popup menu

    }

    private void showPopUp() {
        PopupMenu popup = new PopupMenu(this, lblDate);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_year_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                lblDate.setText(" "+item.getTitle().toString());
//                listener.onSelectedYear(item.getTitle().toString());
                return true;
            }
        });

        popup.show(); //showing popup menu
    }

}
