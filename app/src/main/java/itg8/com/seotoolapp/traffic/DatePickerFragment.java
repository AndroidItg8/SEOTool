package itg8.com.seotoolapp.traffic;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.Prefs;

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
    @BindView(R.id.rbtn_year)
    RadioButton rbtnYear;
    @BindView(R.id.rbtn_month)
    RadioButton rbtnMonth;
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
    Calendar calendar = Calendar.getInstance();
    @BindView(R.id.rbtn_week)
    RadioButton rbtnWeek;
    OnItemClickedListener listener;
    HashMap<String, CommonMethod.WeekList> weekListHashMap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] years;
    private String[] months;
    private Context mContext;
    private String[] weekArray;
    private CommonMethod.WeekList selectWeek;
    private int selectedMonth = -1;
    private Integer selectedYear = 0;
    private static final String TAG = "DatePickerFragment";

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

    private String[] getCurrentYear() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        return years = new String[]{"SELECT YEAR", String.valueOf(mYear),
                String.valueOf(mYear - 1), String.valueOf(mYear - 2),
                String.valueOf(mYear - 3), String.valueOf(mYear - 4), String.valueOf(mYear - 5)};

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (OnItemClickedListener) context;
//        ((TrafficDetailsActivity)mContext) .setDiaogueListener()

    }

    private void setSpinnerItem() {
        setDefaultValueToSpiner();



    }

    private void setDefaultValueToSpiner() {
         if(selectWeek!=null)
         {
             sprWeek.setSelection(selectWeek.getWeek());

         }
          if(Prefs.getInt(CommonMethod.SELECT_MONTH)>0)
          {
              sprMonth.setSelection(Prefs.getInt(CommonMethod.SELECT_MONTH));

          }
          if(Prefs.getInt(CommonMethod.SELECT_YEAR)>0)
          {
              sprYear.setSelection(Prefs.getInt(CommonMethod.SELECT_YEAR));
          }



        setYearDataIntoSpinner();
        setMonthDataIntoSpinner();
    }

    private void createWeekSpinner(Calendar calendar) {
        List<CommonMethod.WeekList> listWeek = CommonMethod.createWeeksFromMonth(calendar);
        setWeekDataIntoSpinner(listWeek);
    }


    private void setWeekDataIntoSpinner(List<CommonMethod.WeekList> listWeek) {
        Observable.just(listWeek).map(new Function<List<CommonMethod.WeekList>, List<String>>() {
            @Override
            public List<String> apply(List<CommonMethod.WeekList> weekLists) throws Exception {
                return createStringsFromWeek(weekLists);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> list) {
                        weekArray = list.toArray(new String[list.size()]);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, weekArray);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sprWeek.setAdapter(dataAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private List<String> createStringsFromWeek(List<CommonMethod.WeekList> listWeek) {
        List<String> list = new ArrayList<>();
        list.add("SELECT WEEK");
        weekListHashMap = new HashMap<>();
        for (CommonMethod.WeekList item :
                listWeek) {
            String s = item.getDates().get(0) + " - " + item.getDates().get(item.getDates().size() - 1);
            weekListHashMap.put(s, item);
            list.add(s);
        }
        return list;
    }


    private void setMonthDataIntoSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getMonthData());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprMonth.setAdapter(dataAdapter);
    }


    private String[] getMonthData() {
        return months = new String[]{"SELECT MONTH", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCt", "NOV", "DEC"};
    }


    private void setYearDataIntoSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, getCurrentYear());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprYear.setAdapter(dataAdapter);
    }

    private void setOnClickListener() {
        rgbtnDate.setOnCheckedChangeListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        sprWeek.setOnItemSelectedListener(this);
        sprMonth.setOnItemSelectedListener(this);
        sprYear.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rbtn_month:
                sprWeek.setVisibility(View.GONE);
                sprMonth.setVisibility(View.VISIBLE);

                break;
            case R.id.rbtn_year:
                sprWeek.setVisibility(View.GONE);
                sprYear.setVisibility(View.VISIBLE);
                sprMonth.setVisibility(View.GONE);
                break;
            case R.id.rbtn_week:
                sprWeek.setVisibility(View.VISIBLE);
                sprMonth.setVisibility(View.VISIBLE);
                break;
            default:
                break;


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (validate()) {
                    if (rbtnWeek.isChecked())
                        listener.onItemSelect(selectWeek, selectedMonth, selectedYear);
                    else if(rbtnMonth.isChecked()) {
                        listener.onItemSelect(selectedMonth, selectedYear);
                    }
                   else if (rbtnYear.isChecked()) {
                        listener.onItemSelect(selectedYear);
                    }

                    getDialog().dismiss();
                }

                break;
            case R.id.btnCancel:
                getDialog().dismiss();
                break;

            default:
                break;
        }
    }

    private boolean validate() {
        boolean isValid = true;
             if (selectedYear <= 0 ) {
                 showError(sprYear, "Please select year");
                 isValid = false;
             }


              if (selectedMonth < 0 && !rbtnYear.isChecked()) {
                  showError(sprMonth, "Please select month");
                  isValid = false;
              }

              if (selectWeek==null && rbtnWeek.isChecked()) {
                  showError(sprWeek, "Please select week");
                  isValid = false;
              }

        return isValid;
          }






    private void showError(Spinner sprMonth, String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {
            case R.id.spr_year:
                if (i == 0)
                    break;
                selectedYear = Integer.parseInt(years[i]);
                calendar.set(Calendar.YEAR, selectedYear);
                Prefs.putInt(CommonMethod.SELECT_YEAR, selectedYear);
                break;
            case R.id.spr_month:
                if (i == 0)
                    break;
                selectedMonth = i - 1;
                calendar.set(Calendar.MONTH, selectedMonth);
                Prefs.putInt(CommonMethod.SELECT_MONTH, selectedMonth);
                createWeekSpinner(calendar);
                break;
            case R.id.spr_week:
                selectWeek = weekListHashMap.get(weekArray[i]);

                break;
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnItemClickedListener {


        void onItemSelect(CommonMethod.WeekList selectWeek, int months, Integer years);

        void onItemSelect(int selectedMonth, Integer selectedYear);

        void onItemSelect(Integer selectedYear);
    }




}
