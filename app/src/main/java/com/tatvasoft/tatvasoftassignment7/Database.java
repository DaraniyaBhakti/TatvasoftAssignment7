package com.tatvasoft.tatvasoftassignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database  extends SQLiteOpenHelper {

    public static int id = 1;
    public Database(@Nullable Context context) {
        super(context, "Cities", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Cities(id NUMBER ,CityName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertData(String CityName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        id=id+1;
        values.put("CityName",CityName);
        db.insert("Cities",null,values);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT DISTINCT * FROM Cities",null);
    }

    public void deleteByName(String CityName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Cities", "CityName=?", new String[]{CityName});
    }
}
