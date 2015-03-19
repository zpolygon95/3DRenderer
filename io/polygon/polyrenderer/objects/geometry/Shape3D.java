package io.polygon.polyrenderer.objects.geometry;

import java.awt.Color;
import io.polygon.polyrenderer.util.RayCollisionResult;
import io.polygon.polyrenderer.util.Vector3D;
/**
 * Used as a parent object for all drawable 3D shapes
 * @author Zachary
 */
public abstract class Shape3D
{
    public Color backColor = Color.RED;
    public Color edgeColor = Color.BLACK;
    
    /**
     * Finds the point of intersection between a ray and the object, and the color of that object at 100% intensity white light
     * @param ray - the ray to be checked in the collision
     * @return The position and color of the intersection
     */
    public abstract RayCollisionResult getRayColorandPos(Line3D ray);
    
    /**
     * Finds the vector normal to the shape. Intended to be facing outward if that shape has volume
     * @return The normal vector
     */
    public abstract Vector3D getNormal();
    
    /**
     * Finds the corner points of the shape, to be used in the polygonal renderer.
     * 
     * @return The format of the returned corner points should be this:
     *     a shape without volume should return all it's points in the first x index
     *       ex: a TriangularPlane3D should return something like [[(0, 0, 0), (1, 0, 0), (0, 1, 0)]]
     *     a shape with volume should return the points from all of its sides in separate x values of the array
     *       ex: a RectangularPrism3D should return something like [[side1], [side2], ..., [side6]]
     * 
     */
    public abstract Vector3D[][] getCorners();

    public Color getBackColor()
    {
        return backColor;
    }
    
    public Color getEdgeColor()
    {
        return edgeColor;
    }
}
