package zdh.bean;

import lombok.Data;

@Data
public class InterfaceInfo {
    private String apiId;
    private String apiName;
    private String type;
    private String domainName;
    private String path;
    private String header;
    @Override
    public String toString(){

        return "apiId="+apiId+",apiName="+apiName+",type="+type+",url="+domainName+path+",header="+header;
    }
}
