package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

import com.avelon.probe.areas.AbstractManager;

public class DajoAlertDialog extends AbstractManager {
    public static String[] permissions = new String[] {};
    private AlertDialog dialog;

    public DajoAlertDialog(Context ctx) throws Exception {
        super(DajoAlertDialog.class, ctx, permissions);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(ctx.getClass().getSimpleName())
                .setPositiveButton("Start", (dialog, id) -> {
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });
        dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    @Override
    public void orchestrate() throws Exception {
        // DAJO dialog.show();
    }
}
