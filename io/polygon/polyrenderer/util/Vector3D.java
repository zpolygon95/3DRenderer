package io.polygon.polyrenderer.util;

import java.awt.Color;

/**
 * Used in an uncountable number of vector geometry calculations
 * @author Zachary
 */
public class Vector3D
{
    private double x, y, z;//components
    
    public static Vector3D ZERO_VECTOR = new Vector3D(0, 0, 0);//the zero vector
    
    /**
     * constructs a new 3-Dimensional vector
     * @param x - the magnitude in the horizontal direction
     * @param y - the magnitude in the vertical direction
     * @param z - the magnitude in the depth direction
     */
    public Vector3D(double x, double y, double z)
    {//assign appropriate values
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * accesses the specific values in this vector
     * @return - The X magnitude
     */
    public double getX()
    {return x;}
    
    /**
     * accesses the specific values in this vector
     * @return - The Y magnitude
     */
    public double getY()
    {return y;}
    
    /**
     * accesses the specific values in this vector
     * @return - The Z magnitude
     */
    public double getZ()
    {return z;}
    
    /**
     * finds the total magnitude of the vector
     * @return - the "length" of the vector
     */
    public double getMagnitude()
    {
        return Math.sqrt((x*x) + (y*y) + (z*z));
    }
    
    /**
     * Scales a vector by a certain value
     * @param v - the vector to be scaled
     * @param scalar - the value to scale by
     * @return - The scaled vector
     */
    public static Vector3D scaleVector(Vector3D v, double scalar)
    {
        double newX = v.x * scalar;
        double newY = v.y * scalar;
        double newZ = v.z * scalar;
        return new Vector3D(newX, newY, newZ);
    }
    
    /**
     * Returns the normalized vector of v
     * @param v the vector to be normalized
     * @return a vector with magnitude 1;
     */
    public static Vector3D normalize(Vector3D v)
    {
        double m = v.getMagnitude();
        if (m == 0)
        {
            return null;
        }
        return scaleVector(v, 1/m);
    }
    
    /**
     * Performs a simple addition of two 3D vectors
     * @param v1 - The first vector in the addition
     * @param v2 - The second vector in the addition
     * @return - The sum of v1 and v2
     */
    public static Vector3D add(Vector3D v1, Vector3D v2)
    {
        double newX = v1.x + v2.x;
        double newY = v1.y + v2.y;
        double newZ = v1.z + v2.z;
        return new Vector3D(newX, newY, newZ);
    }
    
    /**
     * Performs a simple subtraction of two 3D vectors
     * @param v1 - The first vector in the subtraction
     * @param v2 - The second vector in the subtraction
     * @return - The difference between v1 and v2
     */
    public static Vector3D subtract(Vector3D v1, Vector3D v2)
    {
        return add(v1, scaleVector(v2, -1));
    }
    
    /**
     * Finds the angles representing the direction of vector v.
     * 
     * Distinguishable by an X magnitude of 0
     * @param v the vector to be examined
     * @return the vector containing the two angles that represent the direction of vector v
     */
    public static Vector3D getVectorAngle(Vector3D v)
    {
        double XZAngle = Math.toDegrees(Math.acos(getDotProduct(new Vector3D(1, 0, 0), normalize(new Vector3D(v.x, 0, v.z)))));
        double XYAngle = Math.toDegrees(Math.acos(getDotProduct(new Vector3D(1, 0, 0), normalize(new Vector3D(v.x, v.y, 0)))));
        return new Vector3D(XZAngle, XYAngle, 0);
    }
    
    /**
     * Rotate a point v, around the X axis counterclockwise theta degrees, if viewed as if the axis is pointing towards the viewer
     * @param v - the position vector representing the point to be rotated
     * @param theta - the angle of rotation
     * @return The rotated point
     */
    public static Vector3D rotateVectorX(Vector3D v, double theta)
    {
        double x = v.x;
        double y = (Math.cos(theta) * v.y) + (-Math.sin(theta) * v.z);
        double z = (Math.sin(theta) * v.y) + (Math.cos(theta) * v.z);
        x = round(x, 10);
        y = round(y, 10);
        z = round(z, 10);
        return new Vector3D(x, y, z);
    }
    
    /**
     * Rotate a point v, around the Y axis counterclockwise theta degrees, if viewed as if the axis is pointing towards the viewer
     * @param v - the position vector representing the point to be rotated
     * @param theta - the angle of rotation
     * @return The rotated point
     */
    public static Vector3D rotateVectorY(Vector3D v, double theta)
    {
        double x = (Math.cos(theta) * v.x) + (Math.sin(theta) * v.z);
        double y = v.y;
        double z = (-Math.sin(theta) * v.x) + (Math.cos(theta) * v.z);
        x = round(x, 10);
        y = round(y, 10);
        z = round(z, 10);
        return new Vector3D(x, y, z);
    }
    
    /**
     * Rotate a point v, around the Z axis counterclockwise theta degrees, if viewed as if the axis is pointing towards the viewer
     * @param v - the position vector representing the point to be rotated
     * @param theta - the angle of rotation
     * @return The rotated point
     */
    public static Vector3D rotateVectorZ(Vector3D v, double theta)
    {
        double x = (Math.cos(theta) * v.x) + (-Math.sin(theta) * v.y);
        double y = (Math.sin(theta) * v.x) + (Math.cos(theta) * v.y);
        double z = v.z;
        x = round(x, 10);
        y = round(y, 10);
        z = round(z, 10);
        return new Vector3D(x, y, z);
    }
    
    /**
     * Rotate a point v, around an axis r, counterclockwise theta degrees, if viewed as if the axis is pointing towards the viewer
     * @param r
     * @param v
     * @param theta
     * @return 
     */
    public static Vector3D rotateCustom(Vector3D r, Vector3D v, double theta)
    {
        double cosx = Math.cos(theta);//define commonly used values
        double sinx = Math.cos(theta);
        double cosx1 = 1 - cosx;
        
        double a = cosx + ((r.x * r.x) * cosx1);//define all the components of the rotation matrix
        double b = (r.x * r.y * cosx1) - (r.z * sinx);
        double c = (r.x * r.z * cosx1) - (r.x * sinx);
        double d = (r.y * r.x * cosx1) + (r.z * sinx);
        double e = cosx + ((r.y * r.y) * cosx1);
        double f = (r.y * r.z * cosx1) - (r.x * sinx);
        double g = (r.z * r.x * cosx1) - (r.y * sinx);
        double h = (r.z * r.y * cosx1) + (r.x * sinx);
        double i = cosx + ((r.z * r.z) * cosx1);
        
        double x = (a * v.x) + (b * v.y) + (c * v.z);//multiply the rotation matrix by the vector
        double y = (d * v.x) + (e * v.y) + (f * v.z);
        double z = (g * v.x) + (h * v.y) + (i * v.z);
        
        return new Vector3D(x, y, z);
    }
    
    /**
     * returns a vector based on 2 angles
     * 
     * --may be broken to some degree--
     * @param thetaXZ - the angle of the vector in the XZ plane
     * @param thetaXY - the angle of the vector in the XY plane
     * @return the vector
     */
    public static Vector3D angleToVector(double thetaXZ, double thetaXY)
    {
        Vector3D xy = new Vector3D(//create a vector normal to the plane perpindicular to the XZ plane that the result vector lies in
                round(Math.cos(thetaXZ - (Math.PI/4)), 10), 0,
                round(Math.sin(thetaXZ - (Math.PI/4)), 10));
        Vector3D xz = new Vector3D(//create a vector normal to the plane perpindicular to the XY plane that the result vector lies in
                round(Math.cos(thetaXY + (Math.PI/4)), 10),
                round(Math.sin(thetaXY + (Math.PI/4)), 10),
                0);
        Vector3D result =  normalize(getCrossProduct(xy, xz));
        if (result == null)
        {
            System.out.println(xz + "\n" + xy);
        }
        if (result.x == -0)// signed zero is bad for these types of things
        {
            result.x = 0;
        }
        if (result.y == -0)
        {
            result.y = 0;
        }
        if (result.z == -0)
        {
            result.z = 0;
        }
        if (result.equals(ZERO_VECTOR))//check that you don't mess up all the things
        {
            if (thetaXY == Math.PI/2)
            {
                return new Vector3D(0, 1, 0);
            }
            return new Vector3D(0, -1, 0);
        }
        return normalize(result);//make sure the magnitude is 1
    }
    
    /**
     * Method used to calculate the dot product of two vectors
     * @param v1 - the first vector argument
     * @param v2 - the second vector argument
     * @return - v1.getMagnitude * v2.getMagnitude * Math.cos(The angle between the vectors)
     */
    public static double getDotProduct(Vector3D v1, Vector3D v2)
    {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }
    
    /**
     * Method used to calculate the cross product of two vectors.
     * 
     * beware of mistyped variables
     * @param v1 - the first vector argument
     * @param v2 - the second vector argument
     * @return the result of the cross product in right-handed space
     */
    public static Vector3D getCrossProduct(Vector3D v1, Vector3D v2)
    {
        double x = (v1.y * v2.z) - (v1.z * v2.y);
        double y = (v1.z * v2.x) - (v1.x * v2.z);
        double z = (v1.x * v2.y) - (v1.y * v2.x);
        
        return new Vector3D(x, y, z);
    }
    
    /**
     * This method adds colors together in respect to their alpha values
     * 
     * to be used in future lighting effects
     * @param c1 - the first color to be added
     * @param c2 - the seconds color to be added
     * @return the result of the addition
     */
    public static Color addColors(Color c1, Color c2)
    {
        if (c1 == null)
        {
            if (c2 == null)
            {
                return null;
            }
            return c2;
        }
        if (c2 == null)
        {
            if (c1 == null)
            {
                return null;
            }
            return c1;
        }
        Vector3D c1V = new Vector3D(c1.getRed(), c1.getGreen(), c1.getBlue());
        Vector3D c2V = new Vector3D(c2.getRed(), c2.getGreen(), c2.getBlue());
        double c1Proportion = c1.getAlpha();
        double c2Proportion = c2.getAlpha();
        double totalProportion = c1Proportion + c2Proportion;
        c1V = scaleVector(c1V, c1Proportion / totalProportion);
        c2V = scaleVector(c2V, c2Proportion / totalProportion);
        Vector3D result = add(c1V, c2V);
        result = scaleVector(normalize(result), 255);
        return new Color((int)result.x, (int)result.y, (int)result.z);
    }
    
    /**
     * Scales a color between to extremes based on a scalar value
     * @param high - the high extreme color
     * @param low - the low extreme color
     * @param scalar - distance between the two extremes in a range from 0 to 1
     * @return 
     */
    public static Color scaleColor(Color high, Color low, double scalar)
    {
        if (scalar < 0 || scalar > 1)
        {
            throw new IllegalArgumentException("Scalar must be between 0 and 1. Scalar = " + scalar);
        }
        
        double r = (low.getRed() + ((double)(high.getRed() - low.getRed()) * scalar));
        double g = (low.getGreen() + ((double)(high.getGreen() - low.getGreen()) * scalar));
        double b = (low.getBlue() + ((double)(high.getBlue() - low.getBlue()) * scalar));
        
        int R = (int)Math.round(r);
        int G = (int)Math.round(g);
        int B = (int)Math.round(b);
        
        return new Color(R, G, B);
    }
    
    /**
     * A magic method that rounds a double to a specified precision
     * @param value - the value to be rounded
     * @param places - the number of decimal places to round to
     * @return - the magically rounded number
     */
    public static double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }//shamelessly stolen from stackoverflow user Jonik in thread http://stackoverflow.com/questions/2808535/round-a-double-to-2-significant-figures-after-decimal-point
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Vector3D)
        {
            return o.hashCode() == hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    @Override
    public String toString()
    {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
