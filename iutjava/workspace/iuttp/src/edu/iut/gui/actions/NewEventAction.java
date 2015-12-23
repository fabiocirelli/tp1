package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.app.ApplicationSession;
import edu.iut.app.IDateProvider;
import edu.iut.gui.frames.ExamEventDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewEventAction extends AbstractAction {

    private Agenda agenda;
    private Window owner;
    private IDateProvider provider;

    public NewEventAction(Agenda agenda, Window owner, IDateProvider provider){

        super(ApplicationSession.instance().getString("newEvent"));
        this.agenda = agenda;
        this.provider = provider;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ExamEventDialog(agenda, owner, provider.getDate()).setVisible(true);

    }
}
