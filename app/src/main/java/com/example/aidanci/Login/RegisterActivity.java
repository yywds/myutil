package com.example.aidanci.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;
import com.example.aidanci.Utils.DBHelper;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aidanci.Utils.CommonUtils.ClearInfo;
import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.agin_password)
    EditText aginPassword;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.mibao1)
    EditText mibao1;
    @BindView(R.id.mibao2)
    EditText mibao2;
    @BindView(R.id.zuce)
    Button zuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        QuanPing(this);//全屏
        ButterKnife.bind(this);
    }

    @OnClick(R.id.zuce)
    public void onClick() {
        Register();
    }

    //注册判断
    private void Register() {
        //获取输入框中的内容
        final String uphone = phone.getText().toString().trim();
        final String upassword = password.getText().toString().trim();
        final String uaginPassword = aginPassword.getText().toString().trim();
        final String umibao1 = mibao1.getText().toString().trim();
        final String umibao2 = mibao2.getText().toString().trim();
        final String uname = name.getText().toString().trim();

        if (uphone.isEmpty()) {
            DialogShow(this, "请输入手机号！");
        } else if (uphone.length() != 11) {
            DialogShow(this, "请输入正确的手机号！");
        } else if (upassword.isEmpty()) {
            DialogShow(this, "请输入密码！");
        } else if (uaginPassword.isEmpty()) {
            DialogShow(this, "请输入确认密码！");
        } else if (!upassword.equals(uaginPassword)) {
            DialogShow(this, "密码不一致！");
        } else if (uname.isEmpty()) {
            DialogShow(this, "请输入昵称！");
        } else if (umibao1.isEmpty()) {
            DialogShow(this, "请输入密保问题！");
        } else if (umibao2.isEmpty()) {
            DialogShow(this, "请输入密保问题！");
        } else {

            //数据库操作要在子线程中进行，不然会报错
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    //调用DBHelper
                    DBHelper dbHelper = new DBHelper();
                    Boolean result = dbHelper.SelectPhone(uphone);
                    if (result) {
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, "该手机号已被注册！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {

                        Timer timer = new Timer();
                        TimerTask timerTask1 = new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        };
                        timer.schedule(timerTask1, 1000 * 3); //3秒后跳转到登录页
                        DBHelper.InsertUsers(uname, upassword, uphone, umibao1, umibao2);//进行注册

                        //清除保存的信息
                        ClearInfo("UserName");
                        ClearInfo("XuexiLiang");
                        ClearInfo("UserPhone");
                        ClearInfo("Xuanze");
                        ClearInfo("Count");

                        SaveInfo("UserName","name",uname);//存储用户名

                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            }).start();
        }
    }
}
