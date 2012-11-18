package raytracing;

/**
 * Used as a parent object for all drawable 3D shapes
 * @author Zachary
 */
public abstract class shape3D
{
    public abstract rayCollisionResult getRayColorandPos(line3D ray);
}
