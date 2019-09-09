package zdh.caseuse;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.service.BaseProcessor;
import zdh.util.CaseUtil;

public class GetPhone {
    @DataProvider(name = "base1")
    public Object[][] datasget() {
        String[] cellNames = {"Desc","CaseId", "ApiId", "Params", "ExpectedResponseData", "PreValidateSql", "AfterValidateSql"};

        Object[][] datas = CaseUtil.getCaseDataByApiId("5", cellNames);
        System.out.println("输出datas" + datas);
        return datas;
    }
    @Test(dependsOnGroups = "login",dataProvider = "base1")
    public void getPhone(String Desc,String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        String[] authorization={LoginCase.authorizationValue};
        System.out.println("输出认证信息"+authorization[0]);
        BaseProcessor.ExcuteCase(caseId,apiId,parameter,expectedResponseData,preValidateSql,afterValidateSql,authorization);

    }
}
