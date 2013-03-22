package tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.AmbientLightSource;
import objects.LightSource3D;
import objects.PointLightSource;
import objects.geometry.Line3D;
import objects.geometry.Shape3D;
import util.RayCollisionResult;
import util.Vector3D;

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
        {
            nodeTree = new ArrayList<>();
        }
        if (lights == null)
        {
            lights = new ArrayList<>();
        }
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
                RayCollisionResult collision = new RayCollisionResult(new Vector3D(fogDistance, 0, 0), camera.getPosition(), fogColor, null);//we create the default collision result for checking against subsequent results
                
                for (Shape3D s : nodeTree)//we check all shapes in the nodeTree object against the ray
                {
                    RayCollisionResult newC = s.getRayColorandPos(ray);
                    if (newC != null)
                    {
                        if (newC.getRayLength() < collision.getRayLength())
                        {
                            collision = newC;//if the new collision result is closer than the current collision result, set current collision result to the new collision result
                        }
                    }
                }
                
                //the following code controls the illumination of the point
                
                int maxR = 0;//The maximum amounts of red, green, and blue light available in total at the selected point
                int maxG = 0;
                int maxB = 0;
                
                for (LightSource3D ls : lights)//loop through all the light sources
                {
                    Color lsColor = ls.getLightColorForPoint(collision.getColisionPoint());
                    if (ls instanceof AmbientLightSource)//if the light is ambient, it will have no obstructions
                    {
                        if (lsColor.getRed() > maxR)
                        {
                            maxR = lsColor.getRed();
                        }
                        if (lsColor.getGreen() > maxG)
                        {
                            maxG = lsColor.getGreen();
                        }
                        if (lsColor.getBlue() > maxB)
                        {
                            maxB = lsColor.getBlue();
                        }
                    }
                    
                    if (ls instanceof PointLightSource)//if the light is point based, check for obstructions between the point being illuminated and the light source
                    {
                        Vector3D lightRayDir = Vector3D.subtract(ls.getSource(), collision.getColisionPoint());
                        Line3D lightRay = new Line3D(collision.getColisionPoint(), lightRayDir);
                        RayCollisionResult obstruction = null;
                        for (Shape3D s : nodeTree)//check for collisions along a line
                        {
                            obstruction = s.getRayColorandPos(lightRay);
                            if (obstruction != null)
                            {
                                if (obstruction.getObject().equals(collision.getObject()))
                                {
                                    obstruction = null;
                                }
                                break;
                            }
                        }
                        if (obstruction == null)//if there were no obstructions, add the light
                        {
                            if (lsColor.getRed() > maxR)
                            {
                                maxR = lsColor.getRed();
                            }
                            if (lsColor.getGreen() > maxG)
                            {
                                maxG = lsColor.getGreen();
                            }
                            if (lsColor.getBlue() > maxB)
                            {
                                maxB = lsColor.getBlue();
                            }
                        }
                    }
                }
                
                Color totalLightColor = new Color(maxR, maxG, maxB);
                Color pointColor = LightSource3D.applyFilter(totalLightColor, collision.getColor());//filter the color of the shape based on available light
                
                image.setRGB(x, y, pointColor.getRGB());
            }
        }
        
        return image;
    }
    
    public int getPixelColor(Camera3D camera, int x, int y)
    {
        Line3D ray = camera.getRayForPixel(x, y);//we create the ray traveling through the specified pixel
        RayCollisionResult collision = new RayCollisionResult(new Vector3D(fogDistance, 0, 0), camera.getPosition(), fogColor, null);//we create the default collision result for checking against subsequent results
        
        for (Shape3D s : nodeTree)//we check all shapes in the nodeTree object against the ray
        {
            RayCollisionResult newC = s.getRayColorandPos(ray);
            if (newC != null)
            {
                if (newC.getRayLength() < collision.getRayLength())
                {
                    collision = newC;//if the new collision result is closer than the current collision result, set current collision result to the new collision result
                }
            }
        }
        
        //the following code controls the illumination of the point
        if (collision != null)
        {
            int maxR = 0;//The maximum amounts of red, green, and blue light available in total at the selected point
            int maxG = 0;
            int maxB = 0;
            
            for (LightSource3D ls : lights)//loop through all the light sources
            {
                Color lsColor = ls.getLightColorForPoint(collision.getColisionPoint());
                if (ls instanceof AmbientLightSource)//if the light is ambient, it will have no obstructions
                {
                    if (lsColor.getRed() > maxR)
                    {
                        maxR = lsColor.getRed();
                    }
                    if (lsColor.getGreen() > maxG)
                    {
                        maxG = lsColor.getGreen();
                    }
                    if (lsColor.getBlue() > maxB)
                    {
                        maxB = lsColor.getBlue();
                    }
                }
                
                if (ls instanceof PointLightSource)//if the light is point based, check for obstructions between the point being illuminated and the light source
                {
                    Vector3D lightRayDir = Vector3D.subtract(ls.getSource(), collision.getColisionPoint());
                    Line3D lightRay = new Line3D(collision.getColisionPoint(), lightRayDir);
                        RayCollisionResult obstruction = null;
                        for (Shape3D s : nodeTree)//check for collisions along a line
                        {
                            obstruction = s.getRayColorandPos(lightRay);
                            
                            if (obstruction != null)
                            {
                                
                                //if (Vector3D.getDotProduct(obstruction.getObject().getNormal(), Vector3D.subtract(obstruction.getColisionPoint(), ls.getSource())) < Math.PI / 2)
                                //{
                                //       obstruction = null;
                                //}
                                //else
                                //{
                                    break;
                                //}
                            }
                        }
                        //System.out.println("==============" + x + "================" + y + "==============");
                        if (obstruction == null)//if there were no obstructions, add the light
                        {
                            if (lsColor.getRed() > maxR)
                            {
                                maxR = lsColor.getRed();
                            }
                            if (lsColor.getGreen() > maxG)
                            {
                                maxG = lsColor.getGreen();
                            }
                            if (lsColor.getBlue() > maxB)
                            {
                                maxB = lsColor.getBlue();
                            }
                        }
                    }
                }
            
            
            Color totalLightColor = new Color(maxR, maxG, maxB);
            Color pointColor = LightSource3D.applyFilter(totalLightColor, collision.getColor());//filter the color of the shape based on available light

            return pointColor.getRGB();
        }
        return fogColor.getRGB();
    }

    public BufferedImage getPolygonRenderedScene(Camera3D camera)
    {
        BufferedImage image = new BufferedImage(camera.getHorizRes(), camera.getVertRes(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        ArrayList<Integer> distances = new ArrayList();
        ArrayList<Polygon> polygons = new ArrayList();
        for (Shape3D s : nodeTree)
        {
            Vector3D[][] vertices = s.getCorners();
            if (vertices != null)
            {
                for (int x = 0; x < vertices.length; x++)
                {
                    int[] xPoints = new int[vertices[x].length];
                    int[] yPoints = new int[vertices[x].length];
                    int distSum = 0;
                    for (int y = 0; y < vertices[x].length; y++)
                    {
                        Vector3D coord = Vector3D.ZERO_VECTOR;
                        try
                        {
                            Vector3D ray = Vector3D.subtract(vertices[x][y], camera.getPosition());
                            distSum += ray.getMagnitude();
                            coord = camera.getPixelForRay(ray);
                            xPoints[y] = (int)coord.getX();
                            yPoints[y] = (int)coord.getY();
                        }
                        catch (NullPointerException e)
                        {
                            System.out.print(">>");
                            System.out.println(coord);
                            System.out.println(Vector3D.subtract(vertices[x][y], camera.getPosition()));
                        }
                    }
                    distances.add(distSum / vertices[x].length);
                    polygons.add(new Polygon(xPoints, yPoints, vertices[x].length));
                }
                polygons = sortByIntegerArray(polygons, distances);
                for (Polygon polygon : polygons)
                {
                    g.setColor(new Color(245, 245, 50));
                    g.fill(polygon);
                    g.setColor(Color.WHITE);
                    g.draw(polygon);
                }
            }
            else
            {
                //do something cool here
            }
        }
        
        return image;
    }

    private ArrayList<Polygon> sortByIntegerArray(ArrayList<Polygon> polygons, ArrayList<Integer> distances)
    {
        for (int x = polygons.size(); x > 0; x--)
        {
            for (int i = 0; i < x - 1; i++)
            {
                if (distances.get(i) < distances.get(i + 1))
                {
                    Polygon p1 = polygons.get(i);
                    Polygon p2 = polygons.get(i+1);
                    Integer i1 = distances.get(i);
                    Integer i2 = distances.get(i + 1);
                    polygons.set(i, p2);
                    polygons.set(i + 1, p1);
                    distances.set(i, i2);
                    distances.set(i + 1, i1);
                }
            }
        }
        return polygons;
    }
}
