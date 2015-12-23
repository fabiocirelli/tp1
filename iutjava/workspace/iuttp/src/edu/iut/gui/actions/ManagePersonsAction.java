package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.gui.frames.PersonsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ManagePersonsAction extends AbstractAction {

    private Agenda agenda;
    private Window owner;
    private PersonsDialog personWidget;

    public ManagePersonsAction(Agenda agenda, Window owner){
        super("Gérer les personnes");
        this.agenda = agenda;
        this.owner = owner;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        personWidget = new PersonsDialog(agenda, owner);
        personWidget.setVisible(true);
    }
}
