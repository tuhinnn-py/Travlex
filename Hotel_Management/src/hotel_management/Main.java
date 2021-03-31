package hotel_management;

import static hotel_management.Error.ERROR_WIDTH;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Time;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements MouseListener
{
    private JPanel content_pane;
    private Error err_flag;
    
    private JTextField j_field;
    private JComboBox hotels;
    
    private String username, name;
    private Player media_player;
    
    private Component video;
    private Error proceed_flag;
    
    private String location;
    private DisplayManager handle;
    
    public Main(DisplayManager d_manager)
    {
        this.handle = d_manager;
        this.location = this.getFileLocation();
        
        String default_url = location + "Media.avi";
        String title = "Travlex";
        
        this.username = handle.get_login_handle().get_uid();
        this.name = handle.get_login_handle().get_username();
        
        set_flag(null);
        j_field = new JTextField();
        
        content_pane = new JPanel();
        content_pane.setOpaque(false);
        content_pane.setLayout(null);
        
        URL media_url = null;
        try
        {
            media_url = new URL("file:\\" + default_url);
        }
        catch(MalformedURLException e)
        {
            System.err.println("Media cannot be loaded!");
        }
        
        this.add_utilities(this, content_pane, title);
        this.add_components(this, content_pane, d_manager);
        
        this.add(content_pane);
        this.add_video(this, media_url);
        this.add_window_listener();
        this.setVisible(true);
        
        handle.get_login_handle().get_media_player().close();
        handle.get_login_handle().dispose();
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath();
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
    
    public String get_name()
    {
        return this.name;
    }
    
    public String get_username()
    {
        return this.username;
    }
    
    public void set_flag(Error flag)
    {
        this.err_flag = flag;
    }
    
    public void add_components(Main curr_frame, JPanel panel, DisplayManager handle)
    {
        HashMap<String, Double> rate_chart = handle.convert_list_to_map();
        
        JLabel book_hotel = new JLabel("Book hotels");
        book_hotel.setOpaque(false);
        book_hotel.setForeground(new Color(255, 255, 255));
        book_hotel.setFont(new Font("Lucida Fax", Font.BOLD, 50));
        book_hotel.setBounds(600, 150, 400, 200);
        panel.add(book_hotel);
        
        JLabel easier = new JLabel("with Travlex");
        easier.setOpaque(false);
        easier.setForeground(new Color(0, 255, 255));
        easier.setFont(new Font("Lucida Fax", Font.BOLD, 25));
        easier.setBounds(600, 200, 400, 200);
        panel.add(easier);
        
        String[] hotel_list = Arrays.asList(rate_chart.keySet().toArray()).toArray(new String[rate_chart.size()]);
        hotels = new JComboBox(hotel_list);
        hotels.setRenderer(new PromptComboBoxRenderer("                                           ----  Select a hotel  ----"));
        hotels.setSelectedIndex(-1);
        hotels.setBackground(new Color(255, 255, 255));
        if(hotel_list.length > 0)
            hotels.setForeground(new Color(0, 0, 0));
        else
            hotels.setForeground(new Color(200, 75, 73));
        hotels.setFont(new Font("Lucida Fax", Font.BOLD, 14));
        hotels.setFocusable(false);
        hotels.setBounds(600, 350, 640, 25);
        hotels.addMouseListener(this);
        hotels.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                err_flag.setVisible(false);
            }
            
        });
        panel.add(hotels);
        
        DateTime arr_ = new DateTime();
        DateTime dept_ = new DateTime();
        
        String[] years = new String[]{"2021", "2022", "2023", "2024"};
        arr_.add_date(panel, years, new Point(600, 420), new Point(820, 420), new Point(1040, 420), new Dimension(200, 25), new Dimension(200, 25), new Dimension(200, 25), "Check-in");
        dept_.add_date(panel, years, new Point(600, 490), new Point(820, 490), new Point(1040, 490), new Dimension(200, 25), new Dimension(200, 25), new Dimension(200, 25), "Check-out");

        j_field.setBounds(600, 560, 200, 25);
        j_field.setBorder(null);
        j_field.setBackground(new Color(255, 255, 255));
        j_field.setFont(new Font("Lucida Fax", Font.BOLD, 12));
        j_field.setText(" ");
        j_field.setCaretColor(Color.white);
        j_field.addKeyListener(new KeyListener(){
            @Override
            public void keyReleased(KeyEvent e) {
                if(err_flag != null && !is_erroneous_room(j_field.getText().trim()))
                {
                    err_flag.setVisible(false);
                    set_flag(new Error(content_pane, "Rooms", new Point(600 - (ERROR_WIDTH + 20), 560), true, "room"));
                    err_flag.setVisible(true);
                }
                else
                {
                    if(err_flag != null)
                    {
                        err_flag.setVisible(false);
                        set_flag(new Error(content_pane, "Invalid number of rooms", new Point(600 - (ERROR_WIDTH + 20), 560), false, "room"));
                        err_flag.setVisible(true);
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //
            }
            
        });
        j_field.addMouseListener(this);
        panel.add(j_field);
        
        JButton back = new JButton("Cancel");
        back.setBackground(new Color(0, 0, 0, 140));
        back.setForeground(Color.white);
        back.setBounds(600, 630, 180, 25);
        back.setBorder(null);
        back.setFocusable(false);
        back.setVisible(false);
        JButton submit = new JButton("Search");
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                back.setVisible(false);
                submit.setText("Search");
                proceed_flag.setVisible(false);
            }
        });
        content_pane.add(back);
        
        submit.setForeground(Color.white);
        submit.setBorder(null);
        submit.setBackground(new Color(0, 0, 0, 140));
        submit.setBounds(1060, 630, 180, 25);
        submit.setFont(new Font("Arial", Font.BOLD, 15));
        submit.setFocusable(false);

        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(submit.getText().equals("Search"))
                {
                    if(is_erroneous_pair(arr_.get_date(), dept_.get_date()))
                    {
                        dept_.err_flag.setVisible(false);
                        dept_.set_flag(new Error(panel, "Invalid check-out date", new Point(600 - (ERROR_WIDTH + 20), 490), false, ""));
                        dept_.err_flag.setVisible(true);
                        dept_.set_erroneous(true);
                    }
                    else
                    {
                        dept_.set_erroneous(false);
                        if(dept_.err_flag != null)
                            dept_.err_flag.setVisible(false);
                        dept_.set_flag(new Error(panel, "Check-out", new Point(600 - (ERROR_WIDTH + 20), 490), true, ""));
                    }

                    if(hotels.getSelectedItem() == null)
                    {
                        if(err_flag != null)
                            err_flag.setVisible(false);
                        set_flag(new Error(content_pane, "Pick a hotel", new Point(600 - (ERROR_WIDTH + 20), 350), false, "room"));
                        err_flag.setVisible(true);
                    }

                    else if(is_erroneous_room(j_field.getText().trim()))
                    {
                        err_flag.setVisible(false);
                        set_flag(new Error(content_pane, "Invalid number of rooms", new Point(600 - (ERROR_WIDTH + 20), 560), false, "room"));
                        err_flag.setVisible(true);
                    }

                    else if(!is_erroneous_pair(arr_.get_date(), dept_.get_date()) && !is_erroneous_room(j_field.getText().trim()))
                    {
                        int days = calc_days(arr_.get_date(), dept_.get_date());
                        double price = days * Integer.parseInt(j_field.getText().trim()) * rate_chart.get((String)hotels.getSelectedItem());

                        //create_booking((String)hotels.getSelectedItem(), arr_.get_date(), dept_.get_date(), generate_captcha(5), Integer.parseInt(j_field.getText().trim()), price);
                        proceed_flag = new Error(content_pane, "Price : " + price, new Point(1257 - (ERROR_WIDTH + 20), 557), false, "room");
                        err_flag.setVisible(false);
                        set_flag(proceed_flag);
                        err_flag.setVisible(true);

                        //set up a dummy flag
                        set_flag(new Error(content_pane, "Price : " + price, new Point(1257 - (ERROR_WIDTH + 20), 557), false, "room"));

                        submit.setIcon(null);
                        submit.setFont(back.getFont());
                        submit.setText("Proceed");
                        back.setVisible(true);
                    }
                }
                else
                {
                    int days = calc_days(arr_.get_date(), dept_.get_date());
                    double price = days * Integer.parseInt(j_field.getText().trim()) * rate_chart.get((String)hotels.getSelectedItem());
                    
                    curr_frame.media_player.stop();
                    curr_frame.media_player.close();
                    curr_frame.dispose();

                    handle.display_loading_window(username, (String)hotels.getSelectedItem(), arr_.get_date(), dept_.get_date(), generate_captcha(6), Integer.parseInt(j_field.getText().trim()), price);
                }
            }
            
        });
        panel.add(submit);
        
        JLabel travlex = new JLabel();
        try 
        {
            BufferedImage img = ImageIO.read(new File("Logo_new.png"));
            Image button_img = img.getScaledInstance(300, 158, Image.SCALE_SMOOTH);
            travlex.setIcon(new ImageIcon(button_img));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
        travlex.setOpaque(false);
        travlex.setBounds(100, 350, 300, 158);
        panel.add(travlex);
        
        JLabel footer = new JLabel("Copyright ©™ 2021 @Travlex. All rights reserved");
        footer.setOpaque(false);
        footer.setForeground(new Color(255, 255, 255));
        footer.setFont(new Font("Lucida Fax", Font.BOLD, 10));
        footer.setBounds(10, 645, 400, 200);
        panel.add(footer);
        
        //screen_capture();
    }
    
    public int calc_days(String arr, String dept)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String arr_ = (arr.length() == 9)? "0" + arr: arr;
        String dept_ = (dept.length() == 9)? "0" + dept: dept;
        
        int days = 0;
        
        try 
        {
            LocalDate date1 = LocalDate.parse(arr_, dtf);
            LocalDate date2 = LocalDate.parse(dept_, dtf);
            long daysBetween = ChronoUnit.DAYS.between(date1, date2);
            days = (int) daysBetween;
        } 
        catch (Exception e) 
        {
            System.err.println(e + "\nInvalid dates");
        }
        
        return (days == 0)? 1: days;
    }
    
    public static String generate_captcha(int digits)
    {
        Random rand = new Random();
        int range_ = (int)Math.pow(10, digits);
        int cap = (int)Math.pow(10, digits - 1) + rand.nextInt(range_ - (int)Math.pow(10, digits - 1));
        
        String res = "";
        while(cap != 0)
        {
            int d = cap % 10;
            res += (char)(d + 65);
            cap /= 10;
        }
        return res;
    }
    
    public boolean is_erroneous_room(String res)
    {
        try
        {
            int x = Integer.parseInt(res);
            if(x == 0)
                return true;
            return false;
        }
        catch(Exception e)
        {
            return true;
        }
    }
    
    public boolean is_erroneous_pair(String arr, String dept)
    {
        int arr_year = Integer.parseInt(arr.substring(arr.length() - 4));
        int dept_year = Integer.parseInt(dept.substring(dept.length() - 4));
        
        if(dept_year > arr_year)
            return false;
        
        if(dept_year < arr_year)
            return true;
        
        int arr_month = Integer.parseInt(arr.substring(arr.indexOf("-") + 1, arr.length() - 5));
        int dept_month = Integer.parseInt(dept.substring(dept.indexOf("-") + 1, dept.length() - 5));
        
        if(dept_month > arr_month)
            return false;
        
        if(dept_month < arr_month)
            return true;
        
        int arr_day = Integer.parseInt(arr.substring(0, arr.indexOf("-")));
        int dept_day = Integer.parseInt(dept.substring(0, dept.indexOf("-")));
        
        if(dept_day < arr_day)
            return true;
        return false;
    }
    
    public void add_video(JFrame frame, URL media_url)
    {
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            Player media = Manager.createRealizedPlayer(media_url);
            media_player = media;
            video = media.getVisualComponent();
            
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
    
    public void add_utilities(JFrame frame, JPanel panel, String title)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screenSize.getWidth() / 2);
        int h = (int) (screenSize.getHeight() / 2);
        
        frame.setLocation(new Point(w - 640, h - 400));
        frame.setSize(1280, 800);
        panel.setBounds(0, 0, 1280, 800);
        
        frame.setTitle(title);
        frame.setLayout(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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
        if(e.getSource() == j_field)
        {
            if(err_flag == null || err_flag.name.equals("hotel") || err_flag.name.equals(""))
                set_flag(new Error(content_pane, "Rooms", new Point(600 - (ERROR_WIDTH + 20), 560), true, "room"));
            else if(is_erroneous_room(j_field.getText().trim()))
            {
                err_flag.setVisible(false);
                set_flag(new Error(content_pane, "Invalid number of rooms", new Point(600 - (ERROR_WIDTH + 20), 560), false, "room"));
            }
            else
            {
                err_flag.setVisible(false);
                set_flag(new Error(content_pane, "Rooms", new Point(600 - (ERROR_WIDTH + 20), 560), true, "room"));
            }
        }
        else
        {
            if(err_flag == null)
                set_flag(new Error(content_pane, "Hotels", new Point(600 - (ERROR_WIDTH + 20), 350), true, "hotel"));
            else if(err_flag.name.equals("room") || err_flag.name.equals("") || hotels.getSelectedItem() != null)
            {
                err_flag.setVisible(false);
                set_flag(new Error(content_pane, "Hotels", new Point(600 - (ERROR_WIDTH + 20), 350), true, "hotel"));
            }
            else
            {
                err_flag.setVisible(false);
                set_flag(new Error(content_pane, "Pick a hotel", new Point(600 - (ERROR_WIDTH + 20), 350), false, "hotel"));
            }
        }
        err_flag.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(err_flag.message.equals("Pick a hotel") && hotels.getSelectedItem() == null)
            return;
        if(j_field.getText().trim().equals("") || !is_erroneous_room(j_field.getText().trim()))
            err_flag.setVisible(false);
    }
    
}
