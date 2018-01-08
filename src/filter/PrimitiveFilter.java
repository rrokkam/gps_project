package filter;

import openstreetmap.Node;

/**
 * A filter which returns true if the passed in object is a 
 * primitive, i.e. it is a point object with well defined
 * coordinates, and the coordinates() method will not throw
 * an exception generally. No guarantees are made in the case of
 * a false return (the object may or may not be primitive).
 * @author rohithrokkam
 */
public class PrimitiveFilter implements Filter<Object> {

	/**
	 * Returns true if the passed in object is a primitive.
	 * @return True if the passed in object is a primitive.
	 */
	@Override
	public boolean satisfiesCondition(Object e) {
		if(e instanceof Node)
			return true;
		return false;
	}
	
}
