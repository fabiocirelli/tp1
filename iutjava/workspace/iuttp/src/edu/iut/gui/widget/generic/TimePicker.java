package edu.iut.gui.widget.generic;

import edu.iut.app.IDateProvider;
import edu.iut.utils.JComboBoxAutoComplete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimePicker extends JPanel implements ItemListener {

    private IDateProvider provider;
    private Calendar calendar;

    private JComboBoxAutoComplete comboBox;

    public TimePicker(){
        calendar = new GregorianCalendar();

        setLayout(new BorderLayout());

        ArrayList<Integer> hours = new ArrayList<>();
        for(int i = 0; i<= 23; i++){
            hours.add(i);
        }

        comboBox = new JComboBoxAutoComplete(hours);
        comboBox.addItemListener(this);

        add(comboBox, BorderLayout.CENTER);
        add(new JLabel("h"), BorderLayout.EAST);
    }

    public void setDateProvider(IDateProvider provider){

        calendar.setTime(provider.getDate());
        this.provider = provider;
        this.comboBox.setSelectedIndex(calendar.get(Calendar.HOUR_OF_DAY));;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        calendar.set(Calendar.HOUR_OF_DAY, comboBox.getSelectedIndex());
        update(Calendar.HOUR_OF_DAY, comboBox.getSelectedIndex());
    }

    private void update(int field, int value){

            calendar.set(field, value);

            if (provider != null)
                provider.setDate(calendar.getTime());
    }
}
