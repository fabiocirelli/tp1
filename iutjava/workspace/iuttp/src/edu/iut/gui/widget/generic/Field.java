package edu.iut.gui.widget.generic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public static class Form extends JPanel {
        private ArrayList<JPanel> fieldsets = new ArrayList<>();
        private JPanel actionPanel;

        public Form(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(10,10,0,10));

            actionPanel = new JPanel();
            actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(actionPanel);
        }

        public JPanel createFieldset(String name){
            JPanel panel = new JPanel(){
                @Override
                public Dimension getPreferredSize() {
                    Dimension d = super.getPreferredSize();
                    d.width = 460;
                    return d;
                }
            };
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(name), new EmptyBorder(0, 8, 5, 8)));

            panel.setAlignmentX(Component.LEFT_ALIGNMENT);

            add(panel, fieldsets.size());
            fieldsets.add(panel);

            return panel;
        }

        public void addField(int fieldset, Field<?> field){
            fieldsets.get(fieldset).add(field);
        }

        public void addButton(Action action, ActionListener listener){
            JButton btn = new JButton(action);
            btn.addActionListener(listener);
            actionPanel.add(btn);
        }

        public void addButton(String action, ActionListener listener){
            JButton btn = new JButton(action);
            btn.addActionListener(listener);
            actionPanel.add(btn);
        }
    }
}
