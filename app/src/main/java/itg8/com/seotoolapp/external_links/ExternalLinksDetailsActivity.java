package itg8.com.seotoolapp.external_links;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
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
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.external_links.model.Liveurlmaster;
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
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    private int type;
    private TextView tv;
    private boolean isShowBackground = true;
    private List<CommonMethod.WeekList> weeklyData;
    private TableRow tr;
    private boolean isFirstTime = false;


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
        downloadCurrentYearData();
    }

    private void downloadCurrentYearData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadCurrentMonthDateExternalLinks(startDate, endDate, year);
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, selectedMonth);
        lblDate.setText(CommonMethod.getMonthFirstDateToString(calendar) + " M " + String.valueOf(selectedYear) + " " + " Y");
        downloadWeeklyExternalLinks(selectedMonth, selectedYear);


    }

    private void downloadWeeklyExternalLinks(int selectedMonth, Integer selectedYear) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadWeekExternalLinksData(startDate, endDate);

    }

    private void downloadWeekExternalLinksData(String startDate, String endDate) {

        new NetworkUtility.NetworkBuilder().build().getExternalLinksData(
                getString(R.string.url_external_links),
                startDate,
                endDate,
                "1",
                this.type,
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getWeekDataFromMonthExternalLinks(message);
                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });

    }

    private void getWeekDataFromMonthExternalLinks(Object message) {
        if (message instanceof ArrayList) {
            final List<ExternalLinksModel> listExtnalLinksWeek = (List<ExternalLinksModel>) message;

            Observable.create(new ObservableOnSubscribe<List<CommonMethod.TempYearExternalHashMap>>() {
                @Override
                public void subscribe(ObservableEmitter<List<CommonMethod.TempYearExternalHashMap>> e) throws Exception {
                    weeklyData = CommonMethod.createWeeksFromMonth(Calendar.getInstance());
                    List<CommonMethod.TempYearExternalHashMap> list = new ArrayList<>();
                    for (CommonMethod.WeekList week : weeklyData) {
//                        List<ExternalLinksModel> temp = new ArrayList<>();
                        CommonMethod.TempYearExternalHashMap tempYearHashMap = new CommonMethod.TempYearExternalHashMap();
                        tempYearHashMap.setMonth(week.getDates().get(0));
                        tempYearHashMap.setYear(week.getDates().get(week.getDates().size() - 1));
                        int value = 0;

                        for (String weekDate : week.getDatesStrings()) {
                            for (ExternalLinksModel model : listExtnalLinksWeek) {
                                if (weekDate.equalsIgnoreCase(model.getExlinkmaster().getDateof())) {
//                                    temp.add(model);

                                    for (Liveurlmaster master : model.getLiveurlmaster()) {
                                        value += Integer.valueOf(master.getSession());
                                        tempYearHashMap.setLiveUrl(master.getLiveurl());
                                        tempYearHashMap.setStatus(master.getStatus());
                                    }
                                    tempYearHashMap.setKeyWord(model.getExlinkmaster().getTitle());
                                    tempYearHashMap.setDate(weekDate);
                                    tempYearHashMap.setValue(value);
                                    list.add(tempYearHashMap);
                                }
                            }

                        }
                    }
                    e.onNext(list);
                    e.onComplete();
                }

            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CommonMethod.TempYearExternalHashMap>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<CommonMethod.TempYearExternalHashMap> lists) {
                    createRowTableWeek(lists);

//

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
    }

    private void createRowTableWeek(List<CommonMethod.TempYearExternalHashMap> lists) {


        cleanTable(tableLayout);
        tableLayout.addView(tblRowFirst);
        tableLayout.addView(llTbl);
        tableLayout.addView(tblRowSecond);



        for (CommonMethod.TempYearExternalHashMap tempModel : lists) {
            tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_urlstatus, null);

            // Fill out our cells
            tv = (TextView) tr.findViewById(R.id.lbl_keyword);
            tv.setText(tempModel.getKeyWord());
            tv.setPadding(4, 0, 4, 0);
            tv = (TextView) tr.findViewById(R.id.lbl_dates);
            tv.setText(tempModel.getMonth() + "/" + tempModel.getYear());

            tv = (TextView) tr.findViewById(R.id.lbl_url);
            tv.setText(tempModel.getLiveUrl());

            tv = (TextView) tr.findViewById(R.id.lbl_live_status);
            tv.setText(tempModel.getStatus());


            tv = (TextView) tr.findViewById(R.id.lbl_session);
            tv.setText(String.valueOf(tempModel.getValue()));


            if (isShowBackground)
                tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorOrangeTransparent));
            else
                tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorBlueTransparent));


            // Draw separator
            tv = new TextView(ExternalLinksDetailsActivity.this);
            tv.setHeight(CommonMethod.dpTopx(2, ExternalLinksDetailsActivity.this));

            tv.setBackgroundColor(Color.parseColor("#80808080"));


            tableLayout.addView(tr);
            tableLayout.addView(tv);
            registerForContextMenu(tr);
            isShowBackground = !isShowBackground;

        }
    }

    @Override
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        Integer first = selectWeek.getDates().get(0);
        Integer last = selectWeek.getDates().get(selectWeek.getDates().size() - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, months);
        lblDate.setText(String.valueOf(first) + "-" + String.valueOf(last)+ CommonMethod.getMonthFirstDateToString(calendar)+years );
        String FirstWeekDate = selectWeek.getDatesStrings().get(0);
        String lastWeekDate = selectWeek.getDatesStrings().get(selectWeek.getDatesStrings().size() - 1);
        downloadDailyExternalLinks(FirstWeekDate, lastWeekDate, selectWeek);
 }

    private void downloadDailyExternalLinks(String firstWeekDate, String lastWeekDate, final CommonMethod.WeekList selectWeek) {
        new NetworkUtility.NetworkBuilder().build().getExternalLinksData(
                getString(R.string.url_external_links),
                firstWeekDate,
                lastWeekDate,
                "1",
                this.type,
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getDiallyDateFromWeekData((List) message, selectWeek);
                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });

    }

    private void getDiallyDateFromWeekData(List message, CommonMethod.WeekList selectWeek) {

        cleanTable(tableLayout);
        tableLayout.addView(tblRowFirst);
        tableLayout.addView(tblRowSecond);
        if (message instanceof ArrayList) {
            List<ExternalLinksModel> linksList = (List<ExternalLinksModel>) message;

            for (ExternalLinksModel mo : linksList) {
                tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_urlstatus, null);
                tv = (TextView) tr.findViewById(R.id.lbl_dates);
                TableRow.LayoutParams paramss = (TableRow.LayoutParams) tv.getLayoutParams();
                paramss.gravity = Gravity.CENTER;
//                paramss.span= mo.getLiveurlmaster().size();
                tv.setPadding(4, 4, 4, 4);
                tv.setText(mo.getExlinkmaster().getDateof());
                for (Liveurlmaster master : mo.getLiveurlmaster()) {
                    if (isFirstTime) {
                        TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_url_post_status, null);
                        TextView tv = (TextView) tr.findViewById(R.id.lbl_keyword);
                        TableRow.LayoutParams params = (TableRow.LayoutParams) tv.getLayoutParams();

                         params.column = 1;

                        tv.setText(mo.getExlinkmaster().getTitle());
                        tv.setPadding(4, 4, 4, 4);

                        tv = (TextView) tr.findViewById(R.id.lbl_url);

                        tv.setPadding(4, 4, 4, 4);
                        tv.setText(master.getLiveurl());
                        tv = (TextView) tr.findViewById(R.id.lbl_live_status);
                        tv.setPadding(4, 4, 4, 4);

                        tv.setText(master.getStatus());
                        tv = (TextView) tr.findViewById(R.id.lbl_session);
                        tv.setPadding(4, 4, 4, 4);

                        tv.setText(String.valueOf(master.getSession()));

                        if (isShowBackground)
                            tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorGrayLights));
                        else
                            tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorBlueTransparent));


                        tableLayout.addView(tr);
                        registerForContextMenu(tr);
                        isShowBackground = !isShowBackground;


                    } else {
                        tv = (TextView) tr.findViewById(R.id.lbl_keyword);
                        tv.setPadding(4, 4, 4, 4);
                        tv.setText(mo.getExlinkmaster().getTitle());
                        tv = (TextView) tr.findViewById(R.id.lbl_url);
                        tv.setPadding(4, 4, 4, 4);
                        tv.setText(master.getLiveurl());
                        tv = (TextView) tr.findViewById(R.id.lbl_live_status);
                        tv.setPadding(4, 4, 4, 4);
                        tv.setText(master.getStatus());
                        tv = (TextView) tr.findViewById(R.id.lbl_session);
                        tv.setPadding(4, 4, 4, 4);
                        tv.setText(String.valueOf(master.getSession()));

                        if (isShowBackground)
                            tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorOrangeTransparent));
                        else
                            tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorBlueTransparent));



                        tableLayout.addView(tr);
                        registerForContextMenu(tr);
                        isShowBackground = !isShowBackground;
                        isFirstTime = true;
                    }

                }
                isFirstTime = false;


                if (isShowBackground)
                    tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorOrangeTransparent));
                else
                    tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorBlueTransparent));

                // Draw separator
                tv = new TextView(ExternalLinksDetailsActivity.this);
                tv.setHeight(CommonMethod.dpTopx(2, ExternalLinksDetailsActivity.this));
                tv.setBackgroundColor(Color.parseColor("#80808080"));

            }
