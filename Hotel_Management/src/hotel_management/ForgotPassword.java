package hotel_management;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ForgotPassword extends JFrame
{
    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;
    
    private Player media;
    private JPanel content_pane;
    private JPanel layered_pane;
    
    private JPasswordField pwd, cfrm_pwd;
    
    private boolean pwd_started_typing, cfrm_pwd_started_typing;
    private String g_cap;
    
    private String location;
    private DisplayManager handle;
    
    public ForgotPassword(DisplayManager handle)
    {
        this.handle = handle;
        this.pwd_started_typing = true;
        
        this.location = getFileLocation();
        this.cfrm_pwd_started_typing = true;
        
        this.g_cap = Main.generate_captcha(6);
        
        Thread captcha_thread = new Thread(new Runnable(){
            @Override
            public void run() {
                ScriptPython scriptPython = new ScriptPython(handle.get_login_handle().get_email_id(), g_cap);
                try
                {
                    Double.parseDouble(handle.get_login_handle().get_email_id());
                    scriptPython.runScript_mobile();  
                }
                catch(NumberFormatException e)
                {
                    System.err.println(e + "|" + handle.get_login_handle().get_email_id());
                    scriptPython.runScript_email();
                }
            }
        });
        captcha_thread.start();
        
        pwd = new JPasswordField(){
            @Override
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!pwd_started_typing)
                {
                    gr.setColor(Color.black);
                    gr.drawString("New password", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247, 50)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        
        cfrm_pwd = new JPasswordField(){
            @Override
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!cfrm_pwd_started_typing)
                {
                    gr.setColor(Color.black);
                    gr.drawString("Confirm password", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247, 50)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        add_utilities(this);
        add_content_pane(this, handle.get_login_handle());
        add_layered_pane(this);
        try
        {
            add_video(this, new URL("file:\\" + location + "lines.avi"));
        }
        catch(MalformedURLException e)
        {
            System.out.println("Media could not be displayed !");
        }
        this.setVisible(true);
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath().replace("\\", "\\\\");
        
        return path.substring(0, path.length() - 1);
    }
    
    public void add_layered_pane(JFrame frame)
    {
        int width_gap = 120;
        int height_gap = 50;
        
        layered_pane = new JPanel(){
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                g.drawString("Enter sent verification code :", 30, 160);
            }
        };
        layered_pane.setLayout(null);
        layered_pane.setOpaque(true);
        layered_pane.setBackground(new Color(0, 0, 0, 100));

        layered_pane.setBounds(width_gap, height_gap, WIDTH - (2 * width_gap), HEIGHT - (2 * height_gap));
        layered_pane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel travlex = new JLabel();
        travlex.setOpaque(false);
        try 
        {
            BufferedImage img = ImageIO.read(new File("Logo.png"));
            Image button_img = img.getScaledInstance(175, 100, Image.SCALE_SMOOTH);
            travlex.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        
        travlex.setBounds(113, 20, 175, 100);
        layered_pane.add(travlex);
        
        JTextField captcha = new JTextField(){
            @Override
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                gr.setColor(Color.black);
                gr.drawString("Verification code", 10, 10);
            }
        };
        captcha.setHorizontalAlignment(JTextField.TRAILING);
        captcha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        captcha.setFont(new Font("Arial", Font.ITALIC, 11));
        captcha.setText("eg : ABCD");
        captcha.setCaretColor(new Color(245, 246, 247));
        captcha.setBounds(30, 170, 340, 40);
        captcha.addKeyListener(new KeyListener(){
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
                if(captcha.getText().trim().equals(g_cap))
                {
                    layered_pane.setVisible(false);
                    pwd_started_typing = true;
                    content_pane.setVisible(true);
                }
            }
            
        });
        captcha.setBackground(new Color(245, 246, 247));
        captcha.setForeground(Color.black);
        captcha.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pwd_started_typing)
                {
                    pwd_started_typing = false;
                    captcha.repaint();
                    captcha.setText("");
                    captcha.setForeground(Color.black);
                    captcha.setFont(new Font("Arial", Font.ITALIC, 11));
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
                captcha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(captcha.getText().trim().equals(""))
                { 
                    captcha.setForeground(Color.black);
                    captcha.setFont(new Font("Arial", Font.ITALIC, 11));
                    captcha.setText("eg : ABCD");
                    pwd_started_typing = true;
                    captcha.repaint();
                }
            }
            
        });
        layered_pane.add(captcha); 
        
        frame.add(layered_pane);
    }
    
    public void add_content_pane(JFrame frame, LogIn login)
    {
        int width_gap = 120;
        int height_gap = 50;
        
        content_pane = new JPanel();
        content_pane.setLayout(null);
        content_pane.setOpaque(true);
        content_pane.setBackground(new Color(0, 0, 0, 100));

        content_pane.setBounds(width_gap, height_gap, WIDTH - (2 * width_gap), HEIGHT - (2 * height_gap));
        content_pane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel travlex = new JLabel();
        travlex.setOpaque(false);
        try 
        {
            BufferedImage img = ImageIO.read(new File("Logo.png"));
            Image button_img = img.getScaledInstance(175, 100, Image.SCALE_SMOOTH);
            travlex.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        travlex.setBounds(109, 20, 175, 100);
        content_pane.add(travlex);
        
        JLabel eye = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("eye.png"));
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
        
        eye.setBounds(320, 135, 40, 40);
        eye.setOpaque(false);
        eye.setVisible(false);
        content_pane.add(eye);
        
        pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pwd.setEchoChar((char)0);
        pwd.setText("Enter new password");
        pwd.setCaretColor(new Color(245, 246, 247));
        pwd.setBounds(30, 135, 330, 40);
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
                String pass_word = pwd.getText();
                if(pass_word.trim().length() == 0)
                    eye.setVisible(false);
                else
                    eye.setVisible(true);
            }
            
        });
        pwd.setFont(new Font("Arial", Font.PLAIN, 13));
        pwd.setBackground(new Color(245, 246, 247));
        pwd.setForeground(Color.black);
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
                    pwd.setFont(new Font("Arial", Font.PLAIN, 15));
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
                pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(pwd.getText().trim().equals(""))
                {
                    pwd.setEchoChar((char)0);   
                    pwd.setForeground(Color.black);
                    pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    pwd.setText("Enter new password");
                    pwd_started_typing = true;
                    pwd.repaint();
                }
            }
            
        });
        content_pane.add(pwd); 
        
        JLabel cfrm_eye = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("eye.png"));
            Image button_img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            cfrm_eye.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        cfrm_eye.addMouseListener(new MouseListener(){
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
                cfrm_pwd.setEchoChar((char)0);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cfrm_pwd.setEchoChar('•');
            }
            
        });
        
        cfrm_eye.setBounds(320, 195, 40, 40);
        cfrm_eye.setOpaque(false);
        cfrm_eye.setVisible(false);
        content_pane.add(cfrm_eye);
        
        cfrm_pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cfrm_pwd.setEchoChar((char)0);
        cfrm_pwd.setText("Confirm new password");
        cfrm_pwd.setCaretColor(new Color(245, 246, 247));
        cfrm_pwd.setBounds(30, 195, 330, 40);
        cfrm_pwd.addKeyListener(new KeyListener(){
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
                String pass_word = cfrm_pwd.getText();
                String word = pwd.getText();
                
                if(pass_word.trim().equals(word.trim()))
                {
                    handle.change_password(login.get_uid(), word.trim());
                    
                    frame.dispose();
                    media.stop();
                    media.close();
                    
                    login.get_media_player().start();
                    
                }
                if(pass_word.trim().length() == 0)
                    cfrm_eye.setVisible(false);
                else
                    cfrm_eye.setVisible(true);
            }
            
        });
        cfrm_pwd.setFont(new Font("Arial", Font.PLAIN, 13));
        cfrm_pwd.setBackground(new Color(245, 246, 247));
        cfrm_pwd.setForeground(Color.black);
        cfrm_pwd.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(cfrm_pwd_started_typing)
                {
                    cfrm_pwd_started_typing = false;
                    cfrm_pwd.repaint();
                    cfrm_pwd.setText("");
                    cfrm_pwd.setEchoChar('•');
                    cfrm_pwd.setForeground(Color.black);
                    cfrm_pwd.setFont(new Font("Arial", Font.PLAIN, 15));
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
                cfrm_pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(cfrm_pwd.getText().trim().equals(""))
                {
                    cfrm_pwd.setEchoChar((char)0);   
                    cfrm_pwd.setForeground(Color.black);
                    cfrm_pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    cfrm_pwd.setText("Confirm new password");
                    cfrm_pwd_started_typing = true;
                    cfrm_pwd.repaint();
                }
            }
            
        });
        content_pane.add(cfrm_pwd); 
        content_pane.setVisible(false);
        frame.add(content_pane);
    }

    public void add_video(JFrame frame, URL media_url)
    {
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            media = Manager.createRealizedPlayer(media_url);
            Component video = media.getVisualComponent();
            
            if(video != null)
            {
                Dimension frameSize = frame.getSize();
                video.setBounds(0, 0, (int)frameSize.getWidth(), (int)frameSize.getHeight());
                frame.add(video);
                media.addControllerListener((ControllerEvent event) -> {
                    if (event instanceof EndOfMediaEvent) {
                        media.setMediaTime(new Time(0));
                        media.start();
                    }
                });
                media.start(); 
            }
        }
        catch(IOException | CannotRealizeException | NoPlayerException e)
        {
            System.err.println("Media cannot be displayed!");
        }
    }
    
    public void add_utilities(JFrame frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screenSize.getWidth() / 2);
        int h = (int) (screenSize.getHeight() / 2);
        frame.setLocation(new Point((int)(w - WIDTH/2), (int)(h - HEIGHT/2)));
        frame.setLayout(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setUndecorated(true);
    }
}
