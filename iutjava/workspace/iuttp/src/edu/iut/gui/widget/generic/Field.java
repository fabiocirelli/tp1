package edu.iut.gui.widget.generic;

import javax.swing.*;
import java.awt.*;

public class Field<T extends JComponent> extends JPanel {

    private String label;
    private JLabel jlabel;
    private T component;

    public Field(String label, T component){
        this.label = label;
        this.jlabel = new JLabel(label);
        this.component = component;
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) component.getPreferredSize().getHeight()));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));



        add(jlabel);
        add(component);

        //jlabel.setMinimumSize(new Dimension(300,20));
        jlabel.setPreferredSize(new Dimension(100,20));
        //jlabel.setMaximumSize(new Dimension(100,20));*/

        //setMinimumSize(new Dimension(300,20));
        //setPreferredSize(new Dimension(300,20));
        //setMaximumSize(new Dimension(300,20));


    }

    public T getComponent(){
        return component;
    }
}
