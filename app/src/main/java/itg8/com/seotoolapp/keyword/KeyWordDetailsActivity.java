package itg8.com.seotoolapp.keyword;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.keyword.model.Keywordstatusmaster;
import itg8.com.seotoolapp.traffic.DatePickerFragment;
import itg8.com.seotoolapp.traffic.FixTableAdapter;
import itg8.com.seotoolapp.traffic.TrafficDetailsActivity;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;
import itg8.com.seotoolapp.widget.fixtablelayout.FixTableLayout;

public class KeyWordDetailsActivity extends AppCompatActivity implements View.OnClickListener ,DatePickerFragment.OnItemClickedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.fixTableLayout)
    FixTableLayout fixTableLayout;
    @BindView(R.id.ll_data)
    RelativeLayout llData;
    public List<Object> data = new ArrayList<>();

    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    private List<KeyWordModel> list;
    private HashMap<String, Keywordstatusmaster> hashMap= new HashMap<>();
    private List<CommonMethod.WeekList> weeklyData;
    private List<List<KeyWordModel>> listWeekKeyword= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_word_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


    }

    private void init() {
        setOnClickedListner();
        if (getIntent().hasExtra(CommonMethod.KEYWORD_DETAILS)) {
         list = getIntent().getParcelableArrayListExtra(CommonMethod.KEYWORD_DETAILS);

        }
    }

    private void setOnClickedListner() {
        lblDate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.lbl_date:
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
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        Integer first = selectWeek.getDates().get(0);
        Integer last = selectWeek.getDates().get(selectWeek.getDates().size() - 1);
        lblDate.setText(String.valueOf(first) + "-" + String.valueOf(last) + "W "+ months +"M "+ years+"Y");
        String FirstWeekDate = selectWeek.getDatesStrings().get(0);
        String lastWeekDate = selectWeek.getDatesStrings().get(selectWeek.getDatesStrings().size() - 1);
        downloadDailyTrafficDataFromServer(FirstWeekDate, lastWeekDate, selectWeek);


    }

    private void downloadDailyTrafficDataFromServer(String fromDate, String toDate, final CommonMethod.WeekList selectWeek) {
        new NetworkUtility.NetworkBuilder().build().getTrafficCategory(
                getString(R.string.url_kewwird),
                fromDate,
                toDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                      //  getDailyDataFromWeekData((List<KeyWordModel>) message, selectWeek);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }


    @Override
    public void onItemSelect(int selectedMonth, Integer selectedYear) {
        String month = months[selectedMonth];
        lblDate.setText(month + " M " + String.valueOf(selectedYear) + " " + " Y");

//        Map.Entry<Trafficcategorymaster, List<TrafficModel>> entry = listHashMap.entrySet().iterator().next();
//        Trafficcategorymaster trafficcategorymaster = entry.getKey();
//        getWeekDataFromMonthData(listHashMap.get(trafficcategorymaster));

        getWeekDataFromMonthData(list);

    }

    private void getWeekDataFromMonthData(final List<KeyWordModel> listHashMap) {
        Observable.create(new ObservableOnSubscribe<List<CommonMethod.TempYearHashMap>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CommonMethod.TempYearHashMap>> e) throws Exception {
                weeklyData = CommonMethod.createWeeksFromMonth(Calendar.getInstance());
                List<CommonMethod.TempYearHashMap> list = new ArrayList<>();
                for (CommonMethod.WeekList week : weeklyData) {
                    List<KeyWordModel> temp = new ArrayList<>();
                    CommonMethod.TempYearHashMap tempYearHashMap = new CommonMethod.TempYearHashMap();
                    tempYearHashMap.setMonth(week.getDates().get(0));
                    tempYearHashMap.setYear(week.getDates().get(week.getDates().size() - 1));
                    int value = 0;

                    for (String weekDate : week.getDatesStrings()) {
                        for (KeyWordModel model : listHashMap) {
                            if (weekDate.equalsIgnoreCase(model.getKeywordstatusmaster().getDateof())) {
                                temp.add(model);
                                value += Integer.valueOf(model.getKeywordstatusmaster().getRank());
                            } else {
                                temp.add(null);
                            }
                        }
                    }
                    tempYearHashMap.setValue(value);
                    list.add(tempYearHashMap);

                    listWeekKeyword.add(temp);
                }


                e.onNext(list);
                e.onComplete();
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CommonMethod.TempYearHashMap>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<CommonMethod.TempYearHashMap> lists) {

//                listener.onTrafficModelList(lists, getSupportActionBar().getTitle());

                data.clear();
                data.addAll(lists);
                List<Object> list = new ArrayList<>();
                list.addAll(lists);
                setTableAdapter();

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });


    }



    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear) + " " + " Year");
        getCurrentMonthDateDownload(selectedYear);

    }

    private void getCurrentMonthDateDownload(Integer selectedYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadYearTrafficDetails(startDate, endDate, selectedYear);


    }

    private void downloadYearTrafficDetails(String startDate, String endDate, final Integer selectedYear) {
        new NetworkUtility.NetworkBuilder().build().getkeyWordList(
                getString(R.string.url_kewwird),
                startDate,
                endDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getMonthDataFromYearData((List) message, selectedYear);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });



    }

    private void getMonthDataFromYearData(List<KeyWordModel> message, Integer selectedYear) {
        data.clear();
        data.addAll(message);
        List<Object> list = new ArrayList<>();
        list.addAll(message);
        setTableAdapter();
    }



    private void setTableAdapter() {

        String[] title = new String[]{"Date", "keyword","PageNo","Rank"};
//        dates[0] = "Title";
        FixTableAdapter fixTableAdapter = new FixTableAdapter(title, data);
        fixTableLayout.setAdapter(fixTableAdapter);
    }


}
