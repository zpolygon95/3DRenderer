package raytracing;

import java.awt.Color;

/**
 * Used as a parent object for all light sources
 * @author Zachary
 */
public abstract class LightSource3D
{
    /**
     * returns a specific color based on a given position vector
     * @param point - The position vector of the point to be lighted
     * @return the color of the light at that point
     */
    public abstract Color getLightColorForPoint(Vector3D point);
    
    public abstract Vector3D getSource();
    
    /**
     * sets the source point of the light source
     * @param vector the new point of the light source
     */
    public abstract void setPoint(Vector3D vector);
    
    public static Color applyFilter(Color filterColor, Color originalColor)
    {
        int r = originalColor.getRed();
        int g = originalColor.getGreen();
        int b = originalColor.getBlue();
        if (filterColor.getRed() < originalColor.getRed())
        {
            r = filterColor.getRed();
        }
        if (filterColor.getGreen() < originalColor.getGreen())
        {
            g = filterColor.getGreen();
        }
        if (filterColor.getBlue() < originalColor.getBlue())
        {
            b = filterColor.getBlue();
        }
        
        return new Color(r, g, b);
    }
    
    public static Color resolveReflection(SurfaceColor initial, Color reflection)
    {
        Color addingColor = new Color(initial.getRed(), initial.getGreen(), initial.getBlue(), initial.getRelfectiveness());
        return Vector3D.addColors(addingColor, reflection);
    }
    
    public static Color resolveTransparency(SurfaceColor initial, Color behind)
    {
        return null;
    }
}
