package raytracing;

import java.awt.Color;
import java.util.Objects;

/**
 * Somewhat depreciated. plans to extend parallelogram3D at some point
 * @author Zachary
 */
public class rectangularPlane3D extends shape3D
{
    TriangularPlane3D t1, t2;
    
    public rectangularPlane3D(vector3D p, vector3D p1, vector3D p2, double width, double height, Color c)
    {
        assert vector3D.getDotProduct(p1, p2) == 0;
        vector3D vHeight = vector3D.scaleVector(p1, height);
        vector3D vWidth = vector3D.scaleVector(p2, width);
        t1 = new TriangularPlane3D(p, vHeight, vWidth, false, c);
        t2 = new TriangularPlane3D(vector3D.add(vector3D.add(p, vHeight), vWidth),
                vector3D.scaleVector(p1, -height),
                vector3D.scaleVector(p2, -width),
                false, c);
    }

    @Override
    public rayCollisionResult getRayColorandPos(line3D ray)
    {
        rayCollisionResult c1 = t1.getRayColorandPos(ray);
        rayCollisionResult c2 = t2.getRayColorandPos(ray);
        if (c1 == null)
            return c2;
        return c1;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof rectangularPlane3D)
            return o.hashCode() == hashCode();
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.t1);
        hash = 29 * hash + Objects.hashCode(this.t2);
        return hash;
    }
}
