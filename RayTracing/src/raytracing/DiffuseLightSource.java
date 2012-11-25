/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.Color;

/**
 *
 * @author Zachary
 */
public class DiffuseLightSource extends PointLightSource
{
    private double maxDistance;
    
    public DiffuseLightSource(Vector3D p, Color c, double d)
    {
        super(p, c);
        maxDistance = d;
    }
    
    @Override
    public Color getLightColorForPoint(Vector3D v)
    {
        double pointDistance = Vector3D.subtract(v, source).getMagnitude();
        if (pointDistance > maxDistance)
            return new Color(0, 0, 0);
        double scalar = pointDistance / maxDistance;
        return Vector3D.scaleColor(sourceColor, new Color(0, 0, 0), scalar);
    }
}
