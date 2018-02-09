package cting.com.appinfo.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.R;
import cting.com.appinfo.databinding.AppInfoItemBinding;
import cting.com.appinfo.model.AppInfo;
import cting.com.appinfo.model.ClickItem;
import cting.com.appinfo.utils.TextHighlighterHelper;


/**
 * Created by cting on 2018/2/7.
 */

public class AppInfoRecyclerAdapter extends RecyclerView.Adapter<AppInfoRecyclerAdapter.ViewHolder>
        implements Filterable, ClickItem {

    private static final String TAG = "cting/appinfo/adapter";

    private Context mContext;
    private List<AppInfo> mList;
    private LayoutInflater mInflater;
    private MyFilter mFilter;
    private static TextHighlighterHelper mHighlighter = new TextHighlighterHelper();
    private static String mQueryText;

    public AppInfoRecyclerAdapter(Context context, List<AppInfo> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @BindingAdapter("android:text")
    public static void setSpannableText(@NonNull TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mHighlighter != null && !TextUtils.isEmpty(mQueryText)) {
                mHighlighter.updateHighliteInText(textView, text, mQueryText);
            } else {
                textView.setText(text);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        AppInfoItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.app_info_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setAppinfo(getItem(position));
        holder.binding.setClickListener(this);
        holder.binding.executePendingBindings();
    }

    private AppInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void startQuery(String query) {
        mQueryText = query;
        getFilter().filter(query);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    @Override
    public void onClick(AppInfo info) {
        Toast.makeText(mContext, "click " + info.getLabel(), Toast.LENGTH_SHORT).show();
    }

    public class MyFilter extends Filter {

        List<AppInfo> unfilteredList;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<AppInfo> resultList;

            if (unfilteredList == null) {
                unfilteredList = new ArrayList<>(mList);
            }

            if (TextUtils.isEmpty(constraint)) {
                resultList = unfilteredList;
            } else {
                String queryTextLowerCase = constraint.toString().toLowerCase();
                resultList = new ArrayList<>(unfilteredList.size());

                for (AppInfo appInfo : unfilteredList) {
                    if (appInfo.search(queryTextLowerCase)) {
                        resultList.add(appInfo);
                    }
                }
            }
            results.values = resultList;
            results.count = resultList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (List<AppInfo>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppInfoItemBinding binding;

        public ViewHolder(AppInfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
