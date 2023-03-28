package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoContacts extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public DajoContacts(Context ctx) throws Exception {
        super(DajoContacts.class, ctx, permissions);

        /* Manager */
        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);

        /* Listeners */
    }

    @SuppressLint({"MissingPermission", "Range"})
    @Override
    public void orchestrate() throws Exception {
        Cursor cursor = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        //Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_VCARD_URI,null, null, null, null);
        //Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_MULTI_VCARD_URI,null, null, null, null);
        //Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_MULTI_VCARD_URI,null, null, null, null);
        //Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, null);

        cursor.moveToFirst();
        {
            for(String column : cursor.getColumnNames()) {
                Log.e(TAG,"cursor: " + column + " - " + cursor.getString(cursor.getColumnIndex(column)));
            }
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Log.e(TAG, "name: " + name);
            Log.e(TAG, "phone: " + phoneNumber);
        } while(cursor.moveToNext());
        cursor.close();
    }
}
