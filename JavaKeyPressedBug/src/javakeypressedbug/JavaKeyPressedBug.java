
package javakeypressedbug;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class JavaKeyPressedBug
{
    public static Frame f;
    
    public static void main(String[] args)
    {
        JFrame dummy=new JFrame("dummy");
        dummy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dummy.setVisible(true);
        createFrame();
    }
    
    private static void createFrame()
    {
        f = new Frame("Bug");
        replaceKeyListener();
        f.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                f.setVisible(false);
                f.dispose();
                createFrame();
            }
        });
        f.setVisible(true);
    }
    
    private static void replaceKeyListener()
    {
        KeyListener[] all=f.getKeyListeners();
        if (all.length > 0)
            f.removeKeyListener(all[0]);
        f.addKeyListener(new java.awt.event.KeyListener()
        {
            public void keyTyped(KeyEvent evt){}

            public void keyPressed(KeyEvent evt)
            {
                System.out.println(evt);
            }

            public void keyReleased(KeyEvent evt)
            {
                System.out.println(evt);
            }
        });
    }
    
}
