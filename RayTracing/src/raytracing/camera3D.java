package raytracing;

/**
 * This object is used in the creation of 3D images by providing the ray objects to the scene graph
 * @author Zachary
 */
public class camera3D
{
    private vector3D perspective, direction, directionX, directionY;//orientation of the camera
    private double horizResolution, vertResolution;//resolution of the images produced
    
    public camera3D()
    {
        //starting at origin.
        perspective = new vector3D(10, 0, 0);
        
        //direction is always <1, 0, 0>
        //also direction should always have length 1.
        direction = new vector3D(1, 0, 0);
        
        directionX = new vector3D(0, 1, 0);
        directionY = new vector3D(0, 0, 1); 
                
        horizResolution = 500;
        vertResolution = 500;
    }
    
    //accessor methods
    
    public int getHorizRes()
    {
        return (int)horizResolution;
    }
    
    public int getVertRes()
    {
        return (int)vertResolution;
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
    {
        //check for out of bounds exceptions
        if ((x < 0 || x >= horizResolution) || (y < 0 || y >= vertResolution))
            return null;
        
        //x and y with a range [-1, 1]
        double xRespect = ((x-(horizResolution/2))/(horizResolution/2));
        double yRespect = ((y-(vertResolution/2))/(vertResolution/2));
        
        //combine the components and normalize
        //with the predetermined angle and perspective, we know that an pixel x-shift corresponds to a y-shift and a pixel y-shift corresponds to z-shift        
        vector3D pixelPoint = vector3D.add(vector3D.scaleVector(directionX, xRespect), vector3D.scaleVector(directionY, yRespect));
        if(pixelPoint == null)
                System.out.println("pxlpt is null");
        
        vector3D foo = new vector3D(0, 0, 0);
        if(vector3D.normalize(pixelPoint) == null)
            System.out.println("function result was null");
        
        if(foo == null)
            System.out.println("foo is null");
        
        line3D doo = new line3D(new vector3D(0, 0, 1), new vector3D(0, 1, 0));
        return doo;
    }
    
    //mutator methods
    
    /**
     * points the camera in the specified direction
     * @param tilt - if looking along the X axis, tilt is the angle between direction and the X axis in the XY plane
     * @param yaw - if looking along the X axis, yaw is the angle between direction and the X axis in the XZ plane
     * @param roll - if looking along the X axis, roll is the angle between direction and the Z axis in the YZ plane
     */
    public void rotateXY(double rads)
    {
        //recalculate direction vector.
        double lengthProj = Math.sqrt(Math.pow(this.direction.getX(), 2) + Math.pow(this.direction.getY(), 2));
        System.out.println("lengthproj = " + lengthProj);
        
        double theta;
        if(this.direction.getX() == 0)
        {
            theta = (Math.PI/2)*Math.signum(this.direction.getY());
        }
        else
        {
            theta = Math.atan(this.direction.getY()/this.direction.getX());
        }
        
        theta += rads;
        
        this.direction = new vector3D(lengthProj*Math.cos(theta), lengthProj*Math.sin(theta), this.direction.getZ());
        
        System.out.println("direction = " + direction);
        
        //this part must change to accomidate anything more complex than what exists now.
        //recalculate x direction vector in the same manner we did the direction vector.  It lies entirely in the xy plane FOR NOW, so no need to project.
        lengthProj = Math.sqrt(Math.pow(this.directionX.getX(), 2) + Math.pow(this.directionX.getY(), 2));
        if(this.directionX.getX() == 0)
        {
            theta = (Math.PI/2)*Math.signum(this.directionX.getX());
        }
        else
        {
            theta = Math.atan(this.directionX.getY()/this.directionX.getX());
        }
        
        theta += rads;
        
        this.directionX = new vector3D(lengthProj*Math.cos(theta), lengthProj*Math.sin(theta), this.directionX.getZ());
        
        System.out.println("XDir = " + directionX);
    }
    
    public void moveForward()
    {
        perspective = vector3D.add(perspective, direction);
    }
    
    public void moveBackward()
    {
        perspective = vector3D.add(perspective, vector3D.scaleVector(direction, -1));
    }
    
    public double calcTheta()
    {
        if(this.direction.getX() == 0)
        {
            return (Math.PI/2)*Math.signum(this.direction.getY());
        }
        else
        {
            return Math.atan(this.direction.getY()/this.direction.getX());
        }
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
