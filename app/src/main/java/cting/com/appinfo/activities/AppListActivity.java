package cting.com.appinfo.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.appinfo.adapter.AppInfoRecyclerAdapter;
import cting.com.appinfo.R;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.model.AppInfo;
import cting.com.appinfo.utils.FileHelper;

public class AppListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "cting/appinfo/listAct";
    private static final String FILE_NAME = Environment.getExternalStorageDirectory() + "/app_list.txt";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<AppInfo> mList;
    private SearchView mSearchView;
    private AppInfoRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        ButterKnife.bind(this);

        mList = AppDatas.getAllInstalledList(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new AppInfoRecyclerAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenu.getActionView();
        mSearchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export:
                StringBuilder sb = new StringBuilder();
                for (AppInfo appInfo : mList) {
                    sb.append(appInfo.getLabel())
                            .append(" ")
                            .append(appInfo.getPackageName())
                            .append(" ")
                            .append(appInfo.getVersionName())
                            .append(" ")
                            .append(appInfo.getInstallPath())
                            .append("\n");
                }
                boolean succeed = FileHelper.exportToFile(sb.toString(), FILE_NAME);
                Toast.makeText(this, "export to " + FILE_NAME + (succeed ? " succeed" : " failed"),
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsItemSelected: export to " + FILE_NAME + (succeed ? " succeed" : " failed"));
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mAdapter != null) {
            mAdapter.startQuery(newText);
        }
        return true;
    }
}
