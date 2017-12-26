package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huishen on 17/12/11.
 * lambda表达式实现观察者模式
 */

public class LambdaTest03 {
    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerPbserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });
    }
}

interface Observer {
    void notify(String tweet);
}

interface Subject {
    void registerPbserver(Observer o);
    void notifyObservers(String tweet);
}

class Feed implements Subject {
    private final List<Observer> observers =
        new ArrayList<>();

    @Override
    public void registerPbserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }
}
