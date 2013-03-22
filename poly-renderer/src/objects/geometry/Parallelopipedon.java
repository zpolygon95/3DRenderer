package objects.geometry;

import java.awt.Color;
import util.RayCollisionResult;
import util.Vector3D;

/**
 * this class specifies a 3 dimensional parallelogram with the same name
 * @author Zachary
 */
public class Parallelopipedon extends Shape3D
{
    Parallelogram3D[] pGrams;//components
    
    public Parallelopipedon(Vector3D p, Vector3D d, Vector3D d1, Vector3D d2, Color[] c)
    {//assign values and extrapolate components
        if (c.length < 6)
        {
            c = new Color[6];
            for (int i = 0; i < 6; i++)
            {
                c[i] = Color.BLACK;
            }
        }
        pGrams = new Parallelogram3D[6];
        pGrams[0] = new Parallelogram3D(p, d, d1, c[0]);
        pGrams[1] = new Parallelogram3D(p, d1, d2, c[1]);
        pGrams[2] = new Parallelogram3D(p, d2, d, c[2]);
        
        Vector3D p1 = Vector3D.add(p, Vector3D.add(d, Vector3D.add(d1, d2)));
        Vector3D d3 = Vector3D.scaleVector(d, -1);
        Vector3D d4 = Vector3D.scaleVector(d1, -1);
        Vector3D d5 = Vector3D.scaleVector(d2, -1);
        
        pGrams[3] = new Parallelogram3D(p1, d4, d3, c[3]);
        pGrams[4] = new Parallelogram3D(p1, d5, d4, c[4]);
        pGrams[5] = new Parallelogram3D(p1, d3, d5, c[5]);
    }
    
    @Override
    public Vector3D getNormal()
    {
        return null;//no normal for collections of lesser objects
    }
    
    /**
     * Finds the results of the collisions with all of this objects components, and returns the closest one
     * @param ray - the ray to be collided
     * @return - the collision result
     */
    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        RayCollisionResult c = pGrams[0].getRayColorandPos(ray);
        for (Parallelogram3D p : pGrams)
        {
            RayCollisionResult cPrime = p.getRayColorandPos(ray);
            if (c != null && cPrime != null)
            {
                if (cPrime.getRayLength() < c.getRayLength())
                {
                    c = cPrime;
                }
            }
            else if (c == null && cPrime != null)
            {
                c = cPrime;
            }
        }
        return c;
    }

    @Override
    public Vector3D[][] getCorners()
    {
        Vector3D[][] vertices = new Vector3D[pGrams.length][4];
        for (int x = 0; x < pGrams.length; x++)
        {
            vertices[x] = pGrams[x].getCorners()[0];
        }
        return vertices;
    }
    
}
