package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.gui.widget.agenda.ExamEventWidget;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewEventAction extends AbstractAction {

    private Agenda agenda;

    public NewEventAction(Agenda agenda){

        super(ApplicationSession.instance().getString("newEvent"));
        this.agenda = agenda;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ExamEventWidget(agenda).setVisible(true);

    }
}
