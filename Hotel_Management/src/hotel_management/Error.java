package hotel_management;
        
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error extends JLabel
{
    public static final int ERROR_WIDTH = 300;
    public static final int ERROR_HEIGHT = 30;
    public String name;
    public String message;
    
    public Error(JPanel panel, String throw_error, Point point, boolean isGreen, String name)
    {
        this.name = name;
        this.message = throw_error;
        if(isGreen)
            this.setBackground(new Color(34, 90, 9, 170));
        else
            this.setBackground(new Color(190, 75, 73, 170));
        this.setForeground(new Color(255, 255, 255));
        this.setText("   " + this.message);
        this.setFont(new Font("Lucida fax", Font.BOLD, 12));
        this.setBounds(point.x, point.y, ERROR_WIDTH, ERROR_HEIGHT);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVisible(false);
        this.setOpaque(true);
        
        panel.add(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);  
    }
}
