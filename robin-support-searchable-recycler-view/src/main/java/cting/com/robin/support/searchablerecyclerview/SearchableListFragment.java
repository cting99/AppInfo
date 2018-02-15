package cting.com.robin.support.searchablerecyclerview;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cting.com.robin.supoort.searchablerecyclerview.R;
import cting.com.robin.support.commom.model.IClick;
import cting.com.robin.support.commom.model.IDataImportExport;
import cting.com.robin.support.searchablerecyclerview.model.IQuery;
import cting.com.robin.support.searchablerecyclerview.model.ISearchableItem;

public abstract class SearchableListFragment<I extends ISearchableItem, B extends ViewDataBinding>
        extends Fragment
        implements SearchableRecyclerAdapter.Callbacks<I, B>,
        IClick<I>,
        IQuery,
        SearchView.OnQueryTextListener,
        IDataImportExport<I> {

    public static final String TAG = "cting/search/fragment";
    public static final String KEY_DATA_LIST = "dataList";

    RecyclerView mRecyclerView;

    protected ArrayList<I> mDataList;
    protected SearchableRecyclerAdapter mAdapter;
    protected String mQuery;

    public SearchableListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        updateApapter();
        return view;
    }

    protected void updateApapter() {
        if (mAdapter == null) {
            mAdapter = new SearchableRecyclerAdapter(getContext(), this);
            mRecyclerView.setAdapter(mAdapter);
        }
        if (mDataList != null) {
            mAdapter.setDataList(mDataList);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_import_export, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_export) {
            exportData(mAdapter.getCurrentData());
        } else if (i == R.id.action_import) {
            importData();
//            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public String getQuery() {
        return mQuery;
    }

    @Override
    public void setQuery(String queryText) {
        mQuery = queryText;
        mAdapter.setQuery(queryText);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setQuery(newText);
        return true;
    }

}
