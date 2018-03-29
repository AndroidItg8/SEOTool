package itg8.com.seotoolapp.common;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtility {


    private static RetroController controller;


    public NetworkUtility(NetworkBuilder builder) {
        controller = Retro.getInstance().getController(builder.token);
    }

    public void login(String url, String username, String password, final ResponseListener listener) {
        if (listener == null)
            throwNullPointer();
        Observable<ResponseBody> b = controller.checkLogin(url, username, password);
        b.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String response = responseBody.string();
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

    public void getkeyWordList(String url, String dateFrom, String dateTo, String projectId, final ResponseListener listener) {
        if (listener == null) {
            throwNullPointer();
        }
        Call<List<KeyWordModel>> call = controller.getKeyWordStatusList(url, dateFrom, dateTo, projectId);
        call.enqueue(new Callback<List<KeyWordModel>>() {
            @Override
            public void onResponse(Call<List<KeyWordModel>> call, Response<List<KeyWordModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listener.onSuccess(response.body());
                    } else {
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
        if (listener == null)
            throwNullPointer();
        Observable<ResponseBody> bodyObservable = controller.checkOtp();

    }

    public void getTrafficCategory(String url, String fromDate, String toDate, String projectId, final ResponseListener listener) {
        if (listener == null) {
            throwNullPointer();
        }
        Call<List<TrafficModel>> call = controller.getTrafficBetweenDate(url, fromDate, toDate, projectId);
        call.enqueue(new Callback<List<TrafficModel>>() {
            @Override
            public void onResponse(Call<List<TrafficModel>> call, Response<List<TrafficModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        listener.onSuccess(response.body());
                } else {
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

//    public void getSocialMediaLinks(String url, String toDate, String fromDate, String projectId, final ResponseListener listener) {
//        if(listener==null)
//        {
//            throwNullPointer();
//        }
//        Call<List<SocialMediaModel>> call = controller.getSocialMediaBetween(url,toDate,fromDate,projectId );
//        call.enqueue(new Callback<List<SocialMediaModel>>() {
//            @Override
//            public void onResponse(Call<List<SocialMediaModel>> call, Response<List<SocilaMediaModel>> response) {
//                if(response.isSuccessful())
//                {
//                    if(response.body()!=null)
//                        listener.onSuccess(response.body());
//                }
//                else
//                {
//                    listener.onFailure(response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<SocilaMediaModel>> call, Throwable t) {
//                listener.onFailure(t.getMessage());
//                t.printStackTrace();
//            }
//        });
//
//
//    }

    public void getExternalLinksData(String url, String dateTo, String dateFrom, String projectId, int type, final ResponseListener listener) {
        if (listener == null) {
            throwNullPointer();
        }
        Call<List<ExternalLinksModel>> call = controller.getExternalLinksBetween(url, dateTo, dateFrom, projectId, type);
        call.enqueue(new Callback<List<ExternalLinksModel>>() {
            @Override
            public void onResponse(Call<List<ExternalLinksModel>> call, Response<List<ExternalLinksModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<List<ExternalLinksModel>> call, Throwable t) {
                listener.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void changePasscode(String url, String userId, String passcode, String passcodeConfirm, final ResponseListener listener) {
        if (listener == null)
            throwNullPointer();
        Call<ResponseBody> call = controller.changePasscode(url, userId, passcode, passcodeConfirm);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                if (responseBody != null) {

                    JSONObject object = null;

                        object = new JSONObject(responseBody);

                    if (object.has("msg")) {
                        try {
                            if (object.get("msg").equals("Password changed successfully")) {

                                listener.onSuccess(responseBody);
                            } else {
                                listener.onFailure(responseBody);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


    public interface ResponseListener {
        void onSuccess(Object message);

        void onFailure(Object err);

        void onSomethingWrong(Object e);
    }

    public static final class NetworkBuilder {
        String token;

        public NetworkBuilder setHeader() {
            token = Prefs.getString(CommonMethod.USER_ID, null);
            return this;
        }

        public NetworkUtility build() {
            return new NetworkUtility(this);
        }
    }
}
