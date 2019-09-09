package zdh.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DBQueryResult {
    // 脚本编号
    private String no;
    //结果
    private List<Object> columnLableAndValues;

}
