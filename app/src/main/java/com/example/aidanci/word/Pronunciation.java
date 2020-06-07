package com.example.aidanci.word;

public class Pronunciation {

    private String ps;
    private String pron;
    Pronunciation(){
        ps = "";
        pron = "";
    }
    Pronunciation(String ps,String pron){
        this.ps = ps;
        this.pron =pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPron() {
        return pron;
    }

    public String getPs() {
        return ps;
    }
}
