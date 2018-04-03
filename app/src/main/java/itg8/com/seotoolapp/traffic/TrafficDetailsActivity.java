package itg8.com.seotoolapp.traffic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.CommonMethod.TempYearHashMap;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;
import itg8.com.seotoolapp.traffic.model.Trafficmaster;

public class TrafficDetailsActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnItemClickedListener {


    OnItemDateListener listener;
    HashMap<Trafficcategorymaster, List<TrafficModel>> listHashMap;
    List<CommonMethod.WeekList> weeklyData = new ArrayList<>();
    List<List<TrafficModel>> listWeekTraffic = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.ll_data)
    RelativeLayout llData;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};

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
        callFragment();
        setOnClickListener();
        lblDate.setVisibility(View.VISIBLE);
        imgSetting.setVisibility(View.VISIBLE);


    }

    private void checkIntent() {
        if (getIntent().hasExtra(CommonMethod.TRAFFIC_DETAILS)) {
            listHashMap = (HashMap<Trafficcategorymaster, List<TrafficModel>>) getIntent().getSerializableExtra(CommonMethod.TRAFFIC_DETAILS);
            Map.Entry<Trafficcategorymaster, List<TrafficModel>> entry = listHashMap.entrySet().iterator().next();
            Trafficcategorymaster trafficcategorymaster = entry.getKey();
            getSupportActionBar().setTitle(trafficcategorymaster.getTraffic() + " Details");
            lblDate.setText("Current Month: "+CommonMethod.getCurrentDateString());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,1);
            getWeekDataFromMonthData(listHashMap.get(trafficcategorymaster),calendar);


        }

    }

    private void getWeekDataFromMonthData(final List<TrafficModel> listHashMap, final Calendar calendar) {
        Observable.create(new ObservableOnSubscribe<List<TempYearHashMap>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TempYearHashMap>> e) throws Exception {

                weeklyData = CommonMethod.createWeeksFromMonth(calendar);
                List<TempYearHashMap> list = new ArrayList<>();
                for (CommonMethod.WeekList week : weeklyData) {
                    List<TrafficModel> temp = new ArrayList<>();
                    TempYearHashMap tempYearHashMap = new TempYearHashMap();
                    tempYearHashMap.setMonth(week.getDates().get(0));
                    tempYearHashMap.setYear(week.getDates().get(week.getDates().size() - 1));
                    int value = 0;

                    for (String weekDate : week.getDatesStrings()) {
                        for (TrafficModel model : listHashMap) {
                            if (weekDate.equalsIgnoreCase(model.getTrafficmaster().getDateof())) {
                                temp.add(model);
                                value += Integer.valueOf(model.getTrafficmaster().getContof());
                            } else {
                                temp.add(null);
                            }
                        }
                    }
                    tempYearHashMap.setValue(value);
                    list.add(tempYearHashMap);

                    listWeekTraffic.add(temp);
                }


                e.onNext(list);
                e.onComplete();
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TempYearHashMap>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<TempYearHashMap> lists) {
                listener.onTrafficModelList(lists, getSupportActionBar().getTitle());

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

    private void getMonthDataFromYearData(final List<TrafficModel> list, final Integer selectedYear) {
        Observable.create(new ObservableOnSubscribe<List<TempYearHashMap>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TempYearHashMap>> e) throws Exception {
                HashMap<Integer, List<TrafficModel>> tempHashmap = CommonMethod.getMonthHashMapForTraffic();
                Calendar calendar = Calendar.getInstance();
                for (TrafficModel model : list) {
                    long milies = CommonMethod.convertStringToDate(model.getTrafficmaster().getDateof());
                    if (milies > 0) {
                        calendar.setTimeInMillis(milies);

                        tempHashmap.get(calendar.get(Calendar.MONTH)).add(model);
                    }
                }
                List<TempYearHashMap> listTemp = new ArrayList<>();
                for (Map.Entry<Integer, List<TrafficModel>> model : tempHashmap.entrySet()
                        ) {
                    TempYearHashMap tempYearHashMap = new TempYearHashMap();
                    tempYearHashMap.setYear(selectedYear);
                    tempYearHashMap.setMonth(model.getKey() + 1);
                    int value = 0;
                    for (TrafficModel mo :
                            model.getValue()) {

                        value += Integer.parseInt(mo.getTrafficmaster().getContof());
                    }
                    tempYearHashMap.setValue(value);
                    listTemp.add(tempYearHashMap);
                }


                e.onNext(listTemp);
                e.onComplete();
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TempYearHashMap>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<TempYearHashMap> lists) {

                listener.onTrafficYearData(lists, getSupportActionBar().getTitle());
//                listener.onTrafficData(lists, getSupportActionBar().getTitle());


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

    private void getDailyDataFromWeekData(final List<TrafficModel> listHashMap, final CommonMethod.WeekList selectWeek) {
        Observable.create(new ObservableOnSubscribe<List<TrafficModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TrafficModel>> e) throws Exception {


                List<TrafficModel> temp = new ArrayList<>();

                for (String weekDate : selectWeek.getDatesStrings()) {
                    boolean isFound = false;
                    for (TrafficModel model : listHashMap) {
                        if (weekDate.equalsIgnoreCase(model.getTrafficmaster().getDateof())) {
                            temp.add(model);
                            isFound = true;
                        }

//
//                            } else {
//                                temp.add(new TrafficModel(temp.get(temp.size()-1).getTrafficmaster().getDateof()));
//                            }
                    }
                    if (!isFound) {
                        TrafficModel models = new TrafficModel();
                        Trafficmaster trafficmaster = new Trafficmaster();
                        trafficmaster.setDateof(weekDate);
                        models.setTrafficmaster(trafficmaster);
                        Trafficcategorymaster trafficcategorymaster = new Trafficcategorymaster();
                        trafficcategorymaster.setTraffic(getSupportActionBar().getTitle().toString());
                        models.setTrafficcategorymaster(trafficcategorymaster);
                        temp.add(models);

                    }
                }

                e.onNext(temp);
                e.onComplete();


            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TrafficModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<TrafficModel> lists) {
                listener.onTrafficDailyData(lists, getSupportActionBar().getTitle());

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


    private void setOnClickListener() {
        lblDate.setOnClickListener(this);
        llData.setOnClickListener(this);
        imgSetting.setOnClickListener(this);


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

            case R.id.ll_data:
                openDialogueSelectDateFragment();
                break;
            case R.id.lbl_date:
                openDialogueSelectDateFragment();
                break;
            case R.id.img_setting:
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, months);
        calendar.set(Calendar.YEAR, years);
        lblDate.setText(String.valueOf(first) + " - " + String.valueOf(last) +" "+CommonMethod.getMonthFirstDateToString(calendar)  + " "+years );

        calendar.set(Calendar.DAY_OF_MONTH, first);
        String firstDay = CommonMethod.getDateToString(calendar);
        calendar.set(Calendar.MONTH, months);
        calendar.set(Calendar.YEAR, years);
        calendar.set(Calendar.DAY_OF_MONTH, last);
        String lastDay = CommonMethod.getDateToString(calendar);
        downloadDailyTrafficDataFromServer(firstDay, lastDay, selectWeek);


    }

    private void downloadDailyTrafficDataFromServer(String fromDate, String toDate, final CommonMethod.WeekList selectWeek) {
        new NetworkUtility.NetworkBuilder().build().getTrafficCategory(
                getString(R.string.url_traffic_category),
                fromDate,
                toDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getDailyDataFromWeekData((List<TrafficModel>) message, selectWeek);


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


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.YEAR, selectedYear);

        String firstDate = CommonMethod.getMonthDateToString(CommonMethod.getMonthFirstDateToStringFullDate(calendar));
        String lastDate = CommonMethod.getMonthDateToString(CommonMethod.getThisMonthLastFullDate(calendar));


        lblDate.setText("Date: "+CommonMethod.getMonthFirstDateToString(calendar) +" "+ String.valueOf(selectedYear));
        downloadDailyTrafficDataFromServer(firstDate, lastDate, selectedMonth);


    }

    private void downloadDailyTrafficDataFromServer(String fromDate, String toDate, final int selectedMonth) {
        new NetworkUtility.NetworkBuilder().build().getTrafficCategory(
                getString(R.string.url_traffic_category),
                fromDate,
                toDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getDailyDataFromWeekData((List<TrafficModel>) message, selectedMonth);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }

    private void getDailyDataFromWeekData(List<TrafficModel> message, int selectedMonth) {

//        Map.Entry<Trafficcategorymaster, List<TrafficModel>> entry = listHashMap.entrySet().iterator().next();
//        Trafficcategorymaster trafficcategorymaster = entry.getKey();

        if (message instanceof ArrayList) {
            List<TrafficModel> list = (List<TrafficModel>) message;
            if (list.size() > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH,selectedMonth);
                getWeekDataFromMonthData(list, calendar);
                lblDate.setVisibility(View.VISIBLE);
                imgSetting.setVisibility(View.VISIBLE);
            } else {
                listener.onNoItemInList();
                lblDate.setVisibility(View.GONE);
                imgSetting.setVisibility(View.GONE);
            }
        }


    }


    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear) + " " + " Year");
        listener.onItemSelect(selectedYear);
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


        new NetworkUtility.NetworkBuilder().build().getTrafficCategory(
                getString(R.string.url_traffic_category),
                startDate,
                endDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getMonthDataFromYearData((List<TrafficModel>) message, selectedYear);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }

    public void setyearListner(OnItemDateListener yearListner) {
        this.listener = yearListner;
        checkIntent();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public interface OnItemDateListener {
        void onItemSelected(int month, Integer selectedYear);

        void onItemSelected(CommonMethod.WeekList list, int month, Integer selectedYear);

        void onItemSelect(Integer selectedYear);


        void onTrafficModelList(List<TempYearHashMap> lists, CharSequence title);

        void onTrafficDailyData(List<TrafficModel> lists, CharSequence title);

        void onTrafficYearData(List<TempYearHashMap> lists, CharSequence title);

        void onTrafficData(List<TempYearHashMap> lists, CharSequence title);

        void onNoItemInList();

    }


}
