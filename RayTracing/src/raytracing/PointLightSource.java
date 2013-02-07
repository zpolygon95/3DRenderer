package raytracing;

import java.awt.Color;

/**
 * This class specifies a Point based light source used in illumination of a scene
 * @author Zachary
 */
public class PointLightSource extends LightSource3D
{
    protected Vector3D source;
    protected Color sourceColor;
    
    /**
     * Constructs a new PointBasedLightSource object
     * @param p - the point from which the light emanates
     * @param c - the color of the light
     */
    public PointLightSource(Vector3D p, Color c)
    {
        source = p;
        sourceColor = c;
    }
    
    /**
     * Get the position of the light source
     * @return a vector representing the position of the light source
     */
    @Override
    public Vector3D getSource()
    {
        return source;
    }
    
    /**
     * Gets the color of the light that affects the specified point
     * @param point - a vector representing the point to be checked
     * @return the color of the light at the specified point
     */
    @Override
    public Color getLightColorForPoint(Vector3D point)
    {
        return sourceColor;
    }
    
    /**
     * mutator method to change the position of the source
     * @param point the new source point
     */
    @Override
    public void setPoint(Vector3D point)
    {
        source = point;
    }
    
}
