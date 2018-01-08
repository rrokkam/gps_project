package data;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import filter.Filter;

/**
 * An extension of the HashMap class with functionality to
 * obtain iterators over elements given boolean conditions
 * provided by a Filter. The iteratormap is immutable.
 * @author rohithrokkam
 */
public class IteratorMap<K, V> extends AbstractMap<K, V> implements Iterable<V> {
	
	/* The map backing this iterator map. */
	private Map<K, V> map;

	/**
	 * Warn the user if they attempt to construct an empty map, since
	 * IteratorMaps are not mutable. 
	 */
	public IteratorMap() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Construct a new IteratorMap backed by another type of map.
	 */
	public IteratorMap(Map<K, V> elements) {
		map = elements;
	}

	/**
	 * Return a entry set view of the backing map.
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	/**
	 * Return an iterator over the entire set of elements held by this map.
	 */
	@Override
	public Iterator<V> iterator() {
		return map.values().iterator();
	}
	
	/**
	 * Returns an Iterable object which provides iterators over the elements of
	 * this map that pertain to the requested filter.
	 */
	public Iterable<V> createIterator(Filter<V> f) {
		return new Iterable<V>() {
			@Override
			public Iterator<V> iterator() {
				return new FilterIterator<V>(f, map.values().iterator());
			};
		};
	}
}