//            tableLayout.addView(tr);
//            tableLayout.addView(tv);
//            registerForContextMenu(tr);
            isShowBackground = !isShowBackground;

        }

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
        downloadCurrentMonthDateExternalLinks(startDate, endDate, selectedYear);

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
        if (message instanceof ArrayList) {
            final List<ExternalLinksModel> modelList = (List<ExternalLinksModel>) message;
            final HashMap<String, List<CommonMethod.TempYearExternalHashMap>> hashMap = new HashMap<>();
            final List<CommonMethod.TempYearExternalHashMap> list = new ArrayList<>();
            final CommonMethod.TempYearExternalHashMap externalHashMap = new CommonMethod.TempYearExternalHashMap();

            Observable.create(new ObservableOnSubscribe<HashMap<String, List<CommonMethod.TempYearExternalHashMap>>>() {
                @Override
                public void subscribe(ObservableEmitter<HashMap<String, List<CommonMethod.TempYearExternalHashMap>>> e) throws Exception {
                    int value = 0;
                    for (ExternalLinksModel linksModel : modelList
                            ) {

                        for (Liveurlmaster liveurlmaster : linksModel.getLiveurlmaster()) {
                            value += Integer.parseInt(liveurlmaster.getSession());
                            externalHashMap.setStatus(liveurlmaster.getStatus());
                            externalHashMap.setLiveUrl(liveurlmaster.getLiveurl());

                        }
                        externalHashMap.setValue(value);
//                        externalHashMap.setList(modelList);
                        try {
                            externalHashMap.setDate(linksModel.getExlinkmaster().getDateof());
                        } catch (NumberFormatException e1) {
                            e1.printStackTrace();
                        }

                        list.add(externalHashMap);
                        hashMap.put(linksModel.getExlinkmaster().getTitle(), list);
                    }
                    e.onNext(hashMap);
                    e.onComplete();

                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HashMap<String, List<CommonMethod.TempYearExternalHashMap>>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HashMap<String, List<CommonMethod.TempYearExternalHashMap>> tempYearExternalHashMaps) {

                    createRowTable(tempYearExternalHashMaps);
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

    private void createRowTable(HashMap<String, List<CommonMethod.TempYearExternalHashMap>> tempYearExternalHashMaps) {
        cleanTable(tableLayout);
        tableLayout.addView(tblRowFirst);
        tableLayout.addView(llTbl);
        tableLayout.addView(tblRowSecond);

        for (Map.Entry<String, List<CommonMethod.TempYearExternalHashMap>> master : tempYearExternalHashMaps.entrySet()) {

            final TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_urlstatus, null);

            for (CommonMethod.TempYearExternalHashMap tempModel : master.getValue()) {

                // Fill out our cells
                tv = (TextView) tr.findViewById(R.id.lbl_keyword);
                tv.setText(master.getKey());
                tv.setPadding(4, 0, 4, 0);
                tv = (TextView) tr.findViewById(R.id.lbl_dates);
                tv.setText(tempModel.getDate());

                tv = (TextView) tr.findViewById(R.id.lbl_url);
                tv.setText(tempModel.getLiveUrl());

                tv = (TextView) tr.findViewById(R.id.lbl_live_status);
                tv.setText(tempModel.getStatus());


                tv = (TextView) tr.findViewById(R.id.lbl_session);
                tv.setText(String.valueOf(tempModel.getValue()));


                if (isShowBackground)
                    tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorOrangeTransparent));
                else
                    tr.setBackgroundColor(ContextCompat.getColor(ExternalLinksDetailsActivity.this, R.color.colorBlueTransparent));


                // Draw separator
                tv = new TextView(ExternalLinksDetailsActivity.this);
                tv.setHeight(CommonMethod.dpTopx(2, ExternalLinksDetailsActivity.this));

                tv.setBackgroundColor(Color.parseColor("#80808080"));
            }


            tableLayout.addView(tr);
            tableLayout.addView(tv);
            registerForContextMenu(tr);
            isShowBackground = !isShowBackground;


        }
    }

    private void cleanTable(TableLayout table) {
        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(0, childCount);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
