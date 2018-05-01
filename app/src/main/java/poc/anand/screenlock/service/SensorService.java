package poc.anand.screenlock.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import poc.anand.screenlock.broadcastreceiver.UserPresentBroadcastReceiver;

/**
 * Created by Anand.S on 4/30/2018.
 */

public class SensorService extends Service {
    public int counter = 0;
    private Context context;

    public SensorService(Context context) {
        super();
        this.context = context;
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        registerReceiver();
        return START_STICKY;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(new UserPresentBroadcastReceiver(), filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}