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

    private static final SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final String FORMAT_HINT = "jj/mm/aaaa hh:mm";

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
                if(emptyState){
                    setText("");
                    emptyState = false;
                }
            }

            @Override
            public void focusLost(FocusEvent event) {
                if(!emptyState && getText().length() > 0) {
                    try {
                        setDate(formater.parse(getText()));

                    } catch (ParseException e) {
                        new ApplicationErrorMessageDialog().newMessage("Erreur", ApplicationSession.instance().getString("parseDateError"));
                        setDate(null);
                    }
                }else{
                    setDate(null);
                }
            }
        });

    }

    public void setDate(Date date){
        this.date = date;

        if(date == null){
            setText(FORMAT_HINT);
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
