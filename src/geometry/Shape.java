package geometry;

public abstract class Shape {

	public enum ShapeID{SHAPE, BOX, ORIENTED_BOX, SPOT, CONVEX_HULL, CAPSULE};
	
	protected Vector2D center;	
	
	protected ShapeID id = ShapeID.SHAPE;
	
	public ShapeID getShapeID(){
		return id;
	}
	
	public Vector2D getCenter(){
		return new Vector2D(center);
	}
}
