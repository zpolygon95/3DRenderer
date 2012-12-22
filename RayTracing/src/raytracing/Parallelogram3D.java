package raytracing;

import java.awt.Color;
import java.util.Objects;

/**
 * This class defines a 2 dimensional finite plane shaped like a parallelogram, created from 2 triangles
 * @author Zachary
 */
public class Parallelogram3D extends Shape3D
{
    TriangularPlane3D t1, t2;//components
    
    /**
     * creates a new Parallelogram3D
     * @param p - the vertex of the shape
     * @param l - the first leg of the shape
     * @param l1 - the second leg of the shape
     * @param c - the color of the shape
     */
    public Parallelogram3D(Vector3D p, Vector3D l, Vector3D l1, Color c)
    {//assign proper values
        t1 = new TriangularPlane3D(p, l, l1, false, c);
        Vector3D p1 = Vector3D.add(p, Vector3D.add(l, l1));//create adjacent and opposite triangles
        Vector3D l2 = Vector3D.scaleVector(l1, -1);
        Vector3D l3 = Vector3D.scaleVector(l, -1);
        t2 = new TriangularPlane3D(p1, l2, l3, false, c);
    }
    
    //accessor methods
    
    @Override
    public Vector3D getNormal()
    {
        return null;//no normal for collections of lesser objects
    }
    
    
    public TriangularPlane3D getT1()
    {
        return t1;
    }
    
    public TriangularPlane3D getT2()
    {
        return t2;
    }
    
    //end accessor methods
    
    /**
     * Checks both components of the shape for a collision, and returns the first one
     * @param ray - the ray to be collided
     * @return the collision result
     */
    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        RayCollisionResult c = t1.getRayColorandPos(ray);
        if (c != null)
            return c;
        return t2.getRayColorandPos(ray);
    }

    /**
     * creates a specific numerical code for the object, used in comparison
     * @return a numerical code used for comparison
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.t1);
        hash = 89 * hash + Objects.hashCode(this.t2);
        return hash;
    }
    
    /**
     * Compares this object to another, using hash codes
     * @param o - the object to be compared
     * @return true if the object is similar, false if not
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Parallelogram3D)
            return o.hashCode() == hashCode();
        return false;
    }
}
