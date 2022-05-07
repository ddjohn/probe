package com.aptiv.got.downloadmgr.concepts;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class RecursiveScan {
    private static final String TAG = RecursiveScan.class.getCanonicalName();

    public RecursiveScan(Context context, String path) {
        recursive(new File(path));
    }

    private void recursive(File file) {
        Log.e(TAG, "Scanning " + file + "...");
        //if(file.isDirectory()) {
            Log.e(TAG, "d=" + file);
            File files[] = file.listFiles();
            if(files == null || files.length == 0) {
                Log.e(TAG, "e=" + null);
            }
            else {
                for(File f : files) {
                    recursive(f);
                }
            }
        //}
        //else {
        //    Log.e(TAG, "f=" + file);
        //}
    }
}
