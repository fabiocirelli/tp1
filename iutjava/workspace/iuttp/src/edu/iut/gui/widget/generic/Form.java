package edu.iut.gui.widget.generic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Form extends JPanel {
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
                d.width = 400;
                return d;
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createTitledBorder(name));

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
