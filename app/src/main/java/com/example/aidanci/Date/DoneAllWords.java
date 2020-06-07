package com.example.aidanci.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.aidanci.R;
import com.jaeger.library.StatusBarUtil;

import mehdi.sakout.fancybuttons.FancyButton;

public class DoneAllWords extends AppCompatActivity {
    private ListView All_Done_Words;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_all_words);

        StatusBarUtil.setTranslucent(DoneAllWords.this);
        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }



        Cursor cursor = CursorGetAll.limitexecute("AllDone");
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(DoneAllWords.this,R.layout.item_for_all_done,cursor, new String[]{"name"},new int[]{R.id.name},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        try{
            All_Done_Words = findViewById(R.id.All_Done_Words);
        }catch (Exception e){
            Log.d("WSHH",e.toString());
        }
        try {
            All_Done_Words.setAdapter(cursorAdapter);
        }catch (Exception e){
            Log.d("WSHH",e.toString());
        }


    }
}
