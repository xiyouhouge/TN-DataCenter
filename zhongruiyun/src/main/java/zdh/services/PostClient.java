package zdh.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class PostClient {
    public static void main(String[] agrs){
        //接口的url，参数
        String url="http://localhost:8899/v1/login";
        // 参数是以key-value json格式，封装在请求体中
        String age="1";
        String id="111";
        String isDelete="0";
        String password="123456";
        String permission="1";
        String sex="12";
        String userName="Myexception";
        Map<String,String> param = new HashMap<>();
        param.put("age",age);
        param.put("id",id);
        param.put("isDelete",isDelete);
        param.put("password",password);
        param.put("permission",permission);
        param.put("sex",sex);
        param.put("userName",userName);
        dopost(url,param);




    }

    private static String  dopost(String url, Map<String, String> param) {
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> paramter = new ArrayList<>();
        Set<String> keys=param.keySet();// 获取key集合
        for(String name:keys){
            String value = param.get(name);
            paramter.add(new BasicNameValuePair(name,value));
        }
        String result="";
        // 执行接口
        try{
            //请求体
            post.setEntity(new UrlEncodedFormEntity(paramter,"utf-8"));
            System.out.println("插入的参数："+paramter);
            HttpClient client = HttpClients.createDefault();
            HttpResponse httpResponse = client.execute(post);
            int code=httpResponse.getStatusLine().getStatusCode();
            System.out.println(code);
            result = EntityUtils.toString(httpResponse.getEntity());


        }catch (Exception e){
            e.printStackTrace();
        }

        return result;

    }
}
