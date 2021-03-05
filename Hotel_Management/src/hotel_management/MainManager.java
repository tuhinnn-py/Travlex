package hotel_management;

import javax.swing.SwingUtilities;

public class MainManager 
{
    private DisplayManager d_manager;
    private BookingManager b_manager;
    
    private HotelManager h_manager;
    private UserManager u_manager;
    
    public MainManager()
    {
        this.u_manager = new UserManager();
        this.h_manager = new HotelManager();
        this.b_manager = new BookingManager();
    }
    
    public BookingManager get_b_manager()
    {
        return this.b_manager;
    }
    
    public UserManager get_u_manager()
    {
        return this.u_manager;
    }
    
    public HotelManager get_h_manager()
    {
        return this.h_manager;
    }
    
    public void write_data()
    {
        this.b_manager.write_user_bookings();
        this.h_manager.write_hotels();
        this.u_manager.write_user_map();
    }
    
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(() -> {
            MainManager main_manager = new MainManager();
            main_manager.d_manager = new DisplayManager(main_manager);

            //power up the UI through the Display Manager's UI API
            main_manager.d_manager.call_main();
        });
    }
}
