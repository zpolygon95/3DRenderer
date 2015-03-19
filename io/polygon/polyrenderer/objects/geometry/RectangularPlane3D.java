package io.polygon.polyrenderer.objects.geometry;

import java.awt.Color;
import java.util.Objects;
import io.polygon.polyrenderer.util.RayCollisionResult;
import io.polygon.polyrenderer.util.Vector3D;

/**
 * Somewhat depreciated. plans to extend parallelogram3D at some point
 * @author Zachary
 */
public class RectangularPlane3D extends Shape3D
{
    TriangularPlane3D t1, t2;
    
    public RectangularPlane3D(Vector3D p, Vector3D p1, Vector3D p2, double width, double height, Color c)
    {
        assert Vector3D.getDotProduct(p1, p2) == 0;
        Vector3D vHeight = Vector3D.scaleVector(p1, height);
        Vector3D vWidth = Vector3D.scaleVector(p2, width);
        t1 = new TriangularPlane3D(p, vHeight, vWidth, false, c);
        t2 = new TriangularPlane3D(Vector3D.add(Vector3D.add(p, vHeight), vWidth),
                Vector3D.scaleVector(p1, -height),
                Vector3D.scaleVector(p2, -width),
                false, c);
    }
    
    @Override
    public Vector3D getNormal()
    {
        return null;//no normal for collections of lesser objects
    }

    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        RayCollisionResult c1 = t1.getRayColorandPos(ray);
        RayCollisionResult c2 = t2.getRayColorandPos(ray);
        if (c1 == null)
        {
            return c2;
        }
        return c1;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof RectangularPlane3D)
        {
            return o.hashCode() == hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.t1);
        hash = 29 * hash + Objects.hashCode(this.t2);
        return hash;
    }

    @Override
    public Vector3D[][] getCorners() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
