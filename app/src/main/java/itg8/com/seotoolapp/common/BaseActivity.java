package itg8.com.seotoolapp.common;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String PHONE_PERMISSION= Manifest.permission.READ_PHONE_STATE;
    private static final int RC_PHONE_STATE = 101;
    private boolean hasPhonePermission;
    private GrantPhonePermissionListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasPhonePermission=checkPhonePermission();
    }

    private boolean checkPhonePermission() {
        return EasyPermissions.hasPermissions(this,PHONE_PERMISSION);
    }

    @AfterPermissionGranted(RC_PHONE_STATE)
    public void askForPermission(GrantPhonePermissionListener listener){
        if(checkPhonePermission()) {
            hasPhonePermission = true;
            listener.grantedPhonePermission();
            return;
        }
        this.listener = listener;
        EasyPermissions.requestPermissions(this,"You must grant this permission for your login",RC_PHONE_STATE,PHONE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode==RC_PHONE_STATE){
            listener.grantedPhonePermission();
            listener=null;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(requestCode==RC_PHONE_STATE){
            listener.notGrantedPhonePermission();
            listener=null;
        }
    }


    public interface GrantPhonePermissionListener {
        public  void grantedPhonePermission();

        public  void notGrantedPhonePermission();
    }
}
