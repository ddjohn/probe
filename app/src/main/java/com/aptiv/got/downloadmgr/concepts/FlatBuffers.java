package com.aptiv.got.downloadmgr.concepts;

import android.content.Context;
import android.util.Log;

import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

public class FlatBuffers {
    private static final String TAG = FlatBuffers.class.getCanonicalName();

    public FlatBuffers(Context context) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        builder.startObject(2);
        builder.addInt(10);
        builder.addDouble(3.0);
        int o = builder.endObject();
        builder.finish(o);

        Log.e(TAG, "builder=" + builder);
        Log.e(TAG, "data=" + builder.dataBuffer());

        ByteBuffer buffer = builder.dataBuffer();
        for(byte b : buffer.array()) {
            Log.e(TAG, "b=" + b);
        }

        FlatBuffers buffers = new FlatBuffers(context);


    }
}
