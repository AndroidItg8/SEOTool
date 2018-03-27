package itg8.com.seotoolapp.social_media;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.social_media.model.SocialMediaModel;
import itg8.com.seotoolapp.traffic.FixTableAdapter;
import itg8.com.seotoolapp.traffic.controller.HomeController;
import itg8.com.seotoolapp.widget.fixtablelayout.FixTableLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialMediaFragment extends Fragment implements HomeController.SocialMediaFragmentListener<SocialMediaModel>, OnChartValueSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    @BindView(R.id.pieChart)
//    PieChart mChart;
    Unbinder unbinder;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.fixTableLayout)
    FixTableLayout fixTableLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public List<Object> data = new ArrayList<>();


    public SocialMediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialMediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialMediaFragment newInstance(String param1, String param2) {
        SocialMediaFragment fragment = new SocialMediaFragment();
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
        View view = inflater.inflate(R.layout.fragment_external_links, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        lblDate.setText(CommonMethod.getCurrentDateString());
        onClickedListner();


        //createPieChart();
    }

    private void onClickedListner() {
        lblDate.setOnClickListener(this);
    }

    private void createPieChart() {

//        mChart.setUsePercentValues(true);
//        mChart.getDescription().setEnabled(false);
//        mChart.setExtraOffsets(5, 10, 5, 5);
//
//        mChart.setDragDecelerationFrictionCoef(0.95f);
//
//
//        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColor(Color.WHITE);
//
//        mChart.setTransparentCircleColor(Color.WHITE);
//        mChart.setTransparentCircleAlpha(110);
//
//        mChart.setHoleRadius(58f);
//        mChart.setTransparentCircleRadius(61f);
//
//        mChart.setDrawCenterText(true);
//
//        mChart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        mChart.setRotationEnabled(true);
//        mChart.setHighlightPerTapEnabled(true);
//
//        // mChart.setUnit(" €");
//        // mChart.setDrawUnitsInChart(true);
//
//        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);
//
//        setData(4, 100);
//
//        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//        // mChart.spin(2000, 0, 360);
//
//
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//
//        // entry label styling
//        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTextSize(12f);

    }

    private void setData(int count, int range) {
        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        String[] mParties = {"", "", ""};
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),

                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        mChart.setData(data);
//
//        // undo all highlights
//        mChart.highlightValues(null);
//
//        mChart.invalidate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).setSocialMediaFragmentListener(this);
    }

    @Override
    public void onSocMediaAvail(List<SocialMediaModel> t) {
//        countSessionOfSocialMedia(t);

        createTableData(t);
    }


    private void createTableData(List<SocialMediaModel> t) {
        data.clear();
        data.addAll(t);

        setTableAdapter(new String[]{null, "No of KeyStatus","Submission Status"});
    }

    private void setTableAdapter(String[] dates) {
        String [] title = new String[]{"Title" ,dates[1],dates[2]};
//        dates[0] = "Title";
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
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {


        if (e == null)
            return;

        Intent intent = new Intent(getActivity(), SocialMediaActivity.class);
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.lbl_date:
                break;
        }
    }
}
