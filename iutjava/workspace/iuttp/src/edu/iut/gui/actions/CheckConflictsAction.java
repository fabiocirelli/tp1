package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.gui.frames.ConflictsDialog;
import edu.iut.gui.frames.SchedulerFrame;
import edu.iut.utils.I18N;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CheckConflictsAction extends AbstractAction {

    private SchedulerFrame parent;
    private Agenda agenda;

    public CheckConflictsAction(Agenda agenda, SchedulerFrame parent){
        super(I18N.get("checkConflicts"));
        this.agenda = agenda;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ConflictsDialog(agenda, parent);
    }
}
