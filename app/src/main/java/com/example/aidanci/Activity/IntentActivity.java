package com.example.aidanci.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.Utils.AppNetConfig;

import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.PostHttpValue2;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class IntentActivity extends AppCompatActivity {
    String CihuiInfo = SharedInfo("Xuanze","cihui");//词汇
    String NameInfo = SharedInfo("UserName","name");//学习量
    String PhoneInfo = SharedInfo("UserPhone","phone");//手机号
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//        PostHttpValue2("username", NameInfo,"type", CihuiInfo, AppNetConfig.SetBeidanciUrl);
//        //Post请求---后端
//        PostHttpValue2("type",CihuiInfo,"phone",PhoneInfo,AppNetConfig.PostInfoUrl);
        IntentToPage(IntentActivity.this, BeiActivity.class);//跳转到开始背单词页面
         finish();
//            }
//        },1000);

    }
}
