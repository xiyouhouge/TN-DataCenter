package zdh.caseuse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.service.BaseProcessor;
import zdh.util.CaseUtil;

import java.util.Map;

import static zdh.service.BaseProcessor.ResponseMessage;


public class Depend1  {
    public static JSONArray jsonToArray ;

    @DataProvider(name = "base1")
    public Object[][] datasget() {
        String[] cellNames = {"Desc","CaseId", "ApiId", "Params", "ExpectedResponseData", "PreValidateSql", "AfterValidateSql"};

        Object[][] datas = CaseUtil.getCaseDataByApiId("1", cellNames);
        System.out.println("输出datas" + datas);
        return datas;
    }
    @Test(dependsOnGroups = "login",dataProvider = "base1",groups ="selectBrand")
    public void getBrand(String Desc,String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        String[] authorization={LoginCase.authorizationValue};
        System.out.println("输出认证信息"+authorization[0]);
        BaseProcessor.ExcuteCase(caseId,apiId,parameter,expectedResponseData,preValidateSql,afterValidateSql,authorization);
        //动态获取返回值中的id
        String result = ResponseMessage;
        System.out.println("方法中的result"+result);
         jsonToArray=JSONArray.parseArray(result);
    }

}
