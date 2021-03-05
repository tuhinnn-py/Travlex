package hotel_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;

public class UserManager 
{
    private HashMap<String, User> user_map;
    public UserManager()
    {
        this.user_map = read_user_map();
    }
    
    public HashMap<String, User> get_user_map()
    {
        return user_map;
    }
    
    private HashMap<String, User> read_user_map()
    {
        System.out.println("Reading users from persistence");
        
        File file = new File("Users.ser");
        FileInputStream f;
        HashMap<String, User> map;
        
        try 
        {
            f = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(f);
            
            map = (HashMap<String, User>)obj.readObject();
            obj.close();
        }
        
        catch(IOException | ClassNotFoundException err)
        {
            System.err.println(err + "\nNo user records exist");
            map = new HashMap<>();
        }
        
        return map;
    }
    
    public void write_user_map()
    {
        System.out.println("Writing users to persistence");
        
        File output_file = new File("Users.ser");
        FileOutputStream fo;
        
        try 
        {
            fo = new FileOutputStream(output_file);
            ObjectOutputStream obj_out = new ObjectOutputStream(fo);
            obj_out.writeObject(this.user_map);
        } 
        catch (IOException ex) 
        {
            System.err.println(ex + "\nError raised while writing file.");
        } 
    }
    
    public boolean user_exists(String ref)
    {                 
        return user_map.entrySet().stream().map((entry) -> (User)entry.getValue()).anyMatch((curr_user) -> (ref.equals(curr_user.get_mob_email().trim())));
    }
    
    public void add_user(User user)
    {
        user_map.put(user.get_username(), user);
    }
    
    public User get_user(String name, String dob, String pwd, String mob_email)
    {
        return new User(get_username(name), name, dob, pwd, mob_email);
    }
    
    public String get_username(String name)
    {
        Random random = new Random();
        String user_name = name.substring(0, 4) + "@" + random.nextInt(1000);
        
        while(user_map.containsKey(user_name))
            user_name = user_name + random.nextInt(10);

        return user_name;
    }
    
    //overridden method to get a user through the uid
    public User get_user(String username)
    {
        return user_map.get(username);
    }
    
    public boolean del_user(String uid)
    {
        if(user_map.containsKey(uid))
        {
            user_map.remove(uid);
            return true;
        }
        
        return false;
    }
    
    public boolean modify_user(String uid, int choice, String ref)
    {
        User curr_user = user_map.remove(uid);
        switch(choice)
        {
            case 0:
            {
                if(user_map.containsKey(ref))
                {
                    user_map.put(curr_user.get_username(), curr_user);
                    return false;
                }
                curr_user.set_username(ref);
                break;
            }
            
            case 1:
            {
                curr_user.set_name(ref);
                break;
            }
            
            case 2:
            {
                curr_user.set_mob_email(ref);
                break;
            }
            
            case 3:
            {
                curr_user.set_pwd(ref);
                break;
            }
            
            case 4:
            {
                curr_user.set_dob(ref);
                break;
            }
            
            default:
                return false;
        }
        
        user_map.put(curr_user.get_username(), curr_user);
        return true;
    }
    
    public void display_users()
    {
        user_map.entrySet().forEach((user) -> {
            System.out.println(user.getValue());
        });
    }
    
    public static void main(String args[])
    {
        UserManager u_manager = new UserManager();
        u_manager.display_users();
        //u_manager.write_user_map();
    }
}
