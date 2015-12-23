package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.gui.frames.PersonsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManagePersonsAction extends AbstractAction {

    private Agenda agenda;
    private PersonsDialog personWidget;

    public ManagePersonsAction(Agenda agenda){
        super("Gérer les personnes");
        this.agenda = agenda;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        personWidget = new PersonsDialog(agenda);
        personWidget.setVisible(true);
    }
}
