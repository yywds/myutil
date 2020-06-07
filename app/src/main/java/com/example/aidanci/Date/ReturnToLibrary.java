package com.example.aidanci.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReturnToLibrary {

    static public void Dosql(String lastciku,String sql){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        Cursor cursorNo = CursorGetAll.GetlimitedCursor("EveryDW",sql);
        String name;
        while (cursorNo.moveToNext()){
            name = cursorNo.getString(cursorNo.getColumnIndex("name"));
            db.execSQL("update "+lastciku+" set Selected = 0 where name is '"+name+"'");
        }
    }
}
