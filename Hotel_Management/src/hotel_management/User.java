package hotel_management;

import java.io.Serializable;

public class User implements Serializable
{
    private String username;
    private String name, dob, pwd, mob_email;
    
    public User(String username, String name, String dob, String pwd, String mob_email)
    {
        this.name = name;
        this.dob = dob;
        this.pwd = pwd;
        this.mob_email = mob_email;
        this.username = username;
    }
    
    @Override
    public String toString()
    {
        return this.username + ", " + this.dob + ", " + this.pwd + ", " + this.name + ", " + this.mob_email;
    }
    
    public void set_username(String new_username)
    {
        this.username = new_username;
    }
    
    public void set_name(String new_name)
    {
        this.name = new_name;
    }
    
    public void set_dob(String new_dob)
    {
        this.dob = new_dob;
    }
    
    public void set_pwd(String new_pwd)
    {
        this.pwd = new_pwd;
    }
    
    public void set_mob_email(String new_mob_email)
    {
        this.mob_email = new_mob_email;
    }
    
    public String get_username()
    {
        return this.username;
    }
    
    public String get_name()
    {
        return this.name;
    }
    
    public String get_dob()
    {
        return this.dob;
    }
    
    public String get_pwd()
    {
        return this.pwd;
    }
    
    public String get_mob_email()
    {
        return this.mob_email;
    }
}
