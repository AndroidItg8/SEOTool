package itg8.com.seotoolapp.login.mvp;


import org.json.JSONException;
import org.json.JSONObject;

import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.NetworkUtility;
import itg8.com.seotoolapp.common.Prefs;


class LoginModuleImp implements LoginMVP.LoginModule {
    @Override
    public void onStartCall(String url, String username, String password, final LoginMVP.LoginListener listener) {
        new NetworkUtility.NetworkBuilder().build().login(url,username, password, new NetworkUtility.ResponseListener() {
            @Override
            public void onSuccess(Object message) {
                    String response= (String) message;
                if(response!=null){
                    try {
                        JSONObject object=new JSONObject(response);
                        if(object.has("flag")) {
                            if (object.get("flag").equals("1") ) {
                                if(object.get("firsttimelogin").equals("1")) {
//                                Prefs.putString(CommonMethod.TOKEN, object.getString("userid"));
                                    Prefs.putString(CommonMethod.USER_ID, object.getString("userid"));
                                    Prefs.putString(CommonMethod.PROJECT_ID, object.getString("projectid"));
                                    listener.onFirstTimeSuccess();
                                }else
                                    Prefs.putString(CommonMethod.USER_ID, object.getString("userid"));
                                    listener.onSuccess();
                            }
                            else
                            {
                                listener.onFail(object.getString("error_description"));
                            }
                        }else {
                            listener.onFail("Invalid Credential");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError(e);
                    }

                }
            }

            @Override
            public void onFailure(Object err) {
                listener.onError(err);

            }

            @Override
            public void onSomethingWrong(Object e) {
                listener.onError(e);
            }
        });
    }





    @Override
    public void onDestroy() {

    }
}
