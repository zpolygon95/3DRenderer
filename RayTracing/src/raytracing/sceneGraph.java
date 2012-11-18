package raytracing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The object that stores all of the scene data
 * @author Zachary
 */
public class sceneGraph
{
    public ArrayList<shape3D> nodeTree;//shapes
    public ArrayList<lightSource3D> lights;//lights --future use planned--
    private int fogDistance;//values
    private Color fogColor, ambientLight;
    
    public sceneGraph(ArrayList<shape3D> shapes, ArrayList<lightSource3D> light, Color ambient, int d, Color c)
    {//assign proper values
        nodeTree = shapes;
        lights = light;
        if (nodeTree == null)
            nodeTree = new ArrayList<>();
        fogDistance = d;
        fogColor = c;
        ambientLight = ambient;
    }
    
    //mutator methods
    
    public void addShape(shape3D s)
    {
        nodeTree.add(s);
    }
    
    public void deleteShape(shape3D s)
    {
        nodeTree.remove(s);
    }
    
    public void flush()
    {
        nodeTree.clear();
    }
    
    //end mutator methods
    
    /**
     * Creates the image data for the specified camera
     * @param camera
     * @return a BufferedImage object holding image data
     */
    public BufferedImage getImageData(camera3D camera)
    {
        BufferedImage image = new BufferedImage(camera.getHorizRes(), camera.getVertRes(), BufferedImage.TYPE_INT_RGB);//we create the image from the specified camera resolution
        
        for (int x = 0; x < camera.getHorizRes(); x++)//horizantal loop start
        {
            for (int y = 0; y < camera.getVertRes(); y++)//vertical loop start
            {
                line3D ray = camera.getRayForPixel(x, y);//we create the ray traveling through the specified pixel
                rayCollisionResult collision = new rayCollisionResult(new vector3D(fogDistance, 0, 0), camera.getPosition(), fogColor);//we create the default collision result for checking against subsequent results
                
                for (shape3D s : nodeTree)//we check all shapes in the nodeTree object against the ray
                {
                    rayCollisionResult newC = s.getRayColorandPos(ray);
                    if (newC != null)
                    {
                        if (newC.getRayLength() < collision.getRayLength())
                            collision = newC;//if the new collision result is closer than the current collision result, set current collision result to the new collision result
                    }
                }
                
                //to be continued
//                boolean occluded = false;//occluded is the variable we use to determine if the object is in shadow
//                
//                for (lightSource3D ls : lights)
//                {
//                    
//                }
                
                image.setRGB(x, y, collision.getColor().getRGB());
            }
        }
        
        return image;
    }
}
