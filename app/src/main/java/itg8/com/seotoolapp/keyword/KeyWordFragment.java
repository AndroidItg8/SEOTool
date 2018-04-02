package itg8.com.seotoolapp.keyword;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.keyword.model.Keywordstatusmaster;
import itg8.com.seotoolapp.traffic.controller.HomeController;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeyWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeyWordFragment extends Fragment implements HomeController.KeyWordFragmentListener<KeyWordModel>, OnChartValueSelectedListener, View.OnClickListener {
    public static final String GRUOP_1_10 = "1-10";
    public static final String GROUP_11_20 = "11-20";
    public static final String GROUP_21_30 = "21-30";
    public static final String GROUP_31_40 = "31-40";
    public static final String GROUP_41_50 = "41-50";
    public static final String GROUP_50_P = "50+";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FROM_SELECT = 1;
    private static final int FROM_ALL = 2;
    List<Keywordstatusmaster> listGroup1_10 = new ArrayList<>();
    List<Keywordstatusmaster> listGroup11_20 = new ArrayList<>();
    List<Keywordstatusmaster> listGroup21_30 = new ArrayList<>();
    List<Keywordstatusmaster> listGroup31_40 = new ArrayList<>();
    List<Keywordstatusmaster> listGroup41_50 = new ArrayList<>();
    List<Keywordstatusmaster> listGroup50 = new ArrayList<>();
    @BindView(R.id.pieChart)
    PieChart mChart;
    Unbinder unbinder;
    @BindView(R.id.lbl_currentDate)
    TextView lblCurrentDate;
    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.tableLayout)
    TableLayout tableLayout;

    HashMap<String, Keywordstatusmaster> hashMap = new HashMap<>();
    @BindView(R.id.lbl_tbl_date)
    TextView lblTblDate;
    @BindView(R.id.lbl_keyword)
    TextView lblKeyword;
    @BindView(R.id.lbl_page)
    TextView lblPage;
    @BindView(R.id.lbl_ranked)
    TextView lblRanked;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.lbl_loc)
    TextView lblLoc;
    @BindView(R.id.lbl_history)
    TextView lblHistory;
    @BindView(R.id.tbl_row_first)
    TableRow tblRowFirst;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    @BindView(R.id.tbl_row_second)
    TableRow tblRowSecond;
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isShowBackground = true;
    private List<KeyWordModel> listKeyWord;
    TextView tv;
    private boolean isViewCreated;


    public KeyWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeyWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KeyWordFragment newInstance(String param1, String param2) {
        KeyWordFragment fragment = new KeyWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_key_word, container, false);
        unbinder = ButterKnife.bind(this, view);
        isViewCreated=true;
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) context).setKeywordFragmentListener(this);
    }


    @Override
    public void onKeywordDetailAvailable(List<KeyWordModel> t) {
         if(isViewCreated) {
             if (t.size() > 0) {
                 CommonMethod.showHideView(rlData, rlNoItem);
                 listKeyWord = t;
                 sortKeyWordData(t);
             } else
                 CommonMethod.showHideView(rlNoItem, rlData);

         }

    }

    private void sortKeyWordData(List<KeyWordModel> t) {
        Observable.just(t).flatMap(new Function<List<KeyWordModel>, Observable<HashMap<String, Keywordstatusmaster>>>() {
            @Override
            public Observable<HashMap<String, Keywordstatusmaster>> apply(List<KeyWordModel> keyWordModels) throws Exception {
                return createHashForStatus(keyWordModels);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HashMap<String, Keywordstatusmaster>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashMap<String, Keywordstatusmaster> stringKeywordstatusmasterHashMap) {
                addTableRow(stringKeywordstatusmasterHashMap, FROM_ALL);


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Observable<HashMap<String, Keywordstatusmaster>> createHashForStatus(final List<KeyWordModel> keyWordModels) {
        return Observable.create(new ObservableOnSubscribe<HashMap<String, Keywordstatusmaster>>() {
            @Override
            public void subscribe(ObservableEmitter<HashMap<String, Keywordstatusmaster>> e) throws Exception {
                for (KeyWordModel mo :
                        keyWordModels) {
                    hashMap.put(mo.getKeywordstatusmaster().getKeyword(), mo.getKeywordstatusmaster());
                }
                e.onNext(hashMap);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    private void addTableRow(HashMap<String, Keywordstatusmaster> t, int from) {
        if (from == FROM_SELECT) {
            cleanTable(tableLayout);
            tableLayout.addView(tblRowFirst);
            tableLayout.addView(llTable);
            tableLayout.addView(tblRowSecond);

        }

        for (Map.Entry<String, Keywordstatusmaster> master : t.entrySet()) {


            final TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.item_rv_keystatus, null);

            // Fill out our cells
            tv = (TextView) tr.findViewById(R.id.lbl_keyword);
            tv.setText(master.getKey());
            tv.setPadding(4, 0, 4, 0);
            tv = (TextView) tr.findViewById(R.id.lbl_page);
            tv.setText(master.getValue().getPageno());
            tv = (TextView) tr.findViewById(R.id.lbl_ranked);
            tv.setText(master.getValue().getRank());

            tableLayout.addView(tr);
            if (isShowBackground)
                tr.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorOrangeTransparent));
            else
                tr.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlueTransparent));


            // Draw separator
            tv = new TextView(getActivity());
            tv.setHeight(CommonMethod.dpTopx(2, getActivity()));

            tv.setBackgroundColor(Color.parseColor("#80808080"));
            tableLayout.addView(tv);


            registerForContextMenu(tr);
            isShowBackground = !isShowBackground;
        }
        if (from == FROM_ALL)
            genrateKeyWordRankedGroup(t);

    }

    @Override
    public void onDownloadFail() {

    }


    private void init() {
        lblHistory.setOnClickListener(this);
        lblCurrentDate.setText("Current Date : " + CommonMethod.getCurrentDateString());
        lblTblDate.setText(CommonMethod.getCurrentDateString());
        if (listKeyWord != null && listKeyWord.size() > 0)
        {
            CommonMethod.showHideView(rlData,rlNoItem);
            sortKeyWordData(listKeyWord);

        }
        else
            CommonMethod.showHideView(rlNoItem, rlData);



    }


    private void genrateKeyWordRankedGroup(HashMap<String, Keywordstatusmaster> t) {

        for (Map.Entry<String, Keywordstatusmaster> master : t.entrySet()) {
            int rank = Integer.parseInt(master.getValue().getRank());
            if (rank > 0 && rank <= 10) {
                listGroup1_10.add(master.getValue());
            } else if (rank > 10 && rank <= 20) {
                listGroup11_20.add(master.getValue());
            } else if (rank > 20 && rank <= 30) {
                listGroup21_30.add(master.getValue());
            } else if (rank > 30 && rank <= 40) {
                listGroup31_40.add(master.getValue());
            } else if (rank > 40 && rank <= 50) {
                listGroup41_50.add(master.getValue());
            } else if (rank > 50 || rank == 0) {
                listGroup50.add(master.getValue());
            }
        }

        createPieChart();

    }


    private void createPieChart() {


        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);


        mChart.setDrawCenterText(true);
        mChart.setCenterTextSize(8);
        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setRotationEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        setData(6);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(12f);

    }

    private void setData(int count) {


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();


        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        String[] mParties = {GRUOP_1_10, GROUP_11_20, GROUP_21_30, GROUP_31_40, GROUP_41_50, GROUP_50_P};
        for (int i = 0; i < mParties.length; i++) {
            if (getSize(mParties[i]).size() != 0) {
                entries.add(new PieEntry(getSize(mParties[i]).size(),
                        mParties[i],
                        getSize(mParties[i])));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Keyword Status");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);


        dataSet.setColors(getColors());
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    public List<Keywordstatusmaster> getSize(String i) {

        if (i.equalsIgnoreCase(GRUOP_1_10))
            return listGroup1_10;
        else if (i.equalsIgnoreCase(GROUP_11_20))
            return listGroup11_20;
        else if (i.equalsIgnoreCase(GROUP_21_30))
            return listGroup21_30;
        else if (i.equalsIgnoreCase(GROUP_31_40))
            return listGroup31_40;
        else if (i.equalsIgnoreCase(GROUP_41_50))
            return listGroup41_50;
        else if (i.equalsIgnoreCase(GROUP_50_P))
            return listGroup50;


        return new ArrayList<>();
    }

    private int[] getColors() {

        int stacksize = 6;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
        colors[0] = ContextCompat.getColor(getActivity(), R.color.colorGroup1_10);
        colors[1] = ContextCompat.getColor(getActivity(), R.color.colorGroup11_20);
        colors[2] = ContextCompat.getColor(getActivity(), R.color.colorGroup21_30);
        colors[3] = ContextCompat.getColor(getActivity(), R.color.colorGroup31_40);
        colors[4] = ContextCompat.getColor(getActivity(), R.color.colorGroup41_50);
        colors[5] = ContextCompat.getColor(getActivity(), R.color.colorGroup51_PLUS);
        return colors;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {


        if (e == null)
            return;


        if (e.getData() instanceof ArrayList) {
            List<Keywordstatusmaster> master = (List<Keywordstatusmaster>) e.getData();
            HashMap<String, Keywordstatusmaster> hashMap = new HashMap<>();
            for (Keywordstatusmaster m1 : master) {
                hashMap.put(m1.getKeyword(), m1);
            }


            addTableRow(hashMap, FROM_SELECT);


        }
        Log.i("VAL SELECTED",
                "Value: " +
                        e.getData().toString());


    }

    private void callDetailsActivity() {
        Intent intent = new Intent(getActivity(), KeyWordDetailsActivity.class);
        intent.putParcelableArrayListExtra(CommonMethod.KEYWORD_DETAILS, (ArrayList<? extends Parcelable>) listKeyWord);
        startActivity(intent);
    }


    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isViewCreated=false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbl_history:
                callDetailsActivity();
                break;
        }
    }


    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(0, childCount);
        }
    }
}
