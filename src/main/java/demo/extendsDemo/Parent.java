package demo.extendsDemo;


import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author huishen
 * @date 18/7/3 下午8:59
 */
@Data
public abstract class Parent {

    private String parentParam = "parentParam";

    public CopyMan func1() {
        CopyMan copyMan = new CopyMan();
        BeanUtils.copyProperties(this, copyMan);
        return copyMan;
    }

}
