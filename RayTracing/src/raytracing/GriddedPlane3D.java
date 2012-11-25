package raytracing;

import java.awt.Color;

/**
 * This class was created as a test for texturing of surfaces using mock coordinates
 * @author Zachary
 */
public class GriddedPlane3D extends Shape3D
{
    Vector3D zeroPoint, normal, xDirection, yDirection;//orientation vectors
    Color backColor, lineColor;//colors
    double lineWidth, divisionWidth;//values
    
    /**
     * constructs a new griddedPlane
     * @param z - the vector representing the planes "zero" point
     * @param n - the vector normal to the plane
     * @param x - the vector that lies along the planes horizontal axis
     * @param c - the background color of the plane
     * @param lc - the color of the lines
     * @param lw - the width of the lines
     * @param dw - the width of the divisions
     */
    public GriddedPlane3D(Vector3D z, Vector3D n, Vector3D x, Color c, Color lc, double lw, double dw)
    {//assign proper values
        zeroPoint = z;
        normal = n;
        xDirection = x;
        yDirection = Vector3D.getCrossProduct(normal, xDirection);
        backColor = c;
        lineColor = lc;
        lineWidth = lw;
        divisionWidth = dw;
    }
    
    /**
     * This method calculates the collision of a ray of 'light' with this object
     * @param ray the ray to be used in calculation
     * @return the result of the collision
     */
    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {//we find the distance along the ray much like the TriangularPlane3D class
        double scalar = Vector3D.getDotProduct(normal, Vector3D.subtract(zeroPoint, ray.getStartPoint())) / Vector3D.getDotProduct(normal, ray.getDirection());
        if (scalar <= 0)
            return null;
        Vector3D collisionPoint = Vector3D.add(Vector3D.scaleVector(ray.getDirection(), scalar), ray.getStartPoint());
        Vector3D coplanarCollision = Vector3D.subtract(collisionPoint, zeroPoint);
        //we calculate the "coordinates" of the collision with respect to the orientation vectors
        double t = (coplanarCollision.getMagnitude() * Vector3D.getDotProduct(coplanarCollision, xDirection)) / xDirection.getMagnitude();
        double u = (coplanarCollision.getMagnitude() * Vector3D.getDotProduct(coplanarCollision, yDirection)) / yDirection.getMagnitude();
        //color the collision result accordingly
        Color c = backColor;
        if ((Math.abs(t % divisionWidth) < lineWidth) || (Math.abs(u % divisionWidth) < lineWidth))
        {
            c = lineColor;
            return new RayCollisionResult(collisionPoint, ray.getStartPoint(), c);
        }
        return new RayCollisionResult(collisionPoint, ray.getStartPoint(), c);
    }
    
}
