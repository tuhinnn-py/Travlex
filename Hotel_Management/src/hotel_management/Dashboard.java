package hotel_management;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Time;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dashboard extends JFrame
{
    private JLabel dp;
    private Player media_player;
    
    private String location;
    private JPanel content_pane;
    
    public Dashboard(DisplayManager handle)
    {
        this.location = this.getFileLocation();
        String default_url = location + "Seoul.avi";
        URL media_url = null;
        try
        {
            media_url = new URL("file:\\" + default_url);
        }
        catch(MalformedURLException e)
        {
            System.err.println("Media cannot be loaded!");
        }
        
        add_content_pane(this);
        //add_dp("src\\hotel_management\\Python\\user@tm.jpg", this, 565, 200);
        //add_book(this);
        add_utilities(this);
        add_video(this, media_url);
        this.setVisible(true);
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath().replace("\\", "\\\\");
        
        return path.substring(0, path.length() - 1);
    }
    
    public void add_content_pane(Dashboard frame)
    {
        content_pane = new JPanel();
        content_pane.setOpaque(true);
        content_pane.setBackground(new Color(0, 0, 0, 180));
        content_pane.setBounds(20, 300, 300, 440);
        frame.add(content_pane);
    }
    
    public void add_dp(String img_url, Dashboard frame, int x, int y)
    {
        Thread resize_dp = new Thread(new Runnable(){
            @Override
            public void run() {
                dp = new JLabel();
                try 
                {
                    BufferedImage img = ImageIO.read(new File(img_url + "_out.png"));
                    Image button_img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    dp.setIcon(new ImageIcon(button_img));
                    frame.repaint();
                }
                catch (IOException ex) 
                {
                    ScriptPython py = new ScriptPython(img_url, null);
                    py.run_pic_cir();
                    
                    try 
                    {
                        BufferedImage img = ImageIO.read(new File(img_url + "_out.png"));
                        Image button_img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        dp.setIcon(new ImageIcon(button_img));
                        frame.getContentPane().repaint();
                    } 
                    catch(IOException er) 
                    {
                        System.err.println(er);
                    }
                }
                
                dp.setOpaque(false);
                dp.setBounds(x, y, 100, 100);
                dp.setVisible(true);
                frame.add(dp);
            }
                    
        });
        resize_dp.start();
    }
    
    public void add_book(Dashboard frame)
    {
        JLabel book = new JLabel();
        String image_url = "saved.png";
        try 
        {
            BufferedImage img = ImageIO.read(new File(image_url));
            Image button_img = img.getScaledInstance(480, 300, Image.SCALE_SMOOTH);
            book.setIcon(new ImageIcon(button_img));
            frame.repaint();
        } 
        catch(IOException er) 
        {
            System.err.println(er);
        }
        book.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                book.setBorder(BorderFactory.createRaisedBevelBorder());
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                book.setBorder(null);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        book.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        book.setBounds(750, 400, 480, 300);
        this.add(book);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D gr = (Graphics2D)g;
        
        RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHints(rh);
        
        //gr.setStroke(new BasicStroke(1f));
        //gr.setColor(Color.black);
        //gr.drawOval(573, 230, 150, 150);
    }
    
    public void add_utilities(Dashboard frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) screenSize.getWidth();
        int h = (int) screenSize.getHeight();
        
        frame.setLocation(new Point(0, 0));
        frame.setSize(w, h);
        
        frame.setTitle("Travlex");
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void add_video(JFrame frame, URL media_url)
    {
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            media_player = Manager.createRealizedPlayer(media_url);
            Component video = media_player.getVisualComponent();
            
            if(video != null)
            {
                video.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                frame.add(video);
                media_player.addControllerListener((ControllerEvent event) -> {
                    if (event instanceof EndOfMediaEvent) {
                        media_player.setMediaTime(new Time(0));
                        media_player.start();
                    }
                });
                media_player.start(); 
            }
        }
        catch(IOException | CannotRealizeException | NoPlayerException e)
        {
            System.err.println("Media cannot be displayed!");
        }
    }
    
    public static void main(String args[])
    {
        new Dashboard(new DisplayManager(new MainManager()));
    }
}
