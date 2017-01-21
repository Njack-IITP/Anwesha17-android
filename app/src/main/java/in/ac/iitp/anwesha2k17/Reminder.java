package in.ac.iitp.anwesha2k17;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("Notification")) {
            String eventName = intent.getStringExtra("title");
            int id = intent.getIntExtra("event_id", -1);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(context, EventDetails.class);

            notificationIntent.putExtra("eventID", id);
            notificationIntent.putExtra("eventName", eventName);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pintent = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);
            Notification.Builder nb = new Notification.Builder(context)
                    .setAutoCancel(true)
                    .setContentTitle("Anwesha")
                    .setContentText(eventName + " is about to Start")
                    .setContentIntent(pintent)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification = nb.build();
            nm.notify(753246 + id, notification);

        }

    }
}
