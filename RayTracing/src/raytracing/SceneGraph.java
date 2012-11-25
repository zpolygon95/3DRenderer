package raytracing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The object that stores all of the scene data
 * @author Zachary
 */
public class SceneGraph
{
    public ArrayList<Shape3D> nodeTree;//shapes
    public ArrayList<LightSource3D> lights;//lights --future use planned--
    private int fogDistance;//values
    private Color fogColor;
    
    public SceneGraph(ArrayList<Shape3D> shapes, ArrayList<LightSource3D> light, int d, Color c)
    {//assign proper values
        nodeTree = shapes;
        lights = light;
        if (nodeTree == null)
            nodeTree = new ArrayList<>();
        if (lights == null)
            lights = new ArrayList<>();
        fogDistance = d;
        fogColor = c;
    }
    
    //mutator methods
    
    public void addShape(Shape3D s)
    {
        nodeTree.add(s);
    }
    
    public void deleteShape(Shape3D s)
    {
        nodeTree.remove(s);
    }
    
    public void flush()
    {
        nodeTree.clear();
    }
    
    public void addLight(LightSource3D l)
    {
        lights.add(l);
    }
    
    public void deleteLight(LightSource3D l)
    {
        lights.remove(l);
    }
    
    public void flushLights()
    {
        lights.clear();
    }
    
    //end mutator methods
    
    /**
     * Creates the image data for the specified camera
     * @param camera
     * @return a BufferedImage object holding image data
     */
    public BufferedImage getImageData(Camera3D camera)
    {
        BufferedImage image = new BufferedImage(camera.getHorizRes(), camera.getVertRes(), BufferedImage.TYPE_INT_RGB);//we create the image from the specified camera resolution
        
        for (int x = 0; x < camera.getHorizRes(); x++)//horizantal loop start
        {
            for (int y = 0; y < camera.getVertRes(); y++)//vertical loop start
            {
                Line3D ray = camera.getRayForPixel(x, y);//we create the ray traveling through the specified pixel
                RayCollisionResult collision = new RayCollisionResult(new Vector3D(fogDistance, 0, 0), camera.getPosition(), fogColor);//we create the default collision result for checking against subsequent results
                
                for (Shape3D s : nodeTree)//we check all shapes in the nodeTree object against the ray
                {
                    RayCollisionResult newC = s.getRayColorandPos(ray);
                    if (newC != null)
                    {
                        if (newC.getRayLength() < collision.getRayLength())
                            collision = newC;//if the new collision result is closer than the current collision result, set current collision result to the new collision result
                    }
                }
                
                //the following code controls the illumination of the point
                
                int maxR = 0;//The maximum amounts of red, green, and blue light available in total at the selected point
                int maxG = 0;
                int maxB = 0;
                
                for (LightSource3D ls : lights)
                {
                    Color lsColor = ls.getLightColorForPoint(collision.getColisionPoint());
                    if (ls instanceof AmbientLightSource)
                    {
                        if (lsColor.getRed() > maxR)
                            maxR = lsColor.getRed();
                        if (lsColor.getGreen() > maxG)
                            maxG = lsColor.getGreen();
                        if (lsColor.getBlue() > maxB)
                            maxB = lsColor.getBlue();
                    }
                    
                    if (ls instanceof PointLightSource)
                    {
                        Vector3D lightRayDir = Vector3D.subtract(ls.getSource(), collision.getColisionPoint());
                        Line3D lightRay = new Line3D(collision.getColisionPoint(), lightRayDir);
                        RayCollisionResult obstruction = null;
                        for (Shape3D s : nodeTree)
                        {
                            obstruction = s.getRayColorandPos(lightRay);
                            if (obstruction != null)
                                break;
                        }
                        if (obstruction == null)
                        {
                            if (lsColor.getRed() > maxR)
                                maxR = lsColor.getRed();
                            if (lsColor.getGreen() > maxG)
                                maxG = lsColor.getGreen();
                            if (lsColor.getBlue() > maxB)
                                maxB = lsColor.getBlue();
                        }
                    }
                }
                
                Color totalLightColor = new Color(maxR, maxG, maxB);
                Color pointColor = LightSource3D.applyFilter(totalLightColor, collision.getColor());
                
                image.setRGB(x, y, pointColor.getRGB());
            }
        }
        
        return image;
    }
}
