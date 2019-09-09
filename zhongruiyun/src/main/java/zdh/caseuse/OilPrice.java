package zdh.caseuse;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.service.BaseProcessor;
import zdh.util.CaseUtil;

public class OilPrice {
    @DataProvider(name = "base1")
    public Object[][] datasget() {
        String[] cellNames = {"Desc","CaseId", "ApiId", "Params", "ExpectedResponseData", "PreValidateSql", "AfterValidateSql"};

        Object[][] datas = CaseUtil.getCaseDataByApiId("8", cellNames);
        return datas;
    }
    @Test(dependsOnGroups = "login",dataProvider = "base1")
    public void getOilPrice(String Desc,String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        String[] authorization={LoginCase.authorizationValue};
        BaseProcessor.ExcuteCase(caseId,apiId,parameter,expectedResponseData,preValidateSql,afterValidateSql,authorization);

    }
}
