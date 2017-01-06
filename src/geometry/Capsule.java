package geometry;

import geometry.Shape.ShapeID;

public class Capsule extends Shape {

	protected ShapeID id = ShapeID.CAPSULE;
	
	double radius;	
	Edge axis;	
	
	@Override
	public ShapeID getShapeID(){
		return id;
	}
	
	public Capsule(Edge in_axis, double in_radius){
		
		this.axis = new Edge(in_axis);		
		this.radius = in_radius;		
	}
	
	
	public void setAxis(Edge e){
		this.axis = new Edge(e);
	}
	
	public void setRadius(double r){
		this.radius = r;
	}
	
	public Edge getAxis(){
		return (new Edge(axis));
	}
	
	public double getRadius(){
		return radius;
	}		
	
	public float getAngle(){
		
		Vector2D orientation = new Vector2D(axis.end);
		orientation.subtract(axis.start);
		
		return orientation.getAngle();	
	}

}
