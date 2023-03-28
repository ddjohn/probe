package com.avelon.probe.areas.concepts.qrcode;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;
import com.avelon.probe.R;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MyQRGEncoder {
    private static final String TAG = MyQRGEncoder.class.getSimpleName();

    public MyQRGEncoder(Activity ctx) {
        QRGEncoder qrgEncoder = new QRGEncoder("Hello World", null, QRGContents.Type.TEXT, 600);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);

        ImageView qrcode = ctx.findViewById(R.id.qrcode1);
        qrcode.setImageBitmap(qrgEncoder.getBitmap());
    }
}
