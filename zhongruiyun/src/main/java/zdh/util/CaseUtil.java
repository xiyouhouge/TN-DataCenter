package zdh.util;

import zdh.bean.CaseInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CaseUtil {
    /**
     * 保存所有的用例信息
     */
    public static List<CaseInfo> caseInfos = new ArrayList<>();
    static {
        Class<CaseInfo> clazz = CaseInfo.class;
        List<CaseInfo> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"用例",clazz);
        caseInfos.addAll(list);
    }
    /**
     * 根据接口编号拿到对应接口的用例测试数据
     */
    public static Object[][] getCaseDataByApiId(String apiId,String[]cellName){
        Class<CaseInfo> clazz = CaseInfo.class;
        ArrayList<CaseInfo> csList = new ArrayList<>();
        for(CaseInfo cs:caseInfos){
            if(cs.getApiId()!=null ){
                if(cs.getApiId().equals(apiId)){
                    csList.add(cs);
                }

            }
        }
        // 将用例信息保存到数组中提供给dataProvider
        Object[][]datas=new Object[csList.size()][cellName.length];
        for(int i=0;i<csList.size();i++){
            CaseInfo cs=csList.get(i);
            for(int j=0;j<cellName.length;j++){
                String methodName = "get"+cellName[j];
                try{
                    Method method=clazz.getMethod(methodName);
                    String value = (String)method.invoke(cs);
                    datas[i][j] = value;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }
//    public static void main(String[] args){
////        String[] cellNames = {"Desc"};
////        Object[][] datas =getCaseDataByApiId("1",cellNames);
////        for(Object[] objects:datas){
////            for(Object object:objects){
////                System.out.println("["+object+"]");
////            }
////            System.out.println();
////        }
//    }

}
