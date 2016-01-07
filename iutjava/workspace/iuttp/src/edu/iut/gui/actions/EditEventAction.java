package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.app.ExamEvent;
import edu.iut.gui.frames.ExamEventDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditEventAction extends AbstractAction {

    private Agenda agenda;
    private Window owner;
    private ExamEvent event;

    public EditEventAction(Agenda agenda, Window owner, ExamEvent event) {
        this.agenda = agenda;
        this.owner = owner;
        this.event = event;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ExamEventDialog(agenda, owner, event).setVisible(true);
    }
}
