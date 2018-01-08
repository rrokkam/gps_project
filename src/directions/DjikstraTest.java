package directions;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * A defunct unit test for a previous version of the Djikstra algorithm.
 * This unit test relied on a vertex method Collection<Vertex> getAdjacentVertices()
 * which has since been removed in favor of a graph representation.
 * @author rohithrokkam
 */
@SuppressWarnings("unused")
public class DjikstraTest {

	/* The optimal directions, as manually determined. */
	ArrayList<Vertex> optimalDirections = new ArrayList<Vertex>();
	
	/* The direction finding strategy to be used in this test. */
	Djikstra djikstra;
	
	/* Start node for the test.*/
	VertexNode start;
	
	/* End node for the test. */
	VertexNode end;
	
	/**
	 * Set up the Djikstra test.
	 * @throws Exception If anything goes wrong.
	 */
	@Before
	public void setUp() throws Exception {
		VertexNode n1 = new VertexNode();
		VertexNode n2 = new VertexNode();
		VertexNode n3 = new VertexNode();
		VertexNode n4 = new VertexNode();
		
		start = n1;
		end = n4;
		
//		n1.distances.put(n2, 1F);
//		n2.distances.put(n4, 1F);
//		
//		optimalDirections.add(n1);
//		optimalDirections.add(n2);
//		optimalDirections.add(n4);
		
		n1.distances.put(n2, 1F);
		n1.distances.put(n3, 3F);
		n2.distances.put(n4, 10F);
		n3.distances.put(n4, 5F);
		
		n2.distances.put(n1, 1F);
		n3.distances.put(n1, 3F);
		n4.distances.put(n2, 10F);
		n4.distances.put(n3, 5F);
		
		optimalDirections.add(n1);
		optimalDirections.add(n3);
		optimalDirections.add(n4);
		
//		djikstra = new Djikstra();
	}

	/**
	 * Check whether the found directions are the "correct" directions.
	 */
	@Test
	public void testGetDirections() {
		assert(djikstra != null);
		assert(optimalDirections != null);
//		List<Vertex> directions = djikstra.getDirections(start, end);
//		assert(directions != null);
//		assert(optimalDirections.equals(directions));
	}
}