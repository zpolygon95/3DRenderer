package raytracing;

import java.awt.Color;

/**
 * Object used to store data about a ray collision
 * @author Zachary
 */
public class RayCollisionResult
{
    private Vector3D source, dest;//orientation
    private Color color;//data
    
    /**
     * creates a new collision result with the specified data
     * @param colP - the collision point
     * @param camP - the perspective point of the camera
     * @param c - the color of the "reflected light"
     */
    public RayCollisionResult(Vector3D colP, Vector3D camP, Color c)
    {//assigg appropriate values
        source = camP;
        dest = colP;
        color = c;
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
    
    //and accessor methods
}
