package directions;

/**
 * Interface implemented by classes which can find directions
 * between sets of vertices. Initial conditions should be specified
 * elsewhere by those classes (ex. in a constructor or setter).
 * @author rohithrokkam
 */
public interface DirectionStrategy {

	/**
	 * Return a set of directions from the start vertex to the end vertex.
	 * @param start The start vertex for direction finding.
	 * @param end The end vertex for direction finding.
	 * @return A set of directions from the start vertex to the end vertex.
	 */
	public Directions getDirections(Vertex start, Vertex end);
}
