package cting.com.appinfo.activities;

import android.os.Bundle;

import cting.com.appinfo.R;
import cting.com.appinfo.fragments.AppListFragment;

public class AppListActivity extends BasePermissionCheckActivity {

    private static final String TAG = "cting/appinfo/act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppListFragment fragment = new AppListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }


}
