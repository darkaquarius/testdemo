package composition;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by huishen on 17/6/6.
 *
 */

// Broken - Inappropriate use of inheritance!
public class InstrumentHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentHashSet() {

    }

    public InstrumentHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
