package zdh.util;

import zdh.bean.InterfaceInfo;

import java.util.ArrayList;
import java.util.List;

public class InterfaceInfoUtil {
    public static List<InterfaceInfo> rests = new ArrayList<>();

    // 自动加载所有的接口信息
    static {
        List<InterfaceInfo> list = ExcelUtil.load(PropertiesUtil.getExcelPath(), "接口信息", InterfaceInfo.class);
        rests.addAll(list);
    }

    // 根据apiId 获取url
    public static String getDomainByApiId(String apiId) {
        for (InterfaceInfo rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                String domain=rest.getDomainName();
                return domain;
            }
        }
        return "";
    }
    public static String getPathByApiId(String apiId) {
        for (InterfaceInfo rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                String path=rest.getPath();
                return path;
            }
        }
        return "";
    }
    //根据apiId 获取type
    public static String getTypeByApiId(String apiId) {
        for (InterfaceInfo rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                return rest.getType();
            }
        }
        return "";
    }
    public static String getHeaderByApiId(String apiId) {
        for (InterfaceInfo rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                return rest.getHeader();
            }
        }
        return "";
    }
}

