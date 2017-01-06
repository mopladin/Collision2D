package simulation;
import geometry.Box;
import geometry.Capsule;
import geometry.ConvexHull;
import geometry.Vertex;

import java.util.LinkedList;
import java.util.List;


public class ShapeContainer {

	private List<Box>	boxes;
	private List<Vertex>  vertices;
	private List<ConvexHull> hulls;
	private List<Capsule> capsules;
	
	public ShapeContainer(){
		boxes = new LinkedList<Box>();
		vertices = new LinkedList<Vertex>();
		hulls = new LinkedList<ConvexHull>();
		capsules = new LinkedList<Capsule>();
	}
	
	public List<Box> getBoxes(){
		return boxes;
	}
	
	public void addBox(Box b){
		boxes.add(b);
	}
	
	public List<Vertex> getVertices(){
		return vertices;
	}
	
	public void addVertex(Vertex s){
		vertices.add(s);
	}
	
	public List<ConvexHull> getHulls(){
		return hulls;
	}
	
	public void addHull(ConvexHull h){
		hulls.add(h);
	}
	
	public void addCapsule(Capsule c){
		capsules.add(c);
	}
	
	public List<Capsule> getCapsules(){
		return capsules;
	}
}
