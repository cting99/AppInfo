package cting.com.robin.contacts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cting.com.robin.contacts.R;
import cting.com.robin.contacts.databinding.ContactItemBinding;
import cting.com.robin.contacts.dataprovider.ContactDatas;
import cting.com.robin.contacts.model.ContactItem;
import cting.com.robin.support.commom.activities.TextActivity;
import cting.com.robin.support.commom.utils.FileHelper;
import cting.com.robin.support.searchablerecyclerview.LoaderFragment;

public class ContactListFragment extends LoaderFragment<ContactItem,ContactItemBinding> {

    public static final String CONTACT_FILE_JSON = FileHelper.DIR + "contact.json";

    public ContactListFragment() {
    }

    @Override
    public Loader<ArrayList<ContactItem>> onCreateLoader(int id, Bundle args) {
        return new ContactLoader(getActivity());
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ContactItem>> loader) {

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ContactItem>> loader, ArrayList<ContactItem> data) {
        super.onLoadFinished(loader, data);
        Toast.makeText(getActivity(), data.size()+" results loaded!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(ContactItem item) {

    }

    @Override
    public boolean onItemLongClick(ContactItem item) {
        return false;
    }

    @Override
    public void exportData(ArrayList<ContactItem> items) {
        Wrapper wrapper = new Wrapper(items);
        Gson gson = new Gson();
        String content = gson.toJson(wrapper);
        boolean res = FileHelper.exportToFile(content, CONTACT_FILE_JSON);
        Toast.makeText(getActivity(), "contacts export to " + CONTACT_FILE_JSON + " succeed:" + res,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void importData() {
        TextActivity.startShowMessage(getContext(), getFileContent());
    }

    private String getFileContent() {
        FileReader reader = null;
        try {
            File file = new File(CONTACT_FILE_JSON);
            reader = new FileReader(file);
            Gson gson = new Gson();
            Wrapper items = gson.fromJson(reader, Wrapper.class);
            ArrayList<ContactItem> list = items.getItems();
            StringBuilder sb = new StringBuilder();
            for (ContactItem item : list) {
                sb.append(item.toString()).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.contact_item;
    }

    @Override
    public void bindItemData(ContactItem item, ContactItemBinding binding) {
        binding.setContactInfo(item);
        binding.setQueryListener(this);
    }


    private class Wrapper {
        ArrayList<ContactItem> items;

        public Wrapper(ArrayList<ContactItem> items) {
            this.items = items;
        }

        public ArrayList<ContactItem> getItems() {
            return items;
        }

    }

    private static class ContactLoader extends AsyncTaskLoader<ArrayList<ContactItem>> {
        Context context;
        public ContactLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public ArrayList<ContactItem> loadInBackground() {
            return ContactDatas.getContacts(context);
        }
    }

}
