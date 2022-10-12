package com.avelon.probe.areas.managers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DajoPackageManager extends AbstractManager {
    private PackageManager manager;
    public static String[] permissions = new String[] {};

    public DajoPackageManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = ctx.getPackageManager();
    }

    @Override
    public void orchestrate() throws Exception {
        List<ApplicationInfo> packages = manager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo info : packages) {
            Log.i(TAG, "info=" + info);
            Log.i(TAG, "info=" + manager.getLaunchIntentForPackage(info.className));
        }
        //packageManager.deletePackage("com.aptiv.got.testfullframework", null, 0);
        //packageManager.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

        for(FeatureInfo feature : manager.getSystemAvailableFeatures()) {
            Log.i(TAG, "feature: " + feature.name);
        }

        for(PackageInfo pkg : manager.getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA | PackageManager.GET_INTENT_FILTERS)) {
            Log.i(TAG, "package: " + pkg.packageName);
            Log.i(TAG, "user: " + pkg.sharedUserId);
            Log.i(TAG, "version: " + pkg.versionName);
            for(ActivityInfo activity : (pkg.activities != null ? pkg.activities : new ActivityInfo[] {})) {
                Log.i(TAG, "activity: " + activity);
            }
        }

        for(ApplicationInfo application : manager.getInstalledApplications(0)) {
            Log.e(TAG, "application: " + application);
            if (manager.getLaunchIntentForPackage(application.packageName) != null) {
                Log.e(TAG, "action: " + manager.getLaunchIntentForPackage(application.packageName).getAction());
                Log.e(TAG, "type: " + manager.getLaunchIntentForPackage(application.packageName).getType());
                Log.e(TAG, "identifier: " + manager.getLaunchIntentForPackage(application.packageName).getIdentifier());
                Log.e(TAG, "schema: " + manager.getLaunchIntentForPackage(application.packageName).getScheme());
            }
        }

        ArrayList<String> actions = new ArrayList<>();
        for(Field field : Intent.class.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);

            if(name.startsWith("ACTION_")) {
                Class<?> targetType = field.getType();
                Object objectValue = targetType.newInstance();

                actions.add((String)field.get(objectValue));
            }
        }

        for(String action : actions.toArray(new String[0])) {
            Intent intent = new Intent(action, null);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            ResolveInfo resolveInfo = ctx.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if(resolveInfo != null)
                Log.e(TAG, "---> " + action + ":" + resolveInfo.activityInfo.name);

            List<ResolveInfo> apps = ctx.getPackageManager().queryIntentActivities(intent, 0);
            if(apps.size() > 0) {
                Log.e(TAG, "+++> " + action + ":" + apps.get(0).activityInfo.name);
            }
        }
    }
}
