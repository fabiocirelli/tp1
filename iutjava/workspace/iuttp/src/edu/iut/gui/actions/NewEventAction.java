package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;
import edu.iut.app.Person;
import edu.iut.gui.frames.ExamEventDialog;
import edu.iut.gui.listeners.ApplicationWarningMessageDialog;
import edu.iut.utils.I18N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewEventAction extends AbstractAction {

    private Agenda agenda;
    private Window owner;
    private IDateProvider provider;

    public NewEventAction(Agenda agenda, Window owner, IDateProvider provider){

        super(I18N.get("newEvent"));
        this.agenda = agenda;
        this.provider = provider;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(agenda.getPersons().stream().filter(p -> p.getFunction() == Person.PersonFunction.STUDENT).count() > 0) {
            new ExamEventDialog(agenda, owner, provider.getDate()).setVisible(true);
        }else{
            new ApplicationWarningMessageDialog().newMessage(I18N.get("newEvent"), I18N.get("noStudentsError"));
        }

    }
}
