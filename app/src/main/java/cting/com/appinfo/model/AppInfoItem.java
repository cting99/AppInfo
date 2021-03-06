package cting.com.appinfo.model;

import android.os.Parcel;
import android.os.Parcelable;

import cting.com.robin.support.searchablerecyclerview.model.ISearchableItem;


public class AppInfoItem implements ISearchableItem, Parcelable {

    private String packageName;
    private String label;
    private String versionName;
    private String installPath;

    public AppInfoItem() {
    }

    protected AppInfoItem(Parcel in) {
        packageName = in.readString();
        label = in.readString();
        versionName = in.readString();
        installPath = in.readString();
    }

    public static final Creator<AppInfoItem> CREATOR = new Creator<AppInfoItem>() {
        @Override
        public AppInfoItem createFromParcel(Parcel in) {
            return new AppInfoItem(in);
        }

        @Override
        public AppInfoItem[] newArray(int size) {
            return new AppInfoItem[size];
        }
    };

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }


    @Override
    public String toString() {
        return label + " {" + packageName + ", " + versionName + "," + installPath + "}";
    }

    @Override
    public boolean containKeywords(String queryText) {
        return packageName.toLowerCase().contains(queryText)
                || label.toLowerCase().contains(queryText)
                || installPath.toLowerCase().contains(queryText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packageName);
        parcel.writeString(label);
        parcel.writeString(versionName);
        parcel.writeString(installPath);
    }
}
