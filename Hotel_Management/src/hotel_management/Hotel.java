package hotel_management;

import java.io.Serializable;

public class Hotel implements Serializable
{
    private String hotel_name;
    private double price;
    
    public Hotel(String hotel_name, double price)
    {
        this.hotel_name = hotel_name;
        this.price = price;
    }
    
    public void set_hotel_name(String new_hotel_name)
    {
        this.hotel_name = new_hotel_name;
    }
    
    public void set_price(double new_price)
    {
        this.price = new_price;
    }
    
    public String get_hotel_name()
    {
        return this.hotel_name;
    }
    
    public double get_price()
    {
        return this.price;
    }
    
    @Override
    public String toString()
    {
        return this.hotel_name + " | Rs. " + String.valueOf(this.price) + " per room per night";
    }
}
