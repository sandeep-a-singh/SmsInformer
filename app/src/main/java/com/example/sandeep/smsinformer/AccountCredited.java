package com.example.sandeep.smsinformer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class AccountCredited extends Service {
  MediaPlayer player;

    @Override
    public void onCreate() {
     //   player=new MediaPlayer(getApplicationContext(),R.raw.tune);

    }

    public AccountCredited() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service","Service Started");
        return 0;

    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("Service","Service Stopped");
        return true;
    }
}
