package edu.iut.gui.widget.agenda;

import edu.iut.app.*;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.generic.TimePicker;
import edu.iut.utils.I18N;
import edu.iut.utils.JComboBoxAutoComplete;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ExamEventWidget extends JFrame implements IDateProvider {

    private Agenda agenda;
    private ExamEvent event;
    private JPanel content;
    private boolean newEvent = false;

    private DatePicker dateField;
    private TimePicker timeField;
    private Date date;
    private ArrayList<Person> students;
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
        timeField = new TimePicker(ApplicationSession.DAY_START, ApplicationSession.DAY_END);
        timeField.setDateProvider(this);
        students = new ArrayList<>(agenda.getPersons());
        studentCombo = new JComboBoxAutoComplete(students);
        //studentField = new JTextFieldAutoComplete(agenda.getPersons(), studentCombo);

        content = new JPanel();
        content.setLayout(new GridBagLayout());

        gbc.gridwidth = 1;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(new JLabel(I18N.get("date")), gbc);
        gbc.gridx = 1;
        content.add(dateField, gbc);

        gbc.gridy = 1;

        gbc.gridx = 0;
        content.add(new JLabel(I18N.get("time")), gbc);

        gbc.gridx = 1;
        content.add(timeField, gbc);

        gbc.gridy = 2;

        gbc.gridx = 0;
        content.add(new JLabel(I18N.get("type_student")), gbc);
        gbc.gridx = 1;
        content.add(studentCombo, gbc);


        content.setBorder(new EmptyBorder(5,5,5,5));

        JButton ok = new JButton(I18N.get("ok"));
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
        event.setStudent(students.get(studentCombo.getSelectedIndex()));
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
