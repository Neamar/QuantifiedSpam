package fr.neamar.quantifiedspam;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by neamar on 27/09/16.
 */
public class NotificationListener extends NotificationListenerService {
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.e("WTF", sbn.getPackageName());

        Notification notification = sbn.getNotification();
        DB.insertNotification(getBaseContext(), sbn.getPackageName(), notification.extras.getString(Notification.EXTRA_TITLE), false, false);

        Log.e("WTF", sbn.getPackageName() + " => " + notification.extras.getString(Notification.EXTRA_TITLE));
        super.onNotificationPosted(sbn);
    }
}
