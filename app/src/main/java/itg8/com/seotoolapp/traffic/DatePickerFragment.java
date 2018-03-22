package itg8.com.seotoolapp.traffic;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.seotoolapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rbtn_month)
    RadioButton rbtnMonth;
    @BindView(R.id.rbtn_week)
    RadioButton rbtnYear;
    @BindView(R.id.rgbtn_date)
    RadioGroup rgbtnDate;
    Unbinder unbinder;
    @BindView(R.id.spr_year)
    Spinner sprYear;
    @BindView(R.id.spr_month)
    Spinner sprMonth;
    @BindView(R.id.spr_week)
    Spinner sprWeek;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnOk)
    Button btnOk;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer[] years;
    private String[] months;


    public DatePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerFragment newInstance(String param1, String param2) {
        DatePickerFragment fragment = new DatePickerFragment();
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
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setOnClickListener();
        setSpinnerItem();


    }

    private Integer[] getCurrentYear() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        return years = new Integer[]{mYear, mYear - 1, mYear - 2, mYear - 3, mYear - 4, mYear - 5};

    }

    private void setSpinnerItem() {
        sprWeek.setOnItemSelectedListener(this);
        sprMonth.setOnItemSelectedListener(this);
        sprYear.setOnItemSelectedListener(this);
        setYearDataIntoSpinner();
        setMonthDataIntoSpinner();
        setWeekDataIntoSpinner();


    }

    private void setWeekDataIntoSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getMonthData());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprMonth.setAdapter(dataAdapter);
    }

    private void setMonthDataIntoSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getMonthData());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprMonth.setAdapter(dataAdapter);
    }

    private String[] getMonthData() {

        return months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    }

    private void setYearDataIntoSpinner() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getCurrentYear());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprYear.setAdapter(dataAdapter);
    }

    private void setOnClickListener() {
        rgbtnDate.setOnCheckedChangeListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rbtn_week:
                sprWeek.setVisibility(View.GONE);

                break;
            case R.id.rbtn_month:
                sprWeek.setVisibility(View.VISIBLE);

                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:


                break;
            case R.id.btnCancel:
                getDialog().dismiss();

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = null;
        int itemMonth ;
        switch (view.getId()) {
            case R.id.spr_year:
                item = adapterView.getItemAtPosition(i).toString();
                break;

            case R.id.spr_month:
                item = adapterView.getItemAtPosition(i).toString();
                itemMonth = +i;


                break;

            case R.id.spr_week:
                item = adapterView.getItemAtPosition(i).toString();
                break;
        }



    }

    private void getMonthWeekFromCalender() {




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
