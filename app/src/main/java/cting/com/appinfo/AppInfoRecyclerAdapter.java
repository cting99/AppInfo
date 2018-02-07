package cting.com.appinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.model.AppInfo;

/**
 * Created by cting on 2018/2/7.
 */

public class AppInfoRecyclerAdapter extends RecyclerView.Adapter<AppInfoRecyclerAdapter.ViewHolder> {

    private List<AppInfo> mList;
    private LayoutInflater mInflater;
    private AppInfoItemBinding mDataBinding;

    public AppInfoRecyclerAdapter(Context context, List<AppInfo> list) {
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mDataBinding = DataBindingUtil.inflate(mInflater, R.layout.app_info_item, parent, false);
        return new ViewHolder(mDataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mDataBinding.setAppinfo(getItem(position));
    }

    private AppInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
