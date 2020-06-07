package com.example.aidanci.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aidanci.Adapter.BeidanciAdapter;
import com.example.aidanci.Entity.Info;
import com.example.aidanci.Entity.Users;
import com.example.aidanci.Entity.Words;
import com.example.aidanci.R;
import com.example.aidanci.Utils.AppNetConfig;
import com.example.aidanci.Utils.DBHelper;
import com.kongzue.dialog.v2.WaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aidanci.Utils.CommonUtils.CurrentTime;
import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.Fayin;
import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.PostHttpValue;
import static com.example.aidanci.Utils.CommonUtils.PostHttpValue2;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;
import static com.example.aidanci.Utils.CommonUtils.YanChi;

public class BeiActivity extends AppCompatActivity {


    @BindView(R.id.ll_show)
    ListView llShow;
    @BindView(R.id.renshi)
    TextView renshi;
    @BindView(R.id.soucang)
    TextView shoucang;
    @BindView(R.id.burenshi)
    TextView burenshi;
    @BindView(R.id.Show)
    RelativeLayout Show;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.yinbiao)
    TextView yinbiao;
    @BindView(R.id.bofang)
    ImageView bofang;

    private List<Words> datas; //集合对象
    private List<Info> datainfo; //集合对象
    private BeidanciAdapter adapter; //自定义的Adapter对象
    String CihuiInfo = SharedInfo("Xuanze","cihui");//词汇
    String NameInfo = SharedInfo("UserName","name");//用户名
    String PhoneInfo = SharedInfo("UserPhone","phone");//手机号
    String CountInfo = SharedInfo("Count","shu");//单词数量
    String XuexiInfo = SharedInfo("XuexiLiang","xuexi");//学习量

    private String ids;
    private String names;
    private String yinbiaos;
    private String means;
    private String statuss;
    private String types;
    private String instances;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bei);
        ButterKnife.bind(this);
        QuanPing(this);//全屏
        bofang.setVisibility(View.GONE);
        PostHttpValue2("phone", PhoneInfo, "time", CurrentTime(), AppNetConfig.PostmycountUrl);

        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datainfo = new ArrayList<Info>();
                getDatas2(AppNetConfig.GetmycountUrl); //读取生成的json数据
            }
        }, 1000);

    }

    //通过接口获取列表的方法
    public void getDatas2(String url) {
        final RequestQueue mQueue = Volley.newRequestQueue(BeiActivity.this);
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
                                if (item.getString("mycount").equals(XuexiInfo))
                                {
                                    name.setText("");
                                    yinbiao.setText("");
                                    bofang.setVisibility(View.GONE);
                                    Show.setVisibility(View.GONE);
                                    renshi.setVisibility(View.GONE);
                                    burenshi.setVisibility(View.GONE);
                                    shoucang.setVisibility(View.GONE);
                                    DialogShow(BeiActivity.this,"今日背单词任务\n\n已完成");
                                    YanChi(BeiActivity.this,MainActivity2.class);
                                }else {
                                    PostHttpValue2("username", NameInfo, "type", CihuiInfo, AppNetConfig.SetBeidanciUrl);

                                    datas = new ArrayList<Words>();
                                    getDatas(AppNetConfig.GetBeidancisUrl); //读取生成的json数据
                                    adapter = new BeidanciAdapter(BeiActivity.this, datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)


                                }
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
    public void getDatas(String url) {
        datas.clear();
        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            datas.clear();
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("beidanci");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Words data = new Words();
                                data.setId(item.getString("id"));
                                data.setName(item.getString("name"));
                                data.setYinbiao(item.getString("yinbiao"));
                                data.setMean(item.getString("mean"));
                                data.setStatus(item.getString("status"));
                                data.setType(item.getString("type"));
                                data.setInstance(item.getString("instance"));

                                datas.add(data);
                                ids = item.getString("id");
                                names = item.getString("name");
                                yinbiaos = item.getString("yinbiao");
                                means = item.getString("mean");
                                statuss = item.getString("status");
                                types = item.getString("type");
                                instances = item.getString("instance");
                                bofang.setVisibility(View.VISIBLE);
                                Fayin(BeiActivity.this, data.getName());//单词发音
                                name.setText(data.getName());
                                yinbiao.setText(data.getYinbiao());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //请求成功后为ListView设置Adapter
                        llShow.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                WaitDialog.show(BeiActivity.this, "数据加载中...");
            }
        }
        );
        mQueue.add(stringRequest);
    }

    @OnClick({R.id.renshi, R.id.soucang, R.id.burenshi,R.id.Show,R.id.bofang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renshi:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PostHttpValue2("phone", PhoneInfo, "time", CurrentTime(), AppNetConfig.PostmycountUrl);
                        PostHttpValue2("username", NameInfo,"type", CihuiInfo,AppNetConfig.SetBeidanciUrl);
                        IntentToPage(BeiActivity.this,IntentActivity.class);
                        finish();

                    }
                },3000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.InsertMyWord(names, yinbiaos, means, "1", types, instances, NameInfo,PhoneInfo);
                        Looper.prepare();
                        Toast.makeText(BeiActivity.this, "认识", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.soucang:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.Shoucang(names,yinbiaos,means,statuss,types,instances,NameInfo,PhoneInfo);
                        Looper.prepare();
                        Toast.makeText(BeiActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.burenshi:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PostHttpValue2("phone", PhoneInfo, "time", CurrentTime(), AppNetConfig.PostmycountUrl);
                        PostHttpValue2("username", NameInfo,"type", CihuiInfo,AppNetConfig.SetBeidanciUrl);
                        IntentToPage(BeiActivity.this,IntentActivity.class);
                        finish();
                    }
                },3000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        DBHelper.InsertMyWord(names, yinbiaos, means, "0", types, instances,NameInfo,PhoneInfo);
                        Looper.prepare();
                        Toast.makeText(BeiActivity.this, "不认识", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.Show:
                Show.setVisibility(View.GONE);
                break;
            case R.id.bofang:
                Fayin(BeiActivity.this, names);//单词发音
                break;
        }
    }

    //返回键操作
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentToPage(BeiActivity.this,MainActivity2.class);
    }
}
