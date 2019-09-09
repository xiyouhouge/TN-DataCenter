package zdh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties properties = new Properties();
    static {
        try{
            InputStream inputStream = new FileInputStream(new File("src/main/resources/config.properties"));
            properties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getExcelPath(){
        return properties.getProperty("excel.path");
    }
}
