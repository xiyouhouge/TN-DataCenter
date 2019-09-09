package zdh.util;

import org.testng.Assert;

public class AssertUtil {
    /**
     * 自定义断言，比较与实际结果跟期望值是否一致
     */
    public static String assertEquals(String actualResponseData,String expectRespongseData){
        String result="通过";
        try{
            Assert.assertEquals(actualResponseData,expectRespongseData);
        }catch (Throwable e){
            result = actualResponseData;
        }
        return result;
    }

}
