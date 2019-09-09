package zdh.util;

import com.alibaba.fastjson.JSONObject;
import zdh.bean.DBCheck;
import zdh.bean.DBQueryResult;

import java.util.ArrayList;
import java.util.List;


public class DBCheckUtil {
    public static String doQuery(String validateSql){
        List<DBCheck> dbChecks = JSONObject.parseArray(validateSql,DBCheck.class);//直接将字符串反射成对象
        List<DBQueryResult> dbQueryResults = new ArrayList<>();
        for(DBCheck dbCheck:dbChecks){
            String no = dbCheck.getNo();
            String sql = dbCheck.getSql();
            DBQueryResult dbQueryResult = new DBQueryResult();
            dbQueryResult.setNo(no);
            //建立jdbc连接
           // Map<String,Object> columnLableAndValues =
            List list=JDBCUtil.query(sql);
            dbQueryResult.setColumnLableAndValues(list);
            dbQueryResults.add(dbQueryResult);
        }
        return JSONObject.toJSONString(dbQueryResults);
    }
//    public static void main(String[] args){
//        String validateSql ="[{\"no\":\"1\",\"sql\":\"select *from userinfo \"}]";// 要是数组的格式。
//        List<DBCheck> dbChecks=JSONObject.parseArray(validateSql,DBCheck.class);
//        String a=  DBCheckUtil.doQuery(validateSql);
//    }
}
