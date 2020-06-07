package com.example.aidanci.Utils;

public class AppNetConfig {
    //MySQL 驱动
    public static final String driver = "com.mysql.jdbc.Driver";
    //Mysql数据库连接地址
    public static final String MysqlUrl = "jdbc:mysql://139.159.154.117:3306/danci?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    //Mys1数据库用户名
    public static final String Mysqluser = "root";
    //Mysql数据库密码
    public static final String Mysqlpwd = "123456";
    //服务器Tomcat地址
    public static final String TomcatUrl = "http://139.159.154.117:8080/";

    //用户信息post
    public static final String PostUsersUrl = TomcatUrl + "dancisys/users/json.action";
    //用户信息Get
    public static final String GetUsersUrl = TomcatUrl + "danci/users.json";
    //单词总量/已有post
    public static final String PostInfoUrl = TomcatUrl + "dancisys/info/json.action";
    //单词总量/已有Get
    public static final String GetInfoUrl = TomcatUrl + "danci/info.json";
    //已有单词数post
    public static final String PostmycountUrl = TomcatUrl + "dancisys/mycount/json.action";
    //已有单词数get
    public static final String GetmycountUrl = TomcatUrl + "danci/mycount.json";
    //生成背单词
    public static final String SetBeidanciUrl= TomcatUrl + "dancisys/beidanci/json.action";
    //获取背单词
    public static final String GetBeidancisUrl = TomcatUrl + "danci/beidanci.json";
    //收藏Post
    public static final String SetShoucangUrl= TomcatUrl + "dancisys/shoucang/json.action";
    //收藏Get
    public static final String GetShoucangUrl = TomcatUrl + "danci/shoucang.json";

}
