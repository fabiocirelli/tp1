package edu.iut.gui.widget.agenda;

import edu.iut.app.ApplicationSession;
import edu.iut.gui.listeners.ApplicationErrorMessageDialog;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDateField extends JTextField {

    private static final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private Date date;
    private boolean emptyState;

    public JDateField(){
        this(null);
    }

    public JDateField(Date date){
        setDate(date);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent event) {

            }

            @Override
            public void focusLost(FocusEvent event) {
                try {
                    setDate(formater.parse(getText()));

                } catch (ParseException e) {
                    new ApplicationErrorMessageDialog().newMessage("Erreur", ApplicationSession.instance().getString("parseDateError"));
                }
            }
        });

    }

    public void setDate(Date date){
        this.date = date;

        if(date == null){
            setText("jj/mm/aaaa");
            emptyState = true;
        }else{
            setText(formater.format(date));
            emptyState = false;
        }
    }

    public Date getDate(){
        return date;
    }




}
