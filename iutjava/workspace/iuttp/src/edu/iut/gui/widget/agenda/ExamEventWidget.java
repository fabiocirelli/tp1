package edu.iut.gui.widget.agenda;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.ExamEvent;
import edu.iut.app.IDateProvider;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.generic.TimePicker;
import edu.iut.utils.JComboBoxAutoComplete;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class ExamEventWidget extends JFrame implements IDateProvider {

    private Agenda agenda;
    private ExamEvent event;
    private JPanel content;
    private boolean newEvent = false;

    private DatePicker dateField;
    private TimePicker timeField;
    private Date date;
    private JComboBoxAutoComplete studentCombo;

    public ExamEventWidget(Agenda agenda, Date selectedDate){
        this(agenda, new ExamEvent(selectedDate));
        newEvent = true;
    }

    public ExamEventWidget(Agenda agenda, ExamEvent event){

        this.agenda = agenda;
        this.event = event;
        this.date = event.getExamDate();

        GridBagConstraints gbc = new GridBagConstraints();

        dateField = new DatePicker();
        dateField.setDateProvider(this);
        timeField = new TimePicker();
        timeField.setDateProvider(this);
        studentCombo = new JComboBoxAutoComplete(agenda.getPersons());
        //studentField = new JTextFieldAutoComplete(agenda.getPersons(), studentCombo);

        content = new JPanel();
        content.setLayout(new GridBagLayout());

        gbc.gridwidth = 1;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(new JLabel("Date"), gbc);
        gbc.gridx = 1;
        content.add(dateField, gbc);

        gbc.gridy = 1;

        gbc.gridx = 0;
        content.add(new JLabel("Heure"), gbc);

        gbc.gridx = 1;
        content.add(timeField, gbc);

        gbc.gridy = 2;

        gbc.gridx = 0;
        content.add(new JLabel("Etudiant"), gbc);
        gbc.gridx = 1;
        content.add(studentCombo, gbc);


        content.setBorder(new EmptyBorder(5,5,5,5));

        JButton ok = new JButton(ApplicationSession.instance().getString("ok"));
        ok.addActionListener(e -> save());

        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
        add(ok, BorderLayout.SOUTH);

        setSize(400, 150);

    }

    public void beforeClose(){

    }

    public void save(){
        event.setExamDate(date);
        event.setStudent(agenda.getPersons().get(studentCombo.getSelectedIndex()));
        agenda.addCheckedEvent(event);

        setVisible(false);
    }


    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
