package com.example.aidanci.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aidanci.Activity.BeiActivity;
import com.example.aidanci.Activity.Setting;
import com.example.aidanci.Entity.Info;
import com.example.aidanci.Entity.Users;
import com.example.aidanci.R;
import com.example.aidanci.Utils.AppNetConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.aidanci.Utils.CommonUtils.CurrentTime;
import static com.example.aidanci.Utils.CommonUtils.FangWeixinDialog;
import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.PostHttpValue2;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class BeidanciFragment extends Fragment {

    @BindView(R.id.jinriliang)
    TextView jinriliang;
    @BindView(R.id.shengyuliang)
    TextView shengyuliang;
    @BindView(R.id.siji)
    TextView siji;
    @BindView(R.id.jihua)
    TextView jihua;
    @BindView(R.id.jindu)
    TextView jindu;
    @BindView(R.id.qiandao)
    ImageView qiandao;
    Unbinder unbinder;

    private List<Info> datas; //集合对象
    private List<Info> datainfo; //集合对象
    String CihuiInfo = SharedInfo("Xuanze","cihui");//词汇
    String XuexiInfo = SharedInfo("XuexiLiang","xuexi");//学习量
    String PhoneInfo = SharedInfo("UserPhone","phone");//手机号

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beidanci, container, false);
        QuanPing(getActivity());//全屏
        unbinder = ButterKnife.bind(this, view);
        //Post请求---后端
        PostHttpValue2("type",CihuiInfo,"phone",PhoneInfo,AppNetConfig.PostInfoUrl);
        PostHttpValue2("phone", PhoneInfo, "time", CurrentTime(), AppNetConfig.PostmycountUrl);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datainfo = new ArrayList<Info>();
                getDatas2(AppNetConfig.GetmycountUrl); //读取生成的json数据
                if (getActivity() != null) {
                    datas = new ArrayList<Info>();
                    getInfo(AppNetConfig.GetInfoUrl); //读取生成的json数据
                }
            }
        },1000);


        initView();//初始化界面
        return view;
    }

    //初始化界面
    @SuppressLint("SetTextI18n")
    private void initView() {
        jinriliang.setText(XuexiInfo);//每日学习量
        siji.setText(CihuiInfo+"词汇");//选择的词汇
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.jihua, R.id.btn_kaishi,R.id.qiandao})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.jihua:
                IntentToPage(getActivity(), Setting.class);//跳转到每日词汇设置
                break;
            case R.id.btn_kaishi:
                IntentToPage(getActivity(), BeiActivity.class);//跳转到开始背单词页面
                break;
            case R.id.qiandao:
                FangWeixinDialog(getActivity(),"今日打卡成功，记得明天再来哦！");
                break;
        }
    }

    //通过接口获取列表的方法
    public void getDatas2(String url) {
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
                            JSONArray jsonArray = jsonObject2.getJSONArray("mycount");
                            Info data = new Info();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                data.setMycount(item.getString("mycount"));
                                datainfo.add(data);
                                SaveInfo("Count","shu",item.getString("mycount"));
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
    //通过接口获取列表的方法
    public void getInfo(String url) {
        final RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("info");
                            Info data = new Info();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                data.setTotalwords(item.getString("totalwords"));
                                data.setHavewords(item.getString("havewords"));
                                datas.add(data);
                                jindu.setText("学习进度    "+data.getHavewords()+"/" + data.getTotalwords());//显示昵称

                                Integer a = Integer.valueOf(data.getTotalwords());
                                Integer b = Integer.valueOf(data.getHavewords());
                                Integer c = a-b;
                                shengyuliang.setText(String.valueOf(c));//显示未被单词数量

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
