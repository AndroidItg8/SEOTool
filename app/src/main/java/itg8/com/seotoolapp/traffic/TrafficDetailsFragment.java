package itg8.com.seotoolapp.traffic;


import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;
import itg8.com.seotoolapp.widget.fixtablelayout.FixTableLayout;

import static itg8.com.seotoolapp.common.CommonMethod.WeekList;
import static itg8.com.seotoolapp.common.CommonMethod.createWeeksFromMonth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrafficDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrafficDetailsFragment extends Fragment implements TrafficDetailsActivity.OnItemDateListener, OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TrafficDetailsFragment";
    public String[] title = {"Traffi", "title2", "title3", "title4", "title5", "title6", "title7",
            "title8", "title9"};
    public List<Object> data = new ArrayList<>();
    protected RectF mOnValueSelectedRectF = new RectF();
    @BindView(R.id.fixTableLayout)
    FixTableLayout fixTableLayout;
    Unbinder unbinder;
    Calendar calendar = Calendar.getInstance();
    @BindView(R.id.barchart)
    BarChart mChart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private int month;
    private Integer year;
    private WeekList listWeek;
    private HashMap<Trafficcategorymaster, List<TrafficModel>> listHashMap;

    public TrafficDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrafficDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrafficDetailsFragment newInstance(String param1, String param2) {
        TrafficDetailsFragment fragment = new TrafficDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_traffic_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        ((TrafficDetailsActivity) mContext).setyearListner(this);
    }



    private void setBarchart(List<Object> lists, CharSequence title) {

        mChart.setOnChartValueSelectedListener(this);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        mChart.setMaxVisibleValueCount(40);


        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.animateY(3000);


        mChart.setDrawGridBackground(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(false);
//        mChart.setHighlightFullBarEnabled(false);
//        mChart.getAxisRight().removeAllLimitLines();


        mChart.setDrawBorders(false);
        mChart.setHighlightPerTapEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.getLegend().setEnabled(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);
        XAxis xLabels = mChart.getXAxis();
        xLabels.setDrawGridLines(false);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setTextColor(Color.BLACK);


        mChart.getLegend().setEnabled(true);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);


        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


            setData(lists, title);
          }




    private void setTableHeaderData(List<TrafficModel> lists) {
        data.clear();
        data.addAll(lists);
//        for (int i = 0; i <lists.size(); i++) {
//            data.addA(lists.get(i));
//        }


    }

    private int[] getColors(int size) {

        int stacksize = size;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];



//        colors[1] = Color.parseColor("#D4A280");
//        colors[2] = Color.parseColor("#D4A280");
//        colors[3] = Color.parseColor("#D4A280");
//        colors[4] = Color.parseColor("#00B0EC");
//        colors[5] = Color.parseColor("#00CBAC");

        for (int i = 0; i < colors.length; i++) {
            colors[i] = Color.parseColor("#D4A280");

        }
        return colors;
    }

    private void setData(List<Object> list, CharSequence title) {


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        for (int i = (int) 1; i <=list.size()-1; i++) {
            if(list.get(i) instanceof CommonMethod.TempYearHashMap)
            {
                CommonMethod.TempYearHashMap tempYearHashMap = (CommonMethod.TempYearHashMap) list.get(i);
                yVals1.add(new BarEntry(i, tempYearHashMap.getValue(), getResources().getDrawable(R.drawable.custom_spinner_back)));

            }else if(list.get(i) instanceof TrafficModel)
            {
              TrafficModel trafficModel = (TrafficModel) list.get(i);
                yVals1.add(new BarEntry(i, Float.parseFloat(trafficModel.getTrafficmaster().getContof()), getResources().getDrawable(R.drawable.custom_spinner_back)));


            }


        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, title.toString());

            set1.setDrawIcons(false);
            set1.setDrawValues(false);

            set1.setColors(getColors(list.size()));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.7f);

            mChart.setData(data);
        }
    }

    private void setTableAdapter(String[] dates) {
        String [] title = new String[]{"Title" ,dates[1]};
//        dates[0] = "Title";
        FixTableAdapter fixTableAdapter = new FixTableAdapter(title, data);
        fixTableLayout.setAdapter(fixTableAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(int month, Integer selectedYear) {
        this.month = month;
        this.year = selectedYear;

      //  createTableHeader(month, selectedYear);
    }

    private void createTableHeader(int month, Integer selectedYear) {
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, selectedYear);
        List<WeekList> list = createWeeksFromMonth(calendar);
        ;
        List<String> listString = new ArrayList<>();
        for (Integer item :
                list.get(0).getDates()) {

            Log.d(TAG, "createTableHeader: item" + item);
            listString.add(String.valueOf(item));
        }

        setTableAdapter(listString.toArray(new String[listString.size()]));

    }

    @Override
    public void onItemSelected(WeekList list, int month, Integer selectedYear) {

        this.month = month;
        this.year = selectedYear;
        this.listWeek = list;


    }

    private void createTableDaysHeader(List<TrafficModel> list) {
        setTableAdapter(new String[]{null, list.get(0).getTrafficcategorymaster().getTraffic()});


    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }



    @Override
    public void onItemSelect(Integer selectedYear) {
        this.year = selectedYear;
        calendar.set(Calendar.YEAR, selectedYear);
        CommonMethod.createMonthsFromYear(calendar);


    }

    @Override
    public void onTrafficModelList(List<CommonMethod.TempYearHashMap> lists, CharSequence title) {

        data.clear();
        data.addAll(lists);

        setTableAdapter(new String[]{null, String.valueOf(title)});


    }

    @Override
    public void onTrafficDailyData(List<TrafficModel> lists, WeekList selectWeek) {
        initDailyTrafficData(lists, selectWeek);
        List<Object> list = new ArrayList<>();
        list.addAll(lists);
        setBarchart(list,getActivity().getActionBar().getTitle());



    }

    private void initDailyTrafficData(List<TrafficModel> lists, WeekList selectWeek) {
        createTableDaysHeader(lists);
        setTableHeaderData(lists);
        List<Object> list = new ArrayList<>();
        list.addAll(lists);
        setBarchart(list,getActivity().getActionBar().getTitle());



    }

    @Override
    public void onTrafficYearData(List<CommonMethod.TempYearHashMap> lists, CharSequence title) {
        data.clear();
        data.addAll(lists);
        setTableAdapter(new String[]{null, String.valueOf(title)});
        List<Object> list = new ArrayList<>();
        list.addAll(lists);
        setBarchart(list,title);


    }
}
