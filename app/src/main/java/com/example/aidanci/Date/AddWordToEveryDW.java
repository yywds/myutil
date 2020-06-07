package com.example.aidanci.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddWordToEveryDW {
    static public void Dosql(String ciku,String count){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");

        //选择词库中Selected为False的单词
        final Cursor cursorFrom = db.rawQuery("select * from "+ciku+" where Selected is 0 order by random() limit "+count,null);
        ContentValues values = new ContentValues();
        String Date = GetNowDate.DoIt();
        while (cursorFrom.moveToNext()){
            values.put("name",cursorFrom.getString(cursorFrom.getColumnIndex("name")));
            values.put("Done",false);
            values.put("Date",Date);
            db.insert("EveryDW",null,values);
            values.clear();
        }

        //将每个选中的词库的单词的Selected置为True
        cursorFrom.moveToFirst();
        String name = cursorFrom.getString(cursorFrom.getColumnIndex("name"));
        db.execSQL("update "+ciku+" set Selected = 1 where name is '"+name+"'");
        while (cursorFrom.moveToNext()){
            name = cursorFrom.getString(cursorFrom.getColumnIndex("name"));
            db.execSQL("update "+ciku+" set Selected = 1 where name is '"+name+"'");
        }
    }
}
