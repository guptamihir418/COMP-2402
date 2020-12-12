package comp2402a2;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class SlowMinStack<T> implements MinStack<T> {

  protected Comparator<? super T> comp;

  protected List<T> ds;

  public SlowMinStack() {
    this(new DefaultComparator<T>());
  }

  public SlowMinStack(Comparator<? super T> comp) {
    this.comp = comp;
    ds = new LinkedList<T>();
  }

  public Comparator<? super T> comparator() {
    return comp;
  }

  public void push(T x) {
    ds.add(x);
  }

  public T pop() {
    return ds.remove(ds.size()-1);
  }

  public T min() {
    // find the minimum, m, by iterating over everything
    T m = null;
    for (T x : ds) {
      if (m == null || comp.compare(x, m) < 0) {
        m = x;
      }
    }
    return m;
  }

  public int size() {
    return ds.size();
  }

  public Iterator<T> iterator() {
    return ds.iterator();
  }
}
