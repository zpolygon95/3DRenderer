package raytracing;

import java.awt.Color;
import java.util.Objects;

/**
 * This class defines a 2 dimensional finite triangular plane
 * @author Zachary
 */
public class TriangularPlane3D extends shape3D
{
    vector3D vertex, vertex1, vertex2, leg1, leg2, legHyp, normal;
    Color planeColor, edgeColor;
    
    /**
     * defines a finite triangular plane
     * @param p0 the first vertex of the triangle
     * @param p1 if argument vertices is true, defines the second vertex of the triangle.
     * else, it is the vector from the first vertex to the second vertex
     * @param p2 if argument vertices is true, defines the third vertex of the triangle.
     * else, it is the vector from the first vertex to the third vertex
     * @param vertices determines if arguments p1 and p2 define vertices or vectors
     * representing the legs of the triangle
     * @param c the color of the plane
     */
    public TriangularPlane3D(vector3D p0, vector3D p1, vector3D p2, boolean vertices, Color c)
    {
        vertex = p0;//assign all values properly
        if (vertices)
        {
            vertex1 = p1;
            vertex2 = p2;
            leg1 = vector3D.subtract(vertex1, vertex);
            leg2 = vector3D.subtract(vertex2, vertex);
        }
        else
        {
            vertex1 = vector3D.add(vertex, p1);
            vertex2 = vector3D.add(vertex, p2);
            leg1 = p1;
            leg2 = p2;
        }
        legHyp = vector3D.subtract(vector3D.add(vertex, leg1), vector3D.add(vertex, leg2));
        normal = vector3D.getCrossProduct(leg1, leg2);
        planeColor = c;
        edgeColor = Color.WHITE;//used for debugging
    }

    /**
     * finds the result of a collision between this plane and the specifies ray
     * @param ray the ray to be checked for a collision
     * @return a rayCollisionResult object with values that specify where the collision occurred
     * and the color of the plane at that point, or null if there is no collision
     */
    @Override
    public rayCollisionResult getRayColorandPos(line3D ray)
    {//we first find how far along the ray the collision occurs
        double scalar = vector3D.getDotProduct(normal, vector3D.subtract(vertex, ray.getStartPoint())) / vector3D.getDotProduct(normal, ray.getDirection());
        if (scalar <= 0)
            return null;
        //we define the point of collision
        vector3D collisionPoint = vector3D.add(vector3D.scaleVector(ray.getDirection(), scalar), ray.getStartPoint());
        //we find the vector that lies on the plane between the vertex of the triangle and the collsion point
        vector3D coplanarCollision = vector3D.subtract(collisionPoint, vertex);
        
        //we create vectors that are perpindicular to the two legs and lie in the same plane
        vector3D leg1PerpDot = vector3D.getCrossProduct(normal, leg1);
        vector3D leg2PerpDot = vector3D.getCrossProduct(normal, leg2);
        
        //we find the "coordinates" of the collision point relative to the two legs
        //it works by magic
        double sI = vector3D.getDotProduct(coplanarCollision, leg2PerpDot) / vector3D.getDotProduct(leg1, leg2PerpDot);
        double tI = vector3D.getDotProduct(coplanarCollision, leg1PerpDot) / vector3D.getDotProduct(leg2, leg1PerpDot);
        
        if (!((sI >= 0) && (tI >= 0) && (sI + tI <= 1)))//if the collision point lies outside the bounds of the triangle
            return null;
        
        Color collisionColor = planeColor;
        if ((sI < 0.01) || (tI < 0.01))//if the collision is close to an edge
            collisionColor = edgeColor;
        
        if (collisionPoint != null)
            return new rayCollisionResult(collisionPoint, ray.getStartPoint(), collisionColor);
        
        return null;
    }
    
    //accessor methods
    
    public vector3D getVertex()
    {
        return vertex;
    }
    
    public vector3D getVertex1()
    {
        return vertex1;
    }
    
    public vector3D getVertex2()
    {
        return vertex2;
    }
    public vector3D getLeg1()
    {
        return leg1;
    }
    
    public vector3D getLeg2()
    {
        return leg2;
    }
    
    //end accessor methods
    
    /**
     * used for comparison of an object and a triangular plane
     * @param o the object to be compared
     * @return - true if the object is similar, false if it is not
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof TriangularPlane3D)
            return o.hashCode() == hashCode();
        return false;
    }

    /**
     * calculates a specific numerical code for the object, used in comparisons
     * @return the specific numerical code for the object
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.vertex);
        hash = 47 * hash + Objects.hashCode(this.vertex1);
        hash = 47 * hash + Objects.hashCode(this.vertex2);
        hash = 47 * hash + Objects.hashCode(this.planeColor);
        return hash;
    }
}
