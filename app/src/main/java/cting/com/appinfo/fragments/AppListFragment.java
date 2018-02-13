package cting.com.appinfo.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cting.com.appinfo.R;
import cting.com.appinfo.activities.TextActivity;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.model.AppInfoItem;
import cting.com.appinfo.searchable.SearchableListFragment;
import cting.com.appinfo.utils.FileHelper;

/**
 * Created by cting on 2018/2/13.
 */

public class AppListFragment extends SearchableListFragment<AppInfoItem,AppInfoItemBinding> {

    private static final String TAG = "cting/appinfo/fragment";
    private static final String FILE_NAME = FileHelper.DIR + "app_list.txt";

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
    public int getItemLayoutId() {
        return R.layout.app_info_item;
    }

    @Override
    public void bindItemData(AppInfoItem item, AppInfoItemBinding binding) {
        binding.setAppinfo(item);
        binding.setQueryListener(this);
    }

    @Override
    public void exportData(ArrayList<AppInfoItem> items) {
        if (items != null) {
            StringBuilder sb = new StringBuilder();
            for (AppInfoItem appInfo : items) {
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
            Toast.makeText(getContext(), items.size()+" results export to " + FILE_NAME + (succeed ? " succeed" : " failed"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void importData() {
        String content = FileHelper.importFromFile(FILE_NAME);
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "import " + FILE_NAME + " result empty!", Toast.LENGTH_SHORT).show();
        } else {
            TextActivity.startShowMessage(getContext(),content);
        }
    }


    @Override
    public void onItemClick(AppInfoItem item) {
        Toast.makeText(getContext(), "click "+item.getLabel(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemLongClick(AppInfoItem item) {
        Toast.makeText(getContext(), "long click "+item.getLabel(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
