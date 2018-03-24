package itg8.com.seotoolapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by swapnilmeshram on 22/03/18.
 */

public class CommonMethod {


    public static final String TOKEN = "myToken";
    public static final String BASE_URL = "http://192.168.1.55";
    public static final String SECRET_KEY = "sValue";
    public static final String P_KEY = "passwordKey";
    public static final String SELECT_YEAR = "SELECT_YEAR";
    public static final String SELECT_MONTH = "SELECT_MONTH";
    private static final java.lang.String DATE_FORMAT = "MM-yyyy";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(CommonMethod.DATE_FORMAT, Locale.getDefault());
    private static final java.lang.String DATE_FORMAT_CURRENT = "yyyy-MM-dd";
    public static SimpleDateFormat dateFormatCurrent = new SimpleDateFormat(CommonMethod.DATE_FORMAT_CURRENT, Locale.getDefault());


    @SuppressLint("MissingPermission")
    public static String getMyPhoneNO(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(tMgr==null)
            return null;
        //tMgr.getSimSerialNumber()-> this is unique sim id
        String mPhoneNumber=null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            mPhoneNumber = tMgr.getDeviceId();
        }else {
            mPhoneNumber=tMgr.getImei();
        }
//        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return mPhoneNumber;
    }



    public static List<WeekList> createWeeksFromMonth(Calendar calendar) {
        int startDate = calendar.getFirstDayOfWeek();
        int minimalDay = calendar.getMinimalDaysInFirstWeek();
//        Log.d(TAG, "createWeeksFromMonth: "+calendar.getTime());
//        Log.d(TAG, "createWeeksFromMonth: "+startDate);
//        Log.d(TAG, "createWeeksFromMonth: "+minimalDay);
////        Log.d(TAG, "createWeeksFromMonth: "+calendar.get(Calendar.SUNDAY));
//        Log.d(TAG, "createWeeksFromMonth: "+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Log.d(TAG, "createWeeksFromMonth: "+calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));

        List<WeekList> days = new ArrayList<>();
        List<Integer> dates = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        WeekList list = null;
        int weeks = 0;
        do {
            String day = (String) DateFormat.format("EEEE", calendar.getTime());
            if (list == null) {
                list = new WeekList();
                list.setWeek(++weeks);
            }
            dates.add(calendar.get(Calendar.DAY_OF_MONTH));

            if (day.equalsIgnoreCase("Sunday")) {
                list.setDates(dates);
                days.add(list);
                list = null;
                dates = new ArrayList<>();
            }
//            Log.d(TAG, "createWeeksFromMonth: Day:"+);
            calendar.add(Calendar.DATE, 1);
        }
        while ((calendar.get(Calendar.DAY_OF_MONTH) < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
        if (list == null) {
            list = new WeekList();
            list.setWeek(++weeks);
        }
        dates.add(calendar.get(Calendar.DAY_OF_MONTH));
        list.setDates(dates);
        days.add(list);
//        Log.d(TAG, "createWeeksFromMonth: "+new Gson().toJson(list));
        return days;
    }

    public static void createMonthsFromYear(Calendar calendar) {

    }

    public static String getCurrentDateString() {
        String newDate = "";
        try {
            newDate = dateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static String getCurrentDateToString()
    {
        String newDate = "";
        try {
            newDate = dateFormatCurrent.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }   public static String getMonthDateToString(Calendar calendar)
    {
        String newDate = "";
        try {
            newDate = dateFormatCurrent.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static Calendar getThisMonth() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);

        return calendar;
    }
    public static Calendar getThisMonthLast() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    public static int dpTopx(int dp, Context context) {

        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static class WeekList {
        int week;
        List<Integer> dates;

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public List<Integer> getDates() {
            return dates;
        }

        public void setDates(List<Integer> dates) {
            this.dates = dates;
        }
    }


}
