package raytracing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 * the main class to get everything going. put testing things in here
 * @author Zachary
 */
public class Launch
{
    public static final AnimationPane panel = new AnimationPane();
    
    /**
     * main
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test!!!");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        //this is a change I made just now
        
        frame.addKeyListener(new KeyListener()//passes the KeyListener to the animation panel
        {

            @Override
            public void keyTyped(KeyEvent ke)
            {
                panel.keyTyped(ke);
            }

            @Override
            public void keyPressed(KeyEvent ke)
            {
                panel.keyPressed(ke);
            }

            @Override
            public void keyReleased(KeyEvent ke)
            {
                panel.keyReleased(ke);
            }
            
        });
        frame.add(panel);
        frame.setVisible(true);
    }
    
    
}
