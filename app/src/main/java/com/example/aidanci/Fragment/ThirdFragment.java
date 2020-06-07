package com.example.aidanci.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aidanci.Activity.ClockActivity;
import com.example.aidanci.Activity.PingxieActivity;
import com.example.aidanci.Entity.Users;
import com.example.aidanci.Login.ForgetActivity;
import com.example.aidanci.Login.LoginActivity;
import com.example.aidanci.R;
import com.example.aidanci.Activity.Setting;
import com.example.aidanci.Utils.AppNetConfig;
import com.example.aidanci.Utils.CommonUtils;
import com.example.aidanci.Utils.DBHelper;
import com.hb.dialog.dialog.ConfirmDialog;
import com.kongzue.dialog.v2.SelectDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.aidanci.Utils.CommonUtils.ClearInfo;
import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.PostHttpValue;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SelectDialogMessageShow;
import static com.example.aidanci.Utils.CommonUtils.SelectDialogShow;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class ThirdFragment extends Fragment {


    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.switch_1)
    Switch switch1;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.ll_5)
    LinearLayout ll5;
    @BindView(R.id.ll_6)
    LinearLayout ll6;
    @BindView(R.id.ll_7)
    LinearLayout ll7;
    Unbinder unbinder;
    private List<Users> datas; //集合对象
    private TextView nicheng;
    String QiandaoInfo = SharedInfo("Qiandao","daka");//打卡数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        nicheng = (TextView) view.findViewById(R.id.nicheng);

        QuanPing(getActivity());//全屏
        PostHttpValue("phone", SharedInfo("UserPhone", "phone"), AppNetConfig.PostUsersUrl);//向后端请求

        //延迟获取数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    datas = new ArrayList<Users>();
                    getDatas(AppNetConfig.GetUsersUrl); //读取生成的json数据
                }
            }
        }, 1000);
        unbinder = ButterKnife.bind(this, view);

        //每日提醒
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startActivity(new Intent(getActivity(), ClockActivity.class));
                } else {
                    Intent alarmas = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                    startActivity(alarmas);
                }
            }
        };
        switch1.setOnCheckedChangeListener(checkedChangeListener);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3,R.id.ll_5, R.id.ll_6, R.id.ll_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_1:
                IntentToPage(getActivity(), Setting.class);
                break;
            case R.id.ll_2:
                break;
            case R.id.ll_3:
                IntentToPage(getActivity(), PingxieActivity.class);
                break;
            case R.id.ll_5:
                ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
                confirmDialog.setLogoImg(R.drawable.tubiao2).setMsg("确定单词重置吗？");
                confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                    @Override
                    public void ok() {
                        //点击确定弹出消息
                        DialogShow(getActivity(), "已重置");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DBHelper.DeleteMyWords(SharedInfo("UserPhone", "phone"));//单词重置
                            }
                        }).start();
                    }

                    @Override
                    public void cancel() {
                    }
                });
                confirmDialog.show();

                break;
            case R.id.ll_6:
                IntentToPage(getActivity(), ForgetActivity.class);
                break;
            case R.id.ll_7:
                ClearInfo("UserPhone");//清除保存的信息
                SelectDialogShow(getActivity(), "确定要退出登录吗?", LoginActivity.class);
                break;
        }
    }

    //通过接口获取列表的方法
    public void getDatas(String url) {
        final RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("users");
                            Users data = new Users();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                data.setName(item.getString("name"));
                                datas.add(data);
                                nicheng.setText(data.getName());//显示昵称
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        mQueue.add(stringRequest);
    }

}
