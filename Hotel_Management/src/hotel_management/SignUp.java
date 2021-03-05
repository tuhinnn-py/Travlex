package hotel_management;

import java.awt.Color;
import java.awt.Component;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Time;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

public class SignUp extends JFrame
{
    private static final int WIDTH = 442;
    private static final int HEIGHT = 470;
    
    private JPanel content_pane;
    private JPanel layered_pane;
    
    private boolean started_typing, last_started_typing, mob_started_typing;
    private boolean pwd_started_typing, cfrm_pwd_started_typing;
    
    private Player media;
    private DisplayManager handle;
    
    private JLabel[] error_labels;
    private HashMap<JLabel, ErrorWindow> l_win;
    
    private String location;
    private JPasswordField cfrm_pwd;
    
    public SignUp(DisplayManager handle)
    {
        this.handle = handle;
        this.cfrm_pwd = null;
        
        this.error_labels = new JLabel[5];
        this.l_win = new HashMap<>();
        
        this.started_typing = true;
        this.last_started_typing = true;
        
        this.mob_started_typing = true;
        this.pwd_started_typing = true;
        
        this.location = this.getFileLocation();
        this.cfrm_pwd_started_typing = true;
        
        add_utilities(this);
        add_panel(this);
        add_labels();
        add_components(this, handle.get_login_handle());
        content_pane.setVisible(true);
        
        try
        {
            add_video(content_pane, new URL("file:\\" + location + "New_Media.avi"));
        }
        catch(MalformedURLException e){
            System.err.println(e);
        }
        this.setVisible(false);
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath().replace("\\", "\\\\");
        
        return path.substring(0, path.length() - 1);
    }
    
    public void add_labels()
    {
        error_labels[0] = get_label(new Point(162, 108));
        l_win.put(error_labels[0], new ErrorWindow("First name is a compulsary field", new Point(this.getX() + 190, this.getY() + 104)));
        
        error_labels[1] = get_label(new Point(378, 108));
        l_win.put(error_labels[1], new ErrorWindow("Surname is a compulsary field", new Point(this.getX() + 410, this.getY() + 104)));
        
        error_labels[2] = get_label(new Point(378, 164));
        l_win.put(error_labels[2], new ErrorWindow("Please enter a 10 digit mobile number or a valid email address", new Point(this.getX() + 410, this.getY() + 159)));
        
        error_labels[3] = get_label(new Point(378, 217));
        l_win.put(error_labels[3], new ErrorWindow("Password should atleast be 4 characters long", new Point(this.getX() + 410, this.getY() + 216)));
        
        error_labels[4] = get_label(new Point(378, 274));
        l_win.put(error_labels[4], new ErrorWindow("Passwords donot match", new Point(this.getX() + 410, this.getY() + 271)));
        
        for(JLabel label : error_labels)
            content_pane.add(label);
    }
    
    public JLabel get_label(Point location)
    {
        JLabel label = new JLabel();
        label.setOpaque(false);
        label.setBounds(location.x, location.y, 22, 22);
        try 
        {
            BufferedImage img = ImageIO.read(new File("error.png"));
            Image button_img = img.getScaledInstance(22, 22, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        label.addMouseListener(new MouseListener(){
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
                l_win.get(label).setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                l_win.get(label).setVisible(false);
            }
            
        });
        
        label.setVisible(false);
        return label;
    }
    
    public void add_video(JPanel frame, URL media_url)
    {
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            media = Manager.createRealizedPlayer(media_url);
            Component video = media.getVisualComponent();
            
            if(video != null)
            {
                Dimension frameSize = frame.getSize();
                video.setBounds(0, 85, (int)frameSize.getWidth(), (int)frameSize.getHeight() - 145);
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
    
    public void add_components(JFrame frame, LogIn calling_frame)
    {
        JLabel close = new JLabel();
        close.setOpaque(false);
        try
        {
            BufferedImage img = ImageIO.read(new File("cross.png"));
            Image cross_img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            close.setIcon(new ImageIcon(cross_img));
        }
        catch(IOException e)
        {
            System.err.println(e + "Image could not be loaded");
        }
        close.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                for(JLabel label : error_labels)
                        l_win.get(label).dispose();
                
                frame.dispose();
                media.close();
                calling_frame.get_media_player().start();
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
                //  
            }
            
        });
        close.setBounds(410, 10, 20, 20);
        close.setOpaque(false);
        content_pane.add(close);
        
        JLabel sign = new JLabel();
        sign.setText("Sign Up");
        sign.setFont(new Font("Arial", Font.BOLD, 35));
        sign.setOpaque(false);
        sign.setForeground(Color.white);
        sign.setBounds(15, 0, 200, 60);
        content_pane.add(sign);
        
        JTextField first = new JTextField(){
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("First Name", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        first.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        first.setText("First Name");
        first.setCaretColor(new Color(245, 246, 247));
        first.setBounds(22, 100, 165, 40);
        first.setFont(new Font("Arial", Font.PLAIN, 13));
        first.setBackground(new Color(245, 246, 247));
        first.setForeground(new Color(141, 148, 158));
        first.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(started_typing)
                {
                    started_typing = false;
                    first.setText("");
                    first.setForeground(Color.black);
                    first.setFont(new Font("Arial", Font.BOLD, 12));
                    first.repaint();
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
                first.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(first.getText().trim().equals(""))
                {
                    first.setForeground(new Color(141, 148, 158));
                    first.setFont(new Font("Arial", Font.PLAIN, 13));
                    first.setText("First Name");
                    started_typing = true;
                    first.repaint();
                }
            }
            
        });
        first.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(started_typing)
                {
                    started_typing = false;
                    first.setText("");
                    first.setForeground(Color.black);
                    first.setFont(new Font("Arial", Font.BOLD, 12));
                    first.repaint();
                }
                
                if(first.getText().trim().equals(""))
                {
                    error_labels[0].setVisible(true);
                }
                else
                {
                    error_labels[0].setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        });
        content_pane.add(first);
        
