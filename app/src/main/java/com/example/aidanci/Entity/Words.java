package com.example.aidanci.Entity;

import java.io.Serializable;

/**
 * (Words)实体类
 *
 * @author makejava
 * @since 2020-05-13 12:58:40
 */
public class Words implements Serializable {
    private static final long serialVersionUID = -16332285355009456L;
    /**
    * 编号
    */
    public String id;
    /**
    * 单词
    */
    public String name;
    /**
    * 音标
    */
    public String yinbiao;
    /**
    * 翻译
    */
    public String mean;
    /**
    * 状态
    */
    public String status;
    /**
    * 类型
    */
    public String type;
    /**
    * 例句
    */
    public String instance;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYinbiao() {
        return yinbiao;
    }

    public void setYinbiao(String yinbiao) {
        this.yinbiao = yinbiao;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

}