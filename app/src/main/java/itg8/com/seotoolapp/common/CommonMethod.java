package itg8.com.seotoolapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by swapnilmeshram on 22/03/18.
 */

public class CommonMethod {


    public static final String TOKEN = "myToken";
    public static final String BASE_URL = "sdfsdf";
    public static final String SECRET_KEY = "sValue";
    public static final String P_KEY = "passwordKey";

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
