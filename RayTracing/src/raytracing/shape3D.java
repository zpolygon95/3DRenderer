package raytracing;

/**
 * Used as a parent object for all drawable 3D shapes
 * @author Zachary
 */
public abstract class Shape3D
{
    public abstract RayCollisionResult getRayColorandPos(Line3D ray);
}
