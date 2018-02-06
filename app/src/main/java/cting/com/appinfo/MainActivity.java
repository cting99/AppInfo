package cting.com.appinfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_message)
    TextView mTextMessage;
    @BindView(R.id.scroll_container)
    ScrollView mScrollContainer;


    /*@BindView(R.id.message)
    private TextView mTextMsg;

    private ScrollView mScrollView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent();
        List<PackageInfo> installedApk = packageManager.getInstalledPackages(0);
        for (PackageInfo info : installedApk) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(info.packageName);
            setMessage(info.packageName + "\n");

        }

    }

    private void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTextMessage.append(message);
            mScrollContainer.scrollTo(0, mScrollContainer.getBottom());
        }
    }
}
