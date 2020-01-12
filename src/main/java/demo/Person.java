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
public class Person implements Cloneable {
    private int no;
    private String name;
    private int age;
    private List<String> address;

    private Job job;

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        person.setJob((Job) person.getJob().clone());
        return person;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Job implements Cloneable {
        private String jobName;
        private int year;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

}
