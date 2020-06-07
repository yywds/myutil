package com.example.aidanci.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.Activity.MainActivity;
import com.example.aidanci.Activity.MainActivity2;
import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;
import com.example.aidanci.Utils.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.user_et_logo)
    EditText userEtLogo;
    @BindView(R.id.password_et_logo)
    EditText passwordEtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CommonUtils.QuanPing(this);//全屏
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonLogin, R.id.textViewForget, R.id.textViewRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                Login();//登录判断
                break;
            case R.id.textViewForget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.textViewRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }


    //登录判断
    private void Login() {
        String PhoneInfo = SharedInfo("UserPhone","phone");//手机号
        String XuexiInfo = SharedInfo("XuexiLiang","xuexi");//学习量
        //Mysql数据库操作需要在子线程中进行
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                String uName = userEtLogo.getText().toString().trim();
                String uPwd = passwordEtLogo.getText().toString().trim();
                SaveInfo("UserPhone","phone",uName);
                //调用DBHelper操作类
                DBHelper dbHelper = new DBHelper();
                Boolean result = dbHelper.SelectUserLogin(uName, uPwd);

                if (uName.isEmpty() && uPwd.isEmpty()) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else if (uName.length() != 11) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else if (!result) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "手机号或密码错误！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } if (PhoneInfo != null && XuexiInfo == null){
                    IntentToPage(LoginActivity.this,MainActivity.class);
                    System.out.println("手机号："+PhoneInfo);
                    System.out.println("密码："+XuexiInfo);
                    finishAffinity();//销毁登录页
                }else if (PhoneInfo != null && XuexiInfo != null){
                    System.out.println("手机号："+PhoneInfo);
                    System.out.println("密码："+XuexiInfo);
                    IntentToPage(LoginActivity.this, MainActivity2.class);
                    finishAffinity();//销毁登录页
                }
            }
        }).start();
    }
}
