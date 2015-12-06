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
    private int min;
    private int max;

    private JComboBoxAutoComplete comboBox;

    /**
     * TimePicker permettant de choisir une heure
     * @param min heure minimale
     * @param max heure maximale
     */
    public TimePicker(int min, int max){
        calendar = new GregorianCalendar();
        this.min = min;
        this.max = max;

        setLayout(new BorderLayout());

        ArrayList<Integer> hours = new ArrayList<>();
        for(int i = min; i<= max; i++){
            hours.add(i);
        }

        comboBox = new JComboBoxAutoComplete(hours);
        comboBox.addItemListener(this);

        add(comboBox, BorderLayout.CENTER);
        add(new JLabel("h"), BorderLayout.EAST);
    }

    /**
     * Définit le fournisseur de date que ce TimePicker contrôle
     * @param provider
     */
    public void setDateProvider(IDateProvider provider){

        calendar.setTime(provider.getDate());
        this.provider = provider;
        update(Calendar.HOUR_OF_DAY, Math.max(min, Math.min(max, calendar.get(Calendar.HOUR_OF_DAY))));
        this.comboBox.setSelectedItem(calendar.get(Calendar.HOUR_OF_DAY));;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        update(Calendar.HOUR_OF_DAY, (int) comboBox.getSelectedItem());
    }

    /**
     * Met à jour la date
     * @param field
     * @param value
     */
    private void update(int field, int value){
            calendar.setTime(provider.getDate());
            calendar.set(field, value);

            if (provider != null)
                provider.setDate(calendar.getTime());
    }
}
