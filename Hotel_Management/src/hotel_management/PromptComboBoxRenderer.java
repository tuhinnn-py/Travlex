package hotel_management;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class PromptComboBoxRenderer extends BasicComboBoxRenderer
{
    private String prompt;

    /*
     *  Set the text to display when no item has been selected.
     */
    public PromptComboBoxRenderer(String prompt)
    {
        this.prompt = prompt;
    }

    /*
     *  Custom rendering to display the prompt text when no item is selected
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value == null)
        {
            //setForeground(new Color(0, 0, 0));
            setText( prompt );
        }
        
        if (list.getModel().getSize() == 0)
        {
            //setForeground(new Color(200, 75, 73));
            setText ( "                                            Hotel database not found ") ;
        }
        return this;
    }
}
