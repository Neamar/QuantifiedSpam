package fr.neamar.quantifiedspam;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.support.annotation.NonNull;

public class NotificationParser {
    private StatusBarNotification sbn;
    private Notification notification;
    public NotificationParser(StatusBarNotification sbn) {
        this.sbn = sbn;
        this.notification = sbn.getNotification();
    }

    public String getPackageName() {
        return sbn.getPackageName();
    }

    public boolean hasVibration() {
        return false;
    }

    public boolean hasSound() {
        return false;
    }

    public String getTitle() {
        return computeTitle().trim();
    }

    @NonNull
    private String computeTitle() {
        if(getPackageName().toLowerCase().equals("com.slack")) {
            return notification.extras.getString(Notification.EXTRA_TITLE, "").replaceAll("^\\([0-9]+\\)", "");
        }
        return notification.extras.getString(Notification.EXTRA_TITLE, "");
    }

    public String getAccount() {
        return notification.extras.getString(Notification.EXTRA_SUB_TEXT, "");
    }

    public boolean shouldBeTracked() {
        boolean isOngoing = ((notification.flags & Notification.FLAG_ONGOING_EVENT) == Notification.FLAG_ONGOING_EVENT);
        return !isOngoing;
    }
}
