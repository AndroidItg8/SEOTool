package itg8.com.seotoolapp.keyword;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

    private static final String TAG = "KeyWordDetailsActivity";
    public List<Object> data = new ArrayList<>();


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.ll_data)
    RelativeLayout llData;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.lbl_no_data)
    TextView lblNoData;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tableLayoutDetails)
    TableLayout tableLayoutDetails;
    private String[] months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    private List<KeyWordModel> list;
    private HashMap<String, Keywordstatusmaster> hashMap = new HashMap<>();
    private List<CommonMethod.WeekList> weeklyData;
    private List<List<KeyWordModel>> listWeekKeyword = new ArrayList<>();
    private boolean isShowBackground = false;
    private TableRow tr;
    private TextView tv;
    private boolean isFirstTimeHeader = true;



    public HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>> tempYearKeyWordHashMapes;


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


        lblDate.setText(CommonMethod.getCurrentDateString());


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
            case R.id.group_1_10:
                Log.d(TAG, "onClick: group_1_10");
                setDetailDataItemClicked();
                break;
            case R.id.group_11_20:
                Log.d(TAG, "onClick:group_11_20 ");
                setDetailDataItemClicked();
                break;

            case R.id.group_21_30:
                Log.d(TAG, "onClick:group_21_30 ");
                setDetailDataItemClicked();
                break;

            case R.id.group_31_40:
                Log.d(TAG, "onClick:group_31_40 ");
                setDetailDataItemClicked();
                break;

            case R.id.group_41_50:
                Log.d(TAG, "onClick:group_41_50 ");
                break;

            case R.id.group_50_plus:
                Log.d(TAG, "onClick:group_50_plus ");
                setDetailDataItemClicked();
                break;


