package raytracing;

import java.awt.Color;

/**
 *
 * @author Zachary
 */
public class AmbientLightSource extends LightSource3D
{
    private Vector3D source;
    private Color sourceColor;
    private double distance;
    
    public AmbientLightSource(Vector3D p, Color c, double d)
    {
        source = p;
        sourceColor = c;
        distance = d;
    }
    
    public AmbientLightSource(Color c)
    {
        source = null;
        sourceColor = c;
        distance = 0;
    }
    
    @Override
    public Vector3D getSource()
    {
        return source;
    }
    
    @Override
    public Color getLightColorForPoint(Vector3D point)
    {
        if (distance == 0 || source == null)
            return sourceColor;
        if (Vector3D.subtract(point, source).getMagnitude() <= distance)
            return sourceColor;
        return new Color(0, 0, 0);
    }
}
