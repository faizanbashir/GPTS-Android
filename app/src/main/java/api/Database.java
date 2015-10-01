package api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class Database {
    static final String DATABASE_CREATE = "create table Reflections (_id integer primary key autoincrement, title varchar not null, reflection varchar not null)";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StrayReflections";
    private static final String DATABASE_TABLE = "Reflections";
    public static final String KEY_ID = "_id";
    public static final String KEY_ENTRY = "reflection";
    public static final String KEY_REFLECTION = "reflection";
    public static final String KEY_TITLE = "title";
    public static final String TAG = "Database";
    DatabaseHelper DBHelper;
    final Context context;
    SQLiteDatabase db;

    public Database(Context paramContext){
        context = paramContext;
        DBHelper = new DatabaseHelper(context);
    }

    public void close()
    {
        DBHelper.close();
    }

    public boolean delete(long paramLong){
        return db.delete(KEY_ENTRY, "_id=" + paramLong, null) > 0;
    }

    public Cursor getAll(){
        return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TITLE, KEY_ENTRY }, null, null, null, null, null);
    }

    public Cursor getSome(int id) throws SQLException {
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_TITLE, KEY_ENTRY },
                "_id=" + id, null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            return cursor;
        }
        return null;
    }

    public long insert(String paramString1, String paramString2){
        ContentValues localContentValues = new ContentValues();
        localContentValues.put(KEY_TITLE, paramString1);
        localContentValues.put(KEY_ENTRY, paramString2);
        return this.db.insert(DATABASE_TABLE, null, localContentValues);
    }

    public Database open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public boolean update(long paramLong, String paramString1, String paramString2){
        ContentValues localContentValues = new ContentValues();
        localContentValues.put(KEY_TITLE, paramString1);
        localContentValues.put(KEY_ENTRY, paramString2);
        return this.db.update(DATABASE_TABLE, localContentValues, "_id=" + paramLong, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context paramContext){
            super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase paramSQLiteDatabase){
            paramSQLiteDatabase.execSQL(DATABASE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2){
            Log.w("Database", "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ", which will destroy old data");
            paramSQLiteDatabase.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE);
            onCreate(paramSQLiteDatabase);
        }
    }
}
