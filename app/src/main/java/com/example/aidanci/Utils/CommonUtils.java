package com.example.aidanci.Utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import com.example.aidanci.R;
import com.hb.dialog.dialog.ConfirmDialog;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.Notification;
import com.kongzue.dialog.v2.TipDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_MATERIAL;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;


/**
 * 大神之路，代码封装，大大减少你的开发时间
 * 通用工具类封装
 */
public class CommonUtils {


    /**
     * 向后台带参数的Post请求
     * @关键字 key
     * @传递的参数 text
     * @请求地址 url
     */
    public static void PostHttpValue(String key,String text,String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行Http向后端 post请求
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put(key, text);
                client.post(url,params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onFailure(Throwable error, String content) {
                        super.onFailure(error, content);
                    }

                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }
                });
            }
        }).start();
    }
    /**
     * 向后台带参数的Post请求
     * @关键字 key
     * @传递的参数 text
     * @请求地址 url
     */
    public static void PostHttpValue2(String key,String text,String key2,String text2,String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行Http向后端 post请求
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put(key, text);
                params.put(key2, text2);
                client.post(url,params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onFailure(Throwable error, String content) {
                        super.onFailure(error, content);
                    }

                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }
                });
            }
        }).start();
    }


    /**
     * 向后台无参数的Post请求
     * @请求地址 url
     */
    public static void PostHttpNoValue(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行Http向后端 post请求
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                client.post(url,params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onFailure(Throwable error, String content) {
                        super.onFailure(error, content);
                    }

                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }
                });
            }
        }).start();
    }

    /**
     * 存储信息
     * @存储名 Mode
     * @关键字 key
     * @需要存储的值 text
     */
    public static void SaveInfo(String Mode,String key,String text){
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(Mode, Context.MODE_PRIVATE);
        preferences.edit().putString(key, text).apply();
    }

    /**
     * 获取存储信息
     * @存储名 Mode
     * @关键字 key
     * @return
     */
    public static String SharedInfo(String Mode, String key){
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(Mode, Context.MODE_PRIVATE);
        String text = preferences.getString(key,null);
        return text;
    }

    /**
     * 清除保存的存储信息
     * @存储名 Mode
     */
    public static void ClearInfo(String Mode){
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(Mode, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    /**
     * @上下文 context
     * @需要弹出的语句 text
     */
    public static void DialogShow(Context context,String text){
        DialogSettings.style = STYLE_MATERIAL;  //对话框为IOS风格
        DialogSettings.tip_theme = THEME_LIGHT;  //设置提示框主题为亮色主题
        TipDialog.show(context, text, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
    }

    /**
     *
     * @上下文 context
     * @消息 message
     * @要跳转到的class页面 cls
     */
    public static void SelectDialogShow(Context context,String message,Class<?> cls){
        ConfirmDialog confirmDialog = new ConfirmDialog(context);
        confirmDialog.setLogoImg(R.drawable.tubiao2).setMsg(message);
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
                //点击确定跳转到指定的页面
                IntentToPage(context,cls);//挑转到页面
            }

            @Override
            public void cancel() {
            }
        });
        confirmDialog.show();
    }

    /**
     * @上下文 context
     * @消息 message
     * @点击确定消息 messageshow
     */
    public static void SelectDialogMessageShow(Context context,String message,String messageshow){
        ConfirmDialog confirmDialog = new ConfirmDialog(context);
        confirmDialog.setLogoImg(R.drawable.tubiao2).setMsg(message);
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
                //点击确定弹出消息
                DialogShow(context,messageshow);
            }

            @Override
            public void cancel() {
            }
        });
        confirmDialog.show();
    }

    /**
     *
     * @上下文 context
     * @要跳转到的class页面 cls
     */
    public static void IntentToPage(Context context,Class<?> cls){
        context.startActivity(new Intent(context, cls));//挑转到指定class页面
    }

    /**
     * 全屏
     * @param activity
     */
    public static void QuanPing(Activity activity){
        //沉浸式
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    /**
     * 3秒后跳转
     * @上下文 context
     * @要跳转到的class cls
     */
    public static void YanChi(Context context ,Class<?> cls){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(new Intent(context,cls));
            }
        },3000);
    }

    /**
     *
     * @上下文 context
     * @单词 text
     */
    public static void Fayin(Context context,String text){
        String voiceurl =  "http://media.shanbay.com/audio/us/";//发音地址
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse( voiceurl+text+".mp3"));
        mediaPlayer.start();//开始播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();//释放
            }
        });
    }

    /**
     * 仿微信消息通知
     * @param context
     */
    public static void FangWeixinDialog(Context context,String text){
        Notification.show(context, 1, R.drawable.tubiao, context.getString(R.string.app_name), text, Notification.SHOW_TIME_LONG,TYPE_NORMAL );
    }

    /**
     * 关键文字颜色变化
     * @全文 text
     * @关键字 keyword
     * @return
     */
    public SpannableString MatcherSearchText(Context context ,String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(context,R.style.style_color_FA9A3A), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String CurrentTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

}
