package com.course.example.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.os.Messenger;

public class MessengerService extends Service {

    private final static String tag = "MessengerService";
    private Messenger mess = null;
    private Intent serviceIntent = null;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(tag, "Service started");
        //get Messenger object created in Activity
        mess = intent.getParcelableExtra("message");
        //start thread
        Thread t = new Thread(background);
        t.start();

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(tag, "Service destroyed");
    }

    //background thread to send messages to Activity
    Runnable background = new Runnable() {
        public void run() {

            Message msg = null;

            try {
                for (int i = 1; i <= 10; i++) {
                    msg = Message.obtain();        //create Message object
                    msg.arg1 = i;                  //set field
                    mess.send(msg);                //send message through Messenger
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                Log.e(tag, "RemoteException");
            }
        }
    };

}//LogService