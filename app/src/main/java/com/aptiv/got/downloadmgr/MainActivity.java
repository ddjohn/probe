package com.aptiv.got.downloadmgr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MyService.class));

        if (requestPermissions()) return;

        final TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        // On, Off, Disabled, Limited (reverse camera, climate)


        // PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(this.getApplicationContext(), MyService.class), "example");
        //PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "example").setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build();
        //PhoneAccountHandle phoneAccountHandle = telecomManager.getDefaultOutgoingPhoneAccount("ytetet");
        //Bundle extras = new Bundle();
        //extras.putParcelable(TelecomManager, uri);

        //TelecomManager telecom = (TelecomManager)getSystemService(Context.TELECOM_SERVICE);
        //telecom.addNewIncomingCall(phoneAccountHandle, extras);



    }

    private boolean requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}