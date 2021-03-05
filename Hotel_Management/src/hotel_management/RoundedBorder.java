package hotel_management;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.Border;
public class RoundedBorder implements Border
{
    private int radius;
    RoundedBorder(int radius) 
    {
        this.radius = radius;
    }
    
    @Override
    public Insets getBorderInsets(Component c) 
    {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() 
    {
        return true;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
        Graphics2D gr = (Graphics2D)g;
        gr.setStroke(new BasicStroke(2f));
        gr.setColor(Color.white);
        gr.drawRoundRect(x, y, width-1, height, radius, radius);
    }
}