package com.example.aidanci.word;

import java.util.ArrayList;
import java.util.List;

public class Netword {
    //    单词
    private String key;
    //    释义
    private List<Mean> means;
    //    发音
    private List<Pronunciation> pronunciations;
    //    例句
    private List<Sent> sents;

    public Netword(){
        key = "";
        pronunciations = new ArrayList<>();
        means = new ArrayList<>();
        sents = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public String getSents() {
        String str = new String();
        for (Sent sent:sents){
            str += sent.getOrig()+"\n"+sent.getTrans()+"\n"+"\n";
        }
        return str;
    }

    public String  getMeans() {
        String str = new String();
        for (Mean mean:means){
            str += mean.getPos()+mean.getAcceptation()+"\n";
        }
        return str;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public Pronunciation getPronunciations(int flag){
        return getPronunciations().get(flag);
    }

    public void setPronunciations(Pronunciation pronunciation) {
        pronunciations.add(pronunciation);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMeans(Mean mean) {
        means.add(mean);
    }

    public void setSents(Sent sent) {
        sents.add(sent);
    }
}

