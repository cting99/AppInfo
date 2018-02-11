package cting.com.appinfo.activities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cting.com.appinfo.R;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.model.AppInfoItem;
import cting.com.appinfo.searchable.SearchableRecyclerAdapter;
import cting.com.appinfo.utils.FileHelper;

public class AppListActivity extends BaseSearchableListActivity {
    private static final String TAG = "cting/appinfo/listAct";
    private static final String FILE_NAME = Environment.getExternalStorageDirectory() + "/app_list.txt";


    @Override
    protected void export() {

        if (mAdapter != null) {
            List<AppInfoItem> list = mAdapter.getDataList();
            StringBuilder sb = new StringBuilder();
            for (AppInfoItem appInfo : list) {
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
    }

    @Override
    protected SearchableRecyclerAdapter getAdapter() {
        return new MyAdapter(this, AppDatas.getAllInstalledList(this));
    }


    private class MyAdapter extends SearchableRecyclerAdapter<AppInfoItem,AppInfoItemBinding> {

        public MyAdapter(Context context, List<AppInfoItem> dataList) {
            super(context, dataList);
        }

        @Override
        public int getLayoutId() {
            return R.layout.app_info_item;
        }

        @Override
        public void bindData(AppInfoItemBinding binding, AppInfoItem item) {
            binding.setAppinfo(item);
            binding.setAdapter(this);
            binding.executePendingBindings();

        }
    }
}
