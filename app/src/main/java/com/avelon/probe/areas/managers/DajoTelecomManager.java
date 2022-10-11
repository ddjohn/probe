package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;

import com.avelon.probe.MyService;
import com.avelon.probe.areas.AbstractManager;

import java.util.Iterator;

public class DajoTelecomManager extends AbstractManager {
    private TelecomManager manager;
    public static String[] permissions = new String[] {};

    public DajoTelecomManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (TelecomManager)ctx.getSystemService(Context.TELECOM_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        /*
        PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(this.getApplicationContext(), MyService.class), "example");
        PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "example").setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build();
        PhoneAccountHandle phoneAccountHandle = telecomManager.getDefaultOutgoingPhoneAccount("ytetet");
        Bundle extras = new Bundle();
        extras.putParcelable(TelecomManager, uri);

        telecom.addNewIncomingCall(phoneAccountHandle, extras);
        */
       // Log.e(TAG, "mobileCarrier=" + manager.getNetworkOperatorName());

        final Iterator<PhoneAccountHandle> phoneAccounts = manager.getCallCapablePhoneAccounts().listIterator();
        while (phoneAccounts.hasNext()) {
            final PhoneAccountHandle phoneAccountHandle = phoneAccounts.next();
            final PhoneAccount phoneAccount = manager.getPhoneAccount(phoneAccountHandle);
            Log.i(TAG, "phoneAccountHandle=" + phoneAccountHandle);
            Log.i(TAG, "phoneAccount=" + phoneAccount);
        }
    }
}
