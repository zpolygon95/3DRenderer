package raytracing;

import java.awt.Color;

/**
 * To be continued!
 * @author Zachary
 */
public class lightSource3D extends shape3D
{
    shape3D shape;
    vector3D sourcePoint;
    Color lightColor;
    
    public lightSource3D(vector3D p, Color l, shape3D s)
    {
        sourcePoint = p;
        lightColor = l;
        shape = s;
    }

    @Override
    public rayCollisionResult getRayColorandPos(line3D ray)
    {
        if (shape == null)
            return null;
        return shape.getRayColorandPos(ray);
    }
    
    public vector3D getSource()
    {
        return sourcePoint;
    }
    
    public Color getColor()
    {
        return lightColor;
    }
}
