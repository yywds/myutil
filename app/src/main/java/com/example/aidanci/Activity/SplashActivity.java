package com.example.aidanci.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.aidanci.Login.LoginActivity;
import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class SplashActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        QuanPing(this);//全屏

        findView();
        initTask();
    }

    /** 初始化控件 */
    private void findView(){
        context = this;
    }

    /** 延时跳转
     *  延时3秒跳转到主界面
     * */
    private void initTask(){
        String PhoneInfo = SharedInfo("UserPhone","phone");//手机号
        String XuexiInfo = SharedInfo("XuexiLiang","xuexi");//学习量
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (PhoneInfo != null && XuexiInfo == null){
                    IntentToPage(context,MainActivity.class);
                }else if (PhoneInfo != null && XuexiInfo != null){
                    System.out.println("手机号："+PhoneInfo);
                    System.out.println("密码："+XuexiInfo);
                    IntentToPage(context,MainActivity2.class);
                }else {
                    IntentToPage(context,LoginActivity.class);
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,3000);
    }
}
