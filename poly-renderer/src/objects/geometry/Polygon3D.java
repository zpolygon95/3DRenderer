/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.geometry;

import java.awt.Color;
import java.awt.Polygon;
import util.RayCollisionResult;
import util.Vector3D;

/**
 *
 * @author Zachary
 */
public class Polygon3D extends Shape3D
{
    Vector3D normal, xDirection, yDirection, position;
    Polygon shape;
    Parallelogram3D boundingBox;
    int horizRes, vertRes;
    
    public Polygon3D(Vector3D pos, Vector3D norm, Polygon p, Color c)
    {
        position = pos;
        normal = Vector3D.normalize(norm);
        shape = p;
        Vector3D xyPlanar = Vector3D.normalize(new Vector3D(normal.getX(), normal.getY(), 0));
        double xyDiff = (Math.PI/2) - Vector3D.getDotProduct(xyPlanar, new Vector3D(1, 0, 0));
        if (xyPlanar.getY() > 0)
        {
            xDirection = Vector3D.rotateVectorZ(new Vector3D(1, 0, 0), -xyDiff);
        }
        else
        {
            xDirection = Vector3D.rotateVectorZ(new Vector3D(1, 0, 0), xyDiff);
        }
        
        Vector3D zyPlanar = Vector3D.normalize(new Vector3D(0, normal.getY(), normal.getZ()));
        double zyDiff = (Math.PI/2) - Vector3D.getDotProduct(zyPlanar, new Vector3D(0, 0, 1));
        if (zyPlanar.getY() > 0)
        {
            yDirection = Vector3D.rotateVectorZ(new Vector3D(1, 0, 0), -zyDiff);
        }
        else
        {
            yDirection = Vector3D.rotateVectorZ(new Vector3D(1, 0, 0), zyDiff);
        }
        
        int xMin = shape.xpoints[0];
        int xMax = shape.xpoints[0];
        int yMin = shape.ypoints[0];
        int yMax = shape.ypoints[0];
        for (int i = 0; i < shape.npoints; i++)
        {
            int x = shape.xpoints[i];
            int y = shape.ypoints[i];
            if (x < xMin)
            {
                xMin = x;
            }
            if (x > xMax)
            {
                xMax = x;
            }
            if (y < yMin)
            {
                yMin = y;
            }
            if (y > yMax)
            {
                yMax = y;
            }
        }
        Vector3D boundingBoxPos = Vector3D.add(position, Vector3D.add(Vector3D.scaleVector(xDirection, xMin), Vector3D.scaleVector(yDirection, yMin)));
        horizRes = xMax - xMin;
        vertRes = yMax - yMin;
        Vector3D l = Vector3D.scaleVector(xDirection, horizRes);
        Vector3D l1 = Vector3D.scaleVector(yDirection, vertRes);
        boundingBox = new Parallelogram3D(boundingBoxPos, l, l1, c);
    }
    
    public Polygon3D(Vector3D pos, Vector3D norm, Vector3D xdir, Vector3D ydir, Polygon p, Color c)
    {
        position = pos;
        normal = Vector3D.normalize(norm);
        shape = p;
        xDirection = xdir;
        yDirection = ydir;
        
        int xMin = shape.xpoints[0];
        int xMax = shape.xpoints[0];
        int yMin = shape.ypoints[0];
        int yMax = shape.ypoints[0];
        for (int i = 0; i < shape.npoints; i++)
        {
            int x = shape.xpoints[i];
            int y = shape.ypoints[i];
            if (x < xMin)
            {
                xMin = x;
            }
            if (x > xMax)
            {
                xMax = x;
            }
            if (y < yMin)
            {
                yMin = y;
            }
            if (y > yMax)
            {
                yMax = y;
            }
        }
        Vector3D boundingBoxPos = Vector3D.add(position, Vector3D.add(Vector3D.scaleVector(xDirection, xMin), Vector3D.scaleVector(yDirection, yMin)));
        horizRes = xMax - xMin;
        vertRes = yMax - yMin;
        Vector3D l = Vector3D.scaleVector(xDirection, horizRes);
        Vector3D l1 = Vector3D.scaleVector(yDirection, vertRes);
        boundingBox = new Parallelogram3D(boundingBoxPos, l, l1, c);
    }

    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        Vector3D collisionPoint = boundingBox.getCollisionPoint(ray, horizRes, vertRes);
        if (collisionPoint == null)
        {
            return null;
        }
        if (shape.contains(collisionPoint.getX(), collisionPoint.getY()))
        {
            return boundingBox.getRayColorandPos(ray);
        }
        return null;
    }

    @Override
    public Vector3D getNormal()
    {
        return normal;
    }

    @Override
    public Vector3D[][] getCorners()
    {
        Vector3D[][] corners = new Vector3D[1][shape.npoints];
        
        for (int i = 0; i < shape.npoints; i++)
        {
            corners[0][i] = Vector3D.add(position,
                    Vector3D.add(Vector3D.scaleVector(xDirection, shape.xpoints[i]),
                    Vector3D.scaleVector(yDirection, shape.ypoints[i])));
        }
        
        return corners;
    }
    
}
