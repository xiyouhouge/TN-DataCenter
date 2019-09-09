package zdh.bean;

import lombok.Data;

@Data
public class DBCheck {

    private String no;
    private String sql;
    @Override
    public String toString(){
        return "no=["+no+"],sql=["+sql+"]";
    }

}
