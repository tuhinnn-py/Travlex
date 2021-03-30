package hotel_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HotelManager 
{
    private List<Hotel> hotels;
    public HotelManager()
    {
        this.hotels = read_hotels();
    }
    
    public List<Hotel> read_hotels()
    {
        System.out.println("Reading hotels from persistence");
        List<Hotel> list;
        File file = new File("Hotels.ser");

        try
        {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(f);
            
            list = (List<Hotel>)obj.readObject();
            obj.close();
        }
        
        catch(IOException | ClassNotFoundException e)
        {
            System.err.println("No hotels records exist yet | " + e);
            list = new ArrayList<>();
        }
        
        return list;
    }
    
    public List<Hotel> get_hotels()
    {
        return hotels;
    }
    
    public void write_hotels()
    {
        System.out.println("Writing hotels to persistence");
        File file = new File("Hotels.ser");
        
        if(!hotels.isEmpty())
        {
            try
            {
                FileOutputStream f = new FileOutputStream(file);
                ObjectOutputStream obj = new ObjectOutputStream(f);

                obj.writeObject(hotels);
                obj.close();
            }

            catch(IOException e)
            {
                System.err.println("Error occured while writing data | " + e);
            }
        }
        else
            System.err.println("Error occured while writing data.");
    }
    
    public void add_hotel(String hotel_name, double price)
    {
        hotels.add(new Hotel(hotel_name, price));
    }
    
    public void display_hotels()
    {
        for(Hotel hotel : hotels)
            System.out.println(hotel);
    }
    
    public static void main(String args[])
    {
        HotelManager hotel_manager = new HotelManager();
        hotel_manager.display_hotels();
    }
}
