package itg8.com.seotoolapp.login.mvp;

import android.text.TextUtils;
import android.view.View;

import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.BaseWeakPresenter;


/**
 * Created by itg_Android on 9/6/2017.
 */

public class LoginPresenterImp extends BaseWeakPresenter implements LoginMVP.LoginListener,LoginMVP.LoginPresenter {

    LoginMVP.LoginModule module;
    public LoginPresenterImp(LoginMVP.LoginView view) {
        super(view);
        module=new LoginModuleImp();
    }

    @Override
    public void onFirstTimeSuccess() {
        if(hasView()){
            getLoginView().hideProgress();
            getLoginView().onFirstTimeLogin();
        }
    }

    @Override
    public void onDestroy() {
        detactView();
        module.onDestroy();
    }

    @Override
    public void onLoginClicked(View view) {
        if(hasView()){
            boolean isValid=true;
            String password=getLoginView().getPassword();
            String username=getLoginView().getUsername();
            if(TextUtils.isEmpty(username)){
                isValid=false;
                getLoginView().onUsernameInvalid(view.getContext().getString(R.string.empty_mobile));
            }
            if(TextUtils.isEmpty(password)){
                isValid=false;
                getLoginView().onPasswordInvalid(view.getContext().getString(R.string.empty_password));
            }
            if(!isValid)
                return;
            if(username.length()!=10){
                isValid=false;
                getLoginView().onUsernameInvalid(view.getContext().getString(R.string.invalid_mobile));
            }
            if(password.length()!=4){
                isValid=false;
                getLoginView().onPasswordInvalid(view.getContext().getString(R.string.invalid_pass));
            }
            if(isValid){
                getLoginView().showProgress();
                module.onStartCall(view.getContext().getString(R.string.url_login),username,password,this);
            }
        }
    }


    @Override
    public void onSuccess() {
        if(hasView()){
            getLoginView().hideProgress();
            getLoginView().onSuccess();
        }
    }

    @Override
    public void onFail(String message) {
        if(hasView()) {
            getLoginView().hideProgress();
            getLoginView().onFail(message);
        }
    }

    @Override
    public void onError(Object t) {
        if(hasView()) {
            getLoginView().hideProgress();
            getLoginView().onError(t);
        }
    }

    private LoginMVP.LoginView getLoginView(){
        return (LoginMVP.LoginView) getView();
    }
}
