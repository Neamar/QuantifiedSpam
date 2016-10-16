package fr.neamar.quantifiedspam.achievers;

import java.util.Calendar;

public class DayAchiever extends TimeBasedAchiever {
    public DayAchiever() {
        super("DayAchiever", "Day notification achievement", "day", new String[]{
                "Just warming up",
                "Another day in the office",
                "Busy day in the office",
                "Can't wait to go to sleep",
                "Need to sleep",
                "SLEEP PLS",
        });
    }

    @Override
    protected int getTimeBucketKey() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
}
