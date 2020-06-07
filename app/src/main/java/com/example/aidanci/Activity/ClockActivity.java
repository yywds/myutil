package com.example.aidanci.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.aidanci.Utils.CommonUtils.QuanPing;

public class ClockActivity extends AppCompatActivity {

    private TimePicker tp_time; // 声明一个时间选择器对象
    private Button BT_OK;
    private Button BT_delect;
    private ImageView IM_fanhui;

    private int hourOfDay = 0 ;//保存设置的时
    private int minute = 0;//保存设置的分



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        QuanPing(this);
        // 从布局文件中获取名叫tp_time的时间选择器
        tp_time = findViewById(R.id.tp_time);
        BT_OK=findViewById(R.id.btn_ok);
        BT_delect=findViewById(R.id.delect);
        IM_fanhui=findViewById(R.id.mrtx_fanhui);

        //改变线的颜色
        tp_time.setIs24HourView(true);
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");

        NumberPicker hourNumberPicker = (NumberPicker) tp_time.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) tp_time.findViewById(minuteNumberPickerId);

        setNumberPickerDivider(hourNumberPicker);
        setNumberPickerDivider(minuteNumberPicker);

        //设置闹钟
        final String message="背单词啦";
        BT_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAlarm(message,tp_time.getCurrentHour(),tp_time.getCurrentMinute());
                Toast.makeText(ClockActivity.this,"设置成功！",Toast.LENGTH_SHORT).show();

                finish();


            }
        });

        //取消
        BT_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //返回上一界面
        IM_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //改变线的颜色
    private void setNumberPickerDivider(NumberPicker numberPicker) {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            try{
                Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(
                        ContextCompat.getColor(this,R.color.colorOrig));
                dividerField.set(numberPicker,colorDrawable);
                numberPicker.invalidate();
            }
            catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException e){
                Log.w("setNumberPickerTxtClr", e);
            }
        }
    }

    //设置闹钟
    private void createAlarm(String message,int hour,int minutes){
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//周一
        testDays.add(Calendar.TUESDAY);//周二
        testDays.add(Calendar.WEDNESDAY);//周三
        testDays.add(Calendar.THURSDAY);//周四
        testDays.add(Calendar.FRIDAY);//周五
        testDays.add(Calendar.SATURDAY);//周六
        testDays.add(Calendar.SUNDAY);//周日
        String packageName = getApplication().getPackageName();
        Uri ringtoneUri = Uri.parse("android.resource://" + packageName );
        //action为AlarmClock.ACTION_SET_ALARM
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                //闹钟的小时
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                //闹钟的分钟
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                //响铃时提示的信息
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //用于指定该闹铃触发时是否振动
                .putExtra(AlarmClock.EXTRA_VIBRATE, true)
                //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
                //如需使用默认铃声，则无需指定此 extra。
                //.putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri)
                //一个 ArrayList，其中包括应重复触发该闹铃的每个周日。
                // 每一天都必须使用 Calendar 类中的某个整型值（如 MONDAY）进行声明。
                //对于一次性闹铃，无需指定此 extra
                .putExtra(AlarmClock.EXTRA_DAYS, testDays)
                //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
