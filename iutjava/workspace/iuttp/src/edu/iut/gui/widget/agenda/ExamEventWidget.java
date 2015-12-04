package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.ExamEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExamEventWidget extends JFrame {

    private Agenda agenda;
    private ExamEvent event;
    private JPanel content;
    private boolean newEvent = false;

    private JDateField dateField;
    private JTextField studentField;

    public ExamEventWidget(Agenda agenda){
        this(agenda, new ExamEvent());
        agenda.add(event);
        newEvent = true;
    }

    public ExamEventWidget(Agenda agenda, ExamEvent event){

        this.agenda = agenda;
        this.event = event;

        dateField = new JDateField();
        studentField = new JTextField();

        content = new JPanel();

        content.add(new JLabel("Date"));
        content.add(dateField);

        content.add(new JLabel("Etudiant"));
        content.add(studentField);

        content.setLayout(new GridLayout(2,2));
        content.setBorder(new EmptyBorder(5,5,5,5));

        JButton ok = new JButton(ApplicationSession.instance().getString("ok"));
        ok.addActionListener(e -> setVisible(false));

        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
        add(ok, BorderLayout.SOUTH);

        setSize(400, 150);

    }

    public void beforeClose(){
        if(newEvent){
            agenda.remove(event);
        }
    }

    public void save(){
        event.setExamDate(dateField.getDate());
        setVisible(false);
    }


}
