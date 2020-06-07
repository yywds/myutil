package com.example.aidanci.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidanci.R;
import com.example.aidanci.word.HttpCallbackListener;
import com.example.aidanci.word.HttpUtil;
import com.example.aidanci.word.Netword;
import com.example.aidanci.word.Parse;
import com.example.aidanci.word.PronURL;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

import static com.example.aidanci.Utils.CommonUtils.QuanPing;

public class Inquiry extends AppCompatActivity {
    private SearchView search_word;
    private Netword netword;
    private String address;
    private String word;
    private InputStream inputStream;

    private TextView key;
    private TextView Epron;
    private TextView Apron;
    private TextView mean;
    private TextView sent;

    private ImageButton Evoice;
    private ImageButton Avoice;

    private MediaPlayer EmediaPlayer = new MediaPlayer();
    private MediaPlayer AmediaPlayer = new MediaPlayer();

    private LinearLayout AllNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        QuanPing(this);
        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        search_word = findViewById(R.id.search_word);
        key = findViewById(R.id.key);
        Epron = findViewById(R.id.Epron);
        Apron = findViewById(R.id.Apron);
        mean = findViewById(R.id.mean);
        sent = findViewById(R.id.sent);

        Avoice = findViewById(R.id.Avoice);
        Evoice = findViewById(R.id.Evoice);

        AllNet = findViewById(R.id.AllNet);

        final SweetAlertDialog sweetAlertDialogProgress = new SweetAlertDialog(Inquiry.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.setTitleText("请稍等！");
        sweetAlertDialogProgress.setContentText("正在查找！");

        search_word.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                word = string;
                address = "https://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=B4A502949602934F7054BCC1249CF57F";
                sweetAlertDialogProgress.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                inputStream = new ByteArrayInputStream(response.getBytes());
                                try {
                                    netword = Parse.parseXML(inputStream);
                                }catch (Exception e){
                                    Log.d("WSH",e.toString());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        key.setText(word);

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
                                        AllNet.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                                Toast.makeText(Inquiry.this,"产生了错误，请先退出！",Toast.LENGTH_LONG).show();
                            }
                        });
                        try{
                            Thread.sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        sweetAlertDialogProgress.dismiss();
                    }
                }).start();
            }
        });

        search_word.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

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
    }
}

