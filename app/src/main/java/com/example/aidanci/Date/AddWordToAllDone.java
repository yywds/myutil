package com.example.aidanci.Date;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddWordToAllDone {

    static public void Dosql(String name){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        try {
            ContentValues values = new ContentValues();
            values.put("name",name);
            db.insert("AllDone",null,values);
            values.clear();
        }catch (Exception e){
            Log.d("WSHHH",e.toString());
        }
        Log.d("WSHHH","OK");
    }
}
