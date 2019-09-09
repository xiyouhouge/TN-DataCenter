package zdh.caseuse;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import zdh.util.ExcelUtil;

import java.util.Map;

public class ReadExcelCas {
    @Test(dataProvider = "dat")
    public void test1(String parameter){
        Map<String,String>params = (Map<String, String>) JSONObject.parse(parameter);
        System.out.println(params);

    }
    @DataProvider(name="dat")
    public Object[][] datas(){
        String excelPath="E:/zhongruiyun/src/main/resources/apicase.xlsx";
        System.out.println("路径是"+excelPath);
        int[] rows={2,3,4,5};
        int[] cells ={2};
        Object[][] datas= ExcelUtil.datas(excelPath,"接口信息",rows,cells);
        return datas;
    }
}
