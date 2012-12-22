package raytracing;

/**
 * Used as a parent object for all drawable 3D shapes
 * @author Zachary
 */
public abstract class Shape3D
{
    /**
     * finds the point of intersection between a ray and the object, and the color of that object at 100% intensity white light
     * @param ray - the ray to be checked in the collision
     * @return The position and color of the intersection
     */
    public abstract RayCollisionResult getRayColorandPos(Line3D ray);
    
    public abstract Vector3D getNormal();
}
