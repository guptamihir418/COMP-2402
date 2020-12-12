package comp2402a2;

import java.util.Comparator;

public interface MinDeque<T> extends Iterable<T> {
  public Comparator<? super T> comparator();

  public void addFirst(T x);
  public void addLast(T x);

  public T removeFirst();
  public T removeLast();

  public T min();
  
  public int size();
}
