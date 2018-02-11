package cting.com.appinfo.searchable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

public abstract class SearchableRecyclerAdapter<T extends SearchableItem, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements Filterable{

    public static final String TAG = "cting/searchableadapter";

    protected static LayoutInflater inflater;
    private Context context;
    private List<T> dataList;
    private MyFilter filter;
    public String query;

    public SearchableRecyclerAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        query = "";
    }

    protected T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
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

        List<T> unfilteredList;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> resultList;

            if (unfilteredList == null) {
                unfilteredList = new ArrayList<>(dataList);
            }

            if (TextUtils.isEmpty(constraint)) {
                resultList = unfilteredList;
            } else {
                String queryTextLowerCase = constraint.toString().toLowerCase();
                resultList = new ArrayList<>(unfilteredList.size());

                for (T obj : unfilteredList) {
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
            dataList = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }
}
