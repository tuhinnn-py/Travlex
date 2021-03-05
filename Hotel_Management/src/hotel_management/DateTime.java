package hotel_management;

import static hotel_management.Error.ERROR_WIDTH;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class DateTime implements MouseListener
{
    private String current_date;
    public Error err_flag;
    
    private HashMap<String, String> month_map;
    private JPanel panel;
    
    private String message;
    private Point position;
    
    private boolean is_erroneous;
    public DateTime()
    {
        this.change_date("1-01-2021");
        set_flag(null);
        month_map = new HashMap<>();
        set_map();
        set_erroneous(false);
    }
    
    public void set_erroneous(boolean flag)
    {
        this.is_erroneous = flag;
    }
    
    public void set_variables(JPanel panel, String message, Point position)
    {
        this.panel = panel;
        this.message = message;
        this.position = position;
    }
    
    public void set_map()
    {
        month_map.put("January", "01");
        month_map.put("February", "02");
        month_map.put("March", "03");
        month_map.put("April", "04");
        month_map.put("May", "05");
        month_map.put("June", "06");
        month_map.put("July", "07");
        month_map.put("August", "08");
        month_map.put("September", "09");
        month_map.put("October", "10");
        month_map.put("November", "11");
        month_map.put("December", "12");
    }
    
    public void set_flag(Error flag)
    {
        this.err_flag = flag;
    }
    
    public void change_date(String s)
    {
        this.current_date = s;
    }

    public String get_date()
    {
        return this.current_date;
    }

    /* 	
    public static void add_utilities(JFrame frame)
    {
            frame.setLayout(new GridLayout(1, 3, 2, 2));
            frame.setTitle("Demo");
            frame.setSize(700, 60);
            frame.setResizable(false);
            frame.setLocation(new Point(250, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
    */

    public JComboBox get_combo_box(String[] entries, boolean enable)
    {
        JComboBox cb = new JComboBox(entries);
        cb.setEnabled(enable);

        cb.setSelectedIndex(0);	
        return cb;
    }

    public void add_components(JPanel panel, String[] years, Point date, Point month, Point year, Dimension date_d, Dimension month_d, Dimension year_d, String msg)
{
        this.change_date("1-01-" + years[0]);
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int[] d_ates = new int[31];
        for(int i = 0; i < 31; i++)
                d_ates[i] = i + 1;
        String[] dates = Arrays.stream(d_ates).mapToObj(String::valueOf).toArray(String[]::new);

        JComboBox dd = get_combo_box(dates, true);
        dd.setBounds(date.x, date.y, date_d.width, date_d.height);
        dd.setBackground(new Color(255, 255, 255));
        dd.setFocusable(false);
        dd.setFont(new Font("Lucida Fax", Font.BOLD, 12));
        dd.addMouseListener(this);

        JComboBox mm = get_combo_box(months, true);
        mm.setBounds(month.x, month.y, month_d.width, month_d.height);
        mm.setFocusable(false);
        mm.setBackground(new Color(255, 255, 255));
        mm.setFont(new Font("Lucida Fax", Font.BOLD, 12));
        mm.addMouseListener(this);

        JComboBox yy = get_combo_box(years, true);
        yy.setBounds(year.x, year.y, year_d.width, year_d.height);
        yy.setFocusable(false);
        yy.setBackground(new Color(255, 255, 255));
        yy.setFont(new Font("Lucida Fax", Font.BOLD, 12));
        yy.addMouseListener(this);

        dd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    if(err_flag != null)
                        err_flag.setVisible(false);
                    try
                    {
                        String date_ = (String) dd.getSelectedItem();
                        String month_ = (String) mm.getSelectedItem();
                        String year_ = (String) yy.getSelectedItem();

                        change_date(date_ + "-" + month_map.get(month_) + "-" + year_);
                    }
                    catch(Exception exception)
                    {
                        change_date(null);
                    }
                }
        });

        mm.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    if(err_flag != null)
                        err_flag.setVisible(false);
                    fill(months, dd, mm, yy);
                }
        });

        yy.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    if(err_flag != null)
                        err_flag.setVisible(false);
                    fill(months, dd, mm, yy);
                }
        });

        panel.add(dd);
        panel.add(mm);
        panel.add(yy);
    }

    public static boolean is_leap(int year)
    {
        if(year % 4 != 0)
                return false;
        else if(year % 100 != 0)
                return true;
        else if(year% 400 == 0)
                return true;
        else
                return false;
    }		

    public void fill(String[] months, JComboBox dd, JComboBox mm, JComboBox yy)
    {
        try
        {
            String month = (String) mm.getSelectedItem();
            int year = Integer.parseInt((String) yy.getSelectedItem());

            this.change_date((String) dd.getSelectedItem() + "-" + month_map.get(month) + "-" + year);

            if(month.equals("February"))
            {
                    if(is_leap(year))
                    {
                            if(dd.getItemCount() == 28)
                            {
                                    dd.insertItemAt("29", 28);
                            }
                            else if(dd.getItemCount() >= 30)
                            {
                                    while(dd.getItemCount() > 29)
                                            dd.removeItemAt(dd.getItemCount() - 1);
                            }
                    }
                    else
                    {
                            if(dd.getItemCount() > 28)
                            {
                                    while(dd.getItemCount() > 28)
                                            dd.removeItemAt(dd.getItemCount() - 1);
                            }
                    }
            }

            else
            {
                    int month_index = Arrays.asList(months).indexOf(month);
                    if(month_index % 2 == 0)
                    {
                            if(dd.getItemCount() < 31)
                            {
                                    while(dd.getItemCount() < 31)
                                            dd.insertItemAt(String.valueOf(dd.getItemCount() + 1), dd.getItemCount());
                            }
                    }
                    else
                    {
                            if(dd.getItemCount() < 30)
                            {
                                    while(dd.getItemCount() < 30)
                                            dd.insertItemAt(String.valueOf(dd.getItemCount() + 1), dd.getItemCount());
                            }
                            else
                            {
                                    dd.removeItemAt(dd.getItemCount() - 1);
                            }
                    }
            }
        }
        catch(Exception e)
        {
            this.change_date(null);
        }
    }

    public void add_date(JPanel panel, String[] years, Point dd, Point mm, Point yy, Dimension d, Dimension m, Dimension y, String msg)
    {
        //set_flag(new Error(panel, msg, new Point(dd.x - (ERROR_WIDTH + 20), dd.y), true));
        set_variables(panel, msg, dd);
        this.add_components(panel, years, dd, mm, yy, d, m, y, msg);
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
        if(!is_erroneous)
            set_flag(new Error(this.panel, this.message, new Point(this.position.x - (ERROR_WIDTH + 20), this.position.y), true, ""));
        if(!is_erroneous && err_flag != null)
            err_flag.setVisible(false);
        err_flag.setVisible(true);//
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!is_erroneous)
            err_flag.setVisible(false);//
    }

}