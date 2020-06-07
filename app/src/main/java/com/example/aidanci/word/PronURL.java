package com.example.aidanci.word;

public class PronURL {

    static public String ChangePronURL(String url){
        String NewURL = new String();
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.insert(4,"s");
        NewURL = stringBuffer.toString();
        return NewURL;
    }
}
