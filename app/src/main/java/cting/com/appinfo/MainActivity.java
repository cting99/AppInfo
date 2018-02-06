package cting.com.appinfo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.model.AppInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "cting/appinfo/main";

    @BindView(R.id.tv_message)
    TextView mTextMessage;
    @BindView(R.id.scroll_container)
    ScrollView mScrollContainer;

    List<AppInfo> mList;
    private PackageManager mPkgMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mList = AppDatas.getAllInstalledList(this);
        for (AppInfo info : mList) {

            Log.i(TAG, "onCreate: " + info + "\n");
            setMessage(info.getLabel() + ", " + info.getPackageName());
        }

    }

    private void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTextMessage.append(message);
            mTextMessage.append("\n");
            mScrollContainer.scrollTo(0, mScrollContainer.getBottom());
        }
    }
}
