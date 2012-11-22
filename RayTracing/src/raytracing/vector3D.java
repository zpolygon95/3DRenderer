package raytracing;

import java.awt.Color;

/**
 * Used in an uncountable number of vector geometry calculations
 * @author Zachary
 */
public class vector3D
{
    private double x, y, z;//components
    
    public static vector3D ZERO_VECTOR = new vector3D(0, 0, 0);//the zero vector
    
    /**
     * constructs a new 3-Dimensional vector
     * @param x - the magnitude in the horizontal direction
     * @param y - the magnitude in the vertical direction
     * @param z - the magnitude in the depth direction
     */
    public vector3D(double x, double y, double z)
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
    public static vector3D scaleVector(vector3D v, double scalar)
    {
        double newX = v.x * scalar;
        double newY = v.y * scalar;
        double newZ = v.z * scalar;
        return new vector3D(newX, newY, newZ);
    }
    
    /**
     * Returns the normalized vector of v
     * @param v the vector to be normalized
     * @return a vector with magnitude 1;
     */
    public static vector3D normalize(vector3D v)
    {
        if(v == null)
            System.out.println("helpvector3d");
        double m = v.getMagnitude();
        if (m == 0)
            return null;
        vector3D foo = scaleVector(v, 1/m);
        return foo;
    }
    
    /**
     * Performs a simple addition of two 3D vectors
     * @param v1 - The first vector in the addition
     * @param v2 - The second vector in the addition
     * @return - The sum of v1 and v2
     */
    public static vector3D add(vector3D v1, vector3D v2)
    {
        double newX = v1.x + v2.x;
        double newY = v1.y + v2.y;
        double newZ = v1.z + v2.z;
        return new vector3D(newX, newY, newZ);
    }
    
    /**
     * Performs a simple subtraction of two 3D vectors
     * @param v1 - The first vector in the subtraction
     * @param v2 - The second vector in the subtraction
     * @return - The difference between v1 and v2
     */
    public static vector3D subtract(vector3D v1, vector3D v2)
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
    public static vector3D getVectorAngle(vector3D v)
    {
        vector3D v2 = normalize(v);
        double XAngle = Math.toDegrees(Math.acos(getDotProduct(v2, new vector3D(1, 0, 0))));
        double YAngle = Math.toDegrees(Math.acos(getDotProduct(v2, new vector3D(0, 1, 0))));
        double ZAngle = Math.toDegrees(Math.acos(getDotProduct(v2, new vector3D(0, 0, 1))));
        return new vector3D(XAngle, YAngle, ZAngle);
    }
    
    /**
     * returns a vector based on 2 angles
     * 
     * --may be broken to some degree--
     * @param thetaXZ - the angle of the vector in the XZ plane
     * @param thetaXY - the angle of the vector in the XY plane
     * @return the vector
     */
    public static vector3D angleToVector(double thetaXZ, double thetaXY)
    {
        vector3D xy = new vector3D(//create a vector normal to the plane perpindicular to the XZ plane that the result vector lies in
                round(Math.cos(thetaXZ - (Math.PI/4)), 10), 0,
                round(Math.sin(thetaXZ - (Math.PI/4)), 10));
        vector3D xz = new vector3D(//create a vector normal to the plane perpindicular to the XY plane that the result vector lies in
                round(Math.cos(thetaXY + (Math.PI/4)), 10),
                round(Math.sin(thetaXY + (Math.PI/4)), 10),
                0);
        vector3D result =  normalize(getCrossProduct(xy, xz));
        if (result == null)
            System.out.println(xz + "\n" + xy);
        if (result.x == -0)// signed zero is bad for these types of things
            result.x = 0;
        if (result.y == -0)
            result.y = 0;
        if (result.z == -0)
            result.z = 0;
        if (result.equals(ZERO_VECTOR))//check that you don't mess up all the things
        {
            if (thetaXY == Math.PI/2)
                return new vector3D(0, 1, 0);
            return new vector3D(0, -1, 0);
        }
        return normalize(result);//make sure the magnitude is 1
    }
    
    /**
     * Method used to calculate the dot product of two vectors
     * @param v1 - the first vector argument
     * @param v2 - the second vector argument
     * @return - v1.getMagnitude * v2.getMagnitude * Math.cos(The angle between the vectors)
     */
    public static double getDotProduct(vector3D v1, vector3D v2)
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
    public static vector3D getCrossProduct(vector3D v1, vector3D v2)
    {
        double x = (v1.y * v2.z) - (v1.z * v2.y);
        double y = (v1.z * v2.x) - (v1.x * v2.z);
        double z = (v1.x * v2.y) - (v1.y * v2.x);
        
        return new vector3D(x, y, z);
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
                return null;
            return c2;
        }
        if (c2 == null)
        {
            if (c1 == null)
                return null;
            return c1;
        }
        vector3D c1V = new vector3D(c1.getRed(), c1.getGreen(), c1.getBlue());
        vector3D c2V = new vector3D(c2.getRed(), c2.getGreen(), c2.getBlue());
        double c1Proportion = c1.getAlpha();
        double c2Proportion = c2.getAlpha();
        double totalProportion = c1Proportion + c2Proportion;
        c1V = scaleVector(c1V, c1Proportion / totalProportion);
        c2V = scaleVector(c2V, c2Proportion / totalProportion);
        vector3D result = add(c1V, c2V);
        result = scaleVector(normalize(result), 255);
        return new Color((int)result.x, (int)result.y, (int)result.z);
    }
    
    /**
     * A magic method that rounds a double to a specified precision
     * @param value - the value to be rounded
     * @param places - the number of decimal places to round to
     * @return - the magically rounded number
     */
    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }//shamelessly stolen from stackoverflow user Jonik in thread http://stackoverflow.com/questions/2808535/round-a-double-to-2-significant-figures-after-decimal-point
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof vector3D)
            return o.hashCode() == hashCode();
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
