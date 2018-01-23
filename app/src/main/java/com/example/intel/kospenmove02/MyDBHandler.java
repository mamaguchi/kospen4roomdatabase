package com.example.intel.kospenmove02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kospenDB.db";
    public static final String TABLE_KOSPENUSERS = "kospenusers";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "name";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_KOSPENUSERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_KOSPENUSERS);
        onCreate(sqLiteDatabase);
    }

    public void addPerson(Person person){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.get_name());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_KOSPENUSERS, null, values);
        db.close();
    }

    public void deletePerson(String ic){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_KOSPENUSERS + " WHERE " +
                COLUMN_NAME + "=\"" + ic + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        StringBuilder sb = new StringBuilder(50);
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_KOSPENUSERS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("name"))!=null){
                sb.setLength(0);
                sb.setLength(50);
                sb.append(c.getString(c.getColumnIndex("name")));

                dbString += sb.toString();
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }

}
