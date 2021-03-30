package hotel_management;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogIn extends JFrame
{
    private Player media_player;
    private JPanel content_pane;
    
    private JLabel error_message, pwd_message;
    
    private boolean started_typing, pwd_started_typing;
    private String password, email_id, uid, name_;
    
    private JTextField user_name;
    private JPasswordField pwd;
    
    private String location;
    private DisplayManager handle;
    
    public LogIn(String title, DisplayManager handle)
    {
        this.started_typing = true;
        this.pwd_started_typing = true;
        
        this.location = getFileLocation();
        add_labels(this);
        this.handle = handle;
        
        content_pane = new JPanel(){
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                Graphics2D gr = (Graphics2D)g;
                gr.setColor(new Color(245, 246, 247));
                gr.drawLine(30, 380, 150, 380);
                gr.drawLine(250, 380, 370, 380);
                gr.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
                gr.drawString("OR", 190, 385);
            }
        };
        add_panel(this, handle);
        add_utilities(this, title);
        try 
        {
            
            
            add_video((Container)this, new URL("file:\\" + location + "City.avi"), true);
        } 
        catch (MalformedURLException ex) 
        {
            System.err.println(ex + "\nMedia couldnot be loaded.");
        }
        content_pane.setVisible(true);
        add_window_listener();
        this.setVisible(true);
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
    
    public Player get_media_player()
    {
        return this.media_player;
    }
    
    public void add_labels(LogIn frame)
    {
        error_message = new JLabel(){
            @Override
            public void paint(Graphics g)
            {
                g.setColor(new Color(190, 75, 73, 120));
                g.fillRect(0, 0, 250, 40);
                int[] xPoints = new int[]{250, 280, 250};
                int[] yPoints = new int[]{0, 20, 40};
                g.fillPolygon(xPoints, yPoints, 3);
                
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.drawString("              Invalid Username", 10, 24);
            }
        };
        error_message.setBounds(500, 322, 300, 40);
        error_message.setVisible(false);
        error_message.setOpaque(false);
        frame.add(error_message);
        
        pwd_message = new JLabel(){
            @Override
            public void paint(Graphics g)
            {
                g.setColor(new Color(190, 75, 73, 120));
                g.fillRect(0, 0, 250, 40);
                int[] xPoints = new int[]{250, 280, 250};
                int[] yPoints = new int[]{0, 20, 40};
                g.fillPolygon(xPoints, yPoints, 3);
                
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.drawString("                   Wrong Password", 10, 24);
            }
        };
        pwd_message.setBounds(500, 393, 300, 40);
        pwd_message.setVisible(false);
        pwd_message.setOpaque(false);
        frame.add(pwd_message);
    }
    
    public void set_uid(String uid)
    {
        this.uid = uid;
    }
    
    public void set_pwd(String pwd)
    {
        this.password = pwd;
    }
    
    public void set_name_(String name)
    {
        this.name_ = name;
    }
    
    public void set_email_id(String mob_email)
    {
        this.email_id = mob_email;
    }
    
    public String get_username()
    {
        return this.name_;
    }
    
    public String get_email_id()
    {
        return this.email_id;
    }
    
    public String get_pwd()
    {
        return this.password;
    }
    
    public String get_uid()
    {
        return this.uid;
    }
    
    public void add_panel(LogIn frame, DisplayManager handle)
    {
        content_pane.setBounds(780, 150, 400, 500);
        content_pane.setBackground(new Color(255, 255, 255, 50));
        content_pane.setOpaque(true);
        content_pane.setLayout(null);
        
        JLabel travlex = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("Logo.png"));
            Image button_img = img.getScaledInstance(200, 115, Image.SCALE_SMOOTH);
            travlex.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        travlex.setOpaque(false);
        travlex.setBounds(100, 30, 200, 115);
        content_pane.add(travlex); 
        
        user_name = new JTextField(){
            @Override
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("Phone number, username or email address", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        user_name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        user_name.setText("Phone number, username or email address");
        user_name.setCaretColor(new Color(245, 246, 247));
        user_name.setBounds(30, 170, 340, 40);
        user_name.setFont(new Font("Arial", Font.PLAIN, 13));
        user_name.setBackground(new Color(245, 246, 247));
        user_name.setForeground(new Color(141, 148, 158));
        user_name.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(started_typing)
                {
                    started_typing = false;
                    user_name.repaint();
                    user_name.setText("");
                    user_name.setForeground(Color.black);
                    user_name.setFont(new Font("Arial", Font.BOLD, 12));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                user_name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(user_name.getText().trim().equals(""))
                {
                    user_name.setForeground(new Color(141, 148, 158));
                    user_name.setFont(new Font("Arial", Font.PLAIN, 13));
                    user_name.setText("Phone number, username or email address");
                    started_typing = true;
                    user_name.repaint();
                }
                handle.set_login_var(frame, user_name.getText());
            }
            
        });
        user_name.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(started_typing)
                {
                    started_typing = false;
                    user_name.setText("");
                    user_name.setForeground(Color.black);
                    user_name.setFont(new Font("Arial", Font.BOLD, 12));
                    
                    user_name.repaint();
                }
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(user_name.getText().trim().equals(""))
                {
                    user_name.setForeground(new Color(141, 148, 158));
                    user_name.setFont(new Font("Arial", Font.PLAIN, 13));
                    user_name.setText("Phone number, username or email address");
                    started_typing = true;
                    user_name.repaint();
                }
            }
            
        });
        content_pane.add(user_name); 
        
        JLabel eye = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("new_eye.png"));
            Image button_img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            eye.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        eye.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pwd.setEchoChar((char)0);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pwd.setEchoChar('•');
            }
            
        });
        
        eye.setBounds(320, 240, 40, 40);
        eye.setOpaque(false);
        eye.setVisible(false);
        content_pane.add(eye);
        
        
        pwd = new JPasswordField(){
            @Override
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!pwd_started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("Password", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pwd.setEchoChar((char)0);
        pwd.setText("Password");
        pwd.setCaretColor(new Color(245, 246, 247));
        pwd.setBounds(30, 240, 340, 40);
        pwd.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(pwd_started_typing)
                {
                    pwd_started_typing = false;
                    pwd.repaint();
                    pwd.setText("");
                    pwd.setEchoChar('•');
                    pwd.setForeground(Color.black);
                    pwd.setFont(new Font("Arial", Font.BOLD, 12));
                }
                
                String pass_word = pwd.getText();
                if(pass_word.trim().length() == 0)
                    eye.setVisible(false);
                else
                    eye.setVisible(true);
            }
            
        });
        pwd.setFont(new Font("Arial", Font.PLAIN, 13));
        pwd.setBackground(new Color(245, 246, 247));
        pwd.setForeground(new Color(141, 148, 158));
        pwd.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pwd_started_typing)
                {
                    pwd_started_typing = false;
                    pwd.repaint();
                    pwd.setText("");
                    pwd.setEchoChar('•');
                    pwd.setForeground(Color.black);
                    pwd.setFont(new Font("Arial", Font.BOLD, 12));
                }
                handle.set_login_var(frame, user_name.getText());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(pwd.getText().trim().equals(""))
                {
                    pwd.setEchoChar((char)0);   
                    pwd.setForeground(new Color(141, 148, 158));
                    pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    pwd.setText("Password");
                    pwd_started_typing = true;
                    pwd.repaint();
                }
                handle.set_login_var(frame, user_name.getText());
            }
            
        });
        pwd.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(pwd.getText().trim().equals(""))
                {
                    pwd.setEchoChar((char)0);   
                    pwd.setForeground(new Color(141, 148, 158));
                    pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    pwd.setText("Password");
                    pwd_started_typing = true;
                    pwd.repaint();
                }
            }
            
        });
        content_pane.add(pwd); 
        
        JLabel forgot_pwd = new JLabel("Forgot Password?");
        forgot_pwd.setHorizontalAlignment(JLabel.TRAILING);
        forgot_pwd.setForeground(new Color(0, 255, 255));
        forgot_pwd.setBackground(new Color(85, 70, 68));
        Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        forgot_pwd.setFont(new Font("Lucida Fax", Font.PLAIN, 13));
        forgot_pwd.setOpaque(true);
        forgot_pwd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgot_pwd.setBounds(272, 475, 125, 25); //780, 150, 400, 500
        forgot_pwd.setVisible(false);
        forgot_pwd.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                handle.call_forgot_password(handle);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                forgot_pwd.setFont(new Font("Lucida Fax", Font.PLAIN, 13).deriveFont(fontAttributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forgot_pwd.setFont(new Font("Lucida Fax", Font.PLAIN, 13));
            }
            
        });
        content_pane.add(forgot_pwd);
        
        JButton log_in = new JButton("Log In");
        log_in.setFont(new Font("Arial", Font.BOLD, 17));
        log_in.setFocusable(false);
        log_in.setOpaque(true);
        log_in.setForeground(new Color(255, 255, 255, 240));
        log_in.setBackground(new Color(0, 0, 0, 200));
        log_in.setBorder(null);
        log_in.setBounds(30, 310, 340, 40); 
        
        log_in.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(!started_typing && !pwd_started_typing)
                    {
                        if(password == null)
                        {
                            Thread user_thread = new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    try
                                    {
                                        error_message.setVisible(true);
                                        Thread.sleep(3000);
                                        error_message.setVisible(false);
                                    }
                                    catch(InterruptedException e)
                                    {
                                        System.err.println(e + "System interrupted !");
                                    }
                                }
                            });
                            user_thread.start();
                        }
                        else if(!password.equals(pwd.getText().trim()))
                        {
                            Thread pwd_thread = new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    try
                                    {
                                        pwd_message.setVisible(true);
                                        Thread.sleep(3000);
                                        pwd_message.setVisible(false);
                                    }
                                    catch(InterruptedException e)
                                    {
                                        System.err.println(e + "System interrupted !");
                                    }
                                }
                            });
                            pwd_thread.start();
                            forgot_pwd.setVisible(true);
                        }
                        else
                        {
                            //open dashboard !
                            handle.display_booking_page(handle);
                        }
                    }
                    else
                    {
                        Thread error_thread = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                set_blink();
                            }
                            
                        });
                        error_thread.start();
                    }
                }     
        });
        content_pane.add(log_in);
        
        JButton sign_up = new JButton("Create a New Account");
        sign_up.setFont(new Font("Arial", Font.BOLD, 17));
        sign_up.setFocusable(false);
        sign_up.setOpaque(true);
        sign_up.setForeground(new Color(255, 255, 255, 240));
        sign_up.setBackground(new Color(0, 0, 0, 180));
        sign_up.setBorder(null);
        sign_up.setBounds(30, 405, 340, 40); 
        sign_up.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    handle.call_sign_up(handle);
                }     
        });
        sign_up.addMouseListener(new MouseListener(){
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
                sign_up.setBackground(new Color(19, 17, 16));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sign_up.setBackground(new Color(19, 17, 16));
            }
            
        });
        content_pane.add(sign_up);
        
        JLabel footer = new JLabel("Copyright ©™ 2021 @Travlex. All rights reserved");
        footer.setOpaque(false);
        footer.setForeground(new Color(255, 255, 255));
        footer.setFont(new Font("Lucida Fax", Font.BOLD, 10));
        footer.setBounds(10, 645, 400, 200);
        frame.add(footer);
        
        frame.add(content_pane);
    }
    
    public void blink(JComponent component1, JComponent component2)
    {
        try
        {
            for(int i = 0; i < 5; i++)
            {
                component1.setForeground(new Color(189, 45, 45));
                if(component2 != null)
                    component2.setForeground(new Color(189, 45, 45));
                Thread.sleep(300);
                if(component2 != null)
                    component2.setForeground(new Color(141, 148, 158));
                component1.setForeground(new Color(141, 148, 158));
                Thread.sleep(100);
            }
        }
        catch(InterruptedException ex)
        {
            System.err.println(ex + "System interrupted");
        }
    }
    
    public void set_blink()
    {
        if(started_typing && !pwd_started_typing)
            blink(user_name, null);
        else if(!started_typing && pwd_started_typing)
            blink(pwd, null);
        else
            blink(user_name, pwd);
        
    }
    
    public void add_video(Container component, URL media_url, boolean isOpaque)
    {
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            media_player = Manager.createRealizedPlayer(media_url);
            Component video = media_player.getVisualComponent();
            
            if(video != null)
            {
                Dimension component_size = component.getSize();
                video.setBounds(0, 0, (int)component_size.getWidth(), (int)component_size.getHeight());
                component.add(video);
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
            System.err.println("Media could not be displayed!");
        }
    }
    
    public void add_utilities(JFrame frame, String title)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screenSize.getWidth() / 2);
        int h = (int) (screenSize.getHeight() / 2);
        
        frame.setLocation(new Point(w - 640, h - 400));
        frame.setSize(1280, 800);
        
        frame.setTitle(title);
        frame.setLayout(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}