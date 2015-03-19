package io.polygon.polyrenderer.util;

import java.awt.Color;
import io.polygon.polyrenderer.objects.geometry.Shape3D;

/**
 * Object used to store data about a ray collision
 * @author Zachary
 */
public class RayCollisionResult
{
    private Vector3D source, dest;//orientation
    private Color color;//data
    private Shape3D object;
    
    /**
     * creates a new collision result with the specified data
     * @param colP - the collision point
     * @param camP - the perspective point of the camera
     * @param c - the color of the "reflected light"
     */
    public RayCollisionResult(Vector3D colP, Vector3D camP, Color c, Shape3D o)
    {//assigg appropriate values
        source = camP;
        dest = colP;
        color = c;
        object = o;
    }
    
    //accessor methods
    
    public Vector3D getSource()
    {
        return source;
    }
    
    public Vector3D getColisionPoint()
    {
        return dest;
    }
    
    public double getRayLength()
    {
        return Vector3D.subtract(dest, source).getMagnitude();
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public Shape3D getObject()
    {
        return object;
    }
    
    //end accessor methods
    
    @Override
    public String toString()
    {
        return "Ray cast from: " + source + "to : " + dest + "\n- Length: " + getRayLength() + "\n- Object Color: " + color + "\n- Object: " + object;
    }
}
