package itg8.com.seotoolapp.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.Prefs;
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.login.LoginActivity;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.fullImage)
    ImageView fullImage;
    @BindView(R.id.smallImage)
    ImageView smallImage;
    @BindView(R.id.txtSeoAnalysis)
    TextView txtSeoAnalysis;
    private boolean isStopped=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        hide();
        animateOnStart();
    }

    private void animateOnStart() {


        Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.scale_animation);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        smallImage.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                startAnotherAnimation();
                startConstrainint();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    //    private void startAnotherAnimation() {
//        Animation bottomUp = AnimationUtils.loadAnimation(this,
//                R.anim.bottom_up);
//        imgLogo.startAnimation(bottomUp);
//        bottomUp.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startConstrainint();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }

    private void startConstrainint() {

        ConstraintSet constraintSet1 = new ConstraintSet();
        constraintSet1.clone(container);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(container);
            constraintSet1.clear(R.id.imgLogo, ConstraintSet.BOTTOM);
            constraintSet1.connect(R.id.imgLogo, ConstraintSet.BOTTOM, R.id.txtSeoAnalysis, ConstraintSet.TOP, 0);
            constraintSet1.setVisibility(R.id.txtSeoAnalysis, ConstraintSet.VISIBLE);
            constraintSet1.clear(R.id.txtSeoAnalysis, ConstraintSet.TOP);
            constraintSet1.connect(R.id.txtSeoAnalysis, ConstraintSet.TOP, R.id.imgLogo, ConstraintSet.BOTTOM, 0);
            constraintSet1.applyTo(container);
        } else {
            txtSeoAnalysis.setVisibility(View.VISIBLE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isStopped){
                    String userId = Prefs.getString(CommonMethod.USER_ID,null);
                    if(userId==null)
                    {  finish();
                        startLoginActivity();


                    }else
                        finish();
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
            }
        }, 1000);
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStopped=true;

    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }


}