//
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

        lblDate.setText(CommonMethod.getMonthFirstDateToString(calendar) + String.valueOf(selectedYear));
        downloadMonthLyKeyWord(selectedMonth, selectedYear);


    }

    @Override
    public void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years) {
        Integer first = selectWeek.getDates().get(0);
        Integer last = selectWeek.getDates().get(selectWeek.getDates().size() - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, months);
        lblDate.setText(String.valueOf(first) + "-" + String.valueOf(last) + CommonMethod.getMonthFirstDateToString(calendar) + years);
        String FirstWeekDate = selectWeek.getDatesStrings().get(0);
        String lastWeekDate = selectWeek.getDatesStrings().get(selectWeek.getDatesStrings().size() - 1);
        downloadDailyTrafficDataFromServer(FirstWeekDate, lastWeekDate, selectWeek);


    }

    @Override
    public void onItemSelect(Integer selectedYear) {
        lblDate.setText(String.valueOf(selectedYear));
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
//        tableLayout.addView(tblRowFirst);
//        tableLayout.addView(tblRowSecond);


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
        if (listHashMap.size() > 0) {
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
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });

        } else {
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
                CommonMethod.showHideView(rlData, rlNoItem);
                Observable.create(new ObservableOnSubscribe<HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>>>() {
                    @Override
                    public void subscribe(ObservableEmitter<HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>>> e) throws Exception {
                        HashMap<String, List<Keywordstatusmaster>> hasmpaInner = new HashMap<>();

                        HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>> tempHashmap = CommonMethod.getMonthHashMapForKeyWord();

                        Calendar calendar = Calendar.getInstance();
                        for (KeyWordModel model : lists) {
                            long milies = CommonMethod.convertStringToDate(model.getKeywordstatusmaster().getDateof());
                            if (milies > 0) {
                                calendar.setTimeInMillis(milies);
                                int rank = Integer.parseInt(model.getKeywordstatusmaster().getRank());

                                if (tempHashmap.get(calendar.get(Calendar.MONTH)).size() <= 0)
                                    hasmpaInner = new HashMap<>();

                                if (rank > 0 && rank <= 10) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GRUOP_1_10, model.getKeywordstatusmaster());

                                } else if (rank > 10 && rank <= 20) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GROUP_11_20, model.getKeywordstatusmaster());
                                } else if (rank > 20 && rank <= 30) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GROUP_21_30, model.getKeywordstatusmaster());
                                } else if (rank > 30 && rank <= 40) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GROUP_31_40, model.getKeywordstatusmaster());
                                } else if (rank > 40 && rank <= 50) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GROUP_41_50, model.getKeywordstatusmaster());
                                } else if (rank > 50 || rank == 0) {
                                    hasmpaInner = setKeyWordMaster(CommonMethod.GROUP_50_P, model.getKeywordstatusmaster());
                                }


                                tempHashmap.put(calendar.get(Calendar.MONTH), hasmpaInner);
                            }
                        }

                        e.onNext(tempHashmap);
                        e.onComplete();
                    }

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>> tempYearKeyWordHashMaps) {
                        tempYearKeyWordHashMapes = tempYearKeyWordHashMaps;
                        addTableRowHashMap(tempYearKeyWordHashMaps);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        } else {
            CommonMethod.showHideView(rlNoItem, rlData);
        }

    }

    private void addTableRowHashMap(HashMap<Integer, HashMap<String, List<Keywordstatusmaster>>> tempYearKeyWordHashMaps) {


        tableLayout.removeAllViews();
        cleanTable(tableLayout);


        TextView textView = new TextView(KeyWordDetailsActivity.this);
        textView.setPadding(8, 8, 8, 8);
        textView.setText("Groups");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15.0f);
        textView.setGravity(Gravity.CENTER);

        TableRow tbleRowFinal = new TableRow(KeyWordDetailsActivity.this);
        tbleRowFinal.addView(textView);

        TextView tvDate = new TextView(KeyWordDetailsActivity.this);
        TextView tvSize = new TextView(KeyWordDetailsActivity.this);

        tvSize.setText("key Count");
        tvDate.setPadding(8, 8, 8, 8);
        tvSize.setPadding(4, 4, 4, 4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tvDate.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation));
        }

        TextView textViewValue = new TextView(KeyWordDetailsActivity.this);
        textViewValue.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textViewValue.setTextSize(15.0f);
        textViewValue.setTextColor(Color.BLACK);

        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setPadding(4, 8, 4, 8);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15.0f);
        textView.setGravity(Gravity.CENTER);

        // Draw separator
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);

        if (isShowBackground)
            tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorOrangeTransparent));
        else
            tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));


        TableRow tableRowGroup_1_10 = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_1_10.setId(R.id.group_1_10);

        textView.setText(CommonMethod.GRUOP_1_10);
        textView.setPadding(4, 16, 4, 16);
        textView.setTextColor(Color.BLACK);
        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation_yellow));
        }
        textView.setTextSize(15.0f);
        textView.setGravity(Gravity.CENTER);
        tableRowGroup_1_10.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGroup11_20));
        tableRowGroup_1_10.addView(textView);

        TableRow tableRowGroup_11_20 = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_11_20.setId(R.id.group_11_20);

        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setText(CommonMethod.GROUP_11_20);
        textView.setPadding(8, 16, 8, 16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation));
        }

        textView.setTextColor(Color.BLACK);
        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        textView.setTextSize(15.0f);
        textView.setGravity(Gravity.CENTER);
        tableRowGroup_11_20.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));
        tableRowGroup_11_20.addView(textView);



        TableRow tableRowGroup_21_30 = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_21_30.setId(R.id.group_21_30);
        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setText(CommonMethod.GROUP_21_30);
        textView.setPadding(8, 16, 8, 16);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15f);
        textView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation_yellow));
        }

        tableRowGroup_21_30.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGroup11_20));
        textView.setPadding(15, 8, 15, 8);

        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        tableRowGroup_21_30.addView(textView);


        TableRow tableRowGroup_31_40 = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_31_40.setId(R.id.group_31_40);
        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setText(CommonMethod.GROUP_31_40);
        textView.setPadding(8, 16, 8, 16);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15.0f);

        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation));
        }

        tableRowGroup_31_40.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));
        tableRowGroup_31_40.addView(textView);


        TableRow tableRowGroup_41_50 = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_41_50.setId(R.id.group_41_50);
        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setText(CommonMethod.GROUP_41_50);
        textView.setPadding(8, 16, 8, 16);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15.0f);
        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation_yellow));
        }

        tableRowGroup_41_50.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGroup11_20));
        tableRowGroup_41_50.addView(textView);


        TableRow tableRowGroup_50_Plus = new TableRow(KeyWordDetailsActivity.this);
        tableRowGroup_50_Plus.setId(R.id.group_50_plus);
        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setText(CommonMethod.GROUP_50_P);
        params.setMargins(16,8,16,8);
        textView.setLayoutParams(params);
        textView.setPadding(8, 16, 8, 16);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15.0f);
        textView.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation));
        }

        tableRowGroup_50_Plus.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));
        tableRowGroup_50_Plus.addView(textView);


        tableLayout.addView(tbleRowFinal);
        tableLayout.addView(tableRowGroup_1_10);
        tableLayout.addView(tableRowGroup_11_20);
        tableLayout.addView(tableRowGroup_21_30);
        tableLayout.addView(tableRowGroup_31_40);
        tableLayout.addView(tableRowGroup_41_50);
        tableLayout.addView(tableRowGroup_50_Plus);




        for (Map.Entry<Integer, HashMap<String, List<Keywordstatusmaster>>> entry : tempYearKeyWordHashMaps.entrySet()) {
            HashMap<String, List<Keywordstatusmaster>> childMap = entry.getValue();
            tvDate = new TextView(KeyWordDetailsActivity.this);
            Integer date = entry.getKey();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, date);
            tvDate.setPadding(15, 8, 15, 8);
            TableRow.LayoutParams paramss = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            paramss.setMargins(16,8,16,8);
            tvDate.setLayoutParams(paramss);

            tvDate.setTextColor(Color.BLACK);
            tvDate.setTextSize(15.0f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                tvDate.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn_elevation));
            }

            tvDate.setGravity(Gravity.CENTER);
            tvDate.setText(CommonMethod.getMonthFirstDateToString(calendar));
