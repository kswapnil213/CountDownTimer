package in.nilapps.countdowntimer.common.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import in.nilapps.countdowntimer.R;
import in.nilapps.countdowntimer.common.data.AppConstants;

/**
 * Created by Swapnil G. on 02-06-2021.
 */

public class BackgroundService extends Service {

    private CountDownTimer countDownTimer;
    long counter;
    long timeLapsed = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (countDownTimer == null) {

            long totalTimer = intent.getLongExtra(AppConstants.TAG_COUNT_DOWN_COUNTER, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startMyOwnForeground();
            else
                startForeground(1, new Notification());

            final long timer = totalTimer * 1000;

            countDownTimer = new CountDownTimer(timer, 1000) {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onTick(long millisUntilFinished) {

                    counter = millisUntilFinished / 1000;
                    sendTimerBroadcast();
                }

                public void onFinish() {

                    if (countDownTimer != null)
                        countDownTimer.cancel();
                    sendTimerBroadcast();
                    stopSelf();
                }
            }.start();
        }

        return START_NOT_STICKY;
    }

    private void sendTimerBroadcast() {
        timeLapsed++;
        Intent in = new Intent(AppConstants.TAG_ACTION);
        in.putExtra(AppConstants.TAG_RESULT_CODE, Activity.RESULT_OK);
        in.putExtra(AppConstants.TAG_RESULT_VALUE, timeLapsed);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String channelName = getString(R.string.channel_name);
        NotificationChannel channel = new NotificationChannel(AppConstants.TAG_NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, AppConstants.TAG_NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_user_circle)
                .setContentTitle(getString(R.string.service_message))
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}