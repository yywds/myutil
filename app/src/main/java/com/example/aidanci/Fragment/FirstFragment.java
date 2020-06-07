package com.example.aidanci.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidanci.Activity.Setting;
import com.example.aidanci.R;
import com.example.aidanci.Utils.CommonUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.aidanci.Utils.CommonUtils.DialogShow;
import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;
import static com.example.aidanci.Utils.CommonUtils.SaveInfo;
import static com.example.aidanci.Utils.CommonUtils.SelectDialogMessageShow;
import static com.example.aidanci.Utils.CommonUtils.SharedInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    Unbinder unbinder;
    private String cihui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        QuanPing(getActivity());//全屏
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_gaozhong, R.id.rl_siji, R.id.rl_liuji, R.id.rl_yasi, R.id.rl_cihuiliang})
    public void onClick(View view) {
        String success = "选择成功"+"\n\n" + "下一步请设置学习量";
        switch (view.getId()) {
            case R.id.rl_gaozhong:
                SelectDialogMessageShow(getActivity(),"确定选择高中词汇吗？",success);
                cihui = "高中";
                break;
            case R.id.rl_siji:
                SelectDialogMessageShow(getActivity(),"确定选择四级词汇吗？",success);
                cihui = "四级";
                break;
            case R.id.rl_liuji:
                SelectDialogMessageShow(getActivity(),"确定选择六级词汇吗？",success);
                cihui = "六级";
                break;
            case R.id.rl_yasi:
                SelectDialogMessageShow(getActivity(),"确定选择雅思词汇吗？",success);
                cihui = "雅思";
                break;
            case R.id.rl_cihuiliang:
                if (SharedInfo("Xuanze","cihui") != null) {
                    IntentToPage(getActivity(),Setting.class);
                }else {
                    DialogShow(getActivity(),"请先选择词汇");
                }
                break;
        }
        SaveInfo("Xuanze","cihui",cihui);//存储选择的词汇
      }
}
