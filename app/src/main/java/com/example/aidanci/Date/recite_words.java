package com.example.aidanci.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidanci.Activity.MainActivity;
import com.example.aidanci.R;
import com.example.aidanci.word.HttpCallbackListener;
import com.example.aidanci.word.HttpUtil;
import com.example.aidanci.word.Netword;
import com.example.aidanci.word.Parse;
import com.example.aidanci.word.PronURL;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class recite_words extends AppCompatActivity {

    private int monitor_visibility = 0;
    private String getkey;
    private Cursor getcursor;

    private boolean SetToDone = true;

    private InputStream inputStream;

    private LinearLayout AllPron;
    private LinearLayout AllMean;
    private LinearLayout AllSent;

    private TextView key;
    private TextView Epron;
    private TextView Apron;
    private TextView mean;
    private TextView sent;

    private ImageButton Evoice;
    private ImageButton Avoice;


    private Button next_word;
    private Button add_word;
    private Button setsee;

    private MediaPlayer EmediaPlayer = new MediaPlayer();
    private MediaPlayer AmediaPlayer = new MediaPlayer();
    private Netword netword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite_words);

        AllPron = findViewById(R.id.AllPron);
        AllMean = findViewById(R.id.AllMean);
        AllSent = findViewById(R.id.AllSent);

        key = findViewById(R.id.key);
        Epron = findViewById(R.id.Epron);
        Apron = findViewById(R.id.Apron);
        mean = findViewById(R.id.mean);
        sent = findViewById(R.id.sent);

        Avoice = findViewById(R.id.Avoice);
        Evoice = findViewById(R.id.Evoice);

        next_word = findViewById(R.id.next_word);
        add_word = findViewById(R.id.add_button);
        setsee = findViewById(R.id.setsee);


        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

//        取得今日数据库单词
        try {
            String sql = "Done is 0";
            getcursor = CursorGetAll.GetlimitedCursor("EveryDW",sql);
        }catch (Exception e){
            Log.d("WSHH",e.toString());
        }
        getcursor.moveToNext();
        getkey = getcursor.getString(getcursor.getColumnIndex("name"));
        key.setText(getkey);

//       调用API并且解析XML

        final String address = "https://dict-co.iciba.com/api/dictionary.php?w=" + getkey + "&key=B4A502949602934F7054BCC1249CF57F";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        inputStream = new ByteArrayInputStream(response.getBytes());
                        try {
                            netword = Parse.parseXML(inputStream);
                        } catch (Exception e) {
                            Log.d("WSHH", e.toString());
                        }
                        //  刷新界面
                        Log.d("WSHH",netword.getKey());
                        try {
                            Epron.setText(netword.getPronunciations(0).getPs());
                        }catch (Exception e){
                            e.printStackTrace();
                            Epron.setText("没有找到！");
                        }
                        try {
                            Apron.setText(netword.getPronunciations(1).getPs());
                        }catch (Exception e){
                            e.printStackTrace();
                            Apron.setText("没有找到！");
                        }
                        try {
                            mean.setText(netword.getMeans());
                        }catch (Exception e){
                            e.printStackTrace();
                            mean.setText("没有找到！");
                        }
                        try {
                            sent.setText(netword.getSents());
                        }catch (Exception e){
                            e.printStackTrace();
                            sent.setText("没有找到！");
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.d("WSH", e.toString());
            }
        });

        //发音按钮
        Evoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EmediaPlayer.reset();
                    EmediaPlayer.setDataSource(PronURL.ChangePronURL(netword.getPronunciations(0).getPron()));
                    EmediaPlayer.prepareAsync();
                    EmediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) { EmediaPlayer.start();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Avoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AmediaPlayer.reset();
                    AmediaPlayer.setDataSource(PronURL.ChangePronURL(netword.getPronunciations(1).getPron()));
                    AmediaPlayer.prepareAsync();
                    AmediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            AmediaPlayer.start();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        //展示下一个单词
        next_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AvoidClickCrazy.isFastClick()){
                    Toast.makeText(getBaseContext(),"不要过快地点击",Toast.LENGTH_SHORT).show();
                    return ;
                }
                try {
                    if (SetToDone){
                        String sql = "Done = 1 where name is '"+getcursor.getString(getcursor.getColumnIndex("name"))+"'";
                        UpdateSql.execute("EveryDW",sql);
                        AddWordToAllDone.Dosql(getcursor.getString(getcursor.getColumnIndex("name")));
                    }
                }catch (Exception e){
                    Log.d("WSHHH",e.toString());
                }

                try {
                    getcursor.moveToNext();
                    getkey = getcursor.getString(getcursor.getColumnIndex("name"));
                }catch (Exception e){
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(recite_words.this,SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("今日任务完成！");
                    sweetAlertDialog.setContentText("确定返回？");
                    sweetAlertDialog.setConfirmText("是");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                            Intent intent1 = new Intent(recite_words.this, MainActivity.class);
                            startActivity(intent1);
                        }
                    });
                    sweetAlertDialog.setCancelText("否");
                    sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    })
                            .show();
                }
                key.setText(getkey);
                AllPron.setVisibility(View.INVISIBLE);
                AllMean.setVisibility(View.INVISIBLE);
                AllSent.setVisibility(View.INVISIBLE);
                monitor_visibility = 0;
                setsee.setVisibility(View.VISIBLE);
                add_word.setVisibility(View.INVISIBLE);
                //       调用API并且解析XML

                String address = "https://dict-co.iciba.com/api/dictionary.php?w="+getkey+"&key=B4A502949602934F7054BCC1249CF57F";
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                inputStream = new ByteArrayInputStream(response.getBytes());
                                try {
                                    netword = Parse.parseXML(inputStream);
                                }catch (Exception e){
                                    Log.d("WSH",e.toString());
                                }
//  刷新界面
                                try {
                                    Epron.setText(netword.getPronunciations(0).getPs());
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Epron.setText("没有找到！");
                                }
                                try {
                                    Apron.setText(netword.getPronunciations(1).getPs());
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Apron.setText("没有找到！");
                                }
                                try {
                                    mean.setText(netword.getMeans());
                                }catch (Exception e){
                                    e.printStackTrace();
                                    mean.setText("没有找到！");
                                }
                                try {
                                    sent.setText(netword.getSents());
                                }catch (Exception e){
                                    e.printStackTrace();
                                    sent.setText("没有找到！");
                                }
//HUAWEI和一加在这个地方需要使用https
                            }
                        });
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.d("WSH",e.toString());
                    }
                });
            }
        });

        setsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetToDone = false;
                if (monitor_visibility == 0){
                    AllPron.setVisibility(View.VISIBLE);
                }
                else if (monitor_visibility == 1){
                    AllMean.setVisibility(View.VISIBLE);
                }
                else if (monitor_visibility == 2){
                    AllSent.setVisibility(View.VISIBLE);
                    setsee.setVisibility(View.GONE);
                    add_word.setVisibility(View.VISIBLE);
                }
                monitor_visibility=(monitor_visibility+1)%3;
            }
        });

        //加入单词到生词本
        add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWordToVOCAB.Dosql(netword);
            }
        });
    }
}
