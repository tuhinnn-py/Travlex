package hotel_management;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Time;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class LoadingWindow extends JWindow
{
    private static final int LOADING_COUNTER = 2;
    private String location;
    
    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;
    
    private int counter;
    private DisplayManager handle;
    
    public LoadingWindow(DisplayManager handle)
    {
        this.handle = handle;
        this.location = getFileLocation();
        
        Thread handle_thread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                handle.create_template();
            }
        });
        handle_thread.start();
        
        this.counter = 0;
        this.setLayout(null);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screenSize.getWidth() / 2);
        int h = (int) (screenSize.getHeight() / 2);
        this.setLocation(new Point((int)(w - WIDTH/2), (int)(h - HEIGHT/2)));
        this.setSize(WIDTH, HEIGHT);
        
        JLabel footer = new JLabel("Copyright ©™ 2021 @Travlex. All rights reserved.");
        footer.setOpaque(false);
        footer.setForeground(new Color(255, 255, 255));
        footer.setFont(new Font("Lucida Fax", Font.BOLD, 8));
        footer.setBounds(10, 185, 300, 100);
        this.add(footer);
        
        JLabel load = new JLabel("Loading ");
        load.setOpaque(false);
        load.setForeground(new Color(255, 255, 255));
        load.setFont(new Font("Lucida Fax", Font.BOLD, 11));
        load.setBounds(320, 185, 100, 100);
        this.add(load);
        
        Thread loading_thread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    for(int i = 0; i < 8; i++)
                    {
                        Thread.sleep(1000);
                        String foot = "";
                        for(int j = 0; j <= (i + 1) % 3; j++)
                            foot += ".";
                        
                        load.setText("Loading " + foot);
                    }
                    load.setLocation(330, 185);
                    load.setText("Done");
                }
                catch(InterruptedException e)
                {
                    System.err.println(e);
                }
            }
        });
        loading_thread.start();
        
        add_media(this);
        this.setVisible(true);
    }
    
    public String getFileLocation()
    {
        //creating a dummy file to get the absolute path
        File file = new File("_");
        String path = file.getAbsolutePath().replace("\\", "\\\\");
        
        return path.substring(0, path.length() - 1);
    }
    
    public void add_media(JWindow window)
    {
        URL media_url = null;
        try
        {
            media_url = new URL("file:\\" + location + "Loading_Media.avi");
        }
        catch(MalformedURLException e)
        {
            System.err.println(e);
        }
        
        try
        {
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
            Player media_player = Manager.createRealizedPlayer(media_url);
            Component video = media_player.getVisualComponent();
            
            if(video != null)
            {
                Dimension frameSize = window.getSize();
                video.setBounds(0, 0, (int)frameSize.getWidth(), (int)frameSize.getHeight());
                window.add(video);
                media_player.addControllerListener((ControllerEvent event) -> {
                    if (event instanceof EndOfMediaEvent) {
                        counter += 1;
                        if(counter < LOADING_COUNTER)
                        {
                            media_player.setMediaTime(new Time(0));
                            media_player.start();
                        }
                        else
                        {
                            window.dispose();
                            media_player.stop();
                            media_player.close();
                            handle.show_template();
                        }
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
}
