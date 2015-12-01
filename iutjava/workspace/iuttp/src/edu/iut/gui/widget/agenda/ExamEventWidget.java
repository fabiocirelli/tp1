package edu.iut.gui.widget.agenda;

import edu.iut.app.ExamEvent;

import javax.swing.*;
import java.awt.*;

public class ExamEventWidget extends JFrame {
    private ExamEvent event;

    private JPanel content;

    public ExamEventWidget(ExamEvent event){

        content = new JPanel();

        content.add(new JLabel("Date"));
        content.add(new JDateField(null));

        content.add(new JLabel("Etudiant"));
        content.add(new JTextField());

        add(content, BorderLayout.CENTER);
        add(new JButton("Ok"), BorderLayout.SOUTH);
        setLayout(new BorderLayout());
    }


}
