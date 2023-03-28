package com.avelon.probe.areas.concepts.qrcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.avelon.probe.R;

import io.github.g0dkar.qrcode.ErrorCorrectionLevel;
import io.github.g0dkar.qrcode.QRCode;
import io.github.g0dkar.qrcode.QRCodeDataType;
import io.github.g0dkar.qrcode.render.QRCodeGraphics;

public class MyQRCode {
    private static final String TAG = MyQRCode.class.getSimpleName();

    public MyQRCode(Activity ctx) {
        //QRCode qr = new QRCode("https://www.aftonbladet.se", ErrorCorrectionLevel.H, QRCodeDataType.DEFAULT);
        //QRCode qr = new QRCode("031831502", ErrorCorrectionLevel.H, QRCodeDataType.NUMBERS);
        QRCode qr = new QRCode("HELLO WORLD", ErrorCorrectionLevel.H, QRCodeDataType.UPPER_ALPHA_NUM);
        QRCodeGraphics graphics = qr.render(50, 25, Color.GREEN, Color.RED, Color.BLACK);

        Log.e(TAG, "native=" + graphics.nativeImage());
        ImageView qrcode = ctx.findViewById(R.id.qrcode2);
        qrcode.setImageBitmap((Bitmap)graphics.nativeImage());
    }
}
