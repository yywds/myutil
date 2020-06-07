package com.example.aidanci.word;

public class Mean {

    private String pos;
    private String acceptation;
    Mean(){
        pos = "";
        acceptation = "";
    }
    Mean(String pos,String acceptation){
        this.pos = pos;
        this.acceptation = acceptation;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
    }

    public String getPos() {
        return pos;
    }

    public String getAcceptation() {
        return acceptation;
    }
}