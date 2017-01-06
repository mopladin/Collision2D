package simulation;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geometry.Box;
import geometry.Capsule;
import geometry.ConvexHull;
import geometry.Edge;
import geometry.OrientedBox;
import geometry.Shape;
import geometry.Vertex;
import geometry.Vector2D;
import geometry.Shape.ShapeID;

import collision.CollisionManager;

public class LogicManager {

	private ShapeContainer shapes;
	
	public void moveBox(Box box,Vector2D target){
		Vector2D previous = box.getCenter();
		box.setCenter(target);
		if(checkCollisions(box)) 
			box.setCenter(previous);
	}
	
	// This one is horrible
	private boolean checkCollisions(Box box){
		CollisionManager manager = new CollisionManager();
		for(Box other : shapes.getBoxes()){
			
			if(box == other) continue;
			
			Shape.ShapeID first_id = box.getShapeID();
			Shape.ShapeID second_id = other.getShapeID();
			
			if(first_id == Shape.ShapeID.BOX)
			{				
				if(second_id == Shape.ShapeID.BOX)
				{
					if( manager.checkCollision(box, other) ) // box vs box
					{
						return true;
					}
				}
				else if (second_id == Shape.ShapeID.ORIENTED_BOX)
				{
					OrientedBox second = (OrientedBox) other;
					if( manager.checkCollision(box, second) ) // box vs OBB
					{
						return true;
					}
				}
			}
			if(first_id == Shape.ShapeID.ORIENTED_BOX)
			{
				OrientedBox first = (OrientedBox) box;
				if(second_id == Shape.ShapeID.BOX)
				{
					if( manager.checkCollision(first, other) ) // OBB vs box
					{
						return true;
					}
				}
				else if (second_id == Shape.ShapeID.ORIENTED_BOX)
				{
					OrientedBox second = (OrientedBox) other;
					if( manager.checkCollision(first, second) ) // OBB vs OBB
					{
						return true;
					}
				}
			}
		}
		return false;
	}
			
	public ShapeContainer initializeShapes(){
		shapes = new ShapeContainer();
		
		//createPrimitiveBoxes();
		//createConvexFromVertices();
		
		createCapsules();
		
		return shapes;
	}
	
	void createConvexFromVertices(){
		
		createRandVertexCloud();		
		//createVertexCloud();
		createConvexHull();
		
	}
	
	void createVertexCloud(){		

		shapes.addVertex(new Vertex(new Vector2D(300,140))); 
		shapes.addVertex(new Vertex(new Vector2D(450,150))); 
		
		shapes.addVertex(new Vertex(new Vector2D(400,170))); 
		shapes.addVertex(new Vertex(new Vector2D(400,200))); 
		
		shapes.addVertex(new Vertex(new Vector2D(300,200))); 
		
	}
	
	// Point cloud for convex hull
	void createRandVertexCloud(){
		
		// Create 3 to 25 random points
		Random rand = new Random();		
		int vertex_count = rand.nextInt(17) + 3;
		
		// Get a small and central cloud
		float scale_factor = 0.5f;
		float scale_x = 800 * scale_factor;
		float scale_y = 600 * scale_factor;
		Vector2D offset = new Vector2D(200,100);		
		
		for(int i = 0; i < vertex_count; i++){
			float x = rand.nextFloat() * scale_x;
			float y = rand.nextFloat() * scale_y;
			
			Vector2D position = new Vector2D(x,y);
			position.add(offset);
			
			Vertex vertex = new Vertex(position);
			shapes.addVertex(vertex);		 		
		}		
	}
	
	// Create a single hull based on existing vertexs
	void createConvexHull(){
		List<Vector2D> vertices = new LinkedList<Vector2D>();
		for(Vertex vertex : shapes.getVertices()){
			vertices.add(vertex.getCenter());
		}
	
		shapes.addHull(new ConvexHull(vertices));
	}
	
	// Create a few boxes to drag and drop and collide
	void createPrimitiveBoxes(){
	
		Box cube = new Box(new Point(100,100), new Point(50,50));
	 	shapes.addBox(cube);
	 	
	 	Box rectangle = new Box(new Point(400,150), new Point(100,50));
	 	shapes.addBox(rectangle);
	 	
	 	OrientedBox oriented_box = new OrientedBox(new Point(100,300), new Point(50,50), 0.5f);
	 	shapes.addBox(oriented_box);

	 	OrientedBox oriented_box2 = new OrientedBox(new Point(300,300), new Point(20,50), -0.3f);
	 	shapes.addBox(oriented_box2);
		
	}
	
	void createCapsules(){
		
		// Prepare axis
		Vector2D start = new Vector2D(300,300);
		Vector2D end = new Vector2D(200,500);		
		
		Edge axis = new Edge(start,end);
		
		double radius = 50;
		
		Capsule c = new Capsule(axis,radius);
		
		shapes.addCapsule(c);
	}
	

}
