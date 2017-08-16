package com.ylweb.org.mymqtts.app;

/**
 * Created by Administrator on 2017/8/10.
 */



import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.greenrobot.eventbus.EventBus;
/**
 * 使用EventBus分发事件
 *
 * @author LichFaker on 16/3/25.
 * @Email lichfaker@gmail.com
 */
public class MqttCallbackBus implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        Log.e("",cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.d("",topic + "====" + message.toString());
        EventBus.getDefault().post(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}