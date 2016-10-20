package com.example.administrator.cbwapplication.home.entity;

import android.graphics.drawable.Drawable;

import com.example.administrator.cbwapplication.common.entity.BaseEntity;


public
class
AppPackageInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String packageName;
    private String appName;
    private String appUid;
    private Drawable icon;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appName == null) ? 0 : appName.hashCode());
        result = prime * result + ((appUid == null) ? 0 : appUid.hashCode());
        result = prime * result
                + ((packageName == null) ? 0 : packageName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppPackageInfo other = (AppPackageInfo) obj;
        if (appName == null) {
            if (other.appName != null)
                return false;
        } else if (!appName.equals(other.appName))
            return false;
        if (appUid == null) {
            if (other.appUid != null)
                return false;
        } else if (!appUid.equals(other.appUid))
            return false;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        return true;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUid() {
        return appUid;
    }

    public void setAppUid(String appUid) {
        this.appUid = appUid;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
