package io.polygon.polyrenderer.objects.geometry;

import java.awt.Color;
import java.awt.Polygon;
import io.polygon.polyrenderer.util.RayCollisionResult;
import io.polygon.polyrenderer.util.Vector3D;

/**
 *
 * @author Zachary
 */
public class RaisedPolygon extends Shape3D
{
    Vector3D normal;
    Polygon3D base1, base2;
    Parallelogram3D[] sides;
    double height;
    int totalShapes;
    Shape3D[] shapes;
    
    public RaisedPolygon(Vector3D pos, Vector3D norm, Polygon p, double height, Color c)
    {
        normal = norm;
        base1 = new Polygon3D(pos, norm, p, c);
        base2 = new Polygon3D(Vector3D.add(pos, Vector3D.scaleVector(norm, height)), norm, p, c);
        sides = new Parallelogram3D[p.npoints];
        Vector3D[] b1Points = base1.getCorners()[0];
        Vector3D[] b2Points = base2.getCorners()[0];
        for (int i = 0; i < p.npoints; i++)
        {
            Vector3D npos = b1Points[i];
            Vector3D nl;
            nl = Vector3D.subtract(b1Points[i], b1Points[i + 1]);
            Vector3D nl1 = Vector3D.subtract(b1Points[i], b2Points[i]);
            sides[i] = new Parallelogram3D(npos, nl, nl1, c);
        }
        totalShapes = p.npoints + 2;
        shapes = new Shape3D[totalShapes];
        shapes[0] = base1;
        shapes[1] = base2;
        System.arraycopy(sides, 0, shapes, 2, sides.length);
    }
    
    public RaisedPolygon(Vector3D pos, Vector3D norm, Vector3D xdir, Vector3D ydir, Polygon p, double height, Color c)
    {
        normal = norm;
        base1 = new Polygon3D(pos, norm, xdir, ydir, p, c);
        base2 = new Polygon3D(Vector3D.add(pos, Vector3D.scaleVector(norm, height)), norm, xdir, ydir, p, c);
        sides = new Parallelogram3D[p.npoints];
        Vector3D[] b1Points = base1.getCorners()[0];
        Vector3D[] b2Points = base2.getCorners()[0];
        for (int i = 0; i < p.npoints; i++)
        {
            Vector3D npos = b1Points[i];
            Vector3D nl = Vector3D.subtract(b1Points[(i + 1) % (p.npoints - 1)], b1Points[i]);
            Vector3D nl1 = Vector3D.subtract(b2Points[i], b1Points[i]);
            sides[i] = new Parallelogram3D(npos, nl, nl1, c);
        }
        totalShapes = p.npoints + 2;
        shapes = new Shape3D[totalShapes];
        shapes[0] = base1;
        shapes[1] = base2;
        System.arraycopy(sides, 0, shapes, 2, sides.length);
    }

    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        RayCollisionResult closestCollision = shapes[0].getRayColorandPos(ray);
        
        for (int i = 0; i < totalShapes; i++)
        {
            RayCollisionResult n = shapes[i].getRayColorandPos(ray);
            if (closestCollision == null)
            {
                closestCollision = n;
            }
            else
            {
                double dist = closestCollision.getRayLength();
                if (n != null && n.getRayLength() < dist)
                {
                    closestCollision = n;
                }
            }
        }
        
        return closestCollision;
    }

    @Override
    public Vector3D getNormal()
    {
        return normal;
    }

    @Override
    public Vector3D[][] getCorners()
    {
        Vector3D[][] corners = new Vector3D[totalShapes][];
        
        for (int i = 0; i < totalShapes; i++)
        {
            corners[i] = shapes[i].getCorners()[0];
        }
        return corners;
    }
}