//            tableLayouts.addView(tvDate);
            tbleRowFinal.addView(tvDate);
            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tvDate = (TextView) view;
                    Calendar calendar =CommonMethod.getCalenderFromString(tvDate.getText().toString());

                    downloadMonthLyKeyWord(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

                }
            });

            for (Map.Entry<String, List<Keywordstatusmaster>> entry2 : childMap.entrySet()) {
                String childKey = entry2.getKey();
                List<Keywordstatusmaster> childValue = entry2.getValue();

                if (childValue.size() == 0)
                    textViewValue.setText("0");
                else
                    textViewValue.setText(String.valueOf(childValue.size()));

                textViewValue.setPadding(8, 8, 8, 8);
                textViewValue.setTextColor(Color.BLACK);
                textViewValue.setTextSize(15.0f);
                textViewValue.setGravity(Gravity.CENTER);


                if (childKey.equalsIgnoreCase(CommonMethod.GRUOP_1_10)) {
                    tableRowGroup_1_10.addView(textViewValue);
                } else if (childKey.equalsIgnoreCase(CommonMethod.GROUP_11_20)) {
                    tableRowGroup_11_20.addView(textViewValue);
                } else if (childKey.equalsIgnoreCase(CommonMethod.GROUP_21_30)) {
                    tableRowGroup_21_30.addView(textViewValue);
                } else if (childKey.equalsIgnoreCase(CommonMethod.GROUP_31_40)) {
                    tableRowGroup_31_40.addView(textViewValue);
                } else if (childKey.equalsIgnoreCase(CommonMethod.GROUP_41_50)) {
                    tableRowGroup_41_50.addView(textViewValue);
                } else if (childKey.equalsIgnoreCase(CommonMethod.GROUP_50_P)) {
                    tableRowGroup_50_Plus.addView(textViewValue);
                }


                // Draw separator
                tv = new TextView(KeyWordDetailsActivity.this);
                tv.setHeight(CommonMethod.dpTopx(2, KeyWordDetailsActivity.this));
                tv.setBackgroundColor(Color.parseColor("#80808080"));


                if (isShowBackground)
                    tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorOrangeTransparent));
                else
                    tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));

                TextView tvs = new TextView(KeyWordDetailsActivity.this);
                tvs.setHeight(CommonMethod.dpTopx(2, KeyWordDetailsActivity.this));
                tvs.setBackgroundColor(Color.parseColor("#80808080"));
                isShowBackground = !isShowBackground;
                registerForContextMenu(tbleRowFinal);
                tableRowGroup_1_10.setOnClickListener(this);
                tableRowGroup_11_20.setOnClickListener(this);
                tableRowGroup_21_30.setOnClickListener(this);
                tableRowGroup_31_40.setOnClickListener(this);
                tableRowGroup_41_50.setOnClickListener(this);
                tableRowGroup_50_Plus.setOnClickListener(this);
            }

            if (isShowBackground)
                tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGrayLight));
            else
                tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));

            isShowBackground = !isShowBackground;



        }



    }

    private void setDetailDataItemClicked() {

        tableLayoutDetails.removeAllViews();
        cleanTable(tableLayoutDetails);


        TextView textView = new TextView(KeyWordDetailsActivity.this);
        textView.setPadding(8, 8, 8, 8);
        textView.setText("KeyWord");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(12.0f);
        textView.setGravity(Gravity.CENTER);

        TableRow tbleRowFinal = new TableRow(KeyWordDetailsActivity.this);
        TableRow tbleRowItem = new TableRow(KeyWordDetailsActivity.this);
        TableRow tbleRowItemValue = new TableRow(KeyWordDetailsActivity.this);
        tbleRowFinal.addView(textView);

        TextView tvDate = new TextView(KeyWordDetailsActivity.this);
        TextView tvSize = new TextView(KeyWordDetailsActivity.this);

        tvSize.setText("key Count");
        tvDate.setPadding(4, 4, 4, 4);


        TextView textViewValue = new TextView(KeyWordDetailsActivity.this);
        textViewValue.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textViewValue.setTextSize(12.0f);

        textViewValue.setPadding(8, 8, 8, 8);
        textViewValue.setTextColor(Color.BLACK);
        textViewValue.setGravity(Gravity.CENTER);


        TextView textViewKeyWord = new TextView(KeyWordDetailsActivity.this);
        textViewKeyWord.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textViewKeyWord.setTextSize(12.0f);
        textViewKeyWord.setTextColor(Color.BLACK);


        textView = new TextView(KeyWordDetailsActivity.this);
        textView.setPadding(4, 8, 4, 8);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(12.0f);
        textView.setGravity(Gravity.CENTER);
        tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));
        tableLayoutDetails.addView(tbleRowFinal);

 for (Map.Entry<Integer, HashMap<String, List<Keywordstatusmaster>>> entry : tempYearKeyWordHashMapes.entrySet()) {
            HashMap<String, List<Keywordstatusmaster>> childMap = entry.getValue();
            tvDate = new TextView(KeyWordDetailsActivity.this);
            Integer date = entry.getKey();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, date);
            tvDate.setPadding(8, 8, 8, 8);
            tvDate.setTextColor(Color.BLACK);
            tvDate.setTextSize(12.0f);
            tvDate.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tvDate.setBackground(ContextCompat.getDrawable(KeyWordDetailsActivity.this,R.drawable.bg_btn));
            }

            tvDate.setText(CommonMethod.getMonthFirstDateToString(calendar));
