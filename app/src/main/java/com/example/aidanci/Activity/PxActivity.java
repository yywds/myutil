package com.example.aidanci.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.Fayin;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;

public class PxActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_yinbiao)
    TextView tvYinbiao;
    @BindView(R.id.tv_mean)
    TextView tvMean;
    @BindView(R.id.px)
    EditText px;

    private String rname;
    private String ryinbiao;
    private String rmean;
    private String rid;
    private String rtype;
    private String rinstance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.px);
        QuanPing(PxActivity.this);
        ButterKnife.bind(this);
        initViews();
    }

    //初始化界面
    public void initViews() {

        //获取传递过来的参数
        Intent intent = this.getIntent();
        rid = intent.getStringExtra("id");
        rname = intent.getStringExtra("name");
        ryinbiao = intent.getStringExtra("yinbiao");
        rmean = intent.getStringExtra("mean");
        rtype = intent.getStringExtra("type");
        ryinbiao = intent.getStringExtra("yinbiao");
        rinstance = intent.getStringExtra("instance");

        //显示数据
        tvName.setText("点我查看单词");
        tvYinbiao.setText("美  " + ryinbiao);
        tvMean.setText(rmean);
        Fayin(PxActivity.this,rname);
    }


    @OnClick({R.id.tv_name, R.id.bofang, R.id.btn_tijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                tvName.setText(rname);
                break;
            case R.id.bofang:
                Fayin(PxActivity.this, rname);
                break;
            case R.id.btn_tijiao:
                if (px.getText().toString().equals(rname)) {
                    DialogShow(PxActivity.this, "拼写正确");
                } else {
                    DialogShow(PxActivity.this, "拼写不挣正确");
                }
                break;
        }
    }
}
