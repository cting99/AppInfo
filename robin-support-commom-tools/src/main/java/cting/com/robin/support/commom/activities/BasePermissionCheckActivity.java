package cting.com.robin.support.commom.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cting.com.robin.support.commom.utils.PermissionHelper;

public class BasePermissionCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionHelper.getInstance().checkPermission(this, getRequestPermission());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.getInstance().onPermissionResult(this,requestCode, permissions, grantResults);
    }

    protected String[] getRequestPermission() {
        return PermissionHelper.REQUEST_PERMISSIONS;
    }
}
