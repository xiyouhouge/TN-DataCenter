package zdh.bean;

import lombok.Data;

@Data
public class CaseInfo {
    private String caseId;
    private String desc;
    private String apiId;
    private String params;
    private String ExpectedResponseData;
    private String ActualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;

    public String toString(){
        return "caseId="+caseId+",desc="+desc+",apiId="+apiId+",params="+params+
                ",ExpectedResponseData"+ExpectedResponseData+","+
                "ActualResponseData"+ActualResponseData+"," +
                "PreValildateSql"+PreValidateSql+","+
                "PreValidateResult"+PreValidateResult+","+
                "AfterValidateSql"+AfterValidateSql+","+
                "AfterValidateResult"+AfterValidateResult+","
                ;
    }



}
