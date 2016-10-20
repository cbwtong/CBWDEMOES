package com.example.administrator.cbwapplication.util;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.example.administrator.cbwapplication.home.entity.AppPackageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public
class
SystemUtils {

    public static List<PackageInfo> getAllApps(Context context) {
        PackageManager pManager = context.getPackageManager();

        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        return paklist;
    }


    public static List<PackageInfo> getAllAppsNoSystem(Context context) {

        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = null;
        if (context != null) {
            pManager = context.getPackageManager();
            List<PackageInfo> paklist = pManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
            Set<Integer> set = new HashSet<Integer>();
            for (PackageInfo packageInfo : paklist) {
                String[] premissions = packageInfo.requestedPermissions;
                if (premissions != null && premissions.length > 0) {
                    for (String premission : premissions) {
                        if ((Manifest.permission.INTERNET).equals(premission)) {


                            if (packageInfo.applicationInfo.uid > 5000) {
                                if (set.contains(packageInfo.applicationInfo.uid)) {
                                    break;
                                }
                                set.add(packageInfo.applicationInfo.uid);
                                apps.add(packageInfo);
                            }

                        }
                    }
                }
            }
        }
        return apps;
    }

    public static HashMap<String, String> getAllAppsNoSystemHash(Context context) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> paklist = pManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : paklist) {
            String[] premissions = packageInfo.requestedPermissions;
            if (premissions != null && premissions.length > 0) {
                for (String premission : premissions) {
                    if ((Manifest.permission.INTERNET).equals(premission)) {
                        hashMap.put(packageInfo.packageName, packageInfo.applicationInfo.uid + "");
                    }
                }
            }
        }
        return hashMap;
    }


    public static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        return mApps;
    }

    /**
     * 根据应用包名获得UID
     *
     * @param context pack
     */
    public static int getUidByPack(Context context, String pack) {
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : paklist) {
            if (packageInfo.packageName.equals(pack)) {
                return packageInfo.applicationInfo.uid;
            }
        }
        return 0;
    }

    @SuppressWarnings("null")
    public static List<AppPackageInfo> getAllAppPackageList(Context context) {
        PackageManager pManager = context.getPackageManager();
        List<AppPackageInfo> infoList = new ArrayList<AppPackageInfo>();
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (PackageInfo info : paklist) {
            AppPackageInfo pInfo = new AppPackageInfo();
            pInfo.setAppUid(String.valueOf(info.applicationInfo.uid));
            pInfo.setPackageName(info.packageName);
            pInfo.setAppName(info.applicationInfo.loadLabel(context.getPackageManager()).toString());
            pInfo.setIcon(info.applicationInfo.loadIcon(pManager));
            infoList.add(pInfo);
        }
        Log.i("----------------", "packageInfoList:" + infoList.size());
        return infoList;
    }

    /*public static List<TotalApp> getLocalAppPackageList(Context context){
        PackageManager pManager = context.getPackageManager();
        List<TotalApp> infoList = new ArrayList<TotalApp>();
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for(PackageInfo info : paklist){
            TotalApp pInfo = new TotalApp();
            pInfo.setAppUid(String.valueOf(info.applicationInfo.uid)) ;
            pInfo.setPackageName(info.packageName);
            pInfo.setAppName(info.applicationInfo.loadLabel(context.getPackageManager()).toString());
            infoList.add(pInfo);
        }
        return infoList;
    }*/


}  