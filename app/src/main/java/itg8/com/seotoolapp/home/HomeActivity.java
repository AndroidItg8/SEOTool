package itg8.com.seotoolapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.common.Prefs;
import itg8.com.seotoolapp.external_links.ExternalLinksFragment;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.keyword.KeyWordFragment;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.login.LoginActivity;
import itg8.com.seotoolapp.passcode.ChangePassCodeActivity;
import itg8.com.seotoolapp.social_media.SocialMediaFragment;
import itg8.com.seotoolapp.splash.SplashActivity;
import itg8.com.seotoolapp.traffic.TrafficDetailsActivity;
import itg8.com.seotoolapp.traffic.TrafficFragment;
import itg8.com.seotoolapp.traffic.controller.HomeController;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private static final String TAG = "HomeActivity";

    private HomeController.TrafficFragmentListener trafficFragmentListener;
    private HomeController.KeyWordFragmentListener keywordFragmentListener;
    private HomeController.ExternalLinksFragmentListener externalLinksFragmentListener;
    private HomeController.SocialMediaFragmentListener socialMediaFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        checkLogin();
        setSupportActionBar(toolbar);



        init();

        downloadReleatedData();
    }

    private void checkLogin() {
        if (Prefs.getString(CommonMethod.USER_ID,null) == null) {
            callLoginActivity();
            finish();
        }
    }

    private void callLoginActivity() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    private void downloadReleatedData() {
        downloadSocialMediaLinksData();


    }

    private void downloadSocialMediaLinksData() {
        Log.d(TAG, "downloadSocialMediaLinksData: "+CommonMethod.getThisMonth());
        Log.d(TAG, "downloadSocialMediaLinksDataLast : "+CommonMethod.getThisMonthLast());
        new NetworkUtility.NetworkBuilder().build().getExternalLinksData(getString(R.string.url_external_links),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonth()),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonthLast()),
                "1",1, new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        if(socialMediaFragmentListener!=null)
                            socialMediaFragmentListener.onSocMediaAvail((List) message);
                    }
                    @Override
                    public void onFailure(Object err) {
                        if(socialMediaFragmentListener!=null)
                            socialMediaFragmentListener.onDownloadFail();
                    }
                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });

    }



    private void downloadExternalLinksData(final int type) {
        final int types  ;
        if(type== CommonMethod.EXTERNAL_LINKS)
            types = CommonMethod.EXTERNAL_LINKS;
        else
            types = CommonMethod.SOCIAL_MEDIA;


        new NetworkUtility.NetworkBuilder().build().getExternalLinksData(getString(R.string.url_external_links),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonth()),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonthLast()),
                "1",types, new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        if(type==CommonMethod.EXTERNAL_LINKS)
                            sortExternalLinksForSession((List<? extends ExternalLinksModel>) message, CommonMethod.EXTERNAL_LINKS);
                        else
                            sortExternalLinksForSession((List<? extends ExternalLinksModel>) message, CommonMethod.SOCIAL_MEDIA);

                    }

                    @Override
                    public void onFailure(Object err) {
                         externalLinksFragmentListener.onDownloadFail(err.toString(), type);
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });

    }

    private void sortExternalLinksForSession(final List<? extends ExternalLinksModel> list, final int type) {
        Observable.create(new ObservableOnSubscribe<HashMap<String, List<ExternalLinksModel>>>() {
            @Override
            public void subscribe(ObservableEmitter<HashMap<String, List<ExternalLinksModel>>> e) throws Exception {
                HashMap<String, List<ExternalLinksModel>> hashMapMonthDates = CommonMethod.getMonthHashMapForExtLinks(Calendar.getInstance());
                int value=0;
                for (ExternalLinksModel model :list
                     ) {

                    hashMapMonthDates.get(model.getExlinkmaster().getDateof()).add(model);


                }


                e.onNext(hashMapMonthDates);
                e.onComplete();
            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HashMap<String, List<ExternalLinksModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashMap<String, List<ExternalLinksModel>> stringListHashMap) {
                if(type== CommonMethod.EXTERNAL_LINKS)
                externalLinksFragmentListener.onExtLinkAvail(stringListHashMap, CommonMethod.EXTERNAL_LINKS);
                else
                    externalLinksFragmentListener.onExtLinkAvail(stringListHashMap, CommonMethod.SOCIAL_MEDIA);

            }

            @Override
            public void onError(Throwable e) {
                if(type== CommonMethod.EXTERNAL_LINKS)
                    externalLinksFragmentListener.onDownloadFail(e.getMessage(), CommonMethod.EXTERNAL_LINKS);
                else
                    externalLinksFragmentListener.onDownloadFail(e.getMessage(), CommonMethod.SOCIAL_MEDIA);


            }

            @Override
            public void onComplete() {

            }
        });




    }

    private void downloadDashBoardRelatedData() {
        new NetworkUtility.NetworkBuilder().build().getTrafficCategory(getString(R.string.url_traffic_category),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonth()),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonthLast()),
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        if(trafficFragmentListener!=null)
                            trafficFragmentListener.onListOfCategoryAvailable((List<? extends TrafficModel>) message);

                    }

                    @Override
                    public void onFailure(Object err) {
                        if(trafficFragmentListener!=null)
                            trafficFragmentListener.onListDownloadFail();
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }

    private void downloadKeyWordReleatedData() {

        new NetworkUtility.NetworkBuilder().build().getkeyWordList(getString(R.string.url_kewwird),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonth()),
                CommonMethod.getMonthDateToString(CommonMethod.getThisMonthLast()),
                "2",
                new NetworkUtility.ResponseListener() {
                    @Override
                    public void onSuccess(Object message) {
                        keywordFragmentListener.onKeywordDetailAvailable((List<? extends KeyWordModel>) message);

                    }

                    @Override
                    public void onFailure(Object err) {
                        keywordFragmentListener.onDownloadFail();
                    }

                    @Override
                    public void onSomethingWrong(Object e) {

                    }
                });
    }

    private void init() {
        setTabLayout();
    }

    private void setTabLayout() {

        setupViewPager(container);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(container);
    }

    private void setupViewPager(ViewPager container) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrafficFragment(), "Dashboard");
        adapter.addFragment(new KeyWordFragment(), "Keyword Status");
        adapter.addFragment( ExternalLinksFragment.newInstance(CommonMethod.EXTERNAL_LINKS), "Submission");
        adapter.addFragment( ExternalLinksFragment.newInstance(CommonMethod.SOCIAL_MEDIA), "Social");
        container.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_logout:
                Prefs.clear();
                finish();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case R.id.action_change_password:
                finish();
                startActivity(new Intent(HomeActivity.this, ChangePassCodeActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    public void setTrafficFragmentListener(HomeController.TrafficFragmentListener listener) {
        trafficFragmentListener = listener;
        downloadDashBoardRelatedData();
    }

    public void setKeywordFragmentListener(HomeController.KeyWordFragmentListener keywordFragmentListener) {
        this.keywordFragmentListener = keywordFragmentListener;
        downloadKeyWordReleatedData();


    }

    public void setExternalLinksFragmentListener(HomeController.ExternalLinksFragmentListener externalLinksFragmentListener, int type) {
        this.externalLinksFragmentListener = externalLinksFragmentListener;
        if(type==CommonMethod.EXTERNAL_LINKS)
        downloadExternalLinksData(CommonMethod.EXTERNAL_LINKS);
        else
            downloadExternalLinksData(CommonMethod.SOCIAL_MEDIA);
    }

    public void setSocialMediaFragmentListener(HomeController.SocialMediaFragmentListener socialMediaFragmentListener) {
        this.socialMediaFragmentListener = socialMediaFragmentListener;
        downloadExternalLinksData(CommonMethod.SOCIAL_MEDIA);

    }


    public void startTrafficDetail(HashMap<Trafficcategorymaster, List<TrafficModel>> list) {
        Intent intent = new Intent(HomeActivity.this, TrafficDetailsActivity.class);
        intent.putExtra(CommonMethod.TRAFFIC_DETAILS, list);
        startActivity(intent);
    }
}
