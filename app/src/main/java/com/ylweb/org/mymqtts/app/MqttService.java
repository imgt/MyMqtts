package com.ylweb.org.mymqtts.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/10.
 */


public class MqttService extends Service {

//
//    private String host = "tcp://0.0.0.0:61613";
//    private String userName = "admin";
//    private String passWord = "password";
//
//    private Handler handler;
//
//    private MqttClient client;
//
//    private String myTopic = "test/topic";
//
//    private MqttConnectOptions options;
//
//    private ScheduledExecutorService scheduler;
//
//    public MqttService() {
//        super();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.e("Gmw", "onCreate");
//        init();
//
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == 1) {
//                    Log.i("Gmw", "" + (String) msg.obj);
//
//                } else if (msg.what == 2) {
//                    Log.i("Gmw", "连接成功");
//                    try {
//                        client.subscribe(myTopic, 1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else if (msg.what == 3) {
//                    Log.i("Gmw", "连接失败，系统正在重连");
//                }
//            }
//        };
//
//        startReconnect();
//    }
//
//
//    private void startReconnect() {
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new Runnable() {
//
//            @Override
//            public void run() {
//                if (!client.isConnected()) {
//                    connect();
//                }
//            }
//        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
//    }
//
//    private void init() {
//        try {
//            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
//            client = new MqttClient(host, "lalalalalal",
//                    new MemoryPersistence());
//            //MQTT的连接设置
//            options = new MqttConnectOptions();
//            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
//            options.setCleanSession(true);
//            //设置连接的用户名
//            options.setUserName(userName);
//            //设置连接的密码
//            options.setPassword(passWord.toCharArray());
//            // 设置超时时间 单位为秒
//            options.setConnectionTimeout(10);
//            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
//            options.setKeepAliveInterval(20);
//            //设置回调
//            client.setCallback(new MqttCallback() {
//
//                @Override
//                public void connectionLost(Throwable cause) {
//                    //连接丢失后，一般在这里面进行重连
//                    Log.i("Gmw", "connectionLost");
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken token) {
//                    //publish后会执行到这里
//                    Log.i("Gmw", "deliveryComplete");
//                }
//
//                @Override
//                public void messageArrived(String topicName, MqttMessage message)
//                        throws Exception {
//                    //subscribe后得到的消息会执行到这里面
//                    Log.i("Gmw", "messageArrived");
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = topicName + "---" + message.toString();
//                    handler.sendMessage(msg);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void connect() {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    client.connect(options);
//                    Message msg = new Message();
//                    msg.what = 2;
//                    handler.sendMessage(msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Message msg = new Message();
//                    msg.what = 3;
//                    handler.sendMessage(msg);
//                }
//            }
//        }).start();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i("Gmw", "onDestroy");
//        try {
//            scheduler.shutdown();
//            client.disconnect();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }


        public static final String TAG = MqttService.class.getSimpleName();

        private static MqttAndroidClient client;
        private MqttConnectOptions conOpt;

        // private String host = "tcp://10.0.2.2:61613";
        private String host = "tcp://192.168.1.103:61613";
        private String userName = "admin";
        private String passWord = "password";
        private static String myTopic = "topic";
        private String clientId = "test";

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            init();
            return super.onStartCommand(intent, flags, startId);
        }

        public static void publish(String msg){
            String topic = myTopic;
            Integer qos = 0;
            Boolean retained = false;
            try {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        private void init() {
            // 服务器地址（协议+地址+端口号）
            String uri = host;
            client = new MqttAndroidClient(this, uri, clientId);
            // 设置MQTT监听并且接受消息
            client.setCallback(mqttCallback);

            conOpt = new MqttConnectOptions();
            // 清除缓存
            conOpt.setCleanSession(true);
            // 设置超时时间，单位：秒
            conOpt.setConnectionTimeout(10);
            // 心跳包发送间隔，单位：秒
            conOpt.setKeepAliveInterval(20);
            // 用户名
            conOpt.setUserName(userName);
            // 密码
            conOpt.setPassword(passWord.toCharArray());

            // last will message
            boolean doConnect = true;
            String message = "{\"terminal_uid\":\"" + clientId + "\"}";
            String topic = myTopic;
            Integer qos = 0;
            Boolean retained = false;
            if ((!message.equals("")) || (!topic.equals(""))) {
                // 最后的遗嘱
                try {
                    conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
                } catch (Exception e) {
                    Log.i(TAG, "Exception Occured", e);
                    doConnect = false;
                    iMqttActionListener.onFailure(null, e);
                }
            }

            if (doConnect) {
                doClientConnection();
            }

        }

        @Override
        public void onDestroy() {
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            super.onDestroy();
        }

        /** 连接MQTT服务器 */
        private void doClientConnection() {
            if (!client.isConnected() && isConnectIsNomarl()) {
                try {
                    client.connect(conOpt, null, iMqttActionListener);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

        }

        // MQTT是否连接成功
        private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken arg0) {
                Log.i(TAG, "连接成功 ");
                try {
                    // 订阅myTopic话题
                    client.subscribe(myTopic,1);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IMqttToken arg0, Throwable arg1) {
                arg1.printStackTrace();
                // 连接失败，重连
            }
        };

        // MQTT监听并且接受消息
        private MqttCallback mqttCallback = new MqttCallback() {

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                String str1 = new String(message.getPayload());
//                MqttManager msg = new MQTTMessage();
//                msg.setMessage(str1);
//                EventBus.getDefault().post(msg);
                String str2 = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();
                Log.i(TAG, "messageArrived:" + str1);
                Log.i(TAG, str2);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken arg0) {

            }

            @Override
            public void connectionLost(Throwable arg0) {
                // 失去连接，重连
            }
        };

        /** 判断网络是否连接 */
        private boolean isConnectIsNomarl() {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                String name = info.getTypeName();
                Log.i(TAG, "MQTT当前网络名称：" + name);
                return true;
            } else {
                Log.i(TAG, "MQTT 没有可用网络");
                return false;
            }
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

