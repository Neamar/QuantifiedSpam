package fr.neamar.quantifiedspam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DB extends SQLiteOpenHelper {
    private static DateFormat dateAsString = new SimpleDateFormat("yyyy-MM-dd");

    private static final int DATABASE_VERSION = 1;
    private static final String NOTIFICATION_TABLE_NAME = "notification";
    private static final String NOTIFICATION_TABLE_CREATE =
            "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "packageName TEXT," +
                    "title TEXT, " +
                    "date TEXT, " +
                    "hour INTEGER, " +
                    "hasVibration BOOLEAN NOT NULL CHECK (hasVibration IN (0,1)), " +
                    "hasSound BOOLEAN NOT NULL CHECK (hasSound IN (0,1))" +
                    ");";

    public DB(Context context) {
        super(context, "db", null, DATABASE_VERSION);
        dateAsString.setTimeZone(TimeZone.getDefault());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTIFICATION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void insertNotification(Context context, String packageName, String title, boolean hasVibration, boolean hasSound) {
        Date now = new Date();
        String date = dateAsString.format(now);
        int hour = now.getHours();

        SQLiteDatabase db = new DB(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("packageName", packageName);
        values.put("title", title);
        values.put("date", date);
        values.put("hour", hour);
        values.put("hasVibration", hasVibration);
        values.put("hasSound", hasSound);

        db.insert(NOTIFICATION_TABLE_NAME, null, values);
        db.close();
    }
}
