package raytracing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 * This class is meant as a testing / debugging platform, and is temporary
 * @author Zachary
 */
class AnimationPane extends JPanel
{
    private Camera3D camera;//the camera object we use to get image data
    private double cTilt, cYaw, cRoll;//the camera orientation values -- not sure if I am using the correct terminology
    Parallelogram3D pGram1, pGram2, pGram3;//parallelograms
    Parallelopipedon pDon;//a cube
    private SceneGraph sg;//the scene graph that all shapes are contained in
    Vector3D Vert, l1, l2, l3, cPos;//some vectors used as arguments in the construction of shapes
    
    Color[] colors;//an array of colors for the cube
    
    public AnimationPane()
    {
        cTilt = cYaw = 0;//declaring a bunch of fun shapes and objects we use for testing
        cRoll = 0;
        cPos = new Vector3D(0, 2, 0);
        camera = new Camera3D(cPos, cTilt, cYaw, cRoll, 500, 500, Math.PI/4, Math.PI/4);
        
        Vert = new Vector3D(3, 1, 1);
        l1 = new Vector3D(0, 0, 1);
        l2 = new Vector3D(1, 0, 0);
        l3 = Vector3D.getCrossProduct(l1, l2);
        
        colors = new Color[6];
        colors[0] = Color.RED;
        colors[1] = Color.ORANGE;
        colors[2] = Color.YELLOW;
        colors[3] = Color.GREEN;
        colors[4] = Color.BLUE;
        colors[5] = Color.WHITE;
        
        pDon = new Parallelopipedon(Vert, l1, l2, l3, colors);
        Parallelopipedon pDon1 = new Parallelopipedon(new Vector3D(3, 1, -1), l1, l2, l3, colors);
        Parallelopipedon pDon2 = new Parallelopipedon(new Vector3D(3, 3, -1), l1, l2, l3, colors);
        Parallelopipedon pDon3 = new Parallelopipedon(new Vector3D(3, 3, 1), l1, l2, l3, colors);
        
        pGram1 = new Parallelogram3D(new Vector3D(2, 1, 1), new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), Color.LIGHT_GRAY);
        pGram2 = new Parallelogram3D(new Vector3D(3, 1, 0), new Vector3D(0, 1, 0), new Vector3D(1, 0, 0), Color.LIGHT_GRAY);
        pGram3 = new Parallelogram3D(new Vector3D(1, 1, 1), new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), Color.LIGHT_GRAY);
        
        
        GriddedPlane3D gp = new GriddedPlane3D(Vector3D.ZERO_VECTOR, new Vector3D(0, -5, 0), new Vector3D(1, 0, 0), Color.BLUE, Color.BLUE, 0.1, 1);
        
        AmbientLightSource als = new AmbientLightSource(new Color(100, 100, 100));
        DiffuseLightSource pls = new DiffuseLightSource(new Vector3D(10, 10, 10), Color.WHITE, 40);
        
        sg = new SceneGraph(null, null, 40, Color.BLACK);
        sg.addLight(als);
        sg.addLight(pls);
        sg.addShape(pDon);
        sg.addShape(pDon1);
        sg.addShape(pDon2);
        sg.addShape(pDon3);
        sg.addShape(gp);
    }
    
    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;//we create a 2d graphics context in order to draw the buffered image we get from the scene graph
        g2.drawImage(sg.getImageData(camera), null, 0, 0);
        g.setColor(Color.WHITE);
        g.drawString("Position: " + camera.getPosition(), 0, 10);//print some values on the screen
        g.drawString("Tilt: " + cTilt + "        Direction: " + camera.getDirection(), 0, 20);
        g.drawString("Yaw: " + cYaw + "        X Direction: " + camera.getDirectionX(), 0, 30);
        g.drawString("Roll: " + cRoll + "        Y Direction: " + camera.getDirectionY(), 0, 40);
        g.drawString("Dot: " + Vector3D.round(Vector3D.getDotProduct(camera.getDirection(), camera.getDirectionX()), 10), 0, 50);
    }
    
    public void keyTyped(KeyEvent ke)
    {
        //unused
    }
    
    public void keyPressed(KeyEvent ke)
    {
        switch (ke.getKeyCode())       
        {
            case KeyEvent.VK_Q:// move camera down
                camera.moveDown();
                repaint();
                break;
            case KeyEvent.VK_W:// move camera forward
                camera.moveForward();
                repaint();
                break;
            case KeyEvent.VK_E:// move camera up
                camera.moveUp();
                repaint();
                break;
            case KeyEvent.VK_A:// move camera left
                camera.moveLeft();
                repaint();
                break;
            case KeyEvent.VK_S:// move camera backward
                camera.moveBackward();
                repaint();
                break;
            case KeyEvent.VK_D:// move camera right
                camera.moveRight();
                repaint();
                break;
            case KeyEvent.VK_UP:// rotate camera up
                cTilt += Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
            case KeyEvent.VK_DOWN:// rotate camera down
                cTilt -= Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
            case KeyEvent.VK_LEFT:// rotate camera left
                cYaw -= Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
            case KeyEvent.VK_RIGHT:// rotate camera right
                cYaw += Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
            case KeyEvent.VK_PAGE_UP:// roll camera clockwise
                cRoll += Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
            case KeyEvent.VK_PAGE_DOWN:// roll camera counter-clockwise
                cRoll -= Math.PI/16;
                camera.pointInDirection(cTilt, cYaw, cRoll);
                repaint();
                break;
        }
    }
    
    public void keyReleased(KeyEvent ke)
    {
        //unused
    }
}