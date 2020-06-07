package com.example.aidanci.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidanci.Activity.Inquiry;
import com.example.aidanci.Activity.ShoucangActivity;
import com.example.aidanci.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.aidanci.Utils.CommonUtils.IntentToPage;
import static com.example.aidanci.Utils.CommonUtils.QuanPing;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        QuanPing(getActivity());//全屏
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.go_to_search, R.id.shoucang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_to_search:
                IntentToPage(getActivity(), Inquiry.class);
                break;
            case R.id.shoucang:
                IntentToPage(getActivity(), ShoucangActivity.class);
                break;
        }
    }
}
