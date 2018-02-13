package cting.com.appinfo.activities;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cting.com.appinfo.R;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.fragments.AppListFragment;
import cting.com.appinfo.model.AppInfoItem;

public class AppListActivity extends BasePermissionCheckActivity {

    private static final String TAG = "cting/appinfo/act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<AppInfoItem> dataList = AppDatas.getAllInstalledList(this);
        Log.i(TAG, "onCreate: " + dataList);
        AppListFragment fragment = AppListFragment.newInstance(dataList);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }


}
