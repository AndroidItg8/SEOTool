package itg8.com.seotoolapp.external_links;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.traffic.DatePickerFragment;

public class ExternalLinksDetailsActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnItemClickedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.ll_data)
    RelativeLayout llData;
    @BindView(R.id.lbl_loc)
    TextView lblLoc;
    @BindView(R.id.lbl_tbl_date)
    TextView lblTblDate;
    @BindView(R.id.tbl_row_first)
    TableRow tblRowFirst;
    @BindView(R.id.ll_tbl)
    LinearLayout llTbl;
    @BindView(R.id.lbl_keyword)
    TextView lblKeyword;
    @BindView(R.id.lbl_dates)
    TextView lblDates;
    @BindView(R.id.lbl_url)
    TextView lblUrl;
    @BindView(R.id.lbl_live_url)
    TextView lblLiveUrl;
    @BindView(R.id.lbl_session)
    TextView lblSession;
    @BindView(R.id.tbl_row_second)
    TableRow tblRowSecond;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


    }

    private void init() {
        createClickedListner();
        checkIntent();
    }

    private void checkIntent() {
        if (getIntent().hasExtra(CommonMethod.EXTERNAL_LINKS_TYPE)) {
            type = getIntent().getIntExtra(CommonMethod.EXTERNAL_LINKS_TYPE, 0);
        }


    }

    private void createClickedListner() {
        llData.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_data:
                openDialogueSelectDateFragment();
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
    public void onItemSelect(int selectedMonth, Integer selectedYear) {
        String month = months[selectedMonth];
        lblDate.setText(month + " M " + String.valueOf(selectedYear) + " " + " Y");
//        downloadWeeklyExternalLinks(selectedMonth, selectedYear);


    }

    @Override
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        Integer first = selectWeek.getDates().get(0);
        Integer last = selectWeek.getDates().get(selectWeek.getDates().size() - 1);
        lblDate.setText(String.valueOf(first) + "-" + String.valueOf(last) + "W " + months + "M " + years + "Y");
        String FirstWeekDate = selectWeek.getDatesStrings().get(0);
        String lastWeekDate = selectWeek.getDatesStrings().get(selectWeek.getDatesStrings().size() - 1);
//        downloadDailyExternalLinks(FirstWeekDate, lastWeekDate, selectWeek);


    }

    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear) + " " + " Year");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);

        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadCurrentMonthDateExternalLinks(startDate,endDate,selectedYear);

    }

    private void downloadCurrentMonthDateExternalLinks(String startDate, String endDate, final Integer selectedYear) {

        new NetworkUtility.NetworkBuilder().build().getExternalLinksData(
                getString(R.string.url_external_links),
                startDate,
                endDate,
                "1",
                this.type,
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getMonthDateFromYearData((List) message, selectedYear);
                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });


    }

    private void getMonthDateFromYearData(List message, Integer selectedYear) {
        if(message instanceof ArrayList){
            List<ExternalLinksModel> modelList = (List<ExternalLinksModel>)message;

            HashMap<String, List<ExternalLinksModel>> hashMap = new HashMap<>();

            Observable.create(new ObservableOnSubscribe<List<CommonMethod.TempYearExternalHashMap>>() {
                @Override
                public void subscribe(ObservableEmitter<List<CommonMethod.TempYearExternalHashMap>> e) throws Exception {

                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CommonMethod.TempYearExternalHashMap>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<CommonMethod.TempYearExternalHashMap> tempYearExternalHashMaps) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });






        }
    }

}
