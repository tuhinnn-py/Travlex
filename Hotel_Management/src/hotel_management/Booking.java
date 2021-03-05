package hotel_management;

import java.io.Serializable;

public class Booking implements Serializable
{
    private String hotel_name, arrival, departure, captcha;
    private int rooms;
    private double total_price;
    
    public Booking(String hotel_name, String arrival, String departure, String captcha, int rooms, double total_price)
    {
        this.hotel_name = hotel_name;
        this.arrival = arrival;
        this.departure = departure;
        this.captcha = captcha;
        this.rooms = rooms;
        this.total_price = total_price;
    }
    
    public String get_hotel_name()
    {
        return this.hotel_name;
    }
    
    public String get_arrival()
    {
        return this.arrival;
    }
    
    public String get_departure()
    {
        return this.departure;
    }
    
    public int get_rooms()
    {
        return this.rooms;
    }
    
    public double get_total_price()
    {
        return this.total_price;
    }
    
    @Override
    public String toString()
    {
        return hotel_name + "-" + captcha;
    }
    
    public String get_captcha()
    {
        return this.captcha;
    }
    
}
