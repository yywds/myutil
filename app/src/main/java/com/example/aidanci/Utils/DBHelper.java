package com.example.aidanci.Utils;

import android.annotation.SuppressLint;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper {
    private static Connection con = null;//打开数据库对象
    public DBHelper() {
        this.con = getConnection();
    }

    public static Connection getConnection() {
        try {
            Class.forName(AppNetConfig.driver);
            con = (Connection) DriverManager.getConnection(AppNetConfig.MysqlUrl, AppNetConfig.Mysqluser, AppNetConfig.Mysqlpwd);//获取连接
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    //用户注册操作
    public static int InsertUsers(String name, String password, String phone,String mibao1,String mibao2) {
                Connection conn = getConnection();
                String head = "/profile/upload/2020/05/13/people.jpg";
                String sql = "insert into users (name,password,phone,nickname,image,mibao1,mibao2) values(?,?,?,?,?,?,?)";
               int i = 0;
                PreparedStatement pstmt;
                try {
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, password);
                    pstmt.setString(3, phone);
                    pstmt.setString(4, null);
                    pstmt.setString(5, head);
                    pstmt.setString(6, mibao1);
                    pstmt.setString(7, mibao2);
                    i =  pstmt.executeUpdate();  //执行sql语句
                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return i;
    }

    //查询手机号是否存在
    public boolean SelectPhone(String phone){
        Connection conn = getConnection();
        String sql = "select * from users where phone = ? ";
        try {
            java.sql.PreparedStatement pres = conn.prepareStatement(sql);
            pres.setString(1,phone);
            ResultSet res = pres.executeQuery();
            boolean t = res.next();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //匹配密保问题
    public boolean SelectMiBao(String phone,String mibao1,String mibao2){
        Connection conn = getConnection();
        String sql = "select * from users where phone = ? and mibao1 = ? and mibao2 = ?";
        try {
            java.sql.PreparedStatement pres = conn.prepareStatement(sql);
            pres.setString(1,phone);
            pres.setString(2,mibao1);
            pres.setString(3,mibao2);
            ResultSet res = pres.executeQuery();
            boolean t = res.next();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //登录操作
    public boolean SelectUserLogin(String phone, String password){
            Connection conn = getConnection();
            String sql = "select * from users where phone = ? and password = ?";
            try {
                java.sql.PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1,phone);
                pres.setString(2,password);
                ResultSet res = pres.executeQuery();
                boolean t = res.next();
                return t;
            } catch (SQLException e) {
               e.printStackTrace();
            }
        return false;
    }

    //修改密码/找回密码
    public static int UpdatePassword(String password,String phone) {
        Connection conn = getConnection();
        int i = 0;
        String sql = "update users set password='" + password +"'where phone='" + phone +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //单词重置
    public static int DeleteMyWords(String phone) {
        Connection conn = getConnection();
        int i = 0;
        String sql = "delete from mywords where phone = '"+ phone +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //更新单词
    public static int UpdateMyWords(String name,String phone) {
        Connection conn = getConnection();
        int i = 0;
        String sql = "update mywords set status= '1' where name='" + name +"'and phone = '"+ phone +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //添加单词
    public static int InsertMyWord(String name,String yinbiao,String mean,String status, String type,String instance,String username,String phone) {
        Connection conn = getConnection();
        int i = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        String sql = "insert into mywords (name,yinbiao,mean,status,type,instance,username,phone,time) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, yinbiao);
            pstmt.setString(3, mean);
            pstmt.setString(4, status);
            pstmt.setString(5, type);
            pstmt.setString(6, instance);
            pstmt.setString(7, username);
            pstmt.setString(8, phone);
            pstmt.setString(9, time);
            i = pstmt.executeUpdate();  //执行sql语句
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //收藏
    public static int Shoucang(String name,String yinbiao,String mean,String status, String type,String instance,String username,String phone) {
        Connection conn = getConnection();
        int i = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        String sql = "insert into mysoucang (name,yinbiao,mean,status,type,instance,username,phone,time) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, yinbiao);
            pstmt.setString(3, mean);
            pstmt.setString(4, status);
            pstmt.setString(5, type);
            pstmt.setString(6, instance);
            pstmt.setString(7, username);
            pstmt.setString(8, phone);
            pstmt.setString(9, time);
            i = pstmt.executeUpdate();  //执行sql语句
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

}
