package raytracing;

/**
 * Used for collision detection in the renderer, a nice way to store important data
 * @author Zachary
 */
public class line3D
{
    private vector3D startPoint, direction;//orientation
    
    /**
     * Construct a new line3D
     * @param start
     * @param dir 
     */
    public line3D(vector3D start, vector3D dir)
    {
        startPoint = start;
        direction = vector3D.normalize(dir);
    }
    
    //accessor methods
    
    public vector3D getStartPoint()
    {
        return startPoint;
    }
    
    public vector3D getDirection()
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
    public static boolean isColinear(line3D line, vector3D point)
    {
        double tx = (point.getX() - line.startPoint.getX()) / line.direction.getX();
        double ty = (point.getY() - line.startPoint.getY()) / line.direction.getY();
        double tz = (point.getZ() - line.startPoint.getZ()) / line.direction.getZ();
        System.out.println(tx + ", " + ty + ", " + tz);
        return ((tx == ty) && (tx == tz));
    }
}
