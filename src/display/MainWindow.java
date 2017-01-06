package display;
import geometry.Box;
import geometry.Capsule;
import geometry.ConvexHull;
import geometry.Edge;
import geometry.Vertex;
import geometry.Vector2D;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JPanel;

import simulation.IMainWindowController;
import simulation.ShapeContainer;


public class MainWindow extends JPanel{

    private static final long serialVersionUID = 8748413943350417177L;

    private ShapeContainer shapes;
    
    public MainWindow(ShapeContainer in_shapes) {
    	shapes = in_shapes;
    }
    
    public void resetShapes(ShapeContainer in_shapes){
    	shapes = in_shapes;
    }
    
    public void drawShapes(Graphics2D g){
    	drawRectangles(g);
    	drawSpots(g);
    	drawConvexHull(g);
    	
    	drawCapsules(g);
    }
       
    private void drawRectangles(Graphics2D g){
    	
    	for(Box box : shapes.getBoxes()){    		
    	
    		//Draw lines for all edges of the box
    		List<Edge> edges = box.getEdges();
    		for(Edge e : edges){
    			Vector2D start = e.start;
    			Vector2D end = e.end;
    			g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
    		}
    	}
    }
    
    private void drawSpots(Graphics2D g){
    	for(int i = 0; i < shapes.getVertices().size(); i++){
    		Vertex vertex  = shapes.getVertices().get(i);
    		drawEllipse(g,4, vertex.getCenter());
    	}
    	
    }
   
    private void drawEllipse(Graphics2D g, int size, Vector2D position){
		double x = position.getX() -(size/2);
		double y = position.getY() -(size/2);
		
		g.draw(new Ellipse2D.Double(x, y,
                size,
                size)); 	
    }
    
    private void drawCapsules(Graphics2D g){
    	
    	for(int i= 0; i < shapes.getCapsules().size(); i++){
    		
    		Capsule capsule = shapes.getCapsules().get(i);
    		
    		drawCapsuleLineSegment(g,capsule);
    		drawCapsuleSemiCircles(g,capsule);   		
    	}
    }
  
    private void drawCapsuleLineSegment(Graphics2D g, Capsule c){
    	
    	int r = (int)c.getRadius();    	
		Edge axis = c.getAxis();    				

		Vector2D offset = new Vector2D(0,r);
		offset.rotate(-c.getAngle());
		
		// UPPER LINE:
		Vector2D start = new Vector2D(axis.start);
		Vector2D end = new Vector2D(axis.end);

		// transform upwards
		start.subtract(offset);
		end.subtract(offset);

		g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
				
		// LOWER LINE:
		start = new Vector2D(axis.start);
		end = new Vector2D(axis.end);
		
		// transform downwards
		start.add(offset);
		end.add(offset);

		g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
		
    }
    
    // Draw the two semi-circle ends of a capsule using arcs
    private void drawCapsuleSemiCircles(Graphics2D g, Capsule c){
    	
    	int r = (int)c.getRadius();
		Edge axis = c.getAxis();    		
		
		Vector2D start = new Vector2D(axis.start);
		Vector2D end = new Vector2D(axis.end);

  		Vector2D offset = new Vector2D(-r,-r);
  		
  		// Get orientation + 90 degrees
  		int angle_1 = (int)Math.toDegrees(c.getAngle()) +90;
  		int angle_2 = (int)Math.toDegrees(c.getAngle()) -90;
  		
  		// Draw circle ends
		start.add(offset);    		
		g.drawArc((int)start.getX(), (int)start.getY(), r*2, r*2, angle_1, 180);

		end.add(offset);    		
		g.drawArc((int)end.getX(), (int)end.getY(), r*2, r*2, angle_2, 180);
		
    }
    
    private void drawConvexHull(Graphics2D g){
    	
    	List<ConvexHull> hulls = shapes.getHulls();
    	
    	for(int j = 0; j < hulls.size(); j++){
    		ConvexHull hull = hulls.get(j);
	    	if(!hulls.isEmpty()){
	    		
	    		//for quickhull
	    		g.setColor(Color.red);
	    		// highlight base vertex
	    		drawEllipse(g,24,hull.getVertices().get(0));
	    			    
	    		
	    		g.setColor(Color.red);
	    		// highlight base vertex
	    		drawEllipse(g,16,hull.getVertices().get(1));	    			   
	    		
	    		
	        	// highlight vertices
	        	for(Vector2D vertex : hull.getVertices()){
	        		drawEllipse(g,6,vertex);
	        	}	     
	        		        	
	    		//Draw lines for all edges of the hull
	    		List<Edge> edges = hull.getEdges();
	    		int col_step = 255/edges.size();
	    		
	    		for(int i = 0; i< edges.size(); i++){
	    			
	    			Edge e = edges.get(i);
	    			
	    			// Use gradient from red to blue
	    			g.setColor(new Color(255-col_step*i,0,col_step*i));
		    		
	    			Vector2D start = e.start;
	    			Vector2D end = e.end;
	    			g.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
	    		
	    		}
	    	}
    	}
    }
    
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics); 
        Graphics2D g = (Graphics2D) graphics;

        drawShapes(g);
    }
    
    public Component getComponent() {
        return this;
    }
    
    public void updateView() {
        repaint();
    }
    
    public void setupController(IMainWindowController cont) {
    	
        this.addMouseListener(cont);
        this.addMouseMotionListener(cont);
        this.addMouseWheelListener(cont);
    }
    
}
