package com.example.aidanci.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;
import com.example.aidanci.Utils.DBHelper;
import com.kongzue.dialog.v2.TipDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.mibao1)
    EditText mibao1;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.mibao2)
    EditText mibao2;
    @BindView(R.id.agin_password)
    EditText aginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        CommonUtils.QuanPing(this);//全屏
        ButterKnife.bind(this);
    }

    @OnClick(R.id.queren)
    public void onClick() {
        Forget();//找回密码
    }

    private void Forget() {
        //获取输入框中的内容
        final String uphone = phone.getText().toString().trim();
        final String upassword = password.getText().toString().trim();
        final String uaginPassword = aginPassword.getText().toString().trim();
        final String umibao1 = mibao1.getText().toString().trim();
        final String umibao2 = mibao2.getText().toString().trim();

        if (uphone.isEmpty()) {
            CommonUtils.DialogShow(this, "请输入手机号！");
        } else if (uphone.length() != 11) {
            CommonUtils.DialogShow(this, "请输入正确的手机号！");
        } else if (umibao1.isEmpty()) {
            CommonUtils.DialogShow(this, "请输入密保问题！");
        } else if (umibao2.isEmpty()) {
            CommonUtils.DialogShow(this, "请输入密保问题！");
        } else if (upassword.isEmpty()) {
            CommonUtils.DialogShow(this, "请输入密码！");
        } else if (uaginPassword.isEmpty()) {
            CommonUtils.DialogShow(this, "请输入确认密码！");
        } else if (!upassword.equals(uaginPassword)) {
            CommonUtils.DialogShow(this, "密码不一致！");
        } else {
            //数据库操作要在子线程中进行，不然会报错
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    //调用DBHelper
                    DBHelper dbHelper = new DBHelper();
                    Boolean result = dbHelper.SelectPhone(uphone);
                    Boolean result2 = dbHelper.SelectMiBao(uphone, umibao1, umibao2);
                    if (!result) {
                        Looper.prepare();
                        Toast.makeText(ForgetActivity.this, "手机号不存在！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else if (!result2) {
                        Looper.prepare();
                        Toast.makeText(ForgetActivity.this, "密保问题不正确！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Timer timer = new Timer();
                        TimerTask timerTask1 = new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
                            }
                        };
                        timer.schedule(timerTask1, 1000 * 3); //3秒后跳转到登录页
                        DBHelper.UpdatePassword(upassword, uphone);//找回面密码
                        Looper.prepare();
                        Toast.makeText(ForgetActivity.this, "密码找回成功！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            }).start();
        }
    }
}
