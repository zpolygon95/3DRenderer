package io.polygon.polyrenderer.tools;

import java.awt.Color;
import io.polygon.polyrenderer.objects.geometry.Line3D;
import io.polygon.polyrenderer.objects.geometry.Parallelogram3D;
import io.polygon.polyrenderer.util.Vector3D;

/**
 * This object is used in the creation of 3D images by providing the ray objects to the scene graph
 * @author Zachary
 */
public class Camera3D
{
    private Vector3D perspective, direction, directionX, directionY;//orientation of the camera
    private int horizResolution, vertResolution;//resolution of the images produced
    private double horizViewport, vertViewport;//angles of the viewport of the camera
    private Parallelogram3D projectionFace;
    
    /**
     * creates a new Camera3D object using vectors as orientation arguments
     * @param p - the position of the camera
     * @param d - the direction the camera is facing
     * @param dx - the horizontal direction of the camera
     * @param hr - the horizontal resolution of the camera
     * @param vr - the vertical resolution of the camera
     * @param hv - the horizontal viewport angle of the camera
     * @param vv - the vertical viewport angle of the camera
     */
    public Camera3D(Vector3D p, Vector3D d, Vector3D dx, int hr, int vr, double hv, double vv)
    {//assign the appropriate values
        perspective = p;
        direction = d;
        directionX = dx;
        directionY = Vector3D.getCrossProduct(direction, directionX);
        horizResolution = hr;
        vertResolution = vr;
        horizViewport = hv;
        vertViewport = vv;
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    /**
     * creates a new Camera3D object using angles as orientation arguments
     * @param p - the position of the camera
     * @param tilt - if looking along the X axis, tilt is the angle between direction and the X axis in the XY plane
     * @param yaw - if looking along the X axis, yaw is the angle between direction and the X axis in the XZ plane
     * @param roll - if looking along the X axis, roll is the angle between direction and the Z axis in the YZ plane
     * @param hr - horizontal resolution of the camera
     * @param vr - the vertical resolution of the camera
     * @param hv - the horizontal viewport angle of the camera
     * @param vv - the vertical viewport angle of the camera
     */
    public Camera3D(Vector3D p, double tilt, double yaw, double roll, int hr, int vr, double hv, double vv)
    {//assign the proper values
        perspective = p;
        direction = Vector3D.angleToVector(yaw, tilt);
        directionX = Vector3D.angleToVector(yaw + (Math.PI/2), roll);
        directionY = Vector3D.getCrossProduct(direction, directionX);
        horizResolution = hr;
        vertResolution = vr;
        horizViewport = hv;
        vertViewport = vv;
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
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
    
    public Vector3D getPosition()
    {
        return perspective;
    }
    
    public Vector3D getDirection()
    {
        return direction;
    }
    
    public Vector3D getDirectionX()
    {
        return directionX;
    }
    
    public Vector3D getDirectionY()
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
    public Line3D getRayForPixel(int x, int y)
    {//check for out of bounds exceptions
        if ((x < 0 || x >= horizResolution) || (y < 0 || y >= vertResolution))
        {
            return null;
        }
        Vector3D xComponent = Vector3D.scaleVector(directionX, ((double)x - ((double)horizResolution/2))/(double)horizResolution);
        Vector3D yComponent = Vector3D.scaleVector(directionY, ((double)y - ((double)vertResolution/2))/(double)vertResolution);
        //combine the components and normalize
        Vector3D dir = Vector3D.add(direction, Vector3D.add(xComponent, yComponent));
        return new Line3D(perspective, dir);
    }
    
    /**
     * This method will eventually find the pixel that the specified ray runs through, if any
     * @param ray the ray to be checked, position is assumed to be the perspective of the camera
     * @return A Vector3D object representing a 2D point by ignoring the Z dimension
     */
    public Vector3D getPixelForRay (Vector3D ray)
    {
        return projectionFace.getCollisionPoint(new Line3D(perspective, ray), horizResolution, vertResolution);
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
        direction = Vector3D.angleToVector(Yaw, Tilt);//magic stuff
        directionX = Vector3D.angleToVector(Roll, Yaw);
        directionY = Vector3D.getCrossProduct(direction, directionX);
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void rotate(double x, double y, double z)
    {
        direction = Vector3D.rotateVectorX(direction, x);
        direction = Vector3D.rotateVectorY(direction, y);
        direction = Vector3D.rotateVectorZ(direction, z);
        
        directionX = Vector3D.rotateVectorX(directionX, x);
        directionX = Vector3D.rotateVectorY(directionX, y);
        directionX = Vector3D.rotateVectorZ(directionX, z);
        
        directionY = Vector3D.rotateVectorX(directionY, x);
        directionY = Vector3D.rotateVectorY(directionY, y);
        directionY = Vector3D.rotateVectorZ(directionY, z);
        
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveForward()
    {
        perspective = Vector3D.add(perspective, direction);
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveBackward()
    {
        perspective = Vector3D.add(perspective, Vector3D.scaleVector(direction, -1));
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveUp()
    {
        perspective = Vector3D.add(perspective, directionY);
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveDown()
    {
        perspective = Vector3D.add(perspective, Vector3D.scaleVector(directionY, -1));
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveRight()
    {
        perspective = Vector3D.add(perspective, Vector3D.scaleVector(directionX, -1));
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    public void moveLeft()
    {
        perspective = Vector3D.add(perspective, directionX);
        Vector3D vertex = Vector3D.add(perspective, Vector3D.add(direction, Vector3D.add(Vector3D.scaleVector(directionX, -1), Vector3D.scaleVector(directionY, -1))));
        projectionFace = new Parallelogram3D(vertex, Vector3D.scaleVector(directionY, 2), Vector3D.scaleVector(directionX, 2), Color.BLACK);
    }
    
    //end mutator methods
}
