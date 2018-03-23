package itg8.com.seotoolapp.common;


import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public  class NetworkUtility {


    public void login(String url, String username, String password, final ResponseListener listener) {
        if(listener==null)
           throwNullPointer();
        Observable<ResponseBody> b=controller.checkLogin(url,username,password);
        b.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String response=responseBody.string();
                            listener.onSuccess(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            listener.onSomethingWrong(e);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void throwNullPointer() {
        throw new NullPointerException("null provided in NetworkUtility listner");
    }

    public void checkOtp(String otp, ResponseListener listener) {
        if(listener==null)
            throwNullPointer();
        Observable<ResponseBody> bodyObservable=controller.checkOtp();

    }

    public interface ResponseListener{
        void onSuccess(Object message);
        void onFailure(Object err);
        void onSomethingWrong(Object e);
    }

    private static RetroController controller;




    public NetworkUtility(NetworkBuilder builder) {
        controller= Retro.getInstance().getController(builder.token);
    }

    public static final class NetworkBuilder {
            String token;
        public NetworkBuilder setHeader(){
            token= Prefs.getString(CommonMethod.TOKEN,null);
            return this;
        }

        public NetworkUtility build(){
            return new NetworkUtility(this);
        }
    }
}
