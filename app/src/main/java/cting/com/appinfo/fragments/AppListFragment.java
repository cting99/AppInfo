package cting.com.appinfo.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.R;
import cting.com.appinfo.activities.TextActivity;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.model.AppInfoItem;
import cting.com.appinfo.searchable.SearchableListFragment;
import cting.com.appinfo.utils.FileHelper;
import cting.com.appinfo.utils.JSONHelper;

/**
 * Created by cting on 2018/2/13.
 */

public class AppListFragment extends SearchableListFragment<AppInfoItem, AppInfoItemBinding> {

    private static final String TAG = "cting/appinfo/fragment";
    private static final String FILE_NAME = FileHelper.DIR + "app_list.txt";
    private static final String FILE_NAME_JSON = FileHelper.DIR + "app_list.json";
    private static final boolean DEBUG_GSON = true;

    private PackageManager mPkgMgr;
    public AppListFragment() {
    }

    public static AppListFragment newInstance(ArrayList<AppInfoItem> data) {
        AppListFragment fragment = new AppListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_DATA_LIST, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPkgMgr = getActivity().getPackageManager();
    }

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

    @Override
    public void exportData(ArrayList<AppInfoItem> items) {
        if (DEBUG_GSON) {
            JSONHelper.exportToJSONFile(FILE_NAME_JSON, items);
        } else {
            if (items != null) {
                StringBuilder sb = new StringBuilder();
                for (AppInfoItem appInfo : items) {
                    sb.append(appInfo.toString()).append("\n");
                }
                boolean succeed = FileHelper.exportToFile(sb.toString(), FILE_NAME);
                Toast.makeText(getContext(), items.size() + " results export to " + FILE_NAME + (succeed ? " succeed" : " failed"),
                        Toast.LENGTH_SHORT).show();
            }

        }
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


    @Override
    public void onItemClick(AppInfoItem item) {
        Toast.makeText(getContext(), "click " + item.getLabel(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemLongClick(AppInfoItem item) {
        Toast.makeText(getContext(), "long click " + item.getLabel(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
