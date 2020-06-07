package com.example.aidanci.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidanci.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetialWords extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_yinbiao)
    TextView tvYinbiao;
    @BindView(R.id.bofang)
    ImageView bofang;
    @BindView(R.id.tv_mean)
    TextView tvMean;
    @BindView(R.id.tv_instance)
    TextView tvInstance;
    @BindView(R.id.top2)
    RelativeLayout top2;
    private String rname;
    private String ryinbiao;
    private String rmean;
    private String rid;
    private String rtype;
    private String rinstance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailshow);
        ButterKnife.bind(this);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

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

        tvType.setText(rtype);
        tvName.setText(rname);
        tvYinbiao.setText("美  " + ryinbiao);
        tvMean.setText(rmean);
        tvInstance.setText(matcherSearchText(rinstance,rname));
    }

    @OnClick(R.id.bofang)
    public void onClick() {
        String voiceurl =  "http://media.shanbay.com/audio/us/";//发音地址
        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse( voiceurl+rname+".mp3"));
        mediaPlayer.start();//开始播放

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();//释放
            }
        });

    }
    /**
     * 文字颜色变化
     * @param text
     * @param keyword
     * @return
     */
    public SpannableString matcherSearchText(String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(this,R.style.style_color_FA9A3A), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }
}
