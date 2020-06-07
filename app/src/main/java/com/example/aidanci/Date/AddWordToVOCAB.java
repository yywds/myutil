package com.example.aidanci.Date;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aidanci.word.Netword;

public class AddWordToVOCAB {

    static public void Dosql(Netword netword){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("Word.db");
        try {
            ContentValues values = new ContentValues();
            values.put("name",netword.getKey());
            values.put("Eps",netword.getPronunciations(0).getPs());
            values.put("Epron",netword.getPronunciations(0).getPron());
            values.put("Aps",netword.getPronunciations(1).getPs());
            values.put("Apron",netword.getPronunciations(1).getPron());
            values.put("Means",netword.getMeans());
            values.put("Sents",netword.getSents());
            db.insert("VOCAB",null,values);
        }catch (Exception e){
            Log.d("WSHHH",e.toString());
        }
        Log.d("WSHHH","OK");
    }
}
