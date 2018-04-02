package itg8.com.seotoolapp.keyword;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.keyword.model.Keywordstatusmaster;
import itg8.com.seotoolapp.traffic.DatePickerFragment;

public class KeyWordDetailsActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnItemClickedListener {

    public List<Object> data = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.ll_data)
    RelativeLayout llData;
    @BindView(R.id.lbl_loc)
    TextView lblLoc;
    @BindView(R.id.lbl_tbl_date)
    TextView lblTblDate;
    @BindView(R.id.lbl_keyword)
    TextView lblKeyword;
    @BindView(R.id.lbl_page)
    TextView lblPage;
    @BindView(R.id.lbl_ranked)
    TextView lblRanked;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.lbl_dates)
    TextView lblDates;
    @BindView(R.id.tbl_row_first)
    TableRow tblRowFirst;
    @BindView(R.id.ll_tbl)
    LinearLayout llTbl;
    @BindView(R.id.tbl_row_second)
    TableRow tblRowSecond;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;

    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    private List<KeyWordModel> list;
    private HashMap<String, Keywordstatusmaster> hashMap = new HashMap<>();
    private List<CommonMethod.WeekList> weeklyData;
    private List<List<KeyWordModel>> listWeekKeyword = new ArrayList<>();
    private boolean isShowBackground = false;
    private TableRow tr;
    private TextView tv;


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


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadYearTrafficDetails(startDate, endDate, year);
    }

    private void setOnClickedListner() {
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

        lblDate.setText(CommonMethod.getMonthFirstDateToString(calendar)+ " M " + String.valueOf(selectedYear) + " " + " Y");
        downloadMonthLyKeyWord(selectedMonth, selectedYear);


    }

    @Override
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        Integer first = selectWeek.getDates().get(0);
        Integer last = selectWeek.getDates().get(selectWeek.getDates().size() - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, months);
        lblDate.setText(String.valueOf(first) + "-" + String.valueOf(last) + "W " +CommonMethod.getMonthFirstDateToString(calendar)  + " M " + years + "Y");
        String FirstWeekDate = selectWeek.getDatesStrings().get(0);
        String lastWeekDate = selectWeek.getDatesStrings().get(selectWeek.getDatesStrings().size() - 1);
        downloadDailyTrafficDataFromServer(FirstWeekDate, lastWeekDate, selectWeek);


    }

    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear) + " " + " Year");
        getCurrentMonthDateDownload(selectedYear);

    }

    private void downloadDailyTrafficDataFromServer(String fromDate, String toDate, final CommonMethod.WeekList selectWeek) {
        new NetworkUtility.NetworkBuilder().build().getkeyWordList(
                getString(R.string.url_kewwird),
                fromDate,
                toDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getDailyDataFromWeekData((List<KeyWordModel>) message, selectWeek);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }

    private void getDailyDataFromWeekData(List<KeyWordModel> message, CommonMethod.WeekList selectWeek) {
//        tableLayout.removeView(tv);

        cleanTable(tableLayout);
        tableLayout.addView(tblRowFirst);
        tableLayout.addView(tblRowSecond);


        if (message instanceof ArrayList) {
            List<KeyWordModel> list = (List<KeyWordModel>) message;

            for (KeyWordModel mo : list
                    ) {

                tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_key_detail_status, null);

                // Fill out our cells

                tv = (TextView) tr.findViewById(R.id.lbl_date);
                tv.setPadding(4, 0, 4, 0);
                tv.setText(mo.getKeywordstatusmaster().getDateof());

                tv = (TextView) tr.findViewById(R.id.lbl_keyword);

                tv.setText(mo.getKeywordstatusmaster().getKeyword());


                tv = (TextView) tr.findViewById(R.id.lbl_page);
                tv.setText(mo.getKeywordstatusmaster().getPageno());
                tv = (TextView) tr.findViewById(R.id.lbl_ranked);
                tv.setText(String.valueOf(mo.getKeywordstatusmaster().getRank()));


                if (isShowBackground)
                    tr.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorOrangeTransparent));
                else
                    tr.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));


                // Draw separator
                tv = new TextView(KeyWordDetailsActivity.this);
                tv.setHeight(CommonMethod.dpTopx(2, KeyWordDetailsActivity.this));

                tv.setBackgroundColor(Color.parseColor("#80808080"));
                //tableLayout.addView(tv);
                isShowBackground = !isShowBackground;
                registerForContextMenu(tr);
                tableLayout.addView(tr);
            }


        }


    }


    private void downloadMonthLyKeyWord(int selectedMonth, Integer selectedYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = CommonMethod.getMonthDateToString(calendar);
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = CommonMethod.getMonthDateToString(calendar);
        downloadWeekKeyWordData(startDate, endDate);

    }

    private void downloadWeekKeyWordData(String startDate, String endDate) {
        new NetworkUtility.NetworkBuilder().build().getkeyWordList(
                getString(R.string.url_kewwird),
                startDate,
                endDate,
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        getWeekDataFromMonthData((List<KeyWordModel>) message);


                    }

                    @Override
                    public void onFailure(Object err) {
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });

    }

    private void getWeekDataFromMonthData(final List<KeyWordModel> listHashMap) {
        if(listHashMap.size()>0) {
            CommonMethod.showHideView(rlData, rlNoItem);
            Observable.create(new ObservableOnSubscribe<List<CommonMethod.TempYearKeyWordHashMap>>() {
                @Override
                public void subscribe(ObservableEmitter<List<CommonMethod.TempYearKeyWordHashMap>> e) throws Exception {
                    weeklyData = CommonMethod.createWeeksFromMonth(Calendar.getInstance());
                    List<CommonMethod.TempYearKeyWordHashMap> list = new ArrayList<>();
                    for (CommonMethod.WeekList week : weeklyData) {
                        List<KeyWordModel> temp = new ArrayList<>();
                        CommonMethod.TempYearKeyWordHashMap tempYearHashMap = new CommonMethod.TempYearKeyWordHashMap();
                        tempYearHashMap.setMonth(week.getDates().get(0));
                        tempYearHashMap.setYear(week.getDates().get(week.getDates().size() - 1));
                        int value = 0;

                        for (String weekDate : week.getDatesStrings()) {
                            for (KeyWordModel model : listHashMap) {
                                if (weekDate.equalsIgnoreCase(model.getKeywordstatusmaster().getDateof())) {
                                    temp.add(model);
                                    value += Integer.valueOf(model.getKeywordstatusmaster().getRank());

                                }
//                            } else {
//                                temp.add(null);
//                            }
                            }
                        }
                        tempYearHashMap.setValue(value);
                        tempYearHashMap.setList(temp);
                        list.add(tempYearHashMap);

                        listWeekKeyword.add(temp);
                    }


                    e.onNext(list);
                    e.onComplete();
                }

            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CommonMethod.TempYearKeyWordHashMap>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<CommonMethod.TempYearKeyWordHashMap> lists) {
                    addTableRow(lists);

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

        }else
        {
            CommonMethod.showHideView(rlNoItem, rlData);
        }


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
//                        getMonthDataFromYearData((List) message, selectedYear);

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


    private void getMonthDateFromYearData(List message, final Integer selectedYear) {
        if (message instanceof ArrayList) {
            final List<KeyWordModel> lists = (List<KeyWordModel>) message;
            if (lists.size() > 0) {
                CommonMethod.showHideView( rlData,rlNoItem);



                Observable.create(new ObservableOnSubscribe<List<CommonMethod.TempYearKeyWordHashMap>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<CommonMethod.TempYearKeyWordHashMap>> e) throws Exception {
                        HashMap<Integer, List<KeyWordModel>> tempHashmap = CommonMethod.getMonthHashMapForKeyWord();
                        Calendar calendar = Calendar.getInstance();
                        for (KeyWordModel model : lists
                                ) {
                            long milies = CommonMethod.convertStringToDate(model.getKeywordstatusmaster().getDateof());
                            if (milies > 0) {
                                calendar.setTimeInMillis(milies);

                                tempHashmap.get(calendar.get(Calendar.MONTH)).add(model);
                            }
                        }

                        List<CommonMethod.TempYearKeyWordHashMap> listTemp = new ArrayList<>();
                        for (Map.Entry<Integer, List<KeyWordModel>> model : tempHashmap.entrySet()
                                ) {
                            CommonMethod.TempYearKeyWordHashMap tempYearHashMap = new CommonMethod.TempYearKeyWordHashMap();
                            tempYearHashMap.setYear(selectedYear);
                            tempYearHashMap.setMonth(model.getKey() + 1);
                            int value = 0;
                            for (KeyWordModel mo :
                                    model.getValue()) {

                                value += Integer.parseInt(mo.getKeywordstatusmaster().getRank());
                            }
                            tempYearHashMap.setValue(value);
                            tempYearHashMap.setList(model.getValue());
                            listTemp.add(tempYearHashMap);
                        }


                        e.onNext(listTemp);
                        e.onComplete();
                    }

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CommonMethod.TempYearKeyWordHashMap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CommonMethod.TempYearKeyWordHashMap> tempYearKeyWordHashMaps) {
                        addTableRow(tempYearKeyWordHashMaps);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }else
        {
            CommonMethod.showHideView(rlNoItem,rlData);
        }

    }

    private void addTableRow(List<CommonMethod.TempYearKeyWordHashMap> list) {


        cleanTable(tableLayout);
        tableLayout.addView(tblRowFirst);
        tableLayout.addView(llTbl);


        tableLayout.addView(tblRowSecond);

        for (CommonMethod.TempYearKeyWordHashMap model : list
                ) {


            tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_key_detail_status, null);

            // Fill out our cells

            tv = (TextView) tr.findViewById(R.id.lbl_date);
            tv.setPadding(4, 0, 4, 0);
            tv.setText(model.getMonth() + "/" + model.getYear());
            if (model.getList() != null && model.getList().size() > 0) {

                for (KeyWordModel mo : model.getList()
                        ) {

                    tv = (TextView) tr.findViewById(R.id.lbl_keyword);

                    tv.setText(mo.getKeywordstatusmaster().getKeyword());


                    tv = (TextView) tr.findViewById(R.id.lbl_page);
                    tv.setText(mo.getKeywordstatusmaster().getPageno());
                    tv = (TextView) tr.findViewById(R.id.lbl_ranked);
                    tv.setText(String.valueOf(model.getValue()));


                    if (isShowBackground)
                        tr.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorOrangeTransparent));
                    else
                        tr.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));


                    // Draw separator
                    tv = new TextView(KeyWordDetailsActivity.this);
                    tv.setHeight(CommonMethod.dpTopx(2, KeyWordDetailsActivity.this));

                    tv.setBackgroundColor(Color.parseColor("#80808080"));
                    //tableLayout.addView(tv);

                }
            } else {
                continue;

            }
            isShowBackground = !isShowBackground;
            registerForContextMenu(tr);
            tableLayout.addView(tr);
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
