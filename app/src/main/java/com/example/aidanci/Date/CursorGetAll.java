package com.example.aidanci.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CursorGetAll {

    static public Cursor execute(){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursor = db.rawQuery("select * from VOCAB",null);
        return  cursor;
    }
    static public Cursor limitexecute(String table){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursor = db.rawQuery("select * from "+table,null);
        return  cursor;
    }
    static public String limitsql(String table,String limited_sql){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursor = db.rawQuery("select * from "+table+" where "+limited_sql,null);
        if (cursor.getCount() > 0){
            return String.valueOf(cursor.getCount());
        }
        else {
            return String.valueOf(0);
        }
    }
    static public int HaveCursor(String table,String limited_sql){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursor = db.rawQuery("select * from "+table+" where "+limited_sql,null);
        return cursor.getCount();
    }
    static public Cursor GetlimitedCursor(String table,String limited_sql){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursor = db.rawQuery("select * from "+table+" where "+limited_sql,null);
        return cursor;
    }
}
