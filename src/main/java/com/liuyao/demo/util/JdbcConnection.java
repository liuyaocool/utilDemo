package com.liuyao.demo.util;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcConnection {

    private String url;
    private String userid;
    private String password;
    private Connection connection;

    public JdbcConnection(String driverName,String url, String userid, String password) throws SQLException {
        this.url = url;
        this.userid = userid;
        this.password = password;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.connection = DriverManager.getConnection(url, userid, password);
    }
    public JdbcConnection(String url, String userid, String password) throws SQLException {
        this.url = url;
        this.userid = userid;
        this.password = password;
        this.connection = DriverManager.getConnection(url, userid, password);
    }

    /**
     *
     * @param sql
     * @param claz 为null则返回hashmap
     * @return
     */
    public ResultSet excuteSql(String sql, Class claz){
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = this.connection.prepareStatement(sql);
            rs = statement.executeQuery();
            List result = new ArrayList();
            //获得所有字段名
            Map<String, String> columns = new HashMap<>();
            for (int i = 1; i < rs.getMetaData().getColumnCount()+1; i++) {
                columns.put(rs.getMetaData().getColumnName(i),
                        rs.getMetaData().getColumnClassName(i));
            }
            while (rs.next()){

            }
            System.out.println();
//            rs.
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static void main(String[] args) throws SQLException {
        JdbcConnection connection = new JdbcConnection(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://zhuolutech.net:9010;DatabaseName=CHEMISTRY",
                "zhuolu","asdfg12345");
        ResultSet rs = connection.excuteSql("select * from js_config", Map.class);
        System.out.println();




    }

}
