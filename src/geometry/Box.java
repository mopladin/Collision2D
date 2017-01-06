package geometry;


import geometry.Shape.ShapeID;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


public class Box extends Shape{

	protected Vector2D extents;
	
	protected Vector<Vector2D> vertices;
	
	protected ShapeID id = ShapeID.BOX;
	
	
	public Box(){
		center = new Vector2D(0,0);
		extents = new Vector2D(0,0);
	}
	public Box(Point in_pos, Point in_extents){
		center = new Vector2D(in_pos);
		extents = new Vector2D(in_extents);		
		initializeVertices();
	}
	
	public Box(Vector2D in_pos, Vector2D in_extents){
		center = in_pos;
		extents = in_extents;	
		initializeVertices();
	}

	@Override
	public ShapeID getShapeID(){
		return id;
	}
	
	// Create all four corner points of vertex
	protected void initializeVertices(){
		vertices = new Vector<Vector2D>();	
		
		// get offset from center
		double x = extents.getX();
		double y = extents.getY();
		
		vertices.add(new Vector2D(x,y));	// upper left
		vertices.add(new Vector2D(x,-y));	// lower left
		vertices.add(new Vector2D(-x,-y));	// lower right
		vertices.add(new Vector2D(-x,y));	// upper right	
		
		// translate all vertices to box center
		for (Vector2D v : vertices) v.add(center);			
	}
	


	// Set center to target point and apply offset to vertices
	public void setCenter(Vector2D in_pos){
		Vector2D offset = new Vector2D(in_pos);
		offset.subtract(center);
		
		for (Vector2D v : vertices){
			v.add(offset);
		}
		center = in_pos;		
	}
	
	public void translate(Vector2D offset){
		
	}
	
	public Vector2D getExtents(){
		return new Vector2D(extents);
	}
	
	public void setExtents(Vector2D in_extents){
		extents = in_extents;
	}
	
	// Returns true if the input point is within the bounds of the box
	public boolean includes(Vector2D point){
		Vector2D offset = new Vector2D(center);
		offset.subtract(point);
		
		if(Math.abs(offset.getX()) >  (extents.getX()))
			return false;
		
		if(Math.abs(offset.getY()) >  (extents.getY()))
			return false;
		
		return true;		
	}
		
	public Vector2D getVertex(int index){
		Vector2D vertex = new Vector2D(vertices.get(index));
		return vertex;		
	}
	
	public Vector<Vector2D> getVertices(){
		return vertices;
	}
	
	public List<Edge> getEdges(){
		List<Edge> edges = new LinkedList<Edge>();
		
		for(int i=0; i<3;i++){
			Vector2D start = getVertex(i);
			Vector2D end = getVertex(i+1);
			
			Edge e  = new Edge(start,end);
			edges.add(e);
		}
		
		// Add final right edge
		Edge e  = new Edge(getVertex(3), getVertex(0));
		edges.add(e);
		
		return edges;
	}
	
	// Returns a vector of normals, in sync with edges
	public List<Vector2D> getNormals(){
		List<Vector2D> normals = new LinkedList<Vector2D>();
		List<Edge> edges = getEdges();
		for(Edge e : edges){
			normals.add(e.getNormal());
		}
		return normals;
	}
}
