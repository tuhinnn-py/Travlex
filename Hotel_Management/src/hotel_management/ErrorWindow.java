package hotel_management;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class ErrorWindow extends JWindow
{
    public static final int WIDTH = 430;
    public static final int HEIGHT = 30; 
    
    public JLabel label;
    
    public ErrorWindow(String throw_error, Point p)
    {
        add_utilities(this, p);
        add_label(this, throw_error);
        this.setVisible(false);
    }
    
    public void add_label(JWindow window, String error)
    {
        label = new JLabel("   " + error);
        label.setOpaque(true);
        label.setBounds(0, 0, WIDTH, HEIGHT);
        label.setBackground(new Color(190, 75, 73));
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.white);
        this.add(label);
    }
    
    public void add_utilities(JWindow window, Point location)
    {
        this.setSize(WIDTH, HEIGHT);
        this.setLocation(location);
        this.setLayout(null);
    }
}
