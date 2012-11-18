package raytracing;

import java.awt.Color;

/**
 * this class specifies a 3 dimensional parallelogram with the same name
 * @author Zachary
 */
public class parallelopipedon extends shape3D
{
    parallelogram3D[] pGrams;//components
    
    public parallelopipedon(vector3D p, vector3D d, vector3D d1, vector3D d2, Color[] c)
    {//assign values and extrapolate components
        if (c.length < 6)
        {
            c = new Color[6];
            for (int i = 0; i < 6; i++)
                c[i] = Color.BLACK;
        }
        pGrams = new parallelogram3D[6];
        pGrams[0] = new parallelogram3D(p, d, d1, c[0]);
        pGrams[1] = new parallelogram3D(p, d1, d2, c[1]);
        pGrams[2] = new parallelogram3D(p, d2, d, c[2]);
        
        vector3D p1 = vector3D.add(p, vector3D.add(d, vector3D.add(d1, d2)));
        vector3D d3 = vector3D.scaleVector(d, -1);
        vector3D d4 = vector3D.scaleVector(d1, -1);
        vector3D d5 = vector3D.scaleVector(d2, -1);
        
        pGrams[3] = new parallelogram3D(p1, d4, d3, c[3]);
        pGrams[4] = new parallelogram3D(p1, d5, d4, c[4]);
        pGrams[5] = new parallelogram3D(p1, d3, d5, c[5]);
    }
    
    /**
     * Finds the results of the collisions with all of this objects components, and returns the closest one
     * @param ray - the ray to be collided
     * @return - the collision result
     */
    @Override
    public rayCollisionResult getRayColorandPos(line3D ray)
    {
        rayCollisionResult c = pGrams[0].getRayColorandPos(ray);
        for (parallelogram3D p : pGrams)
        {
            rayCollisionResult cPrime = p.getRayColorandPos(ray);
            if (c != null && cPrime != null)
            {
                if (cPrime.getRayLength() < c.getRayLength())
                    c = cPrime;
            }
            else if (c == null && cPrime != null)
                c = cPrime;
        }
        return c;
    }
    
}
