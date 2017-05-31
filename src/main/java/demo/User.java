package demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huishen on 16/10/24.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    final private Address address = new Address("shanghai", "shanghai");
    private List<String> friends;
    private Car car;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Car{
        private String brand;
        private String price;
    }

    // public static void main(String args[]){
    //     User user = new User();
    //     user.address.setProvince("changzhou");
    //     user.address.setCity("changzhou");
    // }

    public static enum Age{
        CHILD,
        TEENAGER,
        ADULT,
        OLDMAN;

        //method 1
        // @Override
        // public String toString() {
        //      return "User.Age." + super.toString();
        // }

        //method 2
        @Override
        public String toString(){
            switch (this){
                case CHILD:
                    return "User.Age.CHILD";
                case TEENAGER:
                    return "User.Age.TEENAGER";
                case ADULT:
                    return "User.Age.ADULT";
                case OLDMAN:
                    return "User.Age.OLDMAN";
                default:
                    return "Unkown";
            }
        }

        //覆写valueOf()方法

     }

}

class Address{
    private String province;
    private String city;

    public Address(){}

    public Address(String province, String city){
        this.province = province;
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
