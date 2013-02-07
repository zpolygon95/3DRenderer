package raytracing;

import java.awt.Color;

/**
 * This class is used in calculation of scene illumination in the renderer
 * @author Zachary
 */
public class AmbientLightSource extends LightSource3D
{
    private Vector3D source;
    private Color sourceColor;
    private double distance;
    
    /**
     * Creates a new finite ambient light source, which only affects a sphere with center point p and radius r
     * @param p - the center of the area affected by this light source
     * @param c - the color of the light
     * @param d - the radius of the area affected by this light source
     */
    public AmbientLightSource(Vector3D p, Color c, double d)
    {
        source = p;
        sourceColor = c;
        distance = d;
    }
    
    /**
     * creates a new infinite ambient light source, which affects all elements in a scene graph
     * @param c - the color of the light
     */
    public AmbientLightSource(Color c)
    {
        source = null;
        sourceColor = c;
        distance = 0;
    }
    
    /**
     * Get the point that the light source starts
     * @return a position vector representing the position of the light source, which will be null in many cases
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
        if (distance == 0 || source == null)
        {
            return sourceColor;
        }
        if (Vector3D.subtract(point, source).getMagnitude() <= distance)
        {
            return sourceColor;
        }
        return new Color(0, 0, 0);
    }
    
    /**
     * Does nothing except fulfill inheritance requirements, as ambient light has no source
     * @param vector unused
     */
    @Override
    public void setPoint(Vector3D vector) {}
}
