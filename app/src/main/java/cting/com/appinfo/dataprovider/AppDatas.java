package cting.com.appinfo.dataprovider;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cting.com.appinfo.model.AppInfoItem;

public class AppDatas {

    public static final String TAG = "cting/data/appinfo";

    public static ArrayList<AppInfoItem> getAllInstalledList(Context context) {
        long time = SystemClock.currentThreadTimeMillis();
        ArrayList<AppInfoItem> list = new ArrayList<>();
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> installedApk = packageManager.getInstalledPackages(0);
        ApplicationInfo applicationInfo;
        AppInfoItem appInfo;
        for (PackageInfo packageInfo : installedApk) {
            applicationInfo = packageInfo.applicationInfo;
            appInfo = new AppInfoItem();
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setLabel(String.valueOf(applicationInfo.loadLabel(packageManager)));
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setInstallPath(applicationInfo.sourceDir);
            list.add(appInfo);
        }

        Collections.sort(list, new Comparator<AppInfoItem>() {
            @Override
            public int compare(AppInfoItem o1, AppInfoItem o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }

        });
        time = SystemClock.currentThreadTimeMillis() - time;
        Log.i(TAG, "getAllInstalledList: cost " + time + " ms");
        return list;
    }

}
