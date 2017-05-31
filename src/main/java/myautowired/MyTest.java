package myautowired;

/**
 * Created by huishen on 17/5/9.
 */
public class MyTest {

    public static void main(String[] args) {
        PersonServiceImpl personServiceImpl =
            (PersonServiceImpl) BeanContainer.getInstance("myautowired.PersonServiceImpl");
        personServiceImpl.addPerson("hello");
    }

}
