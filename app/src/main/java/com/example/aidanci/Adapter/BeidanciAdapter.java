package com.example.aidanci.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidanci.Entity.Words;
import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;
import com.example.aidanci.Utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidanci.Utils.CommonUtils.Fayin;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

public class BeidanciAdapter extends BaseAdapter {

    private List<Words> datas = new ArrayList<Words>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;

    public BeidanciAdapter(Context context, List<Words> datas) {
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
//        this.layoutInflater2 = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size(); //返回列表的长度
    }

    @Override
    public Words getItem(int position) {
        return datas.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.beishow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Words data, ViewHolder holder, int position) {//初始化数据


        holder.tvName.setText(data.getName()); //Name
        holder.tv_mean.setText(data.getMean());
        holder.tv_instance.setText(data.getInstance());
        holder.tv_type.setText(data.getType());

    }

    protected class ViewHolder {
        private ImageView bofang;
        private RelativeLayout Show;
        private TextView tvName, soucang,tv_yinbiao, tv_mean, renshi, burenshi, name, tv_type, tv_instance;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tv_mean = (TextView) view.findViewById(R.id.tv_mean);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_instance = (TextView) view.findViewById(R.id.tv_instance);
        }
    }
}