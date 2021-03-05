package hotel_management;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dashboard extends JFrame
{
    private DisplayManager handle;
    //private ..
    
    public Dashboard(DisplayManager handle)
    {
        this.handle = handle;
        add_utilities();
        this.setVisible(true);
    }
    
    public void add_utilities()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) screenSize.getWidth();
        int h = (int) screenSize.getHeight();
        
        this.setLocation(new Point(0, 0));
        this.setSize(w, h);
        
        this.setTitle("Travlex");
        this.setLayout(null);
	this.setResizable(false);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String args[])
    {
        new Dashboard(new DisplayManager(new MainManager()));
    }
}