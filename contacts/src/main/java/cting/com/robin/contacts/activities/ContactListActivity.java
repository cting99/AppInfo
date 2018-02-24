package cting.com.robin.contacts.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cting.com.robin.contacts.R;
import cting.com.robin.contacts.fragments.ContactListFragment;
import cting.com.robin.support.commom.activities.BasePermissionCheckActivity;

public class ContactListActivity extends BasePermissionCheckActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactListFragment fragment = new ContactListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    @Override
    protected String[] getRequestPermission() {
        return new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS};
    }
}
