
package javakeypressedbug;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class JavaKeyPressedBug1
{
    public static void main(String[] args)
    {
        final Frame f = new Frame("Bug");
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
        f.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                f.setVisible(false);
                f.dispose();
            }
        });
        f.setVisible(true);
    }
    
}
