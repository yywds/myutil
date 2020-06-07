package com.example.aidanci.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidanci.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.addapp.pickers.widget.WheelListView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class NowLibrary extends AppCompatActivity {

    private String lastciku;
    private String newciku="cet4";
    private String getLastciku;
    private String every_day_word;
    private Cursor cursorDone;
    private Cursor cursorNo;
    private Button sure;

    private WheelListView select_ciku;
    private List<String > list = new ArrayList<>();

    private TextView last_ciku;
    private TextView now_ciku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_library);
        StatusBarUtil.setTranslucent(NowLibrary.this);
        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        final SweetAlertDialog sweetAlertDialogProgress = new SweetAlertDialog(NowLibrary.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.setTitleText("请稍等！");
        sweetAlertDialogProgress.setContentText("正在同步！");

        last_ciku = findViewById(R.id.last_ciku);
        now_ciku  = findViewById(R.id.now_ciku);
        select_ciku = findViewById(R.id.select_ciku);
        sure        = findViewById(R.id.sure);

        list.add("四级");
        list.add("六级");
        list.add("考研");
        list.add("专四");
        list.add("专六");
        list.add("专八");
        select_ciku.setItems(list);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        lastciku =prefs.getString("ciku",null);
        every_day_word = prefs.getString("every_day_word",null);
        if (lastciku == null){
            lastciku = "cet4";
        }

        switch (lastciku){
            case "cet4":
                getLastciku = "四级";
                break;
            case "cet6":
                getLastciku = "六级";
                break;
            case "kaoyan":
                getLastciku = "考研";
                break;
            case "vocabulary":
                getLastciku = "专八";
                break;
            case "cet4_import":
                getLastciku = "专四";
                break;
            case "cet6_import":
                getLastciku = "专六";
                break;
        }
        last_ciku.setText(getLastciku);

        select_ciku.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
            @Override
            public void onItemSelected(boolean b, int i, String s) {
                now_ciku.setText(s);
                switch (s){
                    case "四级":
                        newciku = "cet4";
                        break;
                    case "六级":
                        newciku = "cet6";
                        break;
                    case "考研":
                        newciku = "kaoyan";
                        break;
                    case "专八":
                        newciku = "vocabulary";
                        break;
                    case "专四":
                        newciku = "cet4_import";
                        break;
                    case "专六":
                        newciku = "cet6_import";
                        break;
                }
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WSHH",newciku);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NowLibrary.this);
                lastciku =prefs.getString("ciku",null);
                if (!lastciku.equals(newciku)){
                    sweetAlertDialogProgress.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
                            SQLiteDatabase db = mg.getDatabase("Word.db");
                            //把完成了的放到AllDone表
                            String sqlDone = "Done is 1";
                            cursorDone = CursorGetAll.GetlimitedCursor("EveryDW",sqlDone);
                            while (cursorDone.moveToNext()){
                                ContentValues values = new ContentValues();
                                values.put("name",cursorDone.getString(cursorDone.getColumnIndex("name")));
                                db.insert("AllDone",null,values);
                                values.clear();
                            }
                            //还原没有完成的到原来的词库
                            String sqlNo = "Done is 0";
                            cursorNo = CursorGetAll.GetlimitedCursor("EveryDW",sqlNo);
                            String name;
                            while (cursorNo.moveToNext()){
                                name = cursorNo.getString(cursorNo.getColumnIndex("name"));
                                db.execSQL("update "+lastciku+" set Selected = 0 where name is '"+name+"'");
                            }
                            //清空当前的EveryDW
                            db.execSQL("delete from EveryDW");
                            //添加新的单词到EveryDW
                            AddWordToEveryDW.Dosql(newciku,every_day_word);
                            //把新词库写入文件
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(NowLibrary.this).edit();
                            editor.putString("ciku",newciku);
                            editor.apply();
                            try{
                                Thread.sleep(1500);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            String x = new String();
                            switch (newciku){
                                case "cet4":
                                    x = "四级";
                                    break;
                                case "cet6":
                                    x = "六级";
                                    break;
                                case "kaoyan":
                                    x = "考研";
                                    break;
                                case "vocabulary":
                                    x = "专八";
                                    break;
                                case "cet4_import":
                                    x = "专四";
                                    break;
                                case "cet6_import":
                                    x = "专六";
                                    break;
                            }
                            last_ciku.setText(x);
                            sweetAlertDialogProgress.dismiss();
                            Looper.prepare();
                            Toast.makeText(NowLibrary.this, "同步完成！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }).start();
                }
                else {
                    Toast.makeText(NowLibrary.this, "没有做出修改！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
