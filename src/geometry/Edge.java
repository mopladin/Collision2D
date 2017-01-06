package geometry;


public class Edge {

	public Vector2D start;
	public Vector2D end;
	
	public Edge(Vector2D in_start, Vector2D in_end){
		start = in_start;
		end = in_end;
	}
	
	public Edge(Edge other){
		this.start = other.start;
		this.end = other.end;
	}
	
	// Returns Edge as vector
	public Vector2D toVector(){
		Vector2D self = new Vector2D(end);
		self.subtract(start);
		return self;
	}
	// Returns perpendicular, left sided vector
	public Vector2D getNormal(){
		
		Vector2D self = toVector();			
		return new Vector2D(self.getY(), -self.getX());
	}
}
