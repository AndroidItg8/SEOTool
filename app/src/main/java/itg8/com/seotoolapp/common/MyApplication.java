package itg8.com.seotoolapp.common;

import android.app.Application;
//import android.arch.persistence.room.Room;


/**
 * Created by swapnilmeshram on 15/03/18.
 */

public class MyApplication extends Application {

    private static final String PREF_NAME="PARENT_APP";
    private static final String DB_NAME = "ParentApp";
    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mInstance.initPref();
    }

//    public AppDatabase getDatabase(){
//        return Room.databaseBuilder(this,AppDatabase.class,DB_NAME).build();
//    }


    public String getMyString(int resId){
        return getString(resId);
    }

    private void initPref() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(PREF_NAME)
                .setUseDefaultSharedPreference(false)
                .build();
    }


}
