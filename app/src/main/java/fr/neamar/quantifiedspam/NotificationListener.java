package fr.neamar.quantifiedspam;

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
        NotificationParser notificationParser = new NotificationParser(sbn);
        DB.insertNotification(getBaseContext(), notificationParser.getPackageName(), notificationParser.getTitle(), notificationParser.getAccount(), notificationParser.hasVibration(), notificationParser.hasSound());

        Log.e("WTF", notificationParser.getPackageName() + " => " + notificationParser.getTitle() + " [" + notificationParser.getAccount() + "]");
        super.onNotificationPosted(sbn);
    }
}
