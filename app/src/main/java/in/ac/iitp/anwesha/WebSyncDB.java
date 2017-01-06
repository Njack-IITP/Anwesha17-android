package in.ac.iitp.anwesha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WebSyncDB extends SQLiteOpenHelper {

    static final String EVENT_ID = "id";
    static final String EVENT_NAME = "name";
    static final String EVENT_fee = "fee";
    static final String EVENT_day = "day";
    static final String EVENT_size = "size";
    static final String EVENT_code = "code";
    static final String EVENT_details = "details";
    private static final String TABLE_EVENT = "Event";
    private static String DBNAME = "websyncdb";
    private static int VERSION = 1;
    private SQLiteDatabase mDB;

    public WebSyncDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_EVENT + " ( " +
                EVENT_ID + " int(3) , " +
                EVENT_NAME + " varchar(35), " +
                EVENT_fee + " int(4), " +
                EVENT_day + " int(1), " +
                EVENT_size + " int(2), " +
                EVENT_code + " varchar(35), " +
                EVENT_details + " varchar(1000) " +

                " ) ";
        db.execSQL(sql);

    }

    public long insertEvent(ContentValues[] contentValues) {
        mDB.delete(TABLE_EVENT, null, null);
        for (ContentValues cv : contentValues)
            mDB.insert(TABLE_EVENT, null, cv);
        return contentValues.length;
    }

    public int clearEvent() {
        return mDB.delete(TABLE_EVENT, null, null);
    }

    public Cursor getAllEvents() {
        return mDB.query(TABLE_EVENT, new String[]{EVENT_ID, EVENT_NAME, EVENT_fee, EVENT_day, EVENT_size, EVENT_code, EVENT_details}, null, null, null, null, null);
    }

    public Cursor getParticularEvents(String code) {
        return mDB.query(TABLE_EVENT, new String[]{EVENT_ID, EVENT_NAME, EVENT_fee, EVENT_day, EVENT_size, EVENT_code, EVENT_details}, EVENT_code + "=?", new String[]{code}, null, null, null);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
