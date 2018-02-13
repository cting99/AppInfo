package cting.com.appinfo.searchable;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import cting.com.appinfo.searchable.model.IClick;
import cting.com.appinfo.searchable.model.ISearchableItem;

/**
 * Created by cting on 2018/2/10.
 */

public class SearchableRecyclerAdapter<I extends ISearchableItem, B extends ViewDataBinding>
        extends RecyclerView.Adapter<SearchableRecyclerAdapter.ViewHolder>
        implements Filterable {

    public static final String TAG = "cting/searchableadapter";

    protected LayoutInflater inflater;
    private ArrayList<I> dataList;
    private MyFilter filter;
    private Callbacks callbacks;

    public SearchableRecyclerAdapter(Context context, ArrayList<I> dataList, Callbacks<I, B> callbacks) {
        if (callbacks == null) {
            throw new RuntimeException("SearchableRecyclerAdapter.Callbacks must not be null!");
        }
        this.callbacks = callbacks;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    protected I getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(inflater, callbacks.getItemLayoutId(), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        I item = getItem(position);
        callbacks.bindItemData(item, holder.binding);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }

    public void setQuery(String query) {
        Log.i(TAG, "setQuery: " + query);
        getFilter().filter(query);
    }

    public class MyFilter extends Filter {

        ArrayList<I> unfilteredList;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<I> resultList;

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
            dataList = (ArrayList<I>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder<DB extends ViewDataBinding>
            extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnClickListener {
        public DB binding;

        public ViewHolder(DB binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Log.i(TAG, "onLongClick: ");
            return callbacks.onItemLongClick(getItem(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
            callbacks.onItemClick(getItem(getAdapterPosition()));
        }
    }

    public interface Callbacks<I extends ISearchableItem, B extends ViewDataBinding>
            extends IClick<I> {
        int getItemLayoutId();
        void bindItemData(I item, B binding);
    }
}
