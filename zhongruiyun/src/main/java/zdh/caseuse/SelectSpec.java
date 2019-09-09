package zdh.caseuse;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.service.BaseProcessor;
import zdh.service.Parser;
import zdh.util.CaseUtil;

import static zdh.caseuse.Depend1.jsonToArray;

public class SelectSpec {
    @DataProvider(name = "base2")
    public Object[][] datasget() {
        String[] cellNames = {"Desc","CaseId", "ApiId", "Params", "ExpectedResponseData", "PreValidateSql", "AfterValidateSql"};
        Object[][] datas = CaseUtil.getCaseDataByApiId("2", cellNames);
        return datas;
    }
    @Test(dependsOnGroups = {"login","selectBrand"},dataProvider = "base2")
    public void getSpec(String Desc,String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql){
        String[] authorization={LoginCase.authorizationValue};
            JSONObject job=jsonToArray.getJSONObject(5);
            String idValue=String.valueOf(job.get("id"));
            parameter= Parser.parse0(parameter,idValue);

        BaseProcessor.ExcuteCase(caseId,apiId,parameter,expectedResponseData,preValidateSql,afterValidateSql,authorization);
    }

}
