package cting.com.robin.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import cting.com.robin.support.searchablerecyclerview.model.ISearchableItem;

public class ContactItem implements ISearchableItem ,Parcelable{
    private String name;
    private String phoneNumber;

    public ContactItem() {
    }

    protected ContactItem(Parcel in) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + ", " + phoneNumber;
    }

    public static final Creator<ContactItem> CREATOR = new Creator<ContactItem>() {
        @Override
        public ContactItem createFromParcel(Parcel in) {
            return new ContactItem(in);
        }

        @Override
        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };

    @Override
    public boolean containKeywords(String queryText) {
        return name.toLowerCase().contains(queryText) || phoneNumber.contains(queryText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
