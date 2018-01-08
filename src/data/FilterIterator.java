package data;

import java.util.Iterator;
import java.util.NoSuchElementException;

import filter.Filter;

/**
 * An iterator which returns elements meeting the condition of the specified
 * filter.
 * @author rohithrokkam
 */
public class FilterIterator<V> implements Iterator<V> {

	/* The filter for this FilterIterator. */
	private final Filter<V> filter;

	/* Iterator over the values in the backing map. */
	private final Iterator<V> iterator;
	
	/* The next element to be returned. */
	private V next;
	
	/**
	 * Constructs a new FilterIterator.
	 * @param f The filter to be used for this iterator.
	 */
	public FilterIterator(final Filter<V> f, Iterator<V> iterator) {
		filter = f;
		this.iterator = iterator;
		if(iterator.hasNext())
			next = iterator.next();
	}

	/**
	 * Returns true if the iterator has more elements.
	 * @return True if the iterator has more elements.
	 */
	@Override
	public boolean hasNext() {
		while(iterator.hasNext() && !(filter.satisfiesCondition(next)))
			next = iterator.next();
		return iterator.hasNext();
	}

	/**
	 * Returns the next element in the iteration.
	 * @throws NoSuchElementException if hasNext would return false.
	 * @return The next element in the iteration.
	 */
	@Override
	public V next() {
		if(!hasNext()) 
			throw new NoSuchElementException();
		return next;
	}
}