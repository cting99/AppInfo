package cting.com.appinfo.dataprovider;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cting.com.appinfo.model.AppInfoItem;

/**
 * Created by cting on 2018/2/6.
 */

public class AppDatas {

    public static ArrayList<AppInfoItem> getAllInstalledList(Context context) {
        ArrayList<AppInfoItem> list = new ArrayList<>();
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> installedApk = packageManager.getInstalledPackages(0);
        ApplicationInfo applicationInfo;
        AppInfoItem appInfo;
        for (PackageInfo packageInfo : installedApk) {
            applicationInfo = packageInfo.applicationInfo;
            appInfo = new AppInfoItem(packageInfo.packageName,
                    String.valueOf(applicationInfo.loadLabel(packageManager)),
                    packageInfo.versionName,
                    applicationInfo.loadIcon(packageManager),
                    applicationInfo.sourceDir);
            list.add(appInfo);
        }

        Collections.sort(list, new Comparator<AppInfoItem>() {
            @Override
            public int compare(AppInfoItem o1, AppInfoItem o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }

        });
        return list;
    }

}
