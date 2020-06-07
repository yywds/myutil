package com.example.aidanci.word;

public class Sent {

    private String orig;
    private String trans;
    Sent(){
        orig = "";
        trans = "";
    }
    Sent(String orig,String trans){
        this.orig = orig;
        this.trans =trans;
    }
    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getOrig() {
        return orig;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }
}
