package io.polygon.polyrenderer.util;

import java.awt.Color;

/**
 * Used in defining the properties of the surface of a Shape3D object
 * To be continued!
 * @author Zachary
 */
public class SurfaceColor
{
    private int reflectedRed, reflectedGreen, reflectedBlue, transparency, reflectiveness;
    
    /**
     * Constructs a new SurfaceColor object with all texture options enabled
     * @param r - the amount of red light that is reflected
     * @param g - the amount of green light that is reflected
     * @param b - the amount of blue light that is reflected
     * @param t - the amount of transparency of the object
     * @param m - the amount of reflectiveness of the object (m stands for mirror)
     */
    public SurfaceColor(int r, int g, int b, int t, int m)
    {
        if (r < 0 || r > 255)
        {
            throw new IllegalArgumentException("Red out of bounds");
        }
        reflectedRed = r;
        if (g < 0 || g > 255)
        {
            throw new IllegalArgumentException("Green out of bounds");
        }
        reflectedGreen = g;
        if (b < 0 || b > 255)
        {
            throw new IllegalArgumentException("Blue out of bounds");
        }
        reflectedBlue = b;
        if (t < 0 || t > 255)
        {
            throw new IllegalArgumentException("Transparency out of bounds");
        }
        transparency = t;
        if (m < 0 || m > 255)
        {
            throw new IllegalArgumentException("Reflectiveness out of bounds");
        }
        reflectiveness = m;
    }
    
    /**
     * Constructs a new SurfaceColor object with transparency disabled
     * @param r - the amount of red light that is reflected
     * @param g - the amount of green light that is reflected
     * @param b - the amount of blue light that is reflected
     * @param m - the amount of reflectiveness of the object
     */
    public SurfaceColor(int r, int g, int b, int m)
    {
        if (r < 0 || r > 255)
        {
            throw new IllegalArgumentException("Red out of bounds");
        }
        reflectedRed = r;
        if (g < 0 || g > 255)
        {
            throw new IllegalArgumentException("Green out of bounds");
        }
        reflectedGreen = g;
        if (b < 0 || b > 255)
        {
            throw new IllegalArgumentException("Blue out of bounds");
        }
        reflectedBlue = b;
        transparency = 0;
        if (m < 0 || m > 255)
        {
            throw new IllegalArgumentException("Reflectiveness out of bounds");
        }
        reflectiveness = m;
    }
    
    /**
     * Constructs a new SurfaceColor object with reflectiveness disabled, using java.awt.Color to facilitate this
     * @param rgbt - the Color object used to define the SurfaceColor object, using rgbt.getAlpha() as the transparency component
     */
    public SurfaceColor(Color rgbt)
    {
        reflectedRed = rgbt.getRed();
        reflectedBlue = rgbt.getBlue();
        reflectedGreen = rgbt.getGreen();
        transparency = rgbt.getAlpha();
        reflectiveness = 0;
    }
    
    /**
     * Constructs a new SurfecColor object with transparency and reflectiveness disabled
     * @param r - the amount of red light that is reflected
     * @param g - the amount of green light that is reflected
     * @param b - the amount of blue light that is reflected
     */
    public SurfaceColor(int r, int g, int b)
    {
        if (r < 0 || r > 255)
        {
            throw new IllegalArgumentException("Red out of bounds");
        }
        reflectedRed = r;
        if (g < 0 || g > 255)
        {
            throw new IllegalArgumentException("Green out of bounds");
        }
        reflectedGreen = g;
        if (b < 0 || b > 255)
        {
            throw new IllegalArgumentException("Blue out of bounds");
        }
        reflectedBlue = b;
        transparency = 0;
        reflectiveness = 0;
    }
    
    //accessor methods
    
    public int getRed()
    {return reflectedRed;}
    
    public int getGreen()
    {return reflectedGreen;}
    
    public int getBlue()
    {return reflectedBlue;}
    
    public int getTransparency()
    {return transparency;}
    
    public int getRelfectiveness()
    {return reflectiveness;}
    
    public Color getRawColor()
    {return new Color(reflectedRed, reflectedGreen, reflectedBlue);}
    
    //end accessor methods
}
