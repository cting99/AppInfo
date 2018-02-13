package cting.com.appinfo.searchable;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cting.com.appinfo.R;
import cting.com.appinfo.searchable.model.IClick;
import cting.com.appinfo.searchable.model.IDataImportExport;
import cting.com.appinfo.searchable.model.IQuery;
import cting.com.appinfo.searchable.model.ISearchableItem;


/**
 * Created by cting on 2018/2/13.
 */

public abstract class SearchableListFragment<I extends ISearchableItem, B extends ViewDataBinding>
        extends Fragment
        implements SearchableRecyclerAdapter.Callbacks<I, B>,
        IClick<I>,
        IQuery,
        SearchView.OnQueryTextListener,
        IDataImportExport<I> {

    public static final String TAG = "cting/search/fragment";
    public static final String KEY_DATA_LIST = "dataList";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    protected ArrayList<I> mDataList;
    protected SearchableRecyclerAdapter mAdapter;
    protected String mQuery;

    public SearchableListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Log.i(TAG, "onCreate: bundle=" + bundle);
        if (bundle != null && bundle.containsKey(KEY_DATA_LIST)) {
            mDataList = (ArrayList<I>) bundle.get(KEY_DATA_LIST);
        }
        if (mDataList == null) {
            throw new RuntimeException(getContext().toString() + " must pass a valid data list!");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new SearchableRecyclerAdapter(getContext(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export:
                exportData(mDataList);
                break;
            case R.id.action_import:
                importData();
                mAdapter.notifyDataSetChanged();
                break;
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
