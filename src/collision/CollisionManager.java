package collision;

import geometry.Box;
import geometry.OrientedBox;
import geometry.Vector2D;
import java.util.Vector;
import collision.SATResult;

public class CollisionManager {
    
    public boolean checkCollision(Box a, Box b){

        Vector<Vector2D> a_vertices = a.getVertices();
        Vector<Vector2D> b_vertices = b.getVertices();

        // aabb axes
        Vector<Vector2D> all_axes = new Vector<Vector2D>();
        all_axes.add(new Vector2D(1,0));
        all_axes.add(new Vector2D(0,1));

        SATResult result = checkSAT(all_axes, a_vertices, b_vertices);
        return result.hasCollided();
    }    
    
    public boolean checkCollision(OrientedBox a, Box b){
    	return checkCollision(b,a);
    }
    
    public boolean checkCollision(Box a, OrientedBox b){

        Vector<Vector2D> a_vertices = a.getVertices();
        Vector<Vector2D> b_vertices = b.getVertices();

        // aabb axes
        Vector<Vector2D> a_axes = new Vector<Vector2D>();
        a_axes.add(new Vector2D(1,0));
        a_axes.add(new Vector2D(0,1));
        // obb axes
        Vector<Vector2D> b_axes = new Vector<Vector2D>();
        for (Vector2D axis : a_axes ) {
            Vector2D b_axis = new Vector2D(axis);
            b_axis.rotate(b.getRotation());
            b_axes.add(b_axis);
        }

        Vector<Vector2D> all_axes = new Vector<Vector2D>();
        all_axes.addAll(a_axes);
        all_axes.addAll(b_axes);

        SATResult result = checkSAT(all_axes, a_vertices, b_vertices);
        return result.hasCollided();
    }
    
    public boolean checkCollision(OrientedBox a, OrientedBox b){
        Vector<Vector2D> a_vertices = a.getVertices();
        Vector<Vector2D> b_vertices = b.getVertices();

        // aabb axes
        Vector<Vector2D> box_axes = new Vector<Vector2D>();
        box_axes.add(new Vector2D(1,0));
        box_axes.add(new Vector2D(0,1));
        // a axes        
        Vector<Vector2D> a_axes = new Vector<Vector2D>();
        for (Vector2D axis : box_axes ) {
            Vector2D a_axis = new Vector2D(axis);
            a_axis.rotate(a.getRotation());
            a_axes.add(a_axis);
        }
        // b axes        
        Vector<Vector2D> b_axes = new Vector<Vector2D>();
        for (Vector2D axis : box_axes ) {
            Vector2D b_axis = new Vector2D(axis);
            b_axis.rotate(b.getRotation());
            b_axes.add(b_axis);
        }

        Vector<Vector2D> all_axes = new Vector<Vector2D>();
        all_axes.addAll(a_axes);
        all_axes.addAll(b_axes);

        SATResult result = checkSAT(all_axes, a_vertices, b_vertices);  
        return result.hasCollided();
    }

    // how do we return more than one value?
    public SATResult checkSAT(Vector<Vector2D> axes, Vector<Vector2D> vertices1, Vector<Vector2D> vertices2)
    {
        double least_overlap = Double.POSITIVE_INFINITY;
        Vector2D axis_of_least_penetration = new Vector2D(0,0);

        for ( Vector2D axis : axes ) {
            Vector2D minmax1 = project(axis, vertices1);
            Vector2D minmax2 = project(axis, vertices2);
                
            double overlap = calculateOverlap(minmax1, minmax2);
            if( overlap < 0 ) return new SATResult(0, axis_of_least_penetration);
            else if( overlap < least_overlap )
            {
                least_overlap = overlap;
                axis_of_least_penetration = new Vector2D(axis);
            }
        }            
        return new SATResult(least_overlap, axis_of_least_penetration);
    }

    private double calculateOverlap(Vector2D range1, Vector2D range2)
    {
        double overlap1 = range1.getY() - range2.getX();
        double overlap2 = range2.getY() - range1.getX();
        if(Math.abs(overlap1) < Math.abs(overlap2) )
        {
            return overlap1;
        }
        else
        {
            return overlap2;
        }        
    }

    private Vector2D project(Vector2D axis, Vector<Vector2D> vertices){        
        // min max
        Vector2D size = new Vector2D(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);            

        for ( Vector2D vertex : vertices ) {

            double dot = axis.dotProduct(vertex);

            if(size.getX() > dot) {
                size.setX(dot);
            }
            if (size.getY() < dot) {
                size.setY(dot);
            }
        }
        return size;
    }
}

