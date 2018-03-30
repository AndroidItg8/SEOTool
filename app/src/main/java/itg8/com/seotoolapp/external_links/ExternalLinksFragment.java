package itg8.com.seotoolapp.external_links;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.external_links.model.Liveurlmaster;
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.traffic.FixTableAdapter;
import itg8.com.seotoolapp.traffic.controller.HomeController;
import itg8.com.seotoolapp.widget.fixtablelayout.FixTableLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExternalLinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ExternalLinksFragment extends Fragment implements HomeController.ExternalLinksFragmentListener<ExternalLinksModel>, OnChartValueSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ExternalLinksFragment";
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.fixTableLayout)
    FixTableLayout fixTableLayout;
    Unbinder unbinder;
    @BindView(R.id.barchart)
    BarChart mChart;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.lbl_dates)
    TextView lblDates;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_url)
    TextView txtUrl;
    @BindView(R.id.rl_text)
    RelativeLayout rlText;
    @BindView(R.id.lbl_history)
    TextView lblHistory;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Object> data = new ArrayList<>();
    private XAxis xLabels;
    private int type;
    private HashMap<String, List<ExternalLinksModel>> listExternalLinks;
    private boolean isDestroyed = false;
    private boolean isExternalView = true;


    public ExternalLinksFragment() {

        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ExternalLinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExternalLinksFragment newInstance(int param1) {
        ExternalLinksFragment fragment = new ExternalLinksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_external_links, container, false);
        unbinder = ButterKnife.bind(this, view);
        isDestroyed = false;
        init();

        return view;
    }

    private void init() {
        lblHistory.setOnClickListener(this);
        lblDate.setText(CommonMethod.getCurrentDateString());
        if (listExternalLinks != null && listExternalLinks.size() > 0) {
            SortExternalLinksFor(listExternalLinks, this.type);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).setExternalLinksFragmentListener(this, this.type);
    }


    @Override
    public void onExtLinkAvail(HashMap<String, List<ExternalLinksModel>> hashMapList, int type) {
        this.type = type;
        listExternalLinks = hashMapList;

        if (hashMapList != null && hashMapList.size() > 0)
            SortExternalLinksFor(hashMapList, type);
        else
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

//        initDailyTrafficData(lists, selectWeek);
//        List<Object> list = new ArrayList<>();
//        list.addAll(lists);
//        setBarchart(hashMapList,getActivity().getActionBar().getTitle());


    }

    private void SortExternalLinksFor(HashMap<String, List<ExternalLinksModel>> hashMapList, int type) {
        if (!isDestroyed) {
            this.type = type;
            setBarchart();
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            final List<String> list = new ArrayList<>();
            float f = 0;
            for (Map.Entry<String, List<ExternalLinksModel>> entry : hashMapList.entrySet()
                    ) {
                int value = 0;
                ExternalLinksModel models = null;
                for (ExternalLinksModel model : entry.getValue()
                        ) {
                    models = model;
                    for (Liveurlmaster master : model.getLiveurlmaster()) {

                        value += Integer.parseInt(master.getSession());
                    }

                    Log.d(TAG, "SortExternalLinksFor: " + value);
                    list.add(String.valueOf(CommonMethod.convertStringDateToDDMM(entry.getKey())));
                    yVals1.add(new BarEntry(f, value, models));
//                mChart.notifyDataSetChanged();

                    f++;
                }

            }


            xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
            xLabels.setDrawGridLines(false);
            xLabels.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (list.size() > 0)
                        return list.get((int) value);
                    return "";

                }
            });


            setChartData(yVals1, "Submission");
            mChart.setOnChartValueSelectedListener(this);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();


        }
    }

    private void setChartData(ArrayList<BarEntry> yVals1, CharSequence title) {

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

            set1.setColor(Color.parseColor("#D4A280"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.11f);


            mChart.setData(data);
        }
    }


    private void setBarchart() {


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
        mChart.setPinchZoom(true);
        mChart.setScaleEnabled(false);
        mChart.getLegend().setEnabled(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);
        xLabels = mChart.getXAxis();
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


    }


    private void setTableHeaderData(List<Object> lists) {
        data.clear();
        data.addAll(lists);


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


    private void setTableAdapter() {
        String[] title = new String[0];
        if (this.type == CommonMethod.EXTERNAL_LINKS)
            title = new String[]{"", "Live Url", "Status", "Session"};
        else
            title = new String[]{"", "Live Url", "Status", "Session"};

        FixTableAdapter fixTableAdapter = new FixTableAdapter(title, data);
        fixTableLayout.setAdapter(fixTableAdapter);
    }

    @Override
    public void onDownloadFail(String message, int type) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isDestroyed = true;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.d(TAG, "onValueSelected: e");
        ExternalLinksModel model = (ExternalLinksModel) e.getData();
        if (model != null) {

            setTableHeaderData(createTempData(model));
            setTableAdapter();
            lblDates.setText(model.getExlinkmaster().getDateof());
            txtTitle.setText(model.getExlinkmaster().getTitle());
            txtUrl.setText(model.getExlinkmaster().getPostedurl());
        }

    }

    private List<Object> createTempData(ExternalLinksModel model) {
        return new ArrayList<Object>(model.getLiveurlmaster());
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.lbl_history:
               startDetailActivity();
                break;
        }
    }

    private void startDetailActivity() {

        Intent intent = new Intent(getActivity(),ExternalLinksDetailsActivity.class);
//        intent.putParcelableArrayListExtra(CommonMethod.EXTERNAL_LINKS, listExternalLinks);
        intent.putExtra(CommonMethod.EXTERNAL_LINKS_TYPE, type);
        startActivity(intent);
        Log.d(TAG, "startDetailActivity: ");
    }
}
