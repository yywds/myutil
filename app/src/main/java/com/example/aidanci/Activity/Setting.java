package com.example.aidanci.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.addapp.pickers.widget.WheelListView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;
import static com.example.aidanci.Utils.CommonUtils.YanChi;

public class Setting extends AppCompatActivity {
    private Button sure;
    private TextView fuck;

    private WheelListView wordnum;
    private List<String > list = new ArrayList<>();
    private ImageView IM_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuanPing(this);
        setContentView(R.layout.activity_setting);

        StatusBarUtil.setTranslucent(Setting.this);
        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        sure = findViewById(R.id.sure);
        wordnum = findViewById(R.id.select_num);
        fuck = findViewById(R.id.fuck);

        for (int i=10;i <= 150;i=i+10)
        {
            list.add(String.valueOf(i));
        }
        wordnum.setItems(list);
        wordnum.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
            @Override
            public void onItemSelected(boolean b, int i ,String s) {
                fuck.setText(s);
            }
        });

        final SweetAlertDialog sweetAlertDialogProgress = new SweetAlertDialog(Setting.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.setTitleText("请稍等！");
        sweetAlertDialogProgress.setContentText("正在同步！");

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInfo("XuexiLiang","xuexi",fuck.getText().toString());//储蓄学习量
                SharedInfo("XuexiLiang","xuexi");
                System.out.println( SharedInfo("XuexiLiang","xuexi"));
                DialogShow(Setting.this,"设置成功");
                YanChi(Setting.this,MainActivity2.class);//3秒后跳转到首页
            }
        });

        IM_fanhui=findViewById(R.id.sz_fanhui);
        //返回上一界面
        IM_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

