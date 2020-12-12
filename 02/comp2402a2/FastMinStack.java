package comp2402a2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class FastMinStack<T> implements MinStack<T> {

  protected Comparator<? super T> comp;

  protected T small = null;
  protected List<T> l1;
  protected List<T> l2;

  public FastMinStack() {
    this(new DefaultComparator<T>());
  }

  public FastMinStack(Comparator<? super T> comp) {
    this.comp = comp;

    //main list
    l1 = new LinkedList<T>();

    // second list keeps the track of min
    l2 = new LinkedList<T>();
  }

  public void push(T x) {

    // if list is empty add the value in both lists
    l1.add(x);

    if(l2.isEmpty()){

      small = x;
      l2.add(x);
    }

    else if((comp.compare(x, small) <= 0)){
      l2.add(x);
      small = x;
    }
  }

  public T pop() {

    if ((l1.get(l1.size()-1).equals(small))){
      l2.remove(l2.size() - 1);
      if (l2.isEmpty()){
        small = null;
      }
      else{
        small = l2.get(l2.size()-1);
      }
    }
    return l1.remove(l1.size()-1);
  }

  public T min() {

    if(l1.isEmpty()){
      return null;
    }

    return small;
  }

  public int size() {
    
    return l1.size();
  }

  public Iterator<T> iterator() {
    return l1.iterator();
  }

  public Comparator<? super T> comparator() {
    return comp;
  }

  public List<T> getList(){
    return l1;
  }

}
