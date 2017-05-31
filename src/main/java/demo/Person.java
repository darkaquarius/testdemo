package demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huishen on 17/2/6.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int no;
    private String name;
    private int age;
    private List<String> address;
    public Person (int no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }

    // public int getNo(){
    //     return no;
    // }

    public String getName() {
               // System.out.println(name);
        return name;
    }
    public int getAge(){
        return age;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
