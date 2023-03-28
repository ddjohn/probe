package com.avelon.probe.areas.managers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoAccountManager extends AbstractManager {
    private AccountManager manager;
    public static String[] permissions = new String[] {};

    public DajoAccountManager(Context ctx) throws Exception {
        super(DajoAccountManager.class, ctx, permissions);

        manager = (AccountManager)ctx.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        for(Account a : manager.getAccounts()) {
            Log.i(TAG, "account=" + a);
        }
        for(AuthenticatorDescription auth : manager.getAuthenticatorTypes()) {
            Log.i(TAG, "auth=" + auth);
        }
    }
}
