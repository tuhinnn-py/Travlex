package hotel_management;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Template extends JFrame
{
    private DisplayManager handle;
    private String location;
    
    public Template(Booking booking, String user_name, DisplayManager handle)
    {
        this.handle = handle;
        //this.location = this.getFileLocation();
        
        String hname = booking.get_hotel_name().substring(0, booking.get_hotel_name().indexOf(','));
        String hotel_n = booking.get_hotel_name();
        
        String n_rooms = String.valueOf(booking.get_rooms());
        String cap = booking.get_captcha();
        
        String c_in = booking.get_arrival();
        String c_out = booking.get_departure();
        
        String pr = String.valueOf("Rs. " + booking.get_total_price());
        
        add_utilities();
        
        JLabel hotel_logo = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File(hname + "_Logo.png"));
            Image button_img = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            hotel_logo.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        hotel_logo.setOpaque(false);
        hotel_logo.setBounds(1040, 200, 150, 100);
        this.add(hotel_logo);
        
        JLabel download = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("download.png"));
            Image button_img = img.getScaledInstance(90, 50, Image.SCALE_SMOOTH);
            download.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        download.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                screen_capture(booking);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        download.setOpaque(false);
        download.setBounds(23, 25, 90, 50);
        this.add(download);
        
        add_components(user_name, hotel_n, n_rooms, cap, c_in, c_out, pr);
        
        JLabel hotel_image = new JLabel();
        hotel_image.setBounds(20, 25, 1220, 715);
        hotel_image.setOpaque(true);
        try 
        {
            BufferedImage img = ImageIO.read(new File(hname + ".jpg"));
            Image button_img = img.getScaledInstance(1220, 715, Image.SCALE_SMOOTH);
            hotel_image.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        this.add(hotel_image);
        this.add_window_listener();
        this.setVisible(false);
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath().replace("\\", "\\\\");
        
        return path.substring(0, path.length() - 1);
    }
    
    public void add_window_listener()
    {
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                //
            }

            @Override
            public void windowClosing(WindowEvent e) {
                handle.write_data();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //
            }
            
        });
    }
    
    public void add_components(String user_name, String hotel_n, String n_rooms, String cap, String c_in, String c_out, String pr)
    {
        JLabel book_hotel = new JLabel("Book hotels");
        book_hotel.setOpaque(false);
        book_hotel.setForeground(new Color(0, 0, 0));
        book_hotel.setFont(new Font("Lucida Fax", Font.BOLD, 50));
        book_hotel.setBounds(550, 120, 400, 200);
        this.add(book_hotel);
        
        JLabel easier = new JLabel("with Travlex");
        easier.setOpaque(false);
        easier.setForeground(new Color(0, 255, 255));
        easier.setFont(new Font("Lucida Fax", Font.BOLD, 25));
        easier.setBounds(550, 170, 400, 200);
        this.add(easier);
        
        JLabel travlex = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("Logo.png"));
            Image button_img = img.getScaledInstance(300, 158, Image.SCALE_SMOOTH);
            travlex.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        travlex.setOpaque(false);
        travlex.setBounds(100, 350, 300, 158);
        this.add(travlex);

        JLabel name = new JLabel();
        name.setBounds(550, 320, 640, 25);
        name.setBackground(new Color(255, 255, 255, 150));
        name.setOpaque(true);
        name.setText(user_name);
        name.setForeground(Color.black);
        name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(name);
        
        JLabel hotel_name = new JLabel();
        hotel_name.setBounds(550, 365, 640, 25);
        hotel_name.setBackground(new Color(255, 255, 255, 150));
        hotel_name.setOpaque(true);
        hotel_name.setText(hotel_n);
        hotel_name.setForeground(Color.black);
        hotel_name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(hotel_name);
        
        JLabel rooms = new JLabel();
        rooms.setBounds(550, 410, 640, 25);
        rooms.setBackground(new Color(255, 255, 255, 150));
        rooms.setOpaque(true);
        rooms.setText(n_rooms);
        rooms.setForeground(Color.black);
        rooms.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(rooms);
        
        JLabel captcha = new JLabel();
        captcha.setBounds(550, 455, 640, 25);
        captcha.setBackground(new Color(255, 255, 255, 150));
        captcha.setOpaque(true);
        captcha.setText(cap);
        captcha.setForeground(Color.black);
        captcha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(captcha);
        
        JLabel check_in = new JLabel();
        check_in.setBounds(550, 500, 640, 25);
        check_in.setBackground(new Color(255, 255, 255, 150));
        check_in.setOpaque(true);
        check_in.setText(c_in);
        check_in.setForeground(Color.black);
        check_in.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(check_in);
        
        JLabel check_out = new JLabel();
        check_out.setBounds(550, 545, 640, 25);
        check_out.setBackground(new Color(255, 255, 255, 150));
        check_out.setOpaque(true);
        check_out.setText(c_out);
        check_out.setForeground(Color.black);
        check_out.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(check_out);
        
        JLabel price = new JLabel();
        price.setBounds(550, 590, 640, 25);
        price.setBackground(new Color(255, 255, 255, 150));
        price.setOpaque(true);
        price.setText(pr);
        price.setForeground(Color.black);
        price.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(price);
    }
    
    public void screen_capture(Booking booking)
    {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.paint(img.getGraphics());
        File outputfile = new File(booking.toString() + "saved.png");
        try
        {
            ImageIO.write(img, "png", outputfile);
        }
        catch(IOException e)
        {
            System.err.println(e + "\nScreen capture failed.");
        }
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D gr = (Graphics2D)g;
        
        gr.setColor(Color.white);
        gr.setStroke(new BasicStroke(2f));
        
        int xPoints[] = new int[]{20, 1260, 1260, 20};
        int yPoints[] = new int[]{45, 45, 780, 780};
        
        gr.drawPolygon(xPoints, yPoints, 4);
        
        int xp[] = new int[]{30, 1250, 1250, 30};
        int yp[] = new int[]{55, 55, 770, 770}; 
        
        gr.drawPolygon(xp, yp, 4);
        
        gr.setColor(new Color(255, 255, 255));
        gr.setFont(new Font("Lucida Fax", Font.BOLD, 14));
        
        gr.drawString("Name         :", 460, 367);
        gr.drawString("Hotel Name:", 460, 412);
        gr.drawString("Rooms       :", 460, 457);
        gr.drawString("Captcha     :", 460, 502);
        gr.drawString("Check-in    :", 460, 547);
        gr.drawString("Check-out  :", 460, 592);
        gr.drawString("Price          :", 460, 637);
        
        gr.setFont(new Font("Arial", Font.BOLD, 12));
        gr.setColor(new Color(255, 255, 255));
        gr.drawString("Copyright ©™ 2021 @Travlex. All rights reserved.", 955, 760);
        
        gr.drawString("For booking related enquiries, call +91 9804377263", 945, 80);
    }
    
    public void add_utilities()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screenSize.getWidth() / 2);
        int h = (int) (screenSize.getHeight() / 2);
        
        this.setLocation(new Point(w - 640, h - 400));
        this.setSize(1280, 800);
        this.getContentPane().setBackground(Color.black);
        
        this.setLayout(null);
	this.setResizable(false);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
