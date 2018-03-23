package itg8.com.seotoolapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.external_links.ExternalLinksFragment;
import itg8.com.seotoolapp.keyword.KeyWordFragment;
import itg8.com.seotoolapp.social_media.SocialMediaFragment;
import itg8.com.seotoolapp.traffic.TrafficDetailsActivity;
import itg8.com.seotoolapp.traffic.TrafficFragment;

import itg8.com.seotoolapp.splash.SplashActivity;
import itg8.com.seotoolapp.traffic.controller.HomeController;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.fab)
    FloatingActionButton fab;
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
        setSupportActionBar(toolbar);
        startActivity(new Intent(this,SplashActivity.class));
        init();


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
        adapter.addFragment(new ExternalLinksFragment(), "Submission");
        adapter.addFragment(new SocialMediaFragment(), "Social");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTrafficFragmentListener(HomeController.TrafficFragmentListener listener) {
        trafficFragmentListener=listener;
    }

    public void setKeywordFragmentListener(HomeController.KeyWordFragmentListener keywordFragmentListener) {
        this.keywordFragmentListener = keywordFragmentListener;
    }

    public void setExternalLinksFragmentListener(HomeController.ExternalLinksFragmentListener externalLinksFragmentListener) {
        this.externalLinksFragmentListener = externalLinksFragmentListener;
    }

    public void setSocialMediaFragmentListener(HomeController.SocialMediaFragmentListener socialMediaFragmentListener) {
        this.socialMediaFragmentListener = socialMediaFragmentListener;
    }


    public void startTrafficDetail() {
        startActivity(new Intent(HomeActivity.this, TrafficDetailsActivity.class));
    }
}
