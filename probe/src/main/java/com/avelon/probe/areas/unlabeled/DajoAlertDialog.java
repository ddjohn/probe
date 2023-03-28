package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.avelon.probe.MainActivity;
import com.avelon.probe.R;
import com.avelon.probe.areas.AbstractManager;

public class DajoAlertDialog extends AbstractManager {
    public static String[] permissions = new String[] {};
    private AlertDialog dialog;

    public DajoAlertDialog(Activity ctx) throws Exception {
        super(DajoAlertDialog.class, ctx, permissions);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(ctx.getClass().getSimpleName())
                .setPositiveButton("Start", (dialog, id) -> {
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });
        dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        /* Custom progressbar */
        View customLayout = ctx.getLayoutInflater().inflate(R.layout.cutom_alert, null);
        EditText edit = customLayout.findViewById(R.id.editText);
        ProgressBar progress = customLayout.findViewById(R.id.progressBar);
        edit.setOnKeyListener((v, i, keyEvent) -> {
            progress.setProgress(i); //Use ascii value as progress
            return false;
        });

        AlertDialog.Builder builder2 = new AlertDialog.Builder(ctx)
                .setTitle("Some title")
                .setView(customLayout)
                .setPositiveButton("OK", (dialog, id) -> {})
                .setNegativeButton("Cancel", (dialog, id) -> {});

        AlertDialog dialog = builder2.create();
        dialog.show();
    }

    @Override
    public void orchestrate() throws Exception {
        // DAJO dialog.show();
    }
}
