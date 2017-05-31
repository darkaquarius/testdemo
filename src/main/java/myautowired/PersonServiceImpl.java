package myautowired;

/**
 * Created by huishen on 17/5/9.
 */
public class PersonServiceImpl {

    @MyAutowired
    private PersonDao personDao;

    public void addPerson(String str) {
        this.personDao.addPerson(str);
    }

}
