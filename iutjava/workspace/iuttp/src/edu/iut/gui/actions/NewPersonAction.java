package edu.iut.gui.actions;

import edu.iut.app.Agenda;
import edu.iut.app.Person;
import edu.iut.gui.widget.generic.IFormFilledListener;
import edu.iut.gui.frames.PersonDialog;
import edu.iut.utils.I18N;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewPersonAction extends AbstractAction {

    private Agenda agenda;
    private Window owner;
    private Person.PersonFunction function;
    private IFormFilledListener<Person> formFilledListener;
    private PersonDialog personDialog;

    public NewPersonAction(Agenda agenda, Window owner, Person.PersonFunction function, IFormFilledListener<Person> formFilledListener){
        super(I18N.get("new"));
        this.agenda = agenda;
        this.owner = owner;
        this.function = function;
        this.formFilledListener = formFilledListener;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        personDialog = new PersonDialog(function, owner);
        personDialog.setFormListener(formFilledListener);
        personDialog.setVisible(true);
    }
}
