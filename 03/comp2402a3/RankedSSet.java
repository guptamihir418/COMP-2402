package comp2402a3;

import java.util.Comparator;
import java.util.Iterator;

/**
 * The SSet<T> interface is a simple interface that allows a class to implement
 * all the functionality of the (more complicated) SortedSet<T> interface. Any
 * class that implements SSet<T> can be wrapped in a SortedSSet<T> to obtain an
 * implementation of SortedSet<T>
 *
 * @author morin
 *
 * @param <T>
 * @see SortedSSet<T>
 */
public interface RankedSSet<T> extends SSet<T> {
	/**
	 * Get the element of rank i in the SSet. This is the value x in the SSet
	 * such that the SSet contains exactly i values less than x
	 *
	 * @param x
	 * @return the element of rank i
	 */
	public T get(int i);

	/**
	 * Return the rank of x in the SSet (i.e, the number of values in the SSet
	 * that are less than x).
	 *
	 * @param x
	 * @return the number of elements smaller than x
	 */
	public int rank(T x);
}
