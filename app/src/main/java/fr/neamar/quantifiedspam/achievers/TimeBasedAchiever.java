package fr.neamar.quantifiedspam.achievers;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public abstract class TimeBasedAchiever extends Achiever {
    public TimeBasedAchiever(String tag, String humanTag, String[] names) {
        super(tag, humanTag, names);
    }

    @Override
    public void onNotificationReceived(Context context, StatusBarNotification sbn, SharedPreferences prefs, SharedPreferences.Editor editor) {
        // Read previous values
        int currentMilestoneIndex = prefs.getInt(getPrefsKey("milestoneIndex"), 0);
        int currentMilestone = MILESTONES[currentMilestoneIndex];

        int currentBucket = getTimeBucketKey();
        int lastKnownBucket = prefs.getInt(getPrefsKey("currentBucket"), -1);

        int currentValue;
        if(currentBucket == lastKnownBucket) {
            currentValue = prefs.getInt(getPrefsKey("count"), 0);
        }
        else {
            currentValue = 0;
            editor.putInt(getPrefsKey("currentBucket"), currentBucket);
        }

        // Increment value locally
        currentValue += 1;

        // Store new value
        editor.putInt(getPrefsKey("count"), currentValue);

        Log.i(tag, String.format("In bucket %s: %s [%s]", currentBucket, currentValue, currentMilestone));

        if (currentValue >= currentMilestone) {
            String name = achievementNames[Math.min(currentMilestoneIndex, achievementNames.length - 1)];
            Log.i(tag, String.format("Unlocked potential achievement: %s (%s)", name, currentValue));

            if (!name.isEmpty()) {
                unlockAchievement(context, getPrefsKey(Integer.toString(currentMilestone)), name, String.format("Get %s notifications in a single day", currentValue), humanTag);
            }

            editor.putInt(getPrefsKey("milestoneIndex"), currentMilestoneIndex + 1);
        }
    }

    protected abstract int getTimeBucketKey();
}
