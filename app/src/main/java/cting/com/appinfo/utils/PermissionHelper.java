package cting.com.appinfo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cting on 2018/2/10.
 */

public class PermissionHelper {
    public static final String TAG = "cting/util/permission";

    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String[] REQUEST_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final PermissionHelper ourInstance = new PermissionHelper();

    public static PermissionHelper getInstance() {
        return ourInstance;
    }

    private PermissionHelper() {
    }

    public void checkPermission(Activity activity, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionNeedGranted = new ArrayList<>();
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission)== PackageManager.PERMISSION_DENIED) {
                    permissionNeedGranted.add(permission);
                }
            }
            int notGrantCount = permissionNeedGranted.size();
            if (notGrantCount > 0) {
                activity.requestPermissions(permissionNeedGranted.toArray(new String[notGrantCount]), REQUEST_PERMISSION_CODE);
            }
        }
    }




    public void onPermissionResult(Activity activity,
                                   int requestCode, String[] permissions, int[] grantResults) {
        if (REQUEST_PERMISSION_CODE == requestCode) {
            int permissionCount = permissions.length;
            for (int i = 0; i < permissionCount; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Log.w(TAG, "onRequestPermissionsResult: " + permissions[i] + " not granted");
                    Toast.makeText(activity, "need permission " + permissions[i], Toast.LENGTH_LONG).show();
                    activity.finish();
                }
            }
        }
    }
}
