package com.example.aidanci.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidanci.Entity.Words;
import com.example.aidanci.R;

import java.util.ArrayList;
import java.util.List;

public class PingxieAdapter extends BaseAdapter {

    private List<Words> datas = new ArrayList<Words>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;
    private MediaPlayer mp;

    public PingxieAdapter(Context context, List<Words> datas) {
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
            convertView = layoutInflater.inflate(R.layout.pingxieshow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Words data, ViewHolder holder, int position) {

        holder.tv_name.setText(data.getName()); //Name
        holder.tv_yinbiao.setText("美  "+data.getYinbiao());
        holder.tv_mean.setText(data.getMean()); //

    }
    protected class ViewHolder {
        private ImageView bofang;
        private TextView tv_name, tv_type,tv_instance,tv_mean,tv_yinbiao;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_yinbiao = (TextView) view.findViewById(R.id.tv_yinbiao);
            tv_mean = (TextView) view.findViewById(R.id.tv_mean);
        }
    }

}