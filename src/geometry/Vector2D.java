package geometry;
import java.awt.Point;


public class Vector2D {

	private double x_coordinate;
	private double y_coordinate;
		
	public Vector2D(){
		x_coordinate = 0;
		y_coordinate = 0;
	}
	
	public Vector2D(Point in_point){
		x_coordinate = in_point.x;
		y_coordinate = in_point.y;
	}
	
	public Vector2D(double x, double y){
		x_coordinate = x;
		y_coordinate = y;
	}
	
	public Vector2D(Vector2D other){
		x_coordinate = other.getX();
		y_coordinate = other.getY();
	}
	
	public double getX(){
		return x_coordinate;
	}
	public double getY(){
		return y_coordinate;
	}
	
	public void setX(double in){
		x_coordinate = in;
	}
	public void setY(double in){
		y_coordinate = in;
	}
	
	public void add(Vector2D other){
		setX(getX() + other.getX());
		setY(getY() + other.getY());
	}
	
	public void subtract(Vector2D other){
		setX(getX() - other.getX());
		setY(getY() - other.getY());
	}	
	
	public void multiply(double factor){
		setX(getX()*factor);
		setY(getY()*factor);
	}
	
	public void divide(double factor){
		setX(getX()/factor);
		setY(getY()/factor);
	}
	
	// rotate by radians r
	public void rotate(float r){
		double new_x = getX() * Math.cos(r) - getY() * Math.sin(r);
		double new_y = getX() * Math.sin(r) + getY() * Math.cos(r);
		
		setX(new_x);
		setY(new_y);		
	}
	
	public double crossProduct(Vector2D other){
		double result;
		result = getX() * other.getY();
		result += getY() * other.getX();
		
		return result;
	}
	
	public double dotProduct(Vector2D other){
		double result;
		result = getX() * other.getX();
		result += getY() * other.getY();
		
		return result;
	}	
	
	public double magnitude(){
		return(Math.sqrt(getX()*getX() + getY()*getY()));
	}
	
	public void normalize(){
		double magnitude = magnitude();
		double x = getX()/magnitude;
		double y = getY()/magnitude;
		setX(x);
		setY(y);		
	}
	
	// returns angle relative to x axis
	// 0 is right
	// 90 is down
	public float getAngle(){

		Vector2D x_axis = new Vector2D(1,0);	
		
		Vector2D norm = new Vector2D(this);
		norm.normalize();
		double dot = norm.dotProduct(x_axis);
				 
		// Due to normalization and magnitude 1 of base_axis
		// no dividing by magnitude is necessary
		
		// Get vertical direction from sign of -y projection
		Vector2D y_axis = new Vector2D(0,-1);
		double dot_vertical = dotProduct(y_axis);
		
		float angle = (float)Math.acos(dot);
		if(dot_vertical >= 0){
			return angle;	
		} else {
			return -angle;
		}
	}
	
	public Point toPoint(){
		Point result = new Point();
		result.x = (int) getX();
		result.y = (int) getY();
		return result;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Vector2D))return false;
	    Vector2D otherVector2D = (Vector2D)other;

	    if(getX() == otherVector2D.getX()){
	    	if(getY() == otherVector2D.getY()){
	    		return true;
	    	}
	    }
	    return false;
	}
}
