/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracing;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Depreciated
 * 
 * BufferedImage is now used
 * @author Zachary
 */
public class AnimationFrame
{
    Color[][] pixels;
    int height, width, xPos, yPos;
    
    public AnimationFrame(Color[][] p, int x, int y)
    {
        pixels = p;
        height = p.length;
        width = p[0].length;
        xPos = x;
        yPos = y;
    }
    
    public void paint(Graphics2D g)
    {
        long s = System.currentTimeMillis();
        for (int x = 0; x < width; x++)//kludge
        {
            for (int y = 0; y < height; y++)
            {
                g.setColor(pixels[x][y]);
                g.drawRect(x + xPos, y + yPos, 1, 1);
            }
        }
        System.out.println(System.currentTimeMillis() - s);
    }
}
