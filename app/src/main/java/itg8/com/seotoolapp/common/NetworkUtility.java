package itg8.com.seotoolapp.common;


import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



    public  void getkeyWordList(String url , String dateFrom , String dateTo, String projectId ,final ResponseListener listener)
    {
        if(listener==null)
        {
            throwNullPointer();
        }
         Call<List<KeyWordModel>> call = controller.getKeyWordStatusList(url, dateFrom, dateTo, projectId);
        call.enqueue(new Callback<List<KeyWordModel>>() {
            @Override
            public void onResponse(Call<List<KeyWordModel>> call, Response<List<KeyWordModel>> response) {
                 if(response.isSuccessful())
                 {
                     if(response.body()!=null)
                     {
                         listener.onSuccess(response.body());
                     }else
                     {
                         listener.onFailure(response.message());
                     }
                 }

            }

            @Override
            public void onFailure(Call<List<KeyWordModel>> call, Throwable t) {
                listener.onFailure(t.getMessage());
                t.printStackTrace();
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

    public void getTrafficCategory(String url, String fromDate, String toDate, String projectId, final ResponseListener listener) {
        if(listener==null)
        {
            throwNullPointer();
        }
        Call<List<TrafficModel>> call = controller.getTrafficBetweenDate(url,fromDate,toDate,projectId );
        call.enqueue(new Callback<List<TrafficModel>>() {
            @Override
            public void onResponse(Call<List<TrafficModel>> call, Response<List<TrafficModel>> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!=null)
                        listener.onSuccess(response.body());
                }
                else
                {
                    listener.onFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<List<TrafficModel>> call, Throwable t) {
                listener.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
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