//            tableLayouts.addView(tvDate);
            tbleRowFinal.addView(tvDate);
            textViewValue.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textViewValue.setTextSize(12.0f);
            textViewValue.setPadding(8, 8, 8, 8);
            textViewValue.setTextColor(Color.BLACK);
            textViewKeyWord.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textViewKeyWord.setTextSize(12.0f);
            textViewKeyWord.setPadding(8, 8, 8, 8);
            textViewKeyWord.setTextColor(Color.BLACK);
            textViewKeyWord.setGravity(Gravity.CENTER);


            tbleRowFinal.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorBlueTransparent));
            for (Map.Entry<String, List<Keywordstatusmaster>> entry2 : childMap.entrySet()) {
                String childKey = entry2.getKey();
                List<Keywordstatusmaster> childValue = entry2.getValue();

                for (Keywordstatusmaster master : childValue) {
                    textViewKeyWord.setText(master.getKeyword());
                    textViewValue.setText(master.getRank());
                    tbleRowItem.addView(textViewKeyWord);
                    tbleRowItem.addView(textViewValue);



                    if (isShowBackground) {
                        tbleRowItem.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGroup11_20));
                    }
                    else{
                        tbleRowItem.setBackgroundColor(ContextCompat.getColor(KeyWordDetailsActivity.this, R.color.colorGray));
                    }
                    isShowBackground = !isShowBackground;
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tbleRowItem.setElevation(4.0f);
        }
        tableLayoutDetails.addView(tbleRowItem);



    }

    private HashMap<String, List<Keywordstatusmaster>> setKeyWordMaster(String key, Keywordstatusmaster value) {
        HashMap<String, List<Keywordstatusmaster>> hasmpaInner = new HashMap<>();

        if (!hasmpaInner.containsKey(key))
            hasmpaInner.put(key, new ArrayList<Keywordstatusmaster>());
        hasmpaInner.get(key).add(value);


        return hasmpaInner;
    }

    private void addTableRow(List<CommonMethod.TempYearKeyWordHashMap> list) {

        cleanTable(tableLayout);
//        tableLayout.addView(tblRowFirst);
//        tableLayout.addView(llTbl);
//
//
//        tableLayout.addView(tblRowSecond);

        for (CommonMethod.TempYearKeyWordHashMap model : list) {

            tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_key_detail_status, null);

            // Fill out our cells


            tv = (TextView) tr.findViewById(R.id.lbl_date);
            tv.setPadding(4, 0, 4, 0);
            tv.setText(model.getMonth() + "/" + model.getYear());

            if (model.getList() != null && model.getList().size() > 0) {

                for (KeyWordModel mo : model.getList()) {

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
            this.tableLayout.addView(tr);
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
