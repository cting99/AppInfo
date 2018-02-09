package cting.com.appinfo.dataprovider;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cting.com.appinfo.model.AppInfo;

/**
 * Created by cting on 2018/2/6.
 */

public class AppDatas {

    public static ArrayList<AppInfo> getAllInstalledList(Context context) {
        ArrayList<AppInfo> list = new ArrayList<>();
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> installedApk = packageManager.getInstalledPackages(0);
        ApplicationInfo applicationInfo;
        AppInfo appInfo;
        for (PackageInfo packageInfo : installedApk) {
            applicationInfo = packageInfo.applicationInfo;
            appInfo = new AppInfo(packageInfo.packageName,
                    String.valueOf(applicationInfo.loadLabel(packageManager)),
                    packageInfo.versionName,
                    applicationInfo.loadIcon(packageManager),
                    applicationInfo.sourceDir);
            list.add(appInfo);
        }

        Collections.sort(list, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }

        });
        return list;
    }

}
