package cting.com.appinfo.searchable;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.model.AppInfoItem;

/**
 * Created by cting on 2018/2/10.
 */

public abstract class SearchableRecyclerAdapter<I extends SearchableItem, B extends ViewDataBinding>
        extends RecyclerView.Adapter<SearchableRecyclerAdapter<I, B>.ViewHolder>
        implements Filterable {

    public abstract int getLayoutId();
    public abstract void bindData(B binding,I item);

    public static final String TAG = "cting/searchableadapter";

    protected static LayoutInflater inflater;
    private Context context;
    private List<I> dataList;
    private MyFilter filter;
    private String query;

    public SearchableRecyclerAdapter(Context context, List<I> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        query = "";
    }

    protected I getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(inflater, getLayoutId(), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        I item = getItem(position);
        bindData(holder.binding, item);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<I> getDataList() {
        return dataList;
    }

    public void setDataList(List<I> dataList) {
        this.dataList = dataList;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }

    public void setQuery(String query) {
        this.query = query;
        Log.i(TAG, "setQuery: " + query);
        getFilter().filter(query);
    }

    public String getQuery() {
        return query;
    }

    public void onItemClick(AppInfoItem appInfoItem) {
        Toast.makeText(context, "click " + appInfoItem.getLabel(), Toast.LENGTH_SHORT).show();
    }

    public class MyFilter extends Filter {

        List<I> unfilteredList;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<I> resultList;

            if (unfilteredList == null) {
                unfilteredList = new ArrayList<>(dataList);
            }

            if (TextUtils.isEmpty(constraint)) {
                resultList = unfilteredList;
            } else {
                String queryTextLowerCase = constraint.toString().toLowerCase();
                resultList = new ArrayList<>(unfilteredList.size());

                for (I obj : unfilteredList) {
                    if (obj.containKeywords(queryTextLowerCase)) {
                        resultList.add(obj);
                    }
                }
            }
            results.values = resultList;
            results.count = resultList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList = (List<I>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public B binding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
