package raytracing;

/**
 * Used for collision detection in the renderer, a nice way to store important data
 * @author Zachary
 */
public class Line3D
{
    private Vector3D startPoint, direction;//orientation
    
    /**
     * Construct a new Line3D
     * @param start
     * @param dir 
     */
    public Line3D(Vector3D start, Vector3D dir)
    {
        startPoint = start;
        direction = Vector3D.normalize(dir);
    }
    
    //accessor methods
    
    public Vector3D getStartPoint()
    {
        return startPoint;
    }
    
    public Vector3D getDirection()
    {
        return direction;
    }
    
    //end accessor methods
    
    /**
     * finds if a point lies on a line
     * @param line
     * @param point
     * @return 
     */
    public static boolean isColinear(Line3D line, Vector3D point)
    {
        double tx = (point.getX() - line.startPoint.getX()) / line.direction.getX();
        double ty = (point.getY() - line.startPoint.getY()) / line.direction.getY();
        double tz = (point.getZ() - line.startPoint.getZ()) / line.direction.getZ();
        System.out.println(tx + ", " + ty + ", " + tz);
        return ((tx == ty) && (tx == tz));
    }
}
