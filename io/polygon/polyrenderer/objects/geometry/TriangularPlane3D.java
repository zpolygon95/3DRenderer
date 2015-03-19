package io.polygon.polyrenderer.objects.geometry;

import java.awt.Color;
import java.util.Objects;
import io.polygon.polyrenderer.util.RayCollisionResult;
import io.polygon.polyrenderer.util.Vector3D;

/**
 * This class defines a 2 dimensional finite triangular plane
 * @author Zachary
 */
public class TriangularPlane3D extends Shape3D
{
    Vector3D vertex, vertex1, vertex2, leg1, leg2, legHyp, normal;
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
    public TriangularPlane3D(Vector3D p0, Vector3D p1, Vector3D p2, boolean vertices, Color c)
    {
        vertex = p0;//assign all values properly
        if (vertices)
        {
            vertex1 = p1;
            vertex2 = p2;
            leg1 = Vector3D.subtract(vertex1, vertex);
            leg2 = Vector3D.subtract(vertex2, vertex);
        }
        else
        {
            vertex1 = Vector3D.add(vertex, p1);
            vertex2 = Vector3D.add(vertex, p2);
            leg1 = p1;
            leg2 = p2;
        }
        legHyp = Vector3D.subtract(Vector3D.add(vertex, leg1), Vector3D.add(vertex, leg2));
        normal = Vector3D.getCrossProduct(leg1, leg2);
        planeColor = c;
        edgeColor = Color.WHITE;//used for debugging
    }

    /**
     * finds the result of a collision between this plane and the specifies ray
     * @param ray the ray to be checked for a collision
     * @return a RayCollisionResult object with values that specify where the collision occurred
     * and the color of the plane at that point, or null if there is no collision
     */
    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {//we first find how far along the ray the collision occurs
        double scalar = Vector3D.getDotProduct(normal, Vector3D.subtract(vertex, ray.getStartPoint())) / Vector3D.getDotProduct(normal, ray.getDirection());
        if (scalar <= 0)
        {
            return null;
        }
        //we define the point of collision
        Vector3D collisionPoint = Vector3D.add(Vector3D.scaleVector(ray.getDirection(), scalar), ray.getStartPoint());
        //we find the vector that lies on the plane between the vertex of the triangle and the collsion point
        Vector3D coplanarCollision = Vector3D.subtract(collisionPoint, vertex);
        
        //we create vectors that are perpindicular to the two legs and lie in the same plane
        Vector3D leg1PerpDot = Vector3D.getCrossProduct(normal, leg1);
        Vector3D leg2PerpDot = Vector3D.getCrossProduct(normal, leg2);
        
        //we find the "coordinates" of the collision point relative to the two legs
        //it works by magic
        double sI = Vector3D.getDotProduct(coplanarCollision, leg2PerpDot) / Vector3D.getDotProduct(leg1, leg2PerpDot);
        double tI = Vector3D.getDotProduct(coplanarCollision, leg1PerpDot) / Vector3D.getDotProduct(leg2, leg1PerpDot);
        
        if (!((sI >= 0) && (tI >= 0) && (sI + tI <= 1)))//if the collision point lies outside the bounds of the triangle
        {
            return null;
        }
        
        Color collisionColor = planeColor;
        //don't mind this mess, just me playing with colors
        int sIi = (int) (sI * 21);
        int tIi = (int)(tI * 21);
        
        if (((sIi % 3) == 0) || ((tIi % 3) == 0))
        {
            collisionColor = Color.RED;
        }
        else if (((sIi % 3) == 1) || ((tIi % 3) == 1))
        {
            collisionColor = Color.GREEN;
        }
        
        if ((sI < 0.01) || (tI < 0.01))//if the collision is close to an edge
        {
            collisionColor = edgeColor;
        }
        
//        if (((sI > 0.45) && (sI < 0.55)) || ((tI > 0.45) && (tI < 0.55)))
//        {
//            collisionColor = Color.WHITE;
//        }
        
        if (collisionPoint != null)
        {
            return new RayCollisionResult(collisionPoint, ray.getStartPoint(), collisionColor, this);
        }
        
        return null;
    }
    
    /**
     * Finds the "coordinates" of the collision. This is somewhat odd for a triangle because the coordinates stop along a diagonal line
     * @param ray the ray to be collided
     * @param horizRes the horizontal resolution of the pixmap. to be depreciated
     * @param vertRes the vertical resolution of the pixmap. to be depreciated
     * @return 
     */
    public Vector3D getCollisionPoint (Line3D ray, int horizRes, int vertRes)
    {
        double scalar = Vector3D.getDotProduct(normal, Vector3D.subtract(vertex, ray.getStartPoint())) / Vector3D.getDotProduct(normal, ray.getDirection());
        if (scalar <= 0)
        {
            return null;
        }
        //we define the point of collision
        Vector3D collisionPoint = Vector3D.add(Vector3D.scaleVector(ray.getDirection(), scalar), ray.getStartPoint());
        //we find the vector that lies on the plane between the vertex of the triangle and the collsion point
        Vector3D coplanarCollision = Vector3D.subtract(collisionPoint, vertex);
        
        //we create vectors that are perpindicular to the two legs and lie in the same plane
        Vector3D leg1PerpDot = Vector3D.getCrossProduct(normal, leg1);
        Vector3D leg2PerpDot = Vector3D.getCrossProduct(normal, leg2);
        
        //we find the "coordinates" of the collision point relative to the two legs
        //it works by magic
        double sI = Vector3D.getDotProduct(coplanarCollision, leg2PerpDot) / Vector3D.getDotProduct(leg1, leg2PerpDot);
        double tI = Vector3D.getDotProduct(coplanarCollision, leg1PerpDot) / Vector3D.getDotProduct(leg2, leg1PerpDot);
        
        if (((sI >= 0) && (tI >= 0) && (sI + tI <= 1)))//if the collision point lies inside the bounds of the triangle
        {
            int sIi = (int)(sI * vertRes);
            int tIi = (int)(tI * horizRes);
            return new Vector3D(tIi, sIi, 0);//return regular coordinates
        }
        
        int sIi = (int)(sI * vertRes);
        int tIi = (int)(tI * horizRes);
        return new Vector3D(tIi, sIi, 1);//return coordinates that specify the collision took place outside the bounds of the triangle
    }
    
    //accessor methods
    
    @Override
    public Vector3D getNormal()
    {
        return normal;
    }
    
    public Vector3D getVertex()
    {
        return vertex;
    }
    
    public Vector3D getVertex1()
    {
        return vertex1;
    }
    
    public Vector3D getVertex2()
    {
        return vertex2;
    }
    public Vector3D getLeg1()
    {
        return leg1;
    }
    
    public Vector3D getLeg2()
    {
        return leg2;
    }
    
    //end accessor methods
    
    @Override
    public Vector3D[][] getCorners()
    {
        Vector3D[][] vertices = new Vector3D[1][3];//hopefully there comes a better way to do this soon
        vertices[0][0] = vertex;
        vertices[0][1] = vertex1;
        vertices[0][2] = vertex2;
        return vertices;
    }
    
    /**
     * used for comparison of an object and a triangular plane
     * @param o the object to be compared
     * @return - true if the object is similar, false if it is not
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof TriangularPlane3D)
        {
            return o.hashCode() == hashCode();
        }
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
