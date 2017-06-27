package threaddemo.ch01_13;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class MyThreadFactory implements ThreadFactory {

    private int counter;
    private String name;
    private List<String> states;

    public MyThreadFactory(String name) {
        counter = 0;
        this.name = name;
        states = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + counter);
        counter++;
        states.add(String.format("Created thread %d with name %s on %s\n",
            t.getId(), t.getName(), new Date()));
        return t;
    }

    public String getStates() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = states.iterator();
        while(it.hasNext()) {
            buffer.append(it.next());
        }
        return buffer.toString();
    }

}
