package geometry;


import geometry.Shape.ShapeID;

import java.awt.Point;
import java.util.Vector;


public class OrientedBox extends Box{

	protected float rotation;

	protected ShapeID id = ShapeID.ORIENTED_BOX;
	
	
	public OrientedBox(Point in_pos, Point in_extents, float in_rotation){
		center = new Vector2D(in_pos);
		extents = new Vector2D(in_extents);	
		rotation = in_rotation;
		initializeVertices();
	}
	
	public OrientedBox(Vector2D in_pos, Vector2D in_extents, float in_rotation){
		center = in_pos;
		extents = in_extents;	
		rotation = in_rotation;
		initializeVertices();
	}
	
	@Override
	public ShapeID getShapeID(){
		return id;
	}

	public float getRotation()
	{
		return rotation;
	}
	
	@Override
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
		
		
		// transform all vertices to box center
		for (Vector2D v : vertices){
			v.rotate(rotation);
			v.add(center);			
		}
	}
	
	@Override
	// Returns true if the input point is within the bounds of the box
	public boolean includes(Vector2D point){
		Vector2D offset = new Vector2D(center);
		offset.subtract(point);

		offset.rotate(-rotation);
		
		if(Math.abs(offset.getX()) >  (extents.getX()))
			return false;
		
		if(Math.abs(offset.getY()) >  (extents.getY()))
			return false;
		
		return true;		
	}
}
