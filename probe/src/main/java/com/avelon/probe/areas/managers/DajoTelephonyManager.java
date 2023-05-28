package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.Iterator;

public class DajoTelephonyManager extends AbstractManager {
    private TelephonyManager manager;
    public static String[] permissions = new String[] {};

    public DajoTelephonyManager(Context ctx) throws Exception {
        super(DajoTelephonyManager.class, ctx, permissions);

        manager = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {

    }
}
