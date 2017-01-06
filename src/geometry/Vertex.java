package geometry;

import geometry.Shape.ShapeID;

public class Vertex extends Shape {

	protected ShapeID id = ShapeID.SPOT;
	protected Vector2D center;	
	
	@Override
	public ShapeID getShapeID(){
		return id;
	}
	
	public Vertex(Vector2D position){
		center = position;
	}
	
	@Override 
	public Vector2D getCenter(){
		return center;
	}
}
