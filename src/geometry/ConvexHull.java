package geometry;

import geometry.Shape.ShapeID;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class ConvexHull extends Shape {

	protected ShapeID id = ShapeID.CONVEX_HULL;
	
	List<Vector2D> points;
	List<Vector2D> hull_vertices;
	List<Edge> edges;
	
	@Override	
	public ShapeID getShapeID(){
		return id;
	}
	
	public ConvexHull(List<Vector2D> vertices){		
		points = vertices;
		constructHull();
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
	
	protected void constructHull(){
		edges = new LinkedList<Edge>();
			
		//hull_vertices = grahamScan();
		quickHull();
		
		createEdges(hull_vertices);
	}
	
	
	void quickHull(){
		
		hull_vertices = new LinkedList<Vector2D>();
		
		Vector2D min_y = getMinYVertex();
		hull_vertices.add(min_y);
		
		Vector2D min_y2 = new Vector2D(min_y);
		min_y2.setX(min_y.getX() + 0.01); 	// pseudo point to form starting line

		Edge e = new Edge(min_y, min_y2);
		findHullPoints(e,points);
	}
	
	
	void findHullPoints(Edge e, List<Vector2D> p){
		
		if(p.isEmpty())return;
		
		Vector2D f = mostDistantFromLine(e.start, e.end, p);
		
		Edge e1 = new Edge(e.start, f);
		int i = partVertices(e1,p);
		//List<Vector2D> candidates =  points.subList(0,i);
		List<Vector2D> candidates = partialList(p,0,i);
		findHullPoints(e1, candidates);

		hull_vertices.add(f);

		
		Edge e2 = new Edge(f, e.end);
		i = partVertices(e2,p);
		//candidates = points.subList(0, i);
		candidates = partialList(p,0, i);
		findHullPoints(e2,candidates);			
		
	}
	
	List<Vector2D> partialList(List<Vector2D> p, int start, int end){
		
		LinkedList<Vector2D> result = new LinkedList<Vector2D>();
		
		if(p.isEmpty()) return result;
		
		for(int i = start; i< end; i++){
			result.add(p.get(i));
		}
		
		return result;		
	}
	
	// Returns index or newly sorted points list
	// according to position of first point right of edge
	int partVertices(Edge e, List<Vector2D> p){
		int i = 0;
		int j = p.size()-1;
		while(i<=j){
			while(i<=j && isLeft(e,p.get(i))) i++;
			while(i<=j && !isLeft(e,p.get(j))) j--;
			if(i<=j){
				//exchange points
				Vector2D temp = p.get(j);
				p.set(j, p.get(i));
				p.set(i, temp);
			}
		}
		return i;
	}
	
	
	// project point onto normal of edge
	// determine sign
	boolean isLeft(Edge e, Vector2D p){
		Vector2D normal = e.getNormal();
		
		//Vector2D offset = new Vector2D(p);
		//offset.subtract(e.start);
		
		double projection = normal.dotProduct(p)-normal.dotProduct(e.start);
		
		return(projection > 0);
	}
	
	Vector2D mostDistantFromLine(Vector2D a, Vector2D b, List<Vector2D> pointlist){
		
		Vector2D most_distant = new Vector2D(0,0);
		double max_area = Double.NEGATIVE_INFINITY;
		
		if(pointlist.isEmpty()) return most_distant;
		
		for(int i = 0; i < pointlist.size(); i++){
			Vector2D point = pointlist.get(i);
			double area = getArea(a,b,point);
			if(area>max_area){
				max_area = area;
				most_distant = new Vector2D(point);
			}
		}
			
		return most_distant;
	}
	
	// returns the area of a triangle formed by the three input points
	// using Heron's formula
	double getArea(Vector2D a, Vector2D b, Vector2D c){
		
		Vector2D ab = new Vector2D(b);
		ab.subtract(a);
		double mag_ab = ab.magnitude();
		
		Vector2D ac = new Vector2D(c);
		ac.subtract(a);
		double mag_ac = ac.magnitude();
		
		Vector2D bc	= new Vector2D(c);
		bc.subtract(b);
		double mag_bc = bc.magnitude();
		
		double s = 0.5*(mag_ab + mag_ac + mag_bc);
		
		double area = s*(s-mag_ab)*(s-mag_ac)*(s-mag_bc);
		
		return area;
	}
	
	List<Vector2D> exchangeListElements(List<Vector2D> list, int a, int b){
		Vector2D temp = list.get(a);
		list.set(a,list.get(b));
		list.set(b,temp);
		
		return list;
	}
	// Iterate through points 
	List<Vector2D> grahamScan(){
		
		List<Vector2D> p = new LinkedList<Vector2D>();
	
		Vector2D max_y = getMaxYVertex();
		sortPoints(max_y);
		
		p.add(points.get(points.size()-1)); 
		p.add(max_y); // add max_y as p[1]
		
		for(Vector2D v : points) p.add(v);
		int j = 1;
		
		for(int i = 2; i < p.size() ; i++){			
			while(clockwiseTurn(p.get(j-1), p.get(j), p.get(i)) <= 0){
				if(j>1) j--;
				
				else if (i == p.size()-1) break;		
				else i++;
			}			
			
			//Once a right turn is detected, replace previous with current point
			j++;
			Vector2D temp = p.get(j);
			p.set(j, p.get(i));
			p.set(i, temp);
				
		}
		p = p.subList(0, j+1);
		return p;
	}
	
	// Finds the vertex with the smallest y value
	// or smaller x value in case of two equal y coordinates
	Vector2D getMaxYVertex(){
		
		Vector2D max = new Vector2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		
		for(Vector2D point : points){
			if(point.getY() > max.getY()){
				max = new Vector2D(point); 
			}
			else if(point.getY() == max.getY() 
					&& point.getX() < max.getX()){
				max = new Vector2D(point);
			}
		}
		return max;	
	}
	
	// Finds the vertex with the smallest y value
	// or smaller x value in case of two equal y coordinates
	Vector2D getMinYVertex(){
		
		Vector2D min = new Vector2D(Double.MAX_VALUE, Double.MAX_VALUE);
		
		for(Vector2D point : points){
			if(point.getY() < min.getY()){
				min = new Vector2D(point); 
			}
			else if(point.getY() == min.getY() 
					&& point.getX() > min.getX()){
				min = new Vector2D(point);
			}
		}
		return min;	
	}
	
	void createEdges(List<Vector2D> vertices){
		
		// for now just connect according to angle order
		for(int i=1; i< vertices.size(); i++){
			edges.add(new Edge(vertices.get(i-1), vertices.get(i)));
		}
		
		// add last edge
		int size = vertices.size();		
		edges.add(new Edge(vertices.get(size-1), vertices.get(0)));
	}
	
	// Decides whether the edge between the last two points is turned right or left
	// returns: 
	// 		> 0 for counter-clockwise
	// 		= 0 for collinear
	// 		< 0 for clockwise
	double clockwiseTurn(Vector2D origin, Vector2D last, Vector2D current){
		
		double part_a = (last.getX() - origin.getX())*(current.getY() -origin.getY());
		double part_b = (last.getY() - origin.getY())*(current.getX() -origin.getX());
		
		return part_a - part_b;
	}
	
	// Sort the points list according to the angle
	// a vector between them and max forms in relation the the x axis
	void sortPoints(Vector2D max){
		
		// prepare map with angle and and point each
		TreeMap<Float,Vector2D> sorted = new TreeMap<Float,Vector2D>();
		sorted.put(Float.NEGATIVE_INFINITY, max);
		
		Vector2D x_axis = new Vector2D(1,0); // X-axis unit vector
		
		for(Vector2D point : points){
			if(!point.equals(max)){
				Vector2D max_to_current = new Vector2D(point);
				max_to_current.subtract(max);
				
				double dot = max_to_current.dotProduct(x_axis);
				float angle = (float)(dot / max_to_current.magnitude());
				
				sorted.put(angle,point);
			}
		}
		
		// cast back treemap into list
		points = new ArrayList<Vector2D>(sorted.values());
	}
	
	public List<Vector2D> getVertices(){
		return hull_vertices;
	}
}
