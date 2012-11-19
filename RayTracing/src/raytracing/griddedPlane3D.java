package raytracing;

import java.awt.Color;

/**
 * This class was created as a test for texturing of surfaces using mock coordinates
 * @author Zachary
 */
public class griddedPlane3D extends shape3D
{
    vector3D zeroPoint, normal, xDirection, yDirection;//orientation vectors
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
    public griddedPlane3D(vector3D z, vector3D n, vector3D x, Color c, Color lc, double lw, double dw)
    {//assign proper values
        zeroPoint = z;
        normal = n;
        xDirection = x;
        yDirection = vector3D.getCrossProduct(normal, xDirection);
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
    public rayCollisionResult getRayColorandPos(line3D ray)
    {//we find the distance along the ray much like the TriangularPlane3D class
        double scalar = vector3D.getDotProduct(normal, vector3D.subtract(zeroPoint, ray.getStartPoint())) / vector3D.getDotProduct(normal, ray.getDirection());
        vector3D collisionPoint = vector3D.add(vector3D.scaleVector(ray.getDirection(), scalar), ray.getStartPoint());
        vector3D coplanarCollision = vector3D.subtract(collisionPoint, zeroPoint);
        //we calculate the "coordinates" of the collision with respect to the orientation vectors
        double t = (coplanarCollision.getMagnitude() * vector3D.getDotProduct(coplanarCollision, xDirection)) / xDirection.getMagnitude();
        double u = (coplanarCollision.getMagnitude() * vector3D.getDotProduct(coplanarCollision, yDirection)) / yDirection.getMagnitude();
        //color the collision result accordingly
        Color c = backColor;
        if ((Math.abs(t % divisionWidth) < lineWidth) || (Math.abs(u % divisionWidth) < lineWidth))
            c = lineColor;
        
        return new rayCollisionResult(collisionPoint, ray.getStartPoint(), c);
    }
    
}
