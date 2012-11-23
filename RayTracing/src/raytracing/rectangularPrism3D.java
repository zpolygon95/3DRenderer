package raytracing;

import java.awt.Color;

/**
 * Somewhat depreciated, plans to extend parallelopipedon
 * @author Zachary
 */
public class RectangularPrism3D extends Shape3D
{
    RectangularPlane3D[] rects;
    
    public RectangularPrism3D(Vector3D p, Vector3D d, Vector3D d1, double l, double w, double h, Color[] c)
    {
        assert Vector3D.getDotProduct(d, d1) == 0;
        assert c.length == 6;
        
        Vector3D d2 = Vector3D.getCrossProduct(d, d1);
        
        Vector3D vLength = Vector3D.scaleVector(d, l);
        Vector3D vHeight = Vector3D.scaleVector(d1, h);
        Vector3D vWidth = Vector3D.scaleVector(d2, w);
        
        Vector3D p1 = Vector3D.add(p, Vector3D.add(vLength, Vector3D.add(vHeight, vWidth)));
        
        rects = new RectangularPlane3D[6];
        rects[0] = new RectangularPlane3D(p, d, d1, w, l, c[0]);
        rects[1] = new RectangularPlane3D(p, d2, d, l, h, c[1]);
        rects[2] = new RectangularPlane3D(p, d1, d2, w, h, c[2]);
        rects[3] = new RectangularPlane3D(p1, d1, d, -w, -l, c[3]);
        rects[4] = new RectangularPlane3D(p1, d, d2, -l, -h, c[4]);
        rects[5] = new RectangularPlane3D(p1, d2, d1, -h, -w, c[5]);
    }

    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        for (RectangularPlane3D p : rects)
        {
            if (p.getRayColorandPos(ray) != null)
                return p.getRayColorandPos(ray);
        }
        return null;
    }
    
}
