package JSONObjectDemo;

import lombok.Data;

@Data
public class SouthgateApiResponse<T> {

    T data;
    private String msg;

    private int code = 200;


}
