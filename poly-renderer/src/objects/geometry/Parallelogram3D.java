package objects.geometry;

import java.awt.Color;
import java.util.Objects;
import util.RayCollisionResult;
import util.Vector3D;

/**
 * This class defines a 2 dimensional finite plane shaped like a parallelogram, created from 2 triangles
 * @author Zachary
 */
public class Parallelogram3D extends Shape3D
{
    TriangularPlane3D t1, t2;//components
    Color[][] pixMap;
    
    /**
     * creates a new Parallelogram3D
     * @param p - the vertex of the shape
     * @param l - the first leg of the shape
     * @param l1 - the second leg of the shape
     * @param c - the color of the shape
     */
    public Parallelogram3D(Vector3D p, Vector3D l, Vector3D l1, Color c)
    {//assign proper values
        t1 = new TriangularPlane3D(p, l, l1, false, c);
        Vector3D p1 = Vector3D.add(p, Vector3D.add(l, l1));//create adjacent and opposite triangles
        Vector3D l2 = Vector3D.scaleVector(l1, -1);
        Vector3D l3 = Vector3D.scaleVector(l, -1);
        t2 = new TriangularPlane3D(p1, l2, l3, false, c);
        pixMap = new Color[1][10];//setting values for the pixmap images -- eventually there will only be a constructor to handle setting pixmap values
        
        pixMap[0][0] = Color.WHITE;
        pixMap[0][1] = Color.YELLOW.darker();
        pixMap[0][2] = Color.YELLOW.darker();
        pixMap[0][3] = Color.YELLOW.darker();
        pixMap[0][4] = Color.YELLOW.darker();
        pixMap[0][5] = Color.YELLOW.darker();
        pixMap[0][6] = Color.YELLOW.darker();
        pixMap[0][7] = Color.YELLOW.darker();
        pixMap[0][8] = Color.YELLOW.darker();
        pixMap[0][9] = Color.YELLOW.darker();
//        for (int x = 0; x < pixMap.length; x++)
//        {
//            for (int y = 0; y < pixMap[x].length; y++)
//            {
//                pixMap[x][y] = Color.BLACK;
//            }
//        }
//        for (int i = 0; i < pixMap.length; i++)//coloring the edges of the pixmap red
//        {
//            pixMap[i][0] = Color.RED;
//            pixMap[0][i] = Color.RED;
//            pixMap[i][pixMap.length - 1] = Color.RED;
//            pixMap[pixMap.length - 1][i] = Color.RED;
//        }
//        pixMap[3][2] = c;
//        pixMap[4][2] = c;
//        pixMap[5][3] = c;
//        pixMap[6][2] = c;
//        pixMap[7][2] = c;
//        pixMap[8][3] = c;
//        pixMap[8][4] = c;
//        pixMap[8][5] = c;
//        pixMap[7][6] = c;    //Heart
//        pixMap[6][7] = c;
//        pixMap[5][8] = c;
//        pixMap[4][7] = c;
//        pixMap[3][6] = c;
//        pixMap[2][5] = c;
//        pixMap[2][4] = c;
//        pixMap[2][3] = c;
        
//        pixMap[4][2] = c;  //smiley face first eye
//        pixMap[4][3] = c;
//        pixMap[4][4] = c;
//        
//        pixMap[6][2] = c; //smiley face second eye
//        pixMap[6][3] = c;
//        pixMap[6][4] = c;
//        
//        pixMap[3][6] = c;  //smiley face mouth
//        pixMap[4][6] = c;
//        pixMap[5][6] = c;
//        pixMap[6][6] = c;
//        pixMap[7][6] = c;
//        pixMap[7][7] = c;
//        pixMap[6][8] = c;
//        pixMap[5][8] = c;
//        pixMap[4][8] = c;
//        pixMap[3][7] = c;
        
//        pixMap[5][5] = c;
    }
    
    //accessor methods
    
    @Override
    public Vector3D getNormal()
    {
        return null;//no normal for collections of lesser objects
    }
    
    
    public TriangularPlane3D getT1()
    {
        return t1;
    }
    
    public TriangularPlane3D getT2()
    {
        return t2;
    }
    
    //end accessor methods
    
    /**
     * Checks both components of the shape for a collision, and returns the first one
     * @param ray - the ray to be collided
     * @return the collision result
     */
    @Override
    public RayCollisionResult getRayColorandPos(Line3D ray)
    {
        Vector3D point = getCollisionPoint(ray, 1, 10);//finds the "coordinate" on the parallelogram where the collision took place
        if (point == null || point.getZ() == 1)
        {
            return null;
        }
        int x = (int)point.getX();
        int y = (int)point.getY();
        if ((x < 0 || x >= pixMap.length) || (y < 0 || y >= pixMap[x].length))//check for those pesky out of bounds exceptions
        {
            return null;
        }
        Color c = pixMap[x][y];//set returned color to its value in the pixmap
        
        RayCollisionResult col = t1.getRayColorandPos(ray);//get other collision data
        if (col != null)
        {
            return new RayCollisionResult(col.getColisionPoint(), col.getSource(), c, t1);
        }
        col =  t2.getRayColorandPos(ray);
        if (col != null)
        {
            return new RayCollisionResult(col.getColisionPoint(), col.getSource(), c, t2);
        }
        return null;
    }
    
    /**
     * Finds the coordinate on the parallelogram where the collision took place
     * @param ray the ray to be collided
     * @param horizRes the horizontal resolution of the pixmap - will be changed
     * @param vertRes the vertical resolution of the pixmap - will be changed
     * @return a Vector3D object used as a 2D vector containing the point of collision
     */
    public Vector3D getCollisionPoint(Line3D ray, int horizRes, int vertRes)
    {
        Vector3D point = t1.getCollisionPoint(ray, horizRes, vertRes);
        if (point != null && point.getZ() == 0)
        {
            return point;
        }
        point = t2.getCollisionPoint(ray, horizRes, vertRes);
        if (point != null && point.getZ() == 0)
        {
            return new Vector3D(horizRes - point.getY() - 1, horizRes - point.getX() - 1, 0);
        }
        return t1.getCollisionPoint(ray, horizRes, vertRes);
    }
    
    /**
     * finds the points of all of the coordinates of the parallelogram
     * @return the corner points
     */
    @Override
    public Vector3D[][] getCorners()
    {
        Vector3D[][] vertices = new Vector3D[1][4];
        vertices[0][0] = t1.getVertex();
        vertices[0][1] = t1.getVertex1();
        vertices[0][2] = t2.getVertex();
        vertices[0][3] = t1.getVertex2();
        return vertices;
    }

    /**
     * creates a specific numerical code for the object, used in comparison
     * @return a numerical code used for comparison
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.t1);
        hash = 89 * hash + Objects.hashCode(this.t2);
        return hash;
    }
    
    /**
     * Compares this object to another, using hash codes
     * @param o - the object to be compared
     * @return true if the object is similar, false if not
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Parallelogram3D)
        {
            return o.hashCode() == hashCode();
        }
        return false;
    }
}
