package com.example.sandeep.smsinformer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sandeep on 25-10-2016.
 */
public class IncomingSms extends BroadcastReceiver {
    final SmsManager sms;
Intent accountCreditedService ;
    {
        sms = SmsManager.getDefault();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;

                    if(message.contains("credit")||message.contains("Credit"))
                    {
                        Log.i("SmsReceiver", "senderNum: "+ senderNum + "     Credit  message: " + message);


                        Intent background = new Intent(context, AccountCredited.class);
                        context.startService(background);

                        notifyy(context,message);
                       accountCreditedService =  new Intent(context, IncomingSms.class);
                        context.startService(accountCreditedService);
                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }



    public void notifyy(Context context,String message){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_menu_more)
                        .setContentTitle("Credit SMS")
                        .setContentText("Please Check")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.sms));
        // Creates an explicit intent for an Activity in your app
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(context, SetKeyword.class);
        int mId=001;
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SetKeyword.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