        JTextField mob = new JTextField(){
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!mob_started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("Mobile number or email address", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        mob.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mob.setText("Mobile number or email address");
        mob.setCaretColor(new Color(245, 246, 247));
        mob.setBounds(22, 155, 397, 40);
        mob.setFont(new Font("Arial", Font.PLAIN, 13));
        mob.setBackground(new Color(245, 246, 247));
        mob.setForeground(new Color(141, 148, 158));
        mob.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mob_started_typing)
                {
                    mob_started_typing = false;
                    mob.setText("");
                    mob.setForeground(Color.black);
                    mob.setFont(new Font("Arial", Font.BOLD, 12));
                    mob.repaint();
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
                mob.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(mob.getText().trim().equals(""))
                {
                    mob.setForeground(new Color(141, 148, 158));
                    mob.setFont(new Font("Arial", Font.PLAIN, 13));
                    mob.setText("Mobile number or email address");
                    mob_started_typing = true;
                    mob.repaint();
                }
            }
            
        });
        mob.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(mob_started_typing)
                {
                    mob_started_typing = false;
                    mob.setText("");
                    mob.setForeground(Color.black);
                    mob.setFont(new Font("Arial", Font.BOLD, 12));
                    mob.repaint();
                }
                
                l_win.get(error_labels[2]).label.setText("   Please enter a 10 digit mobile number or a valid email address");
                try
                {
                    Long.parseLong(mob.getText().trim());
                    if(mob.getText().trim().equals("") || (mob.getText().trim().length() != 10))
                        error_labels[2].setVisible(true);
                    else
                        error_labels[2].setVisible(false);
                }
                catch(NumberFormatException ex)
                {
                    String regex = "^(.+)@(.+)$";
                    Pattern email = Pattern.compile(regex);
                    boolean does_match = email.matcher(mob.getText().trim()).matches();
                    if(does_match)
                        error_labels[2].setVisible(false);
                    else
                        error_labels[2].setVisible(true);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        });
        content_pane.add(mob);
        
        JPasswordField pwd = new JPasswordField(){
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!pwd_started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("New Password", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        pwd.setEchoChar((char)0);
        pwd.setText("New Password");
        pwd.setCaretColor(new Color(245, 246, 247));
        pwd.setBounds(22, 210, 397, 40);
        pwd.setFont(new Font("Arial", Font.PLAIN, 13));
        pwd.setBackground(new Color(245, 246, 247));
        pwd.setForeground(new Color(141, 148, 158));
        pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pwd.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pwd_started_typing)
                {
                    pwd_started_typing = false;
                    pwd.setText("");
                    pwd.setEchoChar('•');
                    pwd.setForeground(Color.black);
                    pwd.setFont(new Font("Arial", Font.BOLD, 12));
                    pwd.repaint();
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
                    pwd.setForeground(new Color(141, 148, 158));
                    pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    pwd.setText("New password");
                    pwd_started_typing = true;
                    pwd.repaint();
                }
            }
            
        });
        pwd.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(pwd_started_typing)
                {
                    pwd_started_typing = false;
                    pwd.setText("");
                    pwd.setEchoChar('•');
                    pwd.setForeground(Color.black);
                    pwd.setFont(new Font("Arial", Font.BOLD, 12));
                    pwd.repaint();
                }
                
                if(!cfrm_pwd_started_typing && !(cfrm_pwd.getText().trim().equals(pwd.getText().trim())))
                    error_labels[4].setVisible(true);
                else
                    error_labels[4].setVisible(false);
                if(pwd.getText().trim().length() < 4)
                {
                    error_labels[3].setVisible(true);
                }
                else
                {
                    error_labels[3].setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        });
        content_pane.add(pwd);
        
        cfrm_pwd = new JPasswordField(){
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!cfrm_pwd_started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("Confirm Password", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        cfrm_pwd.setEchoChar((char)0);
        cfrm_pwd.setText("Confirm password");
        cfrm_pwd.setCaretColor(new Color(245, 246, 247));
        cfrm_pwd.setBounds(22, 267, 397, 40);
        cfrm_pwd.setFont(new Font("Arial", Font.PLAIN, 13));
        cfrm_pwd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cfrm_pwd.setBackground(new Color(245, 246, 247));
        cfrm_pwd.setForeground(new Color(141, 148, 158));
        cfrm_pwd.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(cfrm_pwd_started_typing)
                {
                    cfrm_pwd_started_typing = false;
                    cfrm_pwd.setText("");
                    cfrm_pwd.setEchoChar('•');
                    cfrm_pwd.setForeground(Color.black);
                    cfrm_pwd.setFont(new Font("Arial", Font.BOLD, 12));
                    cfrm_pwd.repaint();
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
                    cfrm_pwd.setForeground(new Color(141, 148, 158));
                    cfrm_pwd.setFont(new Font("Arial", Font.PLAIN, 13));
                    cfrm_pwd.setText("Confirm password");
                    cfrm_pwd_started_typing = true;
                    cfrm_pwd.repaint();
                }
            }
            
        });
        cfrm_pwd.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(cfrm_pwd_started_typing)
                {
                    cfrm_pwd_started_typing = false;
                    cfrm_pwd.setText("");
                    cfrm_pwd.setEchoChar('•');
                    cfrm_pwd.setForeground(Color.black);
                    cfrm_pwd.setFont(new Font("Arial", Font.BOLD, 12));
                    cfrm_pwd.repaint();
                }
                
                if(cfrm_pwd.getText().trim().equals("") || !(cfrm_pwd.getText().trim().equals(pwd.getText().trim())))
                {
                    error_labels[4].setVisible(true);
                }
                else
                {
                    error_labels[4].setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        });
        content_pane.add(cfrm_pwd);
        
        JTextField last = new JTextField(){
            public void paint(Graphics gr)
            {
                super.paint(gr);
                gr.setFont(new Font("Arial", Font.PLAIN, 9));
                if(!last_started_typing)
                {
                    gr.setColor(new Color(141, 148, 158));
                    gr.drawString("Surname", 10, 10);
                }
                else
                {
                    gr.setColor((new Color(245, 246, 247)));
                    gr.fillRect(0, 0, 300, 9);
                }
            }
        };
        last.setText("Surname");
        last.setCaretColor(new Color(245, 246, 247));
        last.setBounds(230, 100, 189, 40);
        last.setFont(new Font("Arial", Font.PLAIN, 14));
        last.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        last.setBackground(new Color(245, 246, 247));
        last.setForeground(new Color(141, 148, 158));
        last.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(last_started_typing)
                {
                    last_started_typing = false;
                    last.setText(" ");
                    last.setForeground(Color.black);
                    last.setFont(new Font("Arial", Font.BOLD, 12));
                    last.repaint();
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
                last.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                if(last.getText().trim().equals(""))
                {
                    last.setForeground(new Color(141, 148, 158));
                    last.setFont(new Font("Arial", Font.PLAIN, 14));
                    last.setText("Surname");
                    last_started_typing = true;
                    last.repaint();
                }
            }
            
        });
        last.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(last_started_typing)
                {
                    last_started_typing = false;
                    last.setText(" ");
                    last.setForeground(Color.black);
                    last.setFont(new Font("Arial", Font.BOLD, 12));
                    last.repaint();
                }
                
                if(last.getText().trim().equals(""))
                {
                    error_labels[1].setVisible(true);
                }
                else
                {
                    error_labels[1].setVisible(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
        });
        content_pane.add(last);
        
        JRadioButton male = new JRadioButton("Male             ");
        male.setFont(new Font("Lucia Fax", Font.BOLD, 15));
        male.setHorizontalTextPosition(JRadioButton.LEADING);
        male.setForeground(Color.white);
        male.setBounds(25, 382, 114, 22);
        male.setFocusable(false);
        male.setOpaque(false);
        male.setSelected(true);
        content_pane.add(male);
        
        JRadioButton female = new JRadioButton("Female         ");
        female.setFont(new Font("Lucia Fax", Font.BOLD, 15));
        female.setForeground(Color.white);
        female.setBounds(162, 382, 114, 22);
        female.setHorizontalTextPosition(JRadioButton.LEADING);
        female.setFocusable(false);
        female.setOpaque(false);
        content_pane.add(female);
        
        JRadioButton custom = new JRadioButton("Custom       ");
        custom.setFont(new Font("Lucia Fax", Font.BOLD, 15));
        custom.setForeground(Color.white);
        custom.setHorizontalTextPosition(JRadioButton.LEADING);
        custom.setBounds(305, 382, 114, 22);
        custom.setFocusable(false);
        custom.setOpaque(false);
        content_pane.add(custom);
        
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.add(custom);
        
        DateTime dob = new DateTime();
        String[] years = new String[62];
        for(int i = 1960; i <= 2021; i++)
            years[i - 1960] = String.valueOf(i);
        dob.add_date(content_pane, years, new Point(22, 335), new Point(160, 335), new Point(300, 335), new Dimension(120, 25), new Dimension(120, 25), new Dimension(120, 25), ""); 
    
        JButton sign_up = new JButton("Sign Up");
        sign_up.setFocusable(false);
        sign_up.setBorder(null);
        sign_up.setFont(new Font("Arial", Font.BOLD, 15));
        sign_up.setForeground(Color.white);
        sign_up.setBackground(new Color(54, 164, 32));
        sign_up.setBounds(285, 418, 134, 24);
        sign_up.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean erronneous = started_typing || last_started_typing || mob_started_typing || pwd_started_typing || cfrm_pwd_started_typing;
                for(JLabel label : error_labels)
                    erronneous = erronneous || label.isVisible();
                
                if(!erronneous)
                {
                    boolean user_exists = handle.check_for_user(mob.getText().trim());
                    if(!user_exists)
                    {
                        handle.put_user(first.getText().trim() + " " + last.getText().trim(), dob.get_date(), pwd.getText().trim(), mob.getText().trim());
                        
                        //dispose windows and frames and stop media
                        for(JLabel label : error_labels)
                            l_win.get(label).dispose();
                        
                        frame.dispose();
                        media.close();
                        calling_frame.get_media_player().start();
                    }
                    else
                    {
                        l_win.get(error_labels[2]).label.setText("   Credentials already taken");
                        error_labels[2].setVisible(true);
                    }
                }
        
                else
                {
                    boolean[] errors = {started_typing, last_started_typing, mob_started_typing, pwd_started_typing, cfrm_pwd_started_typing};
                    for(int i = 0; i < errors.length; i++)
                        if(errors[i])
                        {
                            error_labels[i].setVisible(true);
                            break;
                        }
                }
                
            }
            
        });
        content_pane.add(sign_up);
    }
    
    @Override
    public void paint(Graphics gr)
    {
        super.paint(gr);
        Graphics2D g = (Graphics2D)gr;
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString("Date of birth", 22, 330);
        g.drawString("By signing up, you agree to our Terms, Data Policy and Cookie Policy", 18, 68);
        g.drawString("Copyright ©™ 2021 @Travlex. All rights reserved.", 18, 450);
    }
    
    public void add_panel(JFrame frame)
    {
        layered_pane = new JPanel();
        layered_pane.setBounds(0, 0, WIDTH, HEIGHT);
        layered_pane.setOpaque(false);
        layered_pane.setBackground(Color.black);
        layered_pane.setLayout(null);
        frame.add(layered_pane);
        
        content_pane = new JPanel();
        content_pane.setBounds(0, 0, WIDTH, HEIGHT);
        content_pane.setOpaque(true);
        content_pane.setBackground(Color.black);
        content_pane.setLayout(null);
        frame.add(content_pane);
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
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /*
    public static void main(String args[])
    {
        new Sign_up();
    }
    */
}