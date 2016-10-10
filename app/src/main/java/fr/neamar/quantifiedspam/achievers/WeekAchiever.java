package fr.neamar.quantifiedspam.achievers;

import java.util.Calendar;

public class WeekAchiever extends TimeBasedAchiever {
    public WeekAchiever() {
        super("WeekAchiever", "Week notification achievement", new String[]{
                "",
                "",
                "",
                "Weekend coming soon?",
                "A hell of a week",
                "Dat week",
                "The week that keeps on giving",
                "Is it friday yet?",
                "SATURDAY PLS",
        });
    }

    @Override
    protected int getTimeBucketKey() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }
}
