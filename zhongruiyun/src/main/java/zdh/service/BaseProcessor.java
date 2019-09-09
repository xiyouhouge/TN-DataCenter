package zdh.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.bean.InterfaceInfo;
import zdh.bean.WriteBackData;
import zdh.util.*;

import java.util.Map;

/**
 *
 */
public class BaseProcessor {
    public static String ResponseMessage = null;
    public  static  void ExcuteCase(String caseId,String apiId ,String parameter,String expectedResponseData,String preValidateSql,String afterValidateSql,String...auth){
        String actualResult="";
        int code;
        //判断开始前接口的数据校验中有没有值
        if(preValidateSql!=null&&preValidateSql.trim().length()>0){
            String preValidateResult = DBCheckUtil.doQuery(preValidateSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData("用例",caseId,"PreValidateResult",preValidateResult));
        }
        // 需要通过接口编号获取url
        String domain = InterfaceInfoUtil.getDomainByApiId(apiId);
        String path = InterfaceInfoUtil.getPathByApiId(apiId);
        // 获取接口类型
        String type = InterfaceInfoUtil.getTypeByApiId(apiId);
        //是否是提供信息头的接口，0代表不是，1代表是
        String proHeader = InterfaceInfoUtil.getHeaderByApiId(apiId);
        // 需要的参数
        Map<String,String> params = (Map<String,String>) JSONObject.parse(parameter);
        // 调用doservice 方法完成接口调用，拿到响应报文
        HttpResponse httpResponse =ClientUtil.doService(domain,path,type,params,auth);
        try{
             actualResult = EntityUtils.toString(httpResponse.getEntity());
             code=httpResponse.getStatusLine().getStatusCode();
             Reporter.log("状态码："+code);
            if(code!=200){
              Assert.assertTrue(false);
            }else {
                Assert.assertTrue(true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

//        String actualResult = ClientUtil.doService(domain,path,type,params,auth);
        ResponseMessage =actualResult;
//        // 断言
         actualResult = AssertUtil.assertEquals(actualResult,expectedResponseData);

         Reporter.log(actualResult);

         WriteBackData writeBackData = new WriteBackData("用例",caseId,"ActualResponseData",actualResult);
          if(afterValidateSql!=null && afterValidateSql.trim().length()>0){
              String afterValidateResult = DBCheckUtil.doQuery(afterValidateSql);
              ExcelUtil.writeBackDatas.add(new WriteBackData("用例",caseId,"AfterValidateResult",afterValidateResult));

    }
    // 保存回写数据对象
      //  ExcelUtil.writeBackData(PropertiesUtil.getExcelPath(),"用例",caseId,"ActualResponseData",actualResult);
         ExcelUtil.writeBackDatas.add(writeBackData);

    }

    @AfterSuite
    public void batchWriteBackDatas(){
        //套件执行完统一回写数据
        System.out.println("执行afterSuit");
        ExcelUtil.batchWriteBackDatas(PropertiesUtil.getExcelPath());
    }
//    public static void main(String[] agrs){
//        String[] cellNames ={"CaseId","ApiId","Params","ExpectedResponseData","PreValidateSql","AfterValidateSql"};
//
//        Object[][]datas = CaseUtil.getCaseDataByApiId("13",cellNames);
//        System.out.println("输出datas"+datas);
//    }
}
