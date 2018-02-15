package cting.com.appinfo.fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.R;
import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.dataprovider.AppDatas;
import cting.com.appinfo.model.AppInfoItem;
import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.appinfo.utils.JSONHelper;
import cting.com.robin.support.searchablerecyclerview.LoaderFragment;

public class AppListFragment extends LoaderFragment<AppInfoItem, AppInfoItemBinding> {

    private static final String TAG = "cting/appinfo/fragment";
    private static final String FILE_NAME = FileHelper.DIR + "app_list.txt";
    private static final String FILE_NAME_JSON = FileHelper.DIR + "app_list.json";
    private static final boolean DEBUG_GSON = true;


    private PackageManager mPkgMgr;

    public AppListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPkgMgr = getActivity().getPackageManager();
    }

    // for SearchableRecyclerAdapter.Callbacks
    @Override
    public int getItemLayoutId() {
        return R.layout.app_info_item;
    }

    @Override
    public void bindItemData(AppInfoItem item, AppInfoItemBinding binding) {
        binding.setAppinfo(item);
        try {
            ApplicationInfo applicationInfo = mPkgMgr.getApplicationInfo(item.getPackageName(), 0);
            binding.setIcon(applicationInfo.loadIcon(mPkgMgr));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        binding.setQueryListener(this);
    }

    // for IDataImportExport
    @Override
    public void exportData(ArrayList<AppInfoItem> items) {
        boolean succeed;
        if (DEBUG_GSON) {
            succeed = JSONHelper.exportToJSONFile(FILE_NAME_JSON, items);
        } else {
            if (items != null) {
                StringBuilder sb = new StringBuilder();
                for (AppInfoItem appInfo : items) {
                    sb.append(appInfo.toString()).append("\n");
                }
                succeed = FileHelper.exportToFile(sb.toString(), FILE_NAME);
            }
        }
        Toast.makeText(getContext(), items.size() + " results export to " + FILE_NAME + (succeed ? " succeed" : " failed"),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importData() {
        String content;
        if (DEBUG_GSON) {
            List<AppInfoItem> items = JSONHelper.importFromJSONFile(FILE_NAME_JSON);
            StringBuilder sb = new StringBuilder();
            for (AppInfoItem appInfo : items) {
                sb.append(appInfo.toString()).append("\n");
            }
            content = sb.toString();
        } else {
            content = FileHelper.importFromFile(FILE_NAME);
        }
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "import " + FILE_NAME + " result empty!", Toast.LENGTH_SHORT).show();
        } else {
            TextActivity.startShowMessage(getContext(), content);
        }
    }

    // for IClick
    @Override
    public void onItemClick(AppInfoItem item) {
        Toast.makeText(getContext(), "click " + item.getLabel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AppInfoItem item) {
        Toast.makeText(getContext(), "long click " + item.getLabel(), Toast.LENGTH_SHORT).show();
        return true;
    }


    // for loader callback
    @Override
    public Loader<ArrayList<AppInfoItem>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader: ");
        return new AppInfoLoader(getContext());
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppInfoItem>> loader) {
        Log.i(TAG, "onLoaderReset: ");

    }

    private static class AppInfoLoader extends AsyncTaskLoader<ArrayList<AppInfoItem>> {
        Context context;

        public AppInfoLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public ArrayList<AppInfoItem> loadInBackground() {
            return AppDatas.getAllInstalledList(context);
        }
    }
}

