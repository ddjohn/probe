package com.avelon.probe.concepts;

import android.content.Context;
import android.util.Log;

import com.google.flatbuffers.FlatBufferBuilder;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT {
    private static final String TAG = MQTT.class.getCanonicalName();

    public MQTT(Context context) throws MqttException {
        MqttClient client = new MqttClient("http://localhost:1883", "id");
        Log.e(TAG, "connect");
        client.connect();
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "connectionLost()");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e(TAG, "messageArrived()");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "deliveryComplete()");
            }
        });
        client.subscribe("topic");
        MqttMessage message = new MqttMessage();
        message.setQos(0);
        message.setPayload("hello world".getBytes());
        client.publish("topic", message);
        Log.e(TAG, "run");
        //client.disconnect();
       //client.close();
    }
}
