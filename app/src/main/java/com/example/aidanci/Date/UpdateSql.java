package com.example.aidanci.Date;

import android.database.sqlite.SQLiteDatabase;

public class UpdateSql {

    static public void execute(String table,String sql){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        String str = "update "+table+" set "+sql;
        db.execSQL(str);
    }
}
