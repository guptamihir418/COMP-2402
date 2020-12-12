package comp2402a2;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

public class FastMinDeque<T> implements MinDeque<T> {

  protected Comparator<? super T> comp;

  protected FastMinStack<T> front;
  protected FastMinStack<T> back;

  public FastMinDeque() {
    this(new DefaultComparator<T>());
  }

  public FastMinDeque(Comparator<? super T> comp) {
    this.comp = comp;

    front = new FastMinStack<T>();
    back = new FastMinStack<T>();

    
  }

  public void addFirst(T x) {

    front.push(x);

    balance();


  }

  public void addLast(T x) {

    back.push(x);
    balance();

  }

  public T removeFirst() {
    
    T x;

    if(!front.getList().isEmpty()){

      x = front.pop();
    }
    else{
      x = back.pop();
    }

    balance();
    return x;

  }

  public T removeLast() {

    T x;

    if(!back.getList().isEmpty()){

      x = back.pop();
    }
    else{
      x = front.pop();
    }

    balance();
    return x;
  }

  public T min() {

    T MinFront = front.min();
    T MinBack = back.min();
    if(MinBack==null){
      return MinFront;
    }
    if(MinFront==null){
      return MinBack;
    }

    if((comp.compare(MinBack, MinFront)) <= 0 ) {
      return MinBack;
    }
    return MinFront;
  }

  public int size() {

    return (front.size()+back.size());

  }

  public Iterator<T> iterator() {

    List<T> list = new LinkedList<T>(front.getList());
    Collections.reverse(list);
    list.addAll(back.getList());
    return list.iterator();
  }

  public Comparator<? super T> comparator() {
    return comp;
  }


  void balance() {
    int TotalSize = size();
    int frontSize = front.size();
    int backSize = back.size();
    // System.out.println("front Size: "+frontSize);
    // System.out.println("back Size: "+ backSize);

    if (3*frontSize < backSize) {

        int s = TotalSize/2 - frontSize;

        FastMinStack<T> nf = new FastMinStack<T>();
        FastMinStack<T> nb = new FastMinStack<T>();

        List<T> tempBack = back.getList().subList(s, backSize);

        for(T x: tempBack){
          nb.push(x);
        }
        
        List<T> tempBack2 = back.getList().subList(0, s);
        Collections.reverse(tempBack2);
        for(T x: tempBack2){
          nf.push(x);
        }


        for(T x: front.getList()){
          nf.push(x);
        }

        front = nf;
        back = nb;
        // System.out.println("balance done here(if)\n\n\n");


    } else if (3*backSize < frontSize) {

        int s = frontSize - (TotalSize/2);
        FastMinStack<T> nf = new FastMinStack<T>();
        FastMinStack<T> nb = new FastMinStack<T>();


        List<T> tempFront = front.getList().subList(s, frontSize);
        for(T x: tempFront){
          nf.push(x);
        }

        List<T> tempFront2 = front.getList().subList(0, s);
        Collections.reverse(tempFront2);
        for(T x: tempFront2){
          nb.push(x);
        }


        for(T x: back.getList()){
          nb.push(x);
        }

        front = nf;
        back = nb;
        // System.out.println("balance done here(else if)\n\n\n");

    }

  }

}
