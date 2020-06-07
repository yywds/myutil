package com.example.aidanci.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aidanci.Adapter.ShoucangAdapter;
import com.example.aidanci.Entity.Words;
import com.example.aidanci.R;
import com.example.aidanci.Utils.AppNetConfig;
import com.example.aidanci.Utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class PingxieActivity extends AppCompatActivity {
    @BindView(R.id.lv_show)
    ListView lvShow;
    private ListView listView;

    private List<Words> datas; //集合对象
    private ShoucangAdapter adapter; //自定义的Adapter对象;

    String PhoneInfo = SharedInfo("UserPhone","phone");//手机号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pingxie);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
         listView = findViewById(R.id.lv_show);
        ButterKnife.bind(this);
        CommonUtils.PostHttpValue("phone",PhoneInfo,AppNetConfig.SetShoucangUrl);
        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas = new ArrayList<Words>();
                getDatas(AppNetConfig.GetShoucangUrl); //读取生成的json数据
                adapter = new ShoucangAdapter(PingxieActivity.this, datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
            }
        }, 1000);

        //数据列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建一个意图
                Intent intent = new Intent(PingxieActivity.this, PxActivity.class);
                //在datas中通过点击的位置position通过get()方法获得具体某个图书
                //的数据然后通过Intent的putExtra()传递到DetailActivity中
                intent.putExtra("id", datas.get(position).getId());
                intent.putExtra("instance", datas.get(position).getInstance());
                intent.putExtra("mean", datas.get(position).getMean());
                intent.putExtra("name", datas.get(position).getName());
                intent.putExtra("type", datas.get(position).getType());
                intent.putExtra("yinbiao", datas.get(position).getYinbiao());
                PingxieActivity.this.startActivity(intent);//启动Activity
            }
        });

    }


    //通过接口获取列表的方法
    public void getDatas(String url) {
        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("shoucang");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Words data = new Words();
                                data.setId(item.getString("id"));
                                data.setName(item.getString("name"));
                                data.setType(item.getString("type"));
                                data.setYinbiao(item.getString("yinbiao"));
                                data.setStatus(item.getString("status"));
                                data.setMean(item.getString("mean"));
                                data.setInstance(item.getString("instance"));
                                datas.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //请求成功后为ListView设置Adapter
                        lvShow.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DialogShow(PingxieActivity.this,"空空如也");
                   }
        }
        );
        mQueue.add(stringRequest);
    }
}
