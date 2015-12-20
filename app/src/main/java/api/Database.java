package api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shit";
    private static final String DATABASE_TABLE_COMPANY = "company";
    private static final String DATABASE_TABLE_PARTNUMBERS = "partnumbers";
    private static final String DATABASE_TABLE_SYMPTOMS = "symptoms";
    private static final String DATABASE_TABLE_CAUSES = "causes";
    private static final String DATABASE_TABLE_POTENTIAL_CAUSES = "potential_causes";
    public static final String KEY_ID = "_id";
    public static final String KEY_GP_NO = "gpno";
    public static final String KEY_MODEL = "model";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_APPLICATION = "application";
    public static final String KEY_POTENTIAL_CAUSE = "potential_cause";
    public static final String KEY_OEM_NO = "oemno";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CAUSE = "cause";
    public static final String KEY_SYMPTOM = "symptom";
    public static final String KEY_POTENTIAL_CAUSE_NO = "cause_no";
    public static final String TAG = Database.class.getSimpleName();
    static final String CREATE_TABLE_COMPANY = "CREATE TABLE " + DATABASE_TABLE_COMPANY +" (" + KEY_ID +
            " INTEGER PRIMARY KEY, " + KEY_COMPANY + " VARCHAR NOT NULL);";
    static final String CREATE_TABLE_PARTNUMBERS = "CREATE TABLE " + DATABASE_TABLE_PARTNUMBERS + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_GP_NO + " VARCHAR NOT NULL, " + KEY_OEM_NO +
            " VARCHAR NOT NULL, " + KEY_APPLICATION + " VARCHAR NOT NULL, " + KEY_MODEL + " VARCHAR NOT NULL, " +
            KEY_COMPANY + " VARCHAR NOT NULL, " + KEY_IMAGE + " INTEGER);";
    static final String CREATE_TABLE_SYMPTOMS = "CREATE TABLE " + DATABASE_TABLE_SYMPTOMS +" (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SYMPTOM + " VARCHAR NOT NULL);";
    static final String CREATE_TABLE_CAUSES = "CREATE TABLE " + DATABASE_TABLE_CAUSES + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_SYMPTOM + " VARCHAR NOT NULL, " + KEY_CAUSE + " VARCHAR NOT NULL, " +
            KEY_POTENTIAL_CAUSE_NO + " VARCHAR NOT NULL);";
    static final String CREATE_TABLE_POTENTIAL_CAUSES = "CREATE TABLE " + DATABASE_TABLE_POTENTIAL_CAUSES +" (" + KEY_ID +
            " INTEGER PRIMARY KEY, " + KEY_POTENTIAL_CAUSE + " VARCHAR NOT NULL);";
    DatabaseHelper DBHelper;
    final Context mCtx;
    SQLiteDatabase db;

    public Database(Context paramContext){
        Log.e(TAG, "Constructor");
        mCtx = paramContext;
        DBHelper = new DatabaseHelper(mCtx);
    }

    public Database open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public void insertCompany(String _id, String company){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _id);
        values.put(KEY_COMPANY, company);
        long id = db.insert(DATABASE_TABLE_COMPANY, null, values);
        Log.e(TAG, "Value inserted into " + DATABASE_TABLE_COMPANY + ": " + id);
    }

    public void insertPotentialCause(String _id, String pcause){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _id);
        values.put(KEY_POTENTIAL_CAUSE, pcause);
        long id = db.insert(DATABASE_TABLE_POTENTIAL_CAUSES, null, values);
        Log.e(TAG, "Value inserted into " + DATABASE_TABLE_POTENTIAL_CAUSES + ": " + id);
    }

    public void insertSymptom(String _id, String symptom){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _id);
        values.put(KEY_SYMPTOM, symptom);
        long id = db.insert(DATABASE_TABLE_SYMPTOMS, null, values);
        Log.e(TAG, "Value inserted into " + DATABASE_TABLE_SYMPTOMS + ": " + id);
    }

    public void insertCause(String _id, String symptom, String cause, String no){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _id);
        values.put(KEY_SYMPTOM, symptom);
        values.put(KEY_CAUSE, cause);
        values.put(KEY_POTENTIAL_CAUSE_NO, no);
        long id = db.insert(DATABASE_TABLE_CAUSES, null, values);
        Log.e(TAG, "Value inserted into " + DATABASE_TABLE_CAUSES + ": " + id);
    }

    public void insertPartnumbers(String _id, String gpno, String oem, String application, String model, String company, String image){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, _id);
        values.put(KEY_GP_NO, gpno);
        values.put(KEY_OEM_NO, oem);
        values.put(KEY_APPLICATION, application);
        values.put(KEY_MODEL, model);
        values.put(KEY_COMPANY, company);
        values.put(KEY_IMAGE, image);
        long id = db.insert(DATABASE_TABLE_PARTNUMBERS, null, values);
        Log.e(TAG, "Value inserted into " + DATABASE_TABLE_PARTNUMBERS + ": " + id);
    }

    public Cursor getCauses(String symptom){
        String sql = "SELECT * FROM " + DATABASE_TABLE_CAUSES + " WHERE symptom  = '" + symptom + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c != null){
            return c;
        }
        return null;
    }

    public Cursor getPotentialCause(String potential_cause){
        String sql = "SELECT * FROM " + DATABASE_TABLE_POTENTIAL_CAUSES + " WHERE _id= '" + potential_cause + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c != null){
            return c;
        }
        return null;
    }

    public Cursor getDetails(String id){
        String sql = "SELECT * FROM PARTNUMBERS WHERE _id = '" + id + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c != null){
            return c;
        }
        return null;
    }

    public Cursor getPartnumbers(String company){
        String sql = "SELECT _id, oemno FROM PARTNUMBERS WHERE company = '" + company + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c != null){
            return c;
        }
        return null;
    }

    public List<String> getCompany(){
        Log.e(TAG, "DB Path: " + mCtx.getDatabasePath(DATABASE_NAME).getAbsolutePath());
        Cursor c = db.query(DATABASE_TABLE_COMPANY, null, null, null, null, null, null);
        //Cursor c = db.rawQuery("SELECT * from " + DATABASE_TABLE_COMPANY, null);
        List<String> results = new ArrayList<>();
        //Log.e(TAG, "Cursor data: " + DatabaseUtils.dumpCursorToString(c));
        int iCM = c.getColumnIndex(KEY_COMPANY);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Log.e(TAG, "Loop data: " + c.getString(iCM));
            results.add(c.getString(iCM));
        }
        Log.e(TAG, "Results: " + results);
        return results;
    }

    public List<String> getSymptoms(){
        Log.e(TAG, "DB Path: " + mCtx.getDatabasePath(DATABASE_NAME).getAbsolutePath());
        Cursor c = db.query(DATABASE_TABLE_SYMPTOMS, null, null, null, null, null, null);
        //Cursor c = db.rawQuery("SELECT * from " + DATABASE_TABLE_COMPANY, null);
        List<String> results = new ArrayList<>();
        //Log.e(TAG, "Cursor data: " + DatabaseUtils.dumpCursorToString(c));
        int iCM = c.getColumnIndex(KEY_SYMPTOM);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Log.e(TAG, "Loop data: " + c.getString(iCM));
            results.add(c.getString(iCM));
        }
        Log.e(TAG, "Results: " + results);
        return results;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context paramContext){
            super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase sdb){
            sdb.execSQL(CREATE_TABLE_COMPANY);
            sdb.execSQL(CREATE_TABLE_PARTNUMBERS);
            sdb.execSQL(CREATE_TABLE_SYMPTOMS);
            sdb.execSQL(CREATE_TABLE_CAUSES);
            sdb.execSQL(CREATE_TABLE_POTENTIAL_CAUSES);
        }

        public void onUpgrade(SQLiteDatabase sdb, int paramInt1, int paramInt2){
            Log.w("Database", "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ", which will destroy old data");
            sdb.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE_COMPANY);
            sdb.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE_PARTNUMBERS);
            sdb.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE_SYMPTOMS);
            sdb.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE_CAUSES);
            sdb.execSQL("DROP TABLE IF EXSISTS " + DATABASE_TABLE_POTENTIAL_CAUSES);
            onCreate(sdb);
        }
    }
}
