package hotel_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingManager 
{
    private HashMap<String, List<Booking>> user_bookings;
    private Booking current_booking;
    
    public BookingManager()
    {
        this.user_bookings = read_user_bookings();
    }
    
    public HashMap<String, List<Booking>> get_user_bookings()
    {
        return user_bookings;
    }
    
    public HashMap<String, List<Booking>> read_user_bookings()
    {
        System.out.println("Reading user bookings from persistence");
        HashMap<String, List<Booking>> map;
        try
        {
            File booking = new File("user_bookings.ser");
            FileInputStream f = new FileInputStream(booking);
            ObjectInputStream obj = new ObjectInputStream(f);
            
            map = (HashMap<String, List<Booking>>)obj.readObject();
            obj.close();
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.err.println(e + "\nNo booking records exist yet");
            map = new HashMap<>();

        }
        
        return map;
    }
    
    public void write_user_bookings()
    {
        System.out.println("Writing user bookings to persistence");
        try
        {
            File booking = new File("user_bookings.ser");
            FileOutputStream f = new FileOutputStream(booking);
            ObjectOutputStream obj = new ObjectOutputStream(f);

            obj.writeObject(user_bookings);
            obj.close();
        }
        catch(IOException exception)
        {
            System.err.println(exception + "\nException occured while writing file.");
        }
    }
    
    public Booking get_current_booking()
    {
        return this.current_booking;
    }
    
    public void add_booking(String username, String hotel_name, String arrival, String departure, String captcha, int rooms, double total_price)
    {
        Booking curr_booking = new Booking(hotel_name, arrival, departure, captcha, rooms, total_price);
        this.current_booking = curr_booking;
        
        List<Booking> curr_user_bookings;
        
        if(user_bookings.containsKey(username))
        {
            curr_user_bookings = user_bookings.get(username);
            curr_user_bookings.add(curr_booking);
        }
        else
        {
            curr_user_bookings = new ArrayList<>();
            curr_user_bookings.add(curr_booking);
        }
        
        user_bookings.put(username, curr_user_bookings);
    }
    
    public boolean del_booking(String username, String captcha)
    {
        if(!user_bookings.containsKey(username))
            return false;
        
        List<Booking> bookings = user_bookings.get(username);
        for(Booking booking : bookings)
            if(booking.get_captcha().equals(captcha))
            {
                System.out.println("Deleting booking | " + booking);
                bookings.remove(booking);
                return true;
            }
        return false;
    }
    
    public void display_bookings()
    {
        user_bookings.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        });
    }
    
    public static void main(String args[])
    {
        BookingManager booking_manager = new BookingManager();
        booking_manager.display_bookings();
    }
}
