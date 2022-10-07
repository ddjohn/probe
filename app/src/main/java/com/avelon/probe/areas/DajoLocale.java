package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.LocaleData;
import android.icu.util.ULocale;
import android.os.LocaleList;
import android.util.Log;

import java.util.Locale;
import java.util.TimeZone;

public class DajoLocale extends AbstractManager {
    public static String[] permissions = new String[] {};

    public DajoLocale(Context ctx) throws Exception {
        super(ctx, permissions);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "locale=" + Locale.getDefault());
        Log.i(TAG, "locale=" + ULocale.forLocale(Locale.getDefault()));
        Log.i(TAG, "locale=" + LocaleData.getMeasurementSystem(ULocale.forLocale(Locale.getDefault())));
        Log.i(TAG, "Metric=" + LocaleData.MeasurementSystem.SI);
        Log.i(TAG, "Metric=" + LocaleData.MeasurementSystem.UK);
        Log.i(TAG, "Metric=" + LocaleData.MeasurementSystem.US);
        Log.i(TAG, "Timezone=" + TimeZone.getDefault());
        Log.i(TAG, "Language=" + Locale.getDefault().getLanguage());
        Log.i(TAG, "language=" + Locale.getDefault().getDisplayLanguage());
        Log.i(TAG, "language=" + Locale.getDefault().getISO3Language());
        Log.i(TAG, "language=" + Locale.getDefault().getDisplayCountry());
        Log.i(TAG, "language=" + Locale.getDefault().getCountry());

        LocaleList l = LocaleList.getDefault();
        for(int i = 0; i < l.size(); i++) {
            Log.i(TAG, "locale=" + l.get(i));
        }
        Locale[] locales = Locale.getAvailableLocales();
        for(Locale locale : locales) {
            Log.i(TAG, "locale=" + locale);
        }
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
    }
}
