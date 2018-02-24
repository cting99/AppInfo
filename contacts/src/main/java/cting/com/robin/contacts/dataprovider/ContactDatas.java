package cting.com.robin.contacts.dataprovider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import cting.com.robin.contacts.model.ContactItem;

public class ContactDatas {

    private static String[] CONTACT_PROJECTS = new String[]{
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public static ArrayList<ContactItem> getContacts(Context context) {
        ArrayList<ContactItem> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder()
                .append(ContactsContract.Data.MIMETYPE)
                .append("='")
                .append(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .append("'");

        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                CONTACT_PROJECTS,
                sb.toString(), null, ContactsContract.Data.DISPLAY_NAME);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactItem item = new ContactItem();
                item.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
                item.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                list.add(item);
            }
            cursor.close();
        }

        return list;
    }
}
