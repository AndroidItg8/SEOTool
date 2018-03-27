package itg8.com.seotoolapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.TypedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.traffic.model.TrafficModel;

/**
 * Created by swapnilmeshram on 22/03/18.
 */

public class CommonMethod {


    public static final String TOKEN = "myToken";
    public static final String BASE_URL = "https://seo.itechgalaxysolutions.com";
    public static final String SECRET_KEY = "sValue";
    public static final String P_KEY = "passwordKey";
    public static final String SELECT_YEAR = "SELECT_YEAR";
    public static final String SELECT_MONTH = "SELECT_MONTH";
    public static final String TRAFFIC_DETAILS = "TRAFFIC_DETAILS";
    private static final java.lang.String DATE_FORMAT = "MM-yyyy";
    private static final java.lang.String DATE_FORMATS = "dd/MM";
    private static final java.lang.String DATE_FORMAT_CURRENT = "yyyy-MM-dd";
    public static final int EXTERNAL_LINKS = 2;
    public static final int SOCIAL_MEDIA = 1;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(CommonMethod.DATE_FORMAT, Locale.getDefault());
    public static SimpleDateFormat dateFormats = new SimpleDateFormat(CommonMethod.DATE_FORMATS, Locale.getDefault());
    public static SimpleDateFormat dateFormatCurrent = new SimpleDateFormat(CommonMethod.DATE_FORMAT_CURRENT, Locale.getDefault());


    @SuppressLint("MissingPermission")
    public static String getMyPhoneNO(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tMgr == null)
            return null;
        //tMgr.getSimSerialNumber()-> this is unique sim id
        String mPhoneNumber = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            mPhoneNumber = tMgr.getDeviceId();
        } else {
            mPhoneNumber = tMgr.getImei();
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
        List<String> datesString = new ArrayList<>();
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
            datesString.add(getMonthDateToString(calendar));

            if (day.equalsIgnoreCase("Sunday")) {
                list.setDates(dates);
                list.setDatesStrings(datesString);
                days.add(list);
                list = null;
                dates = new ArrayList<>();
                datesString = new ArrayList<>();
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
        list.setDatesStrings(datesString);
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



    public static String getCurrentDateToString() {
        String newDate = "";
        try {
            newDate = dateFormatCurrent.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static String getMonthDateToString(Calendar calendar) {
        String newDate = "";
        try {
            newDate = dateFormatCurrent.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static Calendar getThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar;
    }

    public static Calendar getThisMonthLast() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    public static Calendar getThisMonthNext() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,2);
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    public static int dpTopx(int dp, Context context) {

        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static String convertStringToDate(Integer first, int months, Integer years) {
        String newDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, first);
            calendar.set(Calendar.MONTH, months);
            calendar.set(Calendar.YEAR, years);
            newDate = dateFormatCurrent.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static HashMap<Integer, List<TrafficModel>> getMonthHashMapForTraffic() {
        HashMap<Integer, List<TrafficModel>> hashMap = new HashMap<>();
        hashMap.put(Calendar.JANUARY, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.FEBRUARY, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.MARCH, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.APRIL, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.MAY, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.JUNE, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.JULY, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.AUGUST, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.SEPTEMBER, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.OCTOBER, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.NOVEMBER, new ArrayList<TrafficModel>());
        hashMap.put(Calendar.DECEMBER, new ArrayList<TrafficModel>());
        return hashMap;
    }


    public  static HashMap<String,List<ExternalLinksModel>>  getMonthHashMapForExtLinks(Calendar month)
    {
        HashMap<String, List<ExternalLinksModel>> hashMap = new HashMap<>();
        month.set(Calendar.DAY_OF_MONTH, 1);
        do {
            String date = getMonthDateToString(month);

            hashMap.put(date,new ArrayList());
            month.add(Calendar.DAY_OF_MONTH,1);

        }while ((month.get(Calendar.DAY_OF_MONTH) < month.getActualMaximum(Calendar.DAY_OF_MONTH)));
        String date = getMonthDateToString(month);
        hashMap.put(date,new ArrayList<ExternalLinksModel>());



         return hashMap;

    }


    public static String convertStringDateToDDMM(String dateof) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormatCurrent.parse(dateof));
            return dateFormats.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long convertStringToDate(String dateof) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormatCurrent.parse(dateof));
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static class WeekList {
        int week;
        List<Integer> dates;
        List<String> datesStrings;

        public List<String> getDatesStrings() {
            return datesStrings;
        }

        public void setDatesStrings(List<String> datesStrings) {
            this.datesStrings = datesStrings;
        }

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

    public static class TempYearHashMap {
        int year;
        int value;
        int month;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public TempYearHashMap(int year, int value, int month) {
            this.year = year;
            this.value = value;
            this.month = month;
        }

        public TempYearHashMap(int year, int value) {
            this.year = year;
            this.value = value;
        }

        public TempYearHashMap() {
        }

        public int getValue() {

            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getYear() {

            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }


}
