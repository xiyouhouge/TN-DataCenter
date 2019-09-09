package zdh.bean;

import lombok.Data;

/**
 * 回写数据对象
 */
@Data
public class WriteBackData {
    /**
     * 表单名
     */
    private String sheetName;
    private String caseId;
    private String cellName;
    private String result;

    public WriteBackData(String sheetName, String caseId, String cellName, String result) {
        this.sheetName = sheetName;
        this.caseId = caseId;
        this.cellName = cellName;
        this.result = result;
    }

    public WriteBackData() {
        super();
    }
}
