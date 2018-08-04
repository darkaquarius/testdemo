package demo.extendsDemo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author huishen
 * @date 18/7/3 下午9:18
 */
@Data
public class CopyMan {

    private String parentParam;
    private String childParam;

    // public static void main(String[] args) {
    //     Child instance = new Child();
    //     CopyMan copyMan = instance.func1();
    // }

    public static void main(String[] args) {
        Parent child = new Child();
        child.setParentParam("parentParam1");
        // child.setChildParam("childParam1");

        CopyMan copyMan = new CopyMan();
        BeanUtils.copyProperties(child, copyMan);
        System.out.println(copyMan);
    }


}
