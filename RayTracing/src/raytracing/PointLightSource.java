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
public class PointLightSource extends LightSource3D
{
    protected Vector3D source;
    protected Color sourceColor;
    
    public PointLightSource(Vector3D p, Color c)
    {
        source = p;
        sourceColor = c;
    }
    
    @Override
    public Vector3D getSource()
    {
        return source;
    }
    
    @Override
    public Color getLightColorForPoint(Vector3D point)
    {
        return sourceColor;
    }
    
}
