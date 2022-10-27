package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;
import java.io.File;
import java.io.FileOutputStream;

public class DajoDownloadManager extends AbstractManager {
    private DownloadManager manager;
    public static String[] permissions = new String[] {};

    public DajoDownloadManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //request.setAllowedOverRoaming(false);
        //request.allowScanningByMediaScanner();
        //request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "habba.jpg");
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_ALARMS, "habba.jpg");
        //request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Downloads" + File.separator + "a.jpg")));

        //Log.e(TAG, "request=" + request.toString());

        String url = "https://www.sygic.com/assets/enterprise/img/Sygic_logo.svg";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Title");
        request.setDescription("Description");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalFilesDir(ctx, Environment.DIRECTORY_MOVIES, "Sygic_logo.svg");
        manager.enqueue(request);

        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = manager.query(query);
        Log.e(TAG, "count=" + cursor.getCount());
        int columns[] = {
                cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR),
                cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION),
                cursor.getColumnIndex(DownloadManager.COLUMN_ID),
                cursor.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP),
                cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI),
                cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE),
                cursor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI),
                cursor.getColumnIndex(DownloadManager.COLUMN_REASON),
                cursor.getColumnIndex(DownloadManager.COLUMN_STATUS),
                cursor.getColumnIndex(DownloadManager.COLUMN_TITLE),
                cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES),
                cursor.getColumnIndex(DownloadManager.COLUMN_URI),

        };
        String s2 = "";
        for(int c : columns) {
            s2 += "," + cursor.getColumnName(c);
        }
        Log.i(TAG, "name=" + s2);

        while (cursor.moveToNext()) {
            String s = "";
            for(int c : columns) {
                s += "," + cursor.getString(c);
            }
            Log.i(TAG, "name=" + s);
        }
        cursor.close();

//        String filename = URLUtil.guessFileName(url,null, MimeTypeMap.getFileExtensionFromUrl(url));

        //Uri uri = Uri.parse("file:///data/media/0/Android/data/com.aptiv.got.downloadmgr/files/" + filename);
        //Log.e(TAG, "getFilesDir=" + getFilesDir());
        //Log.e(TAG, "getExternalFilesDir=" + getExternalFilesDir("test"));
        //Log.e(TAG, "getExternalFilesDirs=" + getExternalFilesDirs("test")[0]);
/*
        for(File f : this.getExternalFilesDirs(null)) {
            Log.e(TAG, "path=" + f);
        }

        // Emulated storage

        // Unsupported part
        //Uri uri = Uri.parse("file:///data/oem_data/" + File.separator + filename);
        //request.setDestinationUri(uri);
        File f = new File("/data/oem_data");
        Log.e(TAG, "aaa=" + f.exists());
        File file = new File("/data/oem_data/", filename);
        request.setDestinationUri(Uri.fromFile(file));


*/

        File f = new File("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/");
        for(File file : f.listFiles()) {
            Log.e(TAG, "file=" + file);
            if(file.isDirectory()) {
                for (File file2 : f.listFiles()) {
                    Log.e(TAG, "file=" + file2);
                }
            }
        };

        Log.e(TAG, "----------------");
/*
        File f = new File("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies");
        Log.e(TAG, "dir=" + f.isDirectory());
        Log.e(TAG, "dir=" + f.mkdirs());
        Log.e(TAG, "" + !(f.isDirectory() || f.mkdirs()));
        Log.e(TAG, "----------------");

        final File file = new File(Uri.parse("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies").getPath());
        Log.e(TAG, "file=" + file);
        File parent = file.getParentFile().getAbsoluteFile();
        Log.e(TAG, "parent=" + parent);
        File[] parentTest = new File[] { parent };
        String name = file.getName();
        Log.e(TAG, "file=" + name);

        // Ensure target directories are ready
        for (File test : parentTest) {
            Log.e(TAG, "Scan " + test);
            Log.e(TAG, "Scan " + test.isDirectory());
            Log.e(TAG, "Scan " + test.mkdirs());
            if (!(test.isDirectory() || test.mkdirs())) {
                Log.e(TAG, "Failed to create parent for " + test);
                //throw new IOException("Failed to create parent for " + test);
            }
        }
        */

        /*
        try {
            File testDir = new File(this.getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + "maps");
            Log.e(TAG, "path=" + testDir);
            Log.e(TAG, "Create directory: " + testDir.mkdirs());
            File testFile = new File(testDir, "testmap.txt");
            Log.e(TAG, "path=" + testFile);
            FileOutputStream stream = new FileOutputStream(testFile);
            stream.write("hello world".getBytes());
            stream.close();
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }*/
        Log.i(TAG, "----------------");
/*
        File f = new File("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies");
        Log.i(TAG, "dir=" + f.isDirectory());
        Log.i(TAG, "dir=" + f.mkdirs());
        Log.i(TAG, "" + !(f.isDirectory() || f.mkdirs()));
        Log.i(TAG, "----------------");


        final File file = new File(Uri.parse("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies").getPath());
        Log.i(TAG, "file=" + file);
        File parent = file.getParentFile().getAbsoluteFile();
        Log.i(TAG, "parent=" + parent);
        File[] parentTest = new File[] { parent };
        String name = file.getName();
        Log.i(TAG, "file=" + name);

        // Ensure target directories are ready
        for (File test : parentTest) {
            Log.i(TAG, "Scan " + test);
            Log.i(TAG, "Scan " + test.isDirectory());
            Log.i(TAG, "Scan " + test.mkdirs());
            if (!(test.isDirectory() || test.mkdirs())) {
                Log.i(TAG, "Failed to create parent for " + test);
                //throw new IOException("Failed to create parent for " + test);
            }
        }
*/
        try {
            File testDir = new File(ctx.getExternalFilesDir(null).getAbsolutePath() + File.separator + "maps");
            Log.i(TAG, "path=" + testDir);
            Log.i(TAG, "Create directory: " + testDir.mkdirs());
            File testFile = new File(testDir, "testmap.txt");
            Log.i(TAG, "path=" + testFile);
            FileOutputStream stream = new FileOutputStream(testFile);
            stream.write("hello world".getBytes());
            stream.close();
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }
    }
}
