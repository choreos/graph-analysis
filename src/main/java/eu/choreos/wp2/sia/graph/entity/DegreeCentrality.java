package eu.choreos.wp2.sia.graph.entity;

/**
 * In a directed graph we remark the difference between
 * "in centrality", which considers the incoming edges of a vertex,
 * and "out centrality", which considers the outgoing edges of a vertex
 * 
 * @author leofl
 *
 */
public class DegreeCentrality {

	// required precision to equivalence
	private final double EPSILON = 0.0001;
	
	private final double inDegree;
	private final double outDegree;
	// could there be a "total centrality"?
	
	public DegreeCentrality(double inDegree, double outDegree) {
		this.inDegree = inDegree;
		this.outDegree = outDegree;
	}

	public Double getInDegree() {
		return inDegree;
	}

	public Double getOutDegree() {
		return outDegree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(inDegree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(outDegree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DegreeCentrality other = (DegreeCentrality) obj;
		if (Math.abs(inDegree - other.inDegree) > EPSILON)
			return false;
		if (Math.abs(outDegree - other.outDegree) > EPSILON)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DegreeCentrality [inDegreeCentrality=" + inDegree
				+ ", outDegreeCentrality=" + outDegree + "]";
	}
	
}
