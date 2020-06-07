package com.example.aidanci.word;

public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
