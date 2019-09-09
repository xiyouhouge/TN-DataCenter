package zdh.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JDBCUtil {
    public static Properties properties = new Properties();
    static {
        InputStream iStream;
        try{
            iStream = new FileInputStream(new File("src/main/resources/jdbc.properties"));
            properties.load(iStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List query(String sql,Object... values){
        List<Object> list = new ArrayList<>();
        Map<String,Object> columnLableAndValues = null;
        try{
            Connection connection = getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           // 设置条件字段的值（实时绑定）
           for(int i =0;i<values.length;i++){
               preparedStatement.setObject(i+1,values[i]);
           }
            ResultSet resultSet = preparedStatement.executeQuery();
           //调试
           ResultSetMetaData metaData = resultSet.getMetaData();
           int columnCount = metaData.getColumnCount();
            String columnLable="";
            String columValue="";
            System.out.println("查询出的结果列数是"+columnCount);
           while (resultSet.next()){
               columnLableAndValues = new HashMap<String,Object>();
               for(int i=1;i<=columnCount;i++){
                   columnLableAndValues.put( metaData.getColumnLabel(i),resultSet.getObject(i).toString());
               }
               list.add(columnLableAndValues);
           }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;

    }

    private static Connection getConnection() throws SQLException {
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
    }
    public static void main(String[] args){
        String sql = "select *from userinfo";
        List<Object> list=JDBCUtil.query(sql);
        for(Object o:list){
        System.out.println(o);
        }

    }
}
