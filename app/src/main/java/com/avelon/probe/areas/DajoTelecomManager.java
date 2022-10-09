package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import com.avelon.probe.MyService;

public class DajoTelecomManager extends AbstractManager {
    private TelecomManager manager;
    public static String[] permissions = new String[] {};

    public DajoTelecomManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (TelecomManager)ctx.getSystemService(Context.TELECOM_SERVICE);
    }

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

    }
}
