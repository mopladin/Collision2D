package collision;

import geometry.Vector2D;

class SATResult {
    private double penetration_depth;
    private Vector2D axis_of_least_penetration;
    
    public SATResult(double depth, Vector2D axis)
    {
        penetration_depth = depth;
        axis_of_least_penetration = axis;
    }

    public double getPenetrationDepth()
    {
        return penetration_depth;
    }

    public Vector2D getAxisOfLeastPenetration()
    {
        return axis_of_least_penetration;
    }

    public boolean hasCollided()
    {
        return penetration_depth > 0;
    }
}