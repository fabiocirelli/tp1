package edu.iut.gui.frames;

import edu.iut.app.*;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.generic.Field;
import edu.iut.gui.widget.generic.TimePicker;
import edu.iut.utils.I18N;
import edu.iut.utils.JComboBoxAutoComplete;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ExamEventDialog extends JDialog implements IDateProvider {

    private Agenda agenda;
    private ExamEvent event;
    private boolean newEvent;

    private Field<DatePicker> dateField;
    private Field<TimePicker> timeField;
    private Field<JComboBoxAutoComplete> studentField;
    private Field<JButton> juryField;

    private ArrayList<Person> students;
    private JComboBoxAutoComplete studentCombo;

    private Date date;

    public ExamEventDialog(Agenda agenda, Window owner, Date selectedDate){
        this(agenda, owner, new ExamEvent(selectedDate), true);
    }

    public ExamEventDialog(Agenda agenda, Window owner, ExamEvent examEvent){
        this(agenda, owner, examEvent, false);
    }

    public ExamEventDialog(Agenda agenda, Window owner, ExamEvent event, boolean newEvent){

        super(owner, ModalityType.APPLICATION_MODAL);

        this.agenda = agenda;
        this.event = event;
        this.date = event.getExamDate();
        this.newEvent = newEvent;

        JButton juryBtn = new JButton(getJuryButtonText());
        juryBtn.addActionListener(e -> {
            new JurySelectDialog(agenda, this, event.getJury()).setVisible(true);
        });

        students = new ArrayList<>(agenda.getPersons());

        studentCombo = new JComboBoxAutoComplete(students);

        dateField = new Field<>(I18N.get("date"), new DatePicker(){{ setDateProvider(ExamEventDialog.this); }});
        timeField = new Field<>(I18N.get("time"), new TimePicker(ApplicationSession.DAY_START, ApplicationSession.DAY_END){{ setDateProvider(ExamEventDialog.this); }});
        studentField = new Field<>(I18N.get("type_student"), studentCombo);
        juryField = new Field<>(I18N.get("type_jury"), juryBtn);
        Field.Form form = new Field.Form();

        form.createFieldset("Date et heure");
        form.addField(0, dateField);
        form.addField(0, timeField);

        form.createFieldset("Personnes");
        form.addField(1, studentField);
        form.addField(1, juryField);

        form.addButton(I18N.get(newEvent ? "addItem" : "editItem"), e -> save());

        setContentPane(form);
        setTitle(I18N.get(newEvent ? "newEvent" : "editEvent"));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

    }

    public void save(){
        event.setExamDate(date);
        event.setStudent(students.get(studentCombo.getSelectedIndex()));
        if(newEvent) {
            agenda.addCheckedEvent(event);
        }else{
            agenda.notifyChange();
        }

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

    public String getJuryButtonText(){
        ArrayList<Person> jury = event.getJury();
        if(jury.isEmpty()){
            return I18N.get("nobody");
        }else if(jury.size() == 1){
            return jury.get(0).toString();
        }else if(jury.size() == 2){
            return jury.get(0).toString() + ' ' + I18N.get("and") + ' ' + jury.get(1).toString();
        }else{
            return jury.get(0).toString() + ' ' + I18N.get("and") + ' ' + (jury.size()-1) + ' ' + I18N.get("others");
        }
    }

    public void notifyJuryChange(){
        juryField.getComponent().setText(getJuryButtonText());
    }
}
