package zdh.caseuse;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.service.BaseProcessor;
import zdh.service.Parser;
import zdh.util.CaseUtil;

import java.util.Map;

import static zdh.service.BaseProcessor.ResponseMessage;


public class LoginCase  {
    public static String authorizationValue ;
    @DataProvider(name = "base")
    public Object[][] datas() {
        String[] cellNames = {"Desc","CaseId", "ApiId", "Params", "ExpectedResponseData", "PreValidateSql", "AfterValidateSql"};

        Object[][] datas = CaseUtil.getCaseDataByApiId("13", cellNames);
        System.out.println("输出datas" + datas);
        return datas;
    }

    @Test (groups = "login",dataProvider = "base",description = "鉴权登录接口" )
    public void login(String Desc,String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        BaseProcessor.ExcuteCase(caseId,apiId,parameter,expectedResponseData,preValidateSql,afterValidateSql);
        String result = ResponseMessage;
        // 将字符串转化成json，然后拼接字符串
        Map<String, String> params = (Map<String, String>) JSONObject.parse(result);
        authorizationValue = params.get("token_type") +" "+ params.get("access_token"); //=123423434
    }



    // 获取authorization
//    public static String getAuthorizationValue() {
//        String result = ResponseMessage;
//        String authorizationValue = null;
//        System.out.println("输出登录接口的返回信息：" + result);
//        // 将字符串转化成json，然后拼接字符串
//        Map<String, String> params = (Map<String, String>) JSONObject.parse(result);
//        authorizationValue = params.get("Bearer") + params.get("access_token"); //=123423434
//
//        return authorizationValue;
//    }




}


