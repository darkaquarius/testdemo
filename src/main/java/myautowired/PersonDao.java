package myautowired;

/**
 * Created by huishen on 17/5/9.
 */
public class PersonDao {

    public void addPerson(String str) {
        System.out.println("autowired succeed," + str);
    }
}
