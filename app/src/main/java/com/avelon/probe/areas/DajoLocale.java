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
        Log.e(TAG, "locale=" + Locale.getDefault());
        Log.e(TAG, "locale=" + ULocale.forLocale(Locale.getDefault()));
        Log.e(TAG, "locale=" + LocaleData.getMeasurementSystem(ULocale.forLocale(Locale.getDefault())));
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.SI);
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.UK);
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.US);
        Log.e(TAG, "Timezone=" + TimeZone.getDefault());
        Log.e(TAG, "Language=" + Locale.getDefault().getLanguage());
        Log.e(TAG, "language=" + Locale.getDefault().getDisplayLanguage());
        Log.e(TAG, "language=" + Locale.getDefault().getISO3Language());
        Log.e(TAG, "language=" + Locale.getDefault().getDisplayCountry());
        Log.e(TAG, "language=" + Locale.getDefault().getCountry());

        LocaleList l = LocaleList.getDefault();
        for(int i = 0; i < l.size(); i++) {
            Log.e(TAG, "locale=" + l.get(i));
        }
        Locale[] locales = Locale.getAvailableLocales();
        for(Locale locale : locales) {
            Log.e(TAG, "locale=" + locale);
        }
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
    }
}
