package zdh.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Reporter;
import zdh.service.Parser;

import java.util.*;

public class ClientUtil {
    /**
     * post 方式的接口,本期先实现json格式的
     * requestline
     */

  public static Map<String,String>coolies = new HashMap<>();
  // 参数为空或者是路径参数
  public static HttpResponse doGet(String domain,String path,Map<String,String>params,String...auth){
  String result ="";
  String url ="";
  HttpResponse httpResponse=null;
  if(params!=null){
      Set<String> keys = params.keySet();
      int mark =1;
      // 替换变量
      for(String name:keys){
          if(path.contains(name)){
              path= Parser.parse1(path,params.get(name));
          }
      }
  }
      url = domain.replaceAll("\n","")+path;
      Reporter.log("接口url："+url);
      HttpGet get =new HttpGet(url);
      HttpClient httpClient = HttpClients.createDefault();

      if(auth.length!=0) {
          get.setHeader("authorization", auth[0]);
      }
      try{
          httpResponse=httpClient.execute(get);
          //int code = httpResponse.getStatusLine().getStatusCode();
          //result = EntityUtils.toString(httpResponse.getEntity());
         // Reporter.log("状态码："+code);

      }catch (Exception e){
          e.printStackTrace();
      }

      return httpResponse;
  }
  public static HttpResponse doPost(String domain,String path,Map<String,String> params,String...auth){
      String url = domain.replaceAll("\n","")+path.replaceAll("\n","");
      Reporter.log("接口url："+url);
      HttpPost post = new HttpPost(url);
      HttpResponse httpResponse =null;
        List<BasicNameValuePair> paramteres = new ArrayList<>();
        Set<String> keys = params.keySet();
        // 将参数保存到list集合,
        for(String name:keys){
            String value = params.get(name);
            paramteres.add(new BasicNameValuePair(name,value));
        }
        //设置请求头信息，设置header,StringEntity 的标配
        // post.setHeader("content-type","application/json");
        //判断是否需要携带header

        String result="";
        try{
            post.setEntity(new UrlEncodedFormEntity(paramteres,"utf-8"));
            HttpClient client = HttpClients.createDefault();
             httpResponse =client.execute(post);
           // int code = httpResponse.getStatusLine().getStatusCode();
            //result = EntityUtils.toString(httpResponse.getEntity());
           // Reporter.log("状态码："+code);
        }catch(Exception e){

        }
           return httpResponse;
    }
    public static String doPostJsonBody(String url,Map<String,String> params,String...auth){
        HttpPost post = new HttpPost(url);
       // List<BasicNameValuePair> paramteres = new ArrayList<>();
        // 将参数转换成JsonObject
        JSONObject parameters = new JSONObject();
        Set<String> keys = params.keySet();
        // 将参数保存到list集合,
        for(String name:keys){
            String value = params.get(name);
            parameters.put(name,value);
        }
        //设置请求头信息，设置header,StringEntity 的标配
         post.setHeader("content-type","application/json");

        String result="";
        try{
            //post.setEntity(new UrlEncodedFormEntity(paramteres,"utf-8"));
            post.setEntity(new StringEntity(parameters.toString(),"utf-8"));
            HttpClient client = HttpClients.createDefault();
            HttpResponse httpResponse =client.execute(post);
            int code = httpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(httpResponse.getEntity());
            Reporter.log("状态码："+code);
        }catch(Exception e){

        }
        return result;
    }
//public static void main(String[] args){
//      String parameter="{id:\"VQ0000000045\"}";
//    Map<String,String> params=(Map<String,String>) JSONObject.parse(parameter);
//    String domain ="http://dc-basics-api-gw-fat.k8s.lunz.cn";
//    String path ="/api/v1/tyre/brand/{id}/spec";
//    HttpResponse httpResponse =ClientUtil.doGet(domain,path,params);
//    String result="";
//    try{
//        result =EntityUtils.toString(httpResponse.getEntity());
//    }catch (Exception e){
//        e.printStackTrace();
//    }
//   System.out.println("输出返回值"+result);
//}
    public static HttpResponse doService(String domain,
            String path,String type,Map<String,String>params,String...auth){
        HttpResponse httpResponse=null;
      if("post".equalsIgnoreCase(type)){
          httpResponse = ClientUtil.doPost(domain,path,params,auth);
      }else if("get".equalsIgnoreCase(type)){
          httpResponse=ClientUtil.doGet(domain,path,params,auth);
      }
      return httpResponse;
    }

}
