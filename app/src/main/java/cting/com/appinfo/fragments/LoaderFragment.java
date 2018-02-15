package cting.com.appinfo.fragments;

import android.app.ProgressDialog;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.ArrayList;

import cting.com.appinfo.model.AppInfoItem;
import cting.com.appinfo.searchable.SearchableListFragment;
import cting.com.appinfo.searchable.model.ISearchableItem;

/**
 * Created by cting on 2018/2/15.
 */

public abstract class LoaderFragment<I extends ISearchableItem,D extends ViewDataBinding>
        extends SearchableListFragment<I,D>
        implements LoaderManager.LoaderCallbacks<ArrayList<I>>{


    protected ProgressDialog mProgressDlg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: show progress dialog");
        mProgressDlg = new ProgressDialog(getContext());
        mProgressDlg.setCancelable(false);
        mProgressDlg.setMessage("Loading...");
        mProgressDlg.show();

        getActivity().getSupportLoaderManager().initLoader(0, null, this)
                .forceLoad();
        
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<I>> loader, ArrayList<I> data) {
        Log.i(TAG, "onCreateLoader: dismiss progress dialog");
        mProgressDlg.dismiss();
        mDataList = data;
        updateApapter();
    }

}
