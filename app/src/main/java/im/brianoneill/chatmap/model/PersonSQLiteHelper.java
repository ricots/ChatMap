package im.brianoneill.chatmap.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brianoneill on 21/04/16.
 */
public class PersonSQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PersonRecordsDB";

    public PersonSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create student records table
        String CREATE_PERSONRECORDS_TABLE = "CREATE TABLE IF NOT EXISTS PersonRecords ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT)";
        // create student records table
        db.execSQL(CREATE_PERSONRECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older student records table if existed
        db.execSQL("DROP TABLE IF EXISTS PersonRecords");
        // create fresh student records table
        this.onCreate(db);
    }

}
