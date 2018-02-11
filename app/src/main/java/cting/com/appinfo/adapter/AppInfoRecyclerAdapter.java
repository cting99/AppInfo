package cting.com.appinfo.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.R;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.model.AppInfoItem;
import cting.com.appinfo.searchable.SearchableRecyclerAdapter;


/**
 * Created by cting on 2018/2/7.
 */

public class AppInfoRecyclerAdapter extends SearchableRecyclerAdapter<AppInfoItem,AppInfoRecyclerAdapter.ViewHolder>{

    private static final String TAG = "cting/appinfo/adapter";

    public AppInfoRecyclerAdapter(Context context, List<AppInfoItem> dataList) {
        super(context, dataList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AppInfoItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.app_info_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAppinfo(getItem(position));
        holder.binding.setAdapter(this);
        holder.binding.executePendingBindings();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppInfoItemBinding binding;

        public ViewHolder(AppInfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
