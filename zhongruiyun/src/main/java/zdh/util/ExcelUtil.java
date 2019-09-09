package zdh.util;

import org.apache.poi.ss.usermodel.*;
import zdh.bean.InterfaceInfo;
import zdh.bean.WriteBackData;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;

public class ExcelUtil {

    public static Map<String,Integer> caesIdRownumMapping = new HashMap<>();
    public static Map<String,Integer> cellNameCellnumMapping = new HashMap<>();
    public static List<WriteBackData> writeBackDatas = new ArrayList<>();
   static {
       loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"用例");
       // 解析excel，获取行映射和列映射，
    }

    private static void loadRownumAndCellnumMapping(String excelPath, String sheetName) {
        InputStream inputStream =null;
        try{
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbookFactory = WorkbookFactory.create(inputStream);
            Sheet sheet = ((Workbook) workbookFactory).getSheet(sheetName);
            Row titleRow=sheet.getRow(0);
            if(titleRow!=null&&!isEmptyRow(titleRow)){
                int lastCellnum = titleRow.getLastCellNum();
                for(int i=0;i<lastCellnum;i++){
                    Cell cell = titleRow.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title = cell.getStringCellValue();
                    title = title.substring(0,title.indexOf("("));
                    int cellnum = cell.getAddress().getColumn();
                    cellNameCellnumMapping.put(title,cellnum);
                }
            }
            int lastRownum=sheet.getLastRowNum();
            for(int i=1;i<=lastRownum;i++){
                Row dataRow = sheet.getRow(i);
                Cell firstCellOfRow = dataRow.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellOfRow.setCellType(CellType.STRING);
                String caseId=firstCellOfRow.getStringCellValue();
                int rownum = dataRow.getRowNum();
                caesIdRownumMapping.put(caseId,rownum);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }

    public static Object[][] datas(String excelPath,String sheetName,int[]rows,int[]cells){
        Object[][]datas=null;
        try{
            Workbook workbook = create(new File(excelPath));
            Sheet    sheet    = workbook.getSheet(sheetName);
            datas=new Object[rows.length][cells.length];
            //通过循环获取每一行
            for(int i=0;i<rows.length;i++){
                Row row= sheet.getRow(rows[i]-1);

                for(int j=0;j<cells.length;j++){
                    Cell cell=row.getCell(cells[j]-1);
                    //将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String value=cell.getStringCellValue();
                    datas[i][j] = value;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return datas;
    }

    private static boolean isEmptyRow(Row dataRow) {

         if(dataRow==null){
             return true;
         }
        int lastCellNum = dataRow.getLastCellNum();
        for(int i=0;i<lastCellNum;i++){
            Cell cell = dataRow.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            if(value!=null &value.trim().length()>0){
                return false;
            }else
            {
                continue;
            }

        }
        return true;
    }
    // 加载具体的数据反射到对象
    public static<T> List<T> load(String excelPath,String sheetName,Class<T> clazz){
        List<T> list = new ArrayList<>();
        try{
            Workbook workbook = create(new File(excelPath));
            Sheet sheet = workbook.getSheet(sheetName);
            Row titleRow = sheet.getRow(0);
            int lastCellNum =titleRow.getLastCellNum();
            String[] fields = new String[lastCellNum];
            for(int i=0;i<lastCellNum;i++){
                Cell cell =titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String title=cell.getStringCellValue();
                title = title.substring(0,title.indexOf("("));
                fields[i]=title;
            }
            int lastRowIndex = sheet.getLastRowNum();//获取行索引
            // 循环处理每一条数据==case对象
            for(int i=1;i<=lastRowIndex;i++){
                T obj = clazz.newInstance();
                Row dataRow = sheet.getRow(i);
                for(int j=0;j<lastCellNum;j++){
                    if(dataRow==null||isEmptyRow(dataRow)){
                        continue;
                    }
                    Cell cell=dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    // 获取要反射的方法名
                    String methodName = "set"+fields[j];
                    // 获取要反射的方法对象
                    Method method= clazz.getMethod(methodName,String.class);
                    //完成反射调用
                    method.invoke(obj,value);
                }
                list.add(obj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
}
    public static void batchWriteBackDatas(String excelPath){
        InputStream inputStream = null;
        OutputStream outputStream=null;
        try{
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            for(WriteBackData writeBackData:writeBackDatas){
                String sheetName = writeBackData.getSheetName();
                Sheet sheet=workbook.getSheet(sheetName);
                String caseId = writeBackData.getCaseId();
                // 获取行索引
                int rownum=caesIdRownumMapping.get(caseId);
                Row row =sheet.getRow(rownum);
                String cellName = writeBackData.getCellName();
                int cellnum =cellNameCellnumMapping.get(cellName);
              Cell cell = row .getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
              cell.setCellType(CellType.STRING);
              String result = writeBackData.getResult();
              cell.setCellValue(result);
            }
            outputStream =  new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        }catch (Exception e1){
            e1.printStackTrace();
        }finally {
            try{
                if(outputStream!=null)
                {
                    outputStream.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }

        }
    }

    /**
     * 回写数据
     * @param excelPath
     * @param sheetName
     * @param caseId
     * @param cellName
     * @param result
     */

    public  static  void writeBackData(String excelPath,String sheetName, String caseId,String cellName,String result) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int rownum = caesIdRownumMapping.get(caseId);
            Row row = sheet.getRow(rownum);
            int cellnum = cellNameCellnumMapping.get(cellName);
            Cell cell = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }


        }
    }

//    public static void main(String[] args){
//        List list=ExcelUtil.load(PropertiesUtil.getExcelPath(),"接口信息", InterfaceInfo.class);
//        for(Object info:list){
//            System.out.println(info);
//        }
//
//    }
}
