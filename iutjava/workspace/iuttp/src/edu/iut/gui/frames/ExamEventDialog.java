package edu.iut.gui.frames;

import edu.iut.app.*;
import edu.iut.gui.widget.generic.DatePicker;
import edu.iut.gui.widget.generic.Field;
import edu.iut.gui.widget.generic.Form;
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

    private Field<DatePicker> dateField;
    private Field<TimePicker> timeField;
    private Field<JComboBoxAutoComplete> studentField;
    private JComboBoxAutoComplete studentCombo;

    private Date date;
    private ArrayList<Person> students;

    public ExamEventDialog(Agenda agenda, Window owner, Date selectedDate){
        this(agenda, owner, new ExamEvent(selectedDate));
    }

    public ExamEventDialog(Agenda agenda, Window owner, ExamEvent event){

        super(owner, ModalityType.APPLICATION_MODAL);

        this.agenda = agenda;
        this.event = event;
        this.date = event.getExamDate();

        students = new ArrayList<>(agenda.getPersons());
        studentCombo = new JComboBoxAutoComplete(students);

        dateField = new Field<>(I18N.get("date"), new DatePicker(){{ setDateProvider(ExamEventDialog.this); }});
        timeField = new Field<>(I18N.get("time"), new TimePicker(ApplicationSession.DAY_START, ApplicationSession.DAY_END){{ setDateProvider(ExamEventDialog.this); }});
        studentField = new Field<>(I18N.get("type_student"), studentCombo);
        Form form = new Form();

        form.createFieldset("Date et heure");
        form.addField(0, dateField);
        form.addField(0, timeField);

        form.createFieldset("Personnes");
        form.addField(1, studentField);

        form.addButton(I18N.get("addItem"), e -> save());

        setContentPane(form);
        setTitle(I18N.get("newEvent"));
        setResizable(false);
        pack();



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
