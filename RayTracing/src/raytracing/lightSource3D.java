package raytracing;

import java.awt.Color;

/**
 * To be continued!
 * @author Zachary
 */
public class LightSource3D extends Shape3D
{
    Shape3D shape;
    Vector3D sourcePoint;
    Color lightColor;
    
    public LightSource3D(Vector3D p, Color l, Shape3D s)
    {
        sourcePoint = p;
        lightColor = l;
        shape = s;
    }

    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        if (shape == null)
            return null;
        return shape.getRayColorandPos(ray);
    }
    
    public Vector3D getSource()
    {
        return sourcePoint;
    }
    
    public Color getColor()
    {
        return lightColor;
    }
}
