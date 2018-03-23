package itg8.com.seotoolapp.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Devrath on 23-03-2016.
 */
public class UtilSnackbar {
    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen for few seconds*****************************/
    public static void showSnakbarRedColor(View rootView, String mMessage) {
        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .show();
    }

    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen*****************************/
    public static void showSnakbarTypeTwo(View rootView, String mMessage) {

        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null)
                .show();

    }

    /************************************ ShowSnackbar without message, KeepItDisplayedOnScreen, OnClickOfOk restrat the activity*****************************/
    public static void showSnakbarTypeThree(View rootView, final OnSnackbarActionClickListener listener) {

        Snackbar
                .make(rootView, "NoInternetConnectivity", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onRetryClicked();
                    }
                })
                .setActionTextColor(Color.CYAN)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                    }
                })
                .show();

    }

    /************************************ ShowSnackbar with message, KeepItDisplayedOnScreen, OnClickOfOk restrat the activity*****************************/
    public static void showSnakbarTypeFour(View rootView, final Activity activity, String mMessage) {

        Snackbar
                .make(rootView, mMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setActionTextColor(Color.CYAN)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                    }
                })
                .show();

    }


    public interface OnSnackbarActionClickListener{
        void onRetryClicked();
    }
}