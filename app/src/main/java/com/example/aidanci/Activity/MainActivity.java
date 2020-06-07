package com.example.aidanci.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.Fragment.FirstFragment;
import com.example.aidanci.Fragment.SecondFragment;
import com.example.aidanci.Fragment.ThirdFragment;
import com.example.aidanci.R;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.WaitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_MATERIAL;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.first)
    RelativeLayout first;
    @BindView(R.id.iv_second)
    ImageView ivSecond;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.second)
    RelativeLayout second;
    @BindView(R.id.iv_third)
    ImageView ivThird;
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.third)
    RelativeLayout third;
    @BindView(R.id.bottom_tab)
    LinearLayout bottomTab;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.app_main)
    RelativeLayout appMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    //初始化首页界面
    private void init() {
        DialogSettings.style = STYLE_MATERIAL;  //对话框为IOS风格
        DialogSettings.tip_theme = THEME_LIGHT;  //设置提示框主题为亮色主题
        DialogSettings.use_blur = true; //开启模糊
        WaitDialog.show(this, "数据加载中...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WaitDialog.dismiss();//2秒后取消对话框加载数据
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.content_layout, new FirstFragment());//初始页面
                beginTransaction.commit();
            }
        }, 2000);
    }

    @OnClick({R.id.first, R.id.second, R.id.third})
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.first:
                beginTransaction.replace(R.id.content_layout, new FirstFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(0);
                break;
            case R.id.second:
                beginTransaction.replace(R.id.content_layout, new SecondFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(1);
                break;
            case R.id.third:
                beginTransaction.replace(R.id.content_layout, new ThirdFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(2);
                break;
        }
    }

    //点击底部栏颜色变化
    private void setCurPoint(int index) {
        if (index == 0) {
            tvFirst.setTextColor(Color.parseColor("#ffc19a"));
            tvSecond.setTextColor(Color.parseColor("#000000"));
            tvThird.setTextColor(Color.parseColor("#000000"));
            ivFirst.setImageDrawable(getResources().getDrawable(R.drawable.remember1));
            ivSecond.setImageDrawable(getResources().getDrawable(R.drawable.book));
            ivThird.setImageDrawable(getResources().getDrawable(R.drawable.me));

        }
        if (index == 1) {
            tvFirst.setTextColor(Color.parseColor("#000000"));
            tvSecond.setTextColor(Color.parseColor("#ffc19a"));
            tvThird.setTextColor(Color.parseColor("#000000"));
            ivFirst.setImageDrawable(getResources().getDrawable(R.drawable.remember));
            ivSecond.setImageDrawable(getResources().getDrawable(R.drawable.book1));
            ivThird.setImageDrawable(getResources().getDrawable(R.drawable.me));
        }
        if (index == 2) {
            tvFirst.setTextColor(Color.parseColor("#000000"));
            tvSecond.setTextColor(Color.parseColor("#000000"));
            tvThird.setTextColor(Color.parseColor("#ffc19a"));
            ivFirst.setImageDrawable(getResources().getDrawable(R.drawable.remember));
            ivSecond.setImageDrawable(getResources().getDrawable(R.drawable.book));
            ivThird.setImageDrawable(getResources().getDrawable(R.drawable.me1));
        }
    }
}
