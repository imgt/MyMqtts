package com.ylweb.org.mymqtts.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.ylweb.org.mymqtts.R;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/8/10.
 */
public class Man extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        startService(new Intent(this, MqttService.class));
        findViewById(R.id.res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MqttService.publish("CSDN 一口仨馍");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMqttMessage(MqttMessage mqttMessage){
        Log.i(MqttService.TAG,"get message:"+mqttMessage.toString());
        Toast.makeText(this,mqttMessage.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

//    public static final String URL = "tcp://0.0.0.0:61613";
//
//    private String userName = "admin";
//
//    private String password = "password";
//
//    private String clientId = "test/topic";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        EventBus.getDefault().register(this);
//        startService(new Intent(this, MqttService.class));
//        findViewById(R.id.res).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MqttService.publish("CSDN 一口仨馍");
//            }
//        });
//
//
//
//    findViewById(R.id.res).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent serviceIntent = new Intent(Man.this, MqttService.class);
//                startService(serviceIntent);
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Intent serviceIntent = new Intent(Man.this, MqttService.class);
////                                    startService(serviceIntent);
////
////                        boolean b = MqttManager.getInstance().creatConnect(URL, userName, password, clientId);
////                        Log.d("","isConnected: " + b);
////                    }
////                }).start();
//            }
//        });
//
//        findViewById(R.id.res2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        MqttManager.getInstance().publish("test", 2, "hello".getBytes());
//                    }
//                }).start();
//            }
//        });
//
//        findViewById(R.id.res3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        MqttManager.getInstance().subscribe("test", 2);
//                    }
//                }).start();
//            }
//        });
//
//        findViewById(R.id.res4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            MqttManager.getInstance().disConnect();
//                        } catch (MqttException e) {
//
//                        }
//                    }
//                }).start();
//
//            }
//        });
//
//
//    }
//
//    /**
//     * 订阅接收到的消息
//     * 这里的Event类型可以根据需要自定义, 这里只做基础的演示
//     *
//     * @param message
//     */
//    @Subscribe
//    public void onEvent(MqttMessage message) {
//        Log.d("",message.toString());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
////    public void getMqttMessage(MQTTMessage mqttMessage){
////        Log.i(MQTTService.TAG,"get message:"+mqttMessage.getMessage());
////        Toast.makeText(this,mqttMessage.getMessage(),Toast.LENGTH_SHORT).show();
////    }
//
//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
}