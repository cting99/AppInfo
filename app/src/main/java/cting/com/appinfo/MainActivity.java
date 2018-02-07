package cting.com.appinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.model.AppInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "cting/appinfo/main";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<AppInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mList = AppDatas.getAllInstalledList(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        AppInfoRecyclerAdapter adapter = new AppInfoRecyclerAdapter(this,mList);
        mRecyclerView.setAdapter(adapter);

    }

}
