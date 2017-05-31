package demo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huishen on 17/5/25.
 *
 */
public class GenericsTest {

    public static void foo1(List<?> list) { // Java generics are implemented upon ERASURE, so the runtime-type of elements in List<?> and List are both Object.
        if (list.isEmpty()) {
            return;
        }
        Object o = list.get(0); // Type safe if you don't do DOWN-CAST
        // list.add(new Dog()); // Won't compile
    }
    public static void foo2(List list) {
        if (list.isEmpty()) {
            return;
        }
        Object o = list.get(0); // Type safe if you don't do DOWN-CAST
        list.add(new Cat()); //! Compiler won't stop you, just gives you a little complaint
    }
    public static void main(String[] args) {
        List<Cat> cats = new LinkedList<Cat>();
        cats.add(new Cat());
        List<Dog> dogs = new LinkedList<Dog>();
        dogs.add(new Dog());

        foo1(cats); // relatively type-safe
        foo2(dogs); // dangerous

        for (Cat cat : cats) { // Cast from Object to Cat
            cat.meow();
        }

        for (Dog dog : dogs) { //! Cast from Object to Dog, ClassCastException
            dog.bark();
        }

        List<?> list = cats;
        Cat cat = new Cat();

        //注意,这里不能加cat,只能加null !!!!!!!!!!!
        // list.add(cat);
        list.add(null);
    }
}

class Dog {
    public void bark() {
    }
}

class Cat {
    public void meow() {
    }
}
