package cting.com.appinfo.model;

import android.graphics.drawable.Drawable;

/**
 * Created by cting on 2018/2/6.
 */

public class AppInfo {

    private String packageName;
    private String label;
    private String versionName;
    private Drawable icon;
    private String installPath;

    public AppInfo() {
    }

    public AppInfo(String packageName, String label, String versionName, Drawable icon, String installPath) {
        this.packageName = packageName;
        this.label = label;
        this.versionName = versionName;
        this.icon = icon;
        this.installPath = installPath;
    }

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

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public boolean search(String query) {
        return packageName.toLowerCase().contains(query)
                || label.toLowerCase().contains(query)
                || installPath.toLowerCase().contains(query);
    }

    @Override
    public String toString() {
        return label + " {" + packageName + ", " + installPath + "}";
    }
}
