package hotel_management;

import java.util.HashMap;

public class DisplayManager 
{
    private MainManager main_manager;
    private LogIn login_handle;
    
    private SignUp win;
    private Main main;
    
    private LoadingWindow load_win;
    private Template ticket;
    private ForgotPassword f_;
    
    public DisplayManager(MainManager main_manager)
    {
        this.main_manager = main_manager;
    }
    
    public LogIn get_login_handle()
    {
        return this.login_handle;
    }
    
    public ForgotPassword get_forgot_pwd_handle()
    {
        return this.f_;
    }
    
    public SignUp get_signup_handle()
    {
        return this.win;
    }
    
    public Main get_main_handle()
    {
        return this.main;
    }
    
    public LoadingWindow get_loading_window()
    {
        return this.load_win;
    }
    
    public Template get_template()
    {
        return this.ticket;
    }
    
    public void call_main()
    {
        login_handle = new LogIn("Travlex", this);
    }
    
    public boolean check_for_user(String ref)
    {
        return main_manager.get_u_manager().user_exists(ref);
    }
    
    public void set_login_var(LogIn handle_, String ref)
    {
        if(this.main_manager.get_u_manager().get_user_map().containsKey(ref))
        {
            handle_.set_uid(ref);
            handle_.set_pwd(this.main_manager.get_u_manager().get_user(ref).get_pwd());
            handle_.set_email_id(this.main_manager.get_u_manager().get_user(ref).get_mob_email());
            handle_.set_name_(this.main_manager.get_u_manager().get_user(ref).get_name());
        }
        else
        {
            this.main_manager.get_u_manager().get_user_map().entrySet().stream().filter((user) -> (user.getValue().get_mob_email().equals(ref))).map((user) -> {
                handle_.set_uid(user.getKey());
                return user;
            }).map((user) -> {
                handle_.set_pwd(user.getValue().get_pwd());
                return user;
            }).forEachOrdered((user) -> {
                handle_.set_email_id(ref);
                handle_.set_name_(user.getValue().get_name());
            });
        }
    }
    
    public void call_forgot_password(DisplayManager d_manager)
    {
        Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    d_manager.login_handle.get_media_player().stop();
                    f_ = new ForgotPassword(d_manager);
                    f_.setVisible(true);
                }

            });
        thread.start();
    }
    
    public void change_password(String uid, String ref)
    {
        this.main_manager.get_u_manager().modify_user(uid, 3, ref);
    }
    
    public void put_user(String name, String dob, String pwd, String mob_email)
    {
        this.main_manager.get_u_manager().add_user(main_manager.get_u_manager().get_user(name, dob, pwd, mob_email));
    }
    
    public void call_sign_up(DisplayManager d_manager)
    {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                
                login_handle.get_media_player().stop();
                win = new SignUp(d_manager);
                win.setVisible(true);
                
            }

        });
        thread.start();
    }
    
    public void display_booking_page(DisplayManager d_manager)
    {
        Thread start_main = new Thread(new Runnable(){
            @Override
            public void run()
            {
                login_handle.get_media_player().stop();
                main = new Main(d_manager); 
            }
        });
        start_main.start();
    }
    
    public HashMap<String, Double> convert_list_to_map()
    {
        HashMap<String, Double> hotels = new HashMap<>();
        this.main_manager.get_h_manager().get_hotels().forEach((hotel) -> {
            hotels.put(hotel.get_hotel_name(), hotel.get_price());
        });
        return hotels;
    }
    
    public void display_loading_window(String username, String hotel_name, String arrival, String departure, String captcha, int rooms, double total_price)
    {
        this.main_manager.get_b_manager().add_booking(username, hotel_name, arrival, departure, captcha, rooms, total_price);
        load_win = new LoadingWindow(this);
    }
    
    public void create_template()
    {
        String current_user_name = this.main.get_name();
        ticket = new Template(this.main_manager.get_b_manager().get_current_booking(), current_user_name, this);
    }
    
    public void show_template()
    {
        this.ticket.setVisible(true);
    }
    
    public void write_data()
    {
        this.main_manager.write_data();
    }
}
