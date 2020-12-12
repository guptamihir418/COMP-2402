package comp2402a2;

import java.util.Iterator;
import java.util.Random;

public class Tester {

  static <T> void showContents(Iterable<T> ds) {
    System.out.print("[");
    Iterator<T> it = ds.iterator();
    while (it.hasNext()) {
      System.out.print(it.next());
      if (it.hasNext()) {
        System.out.print(",");
      }
    }
    System.out.println("]");
  }

  static void minStackTest(MinStack<Integer> ms, int n) {
    System.out.println(ms.getClass().getName());

    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      int x = rand.nextInt(3*n/2);
      System.out.println("push(" + x + ")");
      ms.push(x);
      showContents(ms);
      System.out.println("min() = " + ms.min());
    }

    while (ms.size() > 0) {
      showContents(ms);
      System.out.println("min() = " + ms.min());
      System.out.println("pop() = " + ms.pop());
    }
  }

  static void minDequeTest(MinDeque<Integer> ms, int n) {
    System.out.println(ms.getClass().getName());

    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      int x = rand.nextInt(3*n/2);
      if (rand.nextBoolean()) {
        System.out.println("addFirst(" + x + ")");
        ms.addFirst(x);
      } else {
        System.out.println("addLast(" + x + ")");
        ms.addLast(x);
      }
      showContents(ms);
      System.out.println("min() = " + ms.min());
    }

    while (ms.size() > 0) {
      showContents(ms);
      System.out.println("min() = " + ms.min());
      if (rand.nextBoolean()) {
        System.out.println("removeFirst() = " + ms.removeFirst());
      } else {
        System.out.println("removeLast() = " + ms.removeLast());
      }
    }
  }


  public static void main(String[] args) {
    minStackTest(new SlowMinStack<>(), 20);
    minDequeTest(new SlowMinDeque<>(), 20);
    minStackTest(new FastMinStack<>(), 20);
    minDequeTest(new FastMinDeque<>(), 20);
  }

}
