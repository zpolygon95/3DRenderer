package raytracing;

import java.awt.Color;

/**
 * Somewhat depreciated, plans to extend parallelopipedon
 * @author Zachary
 */
public class rectangularPrism3D extends shape3D
{
    rectangularPlane3D[] rects;
    
    public rectangularPrism3D(vector3D p, vector3D d, vector3D d1, double l, double w, double h, Color[] c)
    {
        assert vector3D.getDotProduct(d, d1) == 0;
        assert c.length == 6;
        
        vector3D d2 = vector3D.getCrossProduct(d, d1);
        
        vector3D vLength = vector3D.scaleVector(d, l);
        vector3D vHeight = vector3D.scaleVector(d1, h);
        vector3D vWidth = vector3D.scaleVector(d2, w);
        
        vector3D p1 = vector3D.add(p, vector3D.add(vLength, vector3D.add(vHeight, vWidth)));
        
        rects = new rectangularPlane3D[6];
        rects[0] = new rectangularPlane3D(p, d, d1, w, l, c[0]);
        rects[1] = new rectangularPlane3D(p, d2, d, l, h, c[1]);
        rects[2] = new rectangularPlane3D(p, d1, d2, w, h, c[2]);
        rects[3] = new rectangularPlane3D(p1, d1, d, -w, -l, c[3]);
        rects[4] = new rectangularPlane3D(p1, d, d2, -l, -h, c[4]);
        rects[5] = new rectangularPlane3D(p1, d2, d1, -h, -w, c[5]);
    }

    @Override
    public rayCollisionResult getRayColorandPos(line3D ray)
    {
        for (rectangularPlane3D p : rects)
        {
            if (p.getRayColorandPos(ray) != null)
                return p.getRayColorandPos(ray);
        }
        return null;
    }
    
}
