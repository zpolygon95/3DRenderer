package objects;

import java.awt.Color;
import util.Vector3D;

/**
 * This class specifies a type of point based light source that diffuses over a distance
 * @author Zachary
 */
public class DiffuseLightSource extends PointLightSource
{
    private double maxDistance;
    
    /**
     * Constructs a new DiffuseLightSource object
     * @param p - the point from which the light emanates
     * @param c - the color of the light
     * @param d - the distance from p at which the light completely diffuses
     */
    public DiffuseLightSource(Vector3D p, Color c, double d)
    {
        super(p, c);
        maxDistance = d;
    }
    
    /**
     * Constructs a new PointBasedLightSource object
     * @param p - the point from which the light emanates
     * @param c - the color of the light
     */
    @Override
    public Color getLightColorForPoint(Vector3D v)
    {
        double pointDistance = Vector3D.subtract(v, source).getMagnitude();
        if (pointDistance > maxDistance)
        {
            return new Color(0, 0, 0);
        }
        double scalar = pointDistance / maxDistance;
        return Vector3D.scaleColor(new Color(0, 0, 0), sourceColor, scalar);
    }
}
