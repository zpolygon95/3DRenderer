package raytracing;

/**
 * This object is used in the creation of 3D images by providing the ray objects to the scene graph
 * @author Zachary
 */
public class camera3D
{
    private vector3D perspective, direction, directionX, directionY;//orientation of the camera
    private int horizResolution, vertResolution;//resolution of the images produced
    private double horizViewport, vertViewport;//angles of the viewport of the camera
    
    /**
     * creates a new camera3D object using vectors as orientation arguments
     * @param p - the position of the camera
     * @param d - the direction the camera is facing
     * @param dx - the horizontal direction of the camera
     * @param hr - the horizontal resolution of the camera
     * @param vr - the vertical resolution of the camera
     * @param hv - the horizontal viewport angle of the camera
     * @param vv - the vertical viewport angle of the camera
     */
    public camera3D(vector3D p, vector3D d, vector3D dx, int hr, int vr, double hv, double vv)
    {//assign the appropriate values
        perspective = p;
        direction = d;
        directionX = dx;
        directionY = vector3D.getCrossProduct(direction, directionX);
        horizResolution = hr;
        vertResolution = vr;
        horizViewport = hv;
        vertViewport = vv;
    }
    
    /**
     * creates a new camera3D object using angles as orientation arguments
     * @param p - the position of the camera
     * @param tilt - if looking along the X axis, tilt is the angle between direction and the X axis in the XY plane
     * @param yaw - if looking along the X axis, yaw is the angle between direction and the X axis in the XZ plane
     * @param roll - if looking along the X axis, roll is the angle between direction and the Z axis in the YZ plane
     * @param hr - horizontal resolution of the camera
     * @param vr - the vertical resolution of the camera
     * @param hv - the horizontal viewport angle of the camera
     * @param vv - the vertical viewport angle of the camera
     */
    public camera3D(vector3D p, double tilt, double yaw, double roll, int hr, int vr, double hv, double vv)
    {//assign the proper values
        perspective = p;
        direction = vector3D.angleToVector(yaw, tilt);
        directionX = vector3D.angleToVector(yaw + (Math.PI/2), roll);
        directionY = vector3D.getCrossProduct(direction, directionX);
        horizResolution = hr;
        vertResolution = vr;
        horizViewport = hv;
        vertViewport = vv;
    }
    
    //accessor methods
    
    public int getHorizRes()
    {
        return horizResolution;
    }
    
    public int getVertRes()
    {
        return vertResolution;
    }
    
    public vector3D getPosition()
    {
        return perspective;
    }
    
    public vector3D getDirection()
    {
        return direction;
    }
    
    public vector3D getDirectionX()
    {
        return directionX;
    }
    
    public vector3D getDirectionY()
    {
        return directionY;
    }
    
    // end accessor methods
    
    /**
     * finds the ray from the perspective point through the specified pixel
     * @param x - the column the pixel is in
     * @param y - the row the pixel is in
     * @return the ray from the perspective point and through the pixel,
     * or null if the x or y values are out of the bounds of the camera's resolution
     */
    public line3D getRayForPixel(int x, int y)
    {//check for out of bounds exceptions
        if ((x < 0 || x >= horizResolution) || (y < 0 || y >= vertResolution))
            return null;
        vector3D xComponent = vector3D.scaleVector(directionX, ((double)x - ((double)horizResolution/2))/(double)horizResolution);
        vector3D yComponent = vector3D.scaleVector(directionY, ((double)y - ((double)vertResolution/2))/(double)vertResolution);
        //combine the components and normalize
        vector3D dir = vector3D.add(direction, vector3D.add(xComponent, yComponent));
        return new line3D(perspective, dir);
    }
    
    //mutator methods
    
    /**
     * points the camera in the specified direction
     * @param tilt - if looking along the X axis, tilt is the angle between direction and the X axis in the XY plane
     * @param yaw - if looking along the X axis, yaw is the angle between direction and the X axis in the XZ plane
     * @param roll - if looking along the X axis, roll is the angle between direction and the Z axis in the YZ plane
     */
    public void pointInDirection(double tilt, double yaw, double roll)
    {
        double Tilt = tilt % (Math.PI * 2);//make the numbers clean
        double Yaw = yaw % (Math.PI * 2);
        double Roll = roll % (Math.PI * 2);
        direction = vector3D.angleToVector(Yaw, Tilt);//magic stuff
        directionX = vector3D.angleToVector(Roll, Yaw);
        directionY = vector3D.getCrossProduct(direction, directionX);
    }
    
    public void moveForward()
    {
        perspective = vector3D.add(perspective, direction);
    }
    
    public void moveBackward()
    {
        perspective = vector3D.add(perspective, vector3D.scaleVector(direction, -1));
    }
    
    public void moveUp()
    {
        perspective = vector3D.add(perspective, directionY);
    }
    
    public void moveDown()
    {
        perspective = vector3D.add(perspective, vector3D.scaleVector(directionY, -1));
    }
    
    public void moveRight()
    {
        perspective = vector3D.add(perspective, vector3D.scaleVector(directionX, -1));
    }
    
    public void moveLeft()
    {
        perspective = vector3D.add(perspective, directionX);
    }
    
    //end mutator methods
}