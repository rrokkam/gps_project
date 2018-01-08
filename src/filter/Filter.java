package filter;

/**
 * Interface implemented by classes which can determine whether an 
 * OSMElement meets a specified condition. Filters are effectively
 * strategies which return booleans.
 * @author rohithrokkam
 */
public interface Filter<T> {
	
	/**
	 * @param e The element which is to be analyzed for condition satisfaction.
	 * @return Whether the element satisfies the condition imposed by this filter.
	 */
	public boolean satisfiesCondition(T e);
}