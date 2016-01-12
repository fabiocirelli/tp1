package edu.iut.gui.frames;

import edu.iut.app.Agenda;
import edu.iut.app.Person;
import edu.iut.gui.widget.persons.PersonsWidget;
import edu.iut.utils.I18N;

import javax.swing.*;
import java.awt.*;

public class PersonsDialog extends JDialog {

    private Agenda agenda;
    private JTabbedPane tabbedPane;

    public PersonsDialog(Agenda agenda, Window owner){
        super(owner, ModalityType.APPLICATION_MODAL);
        this.agenda = agenda;
        this.tabbedPane = new JTabbedPane();

        tabbedPane.addTab(I18N.get("type_students"), new PersonsWidget(agenda, owner, Person.PersonFunction.STUDENT));
        tabbedPane.addTab(I18N.get("type_jurys"), new PersonsWidget(agenda, owner, Person.PersonFunction.JURY));

        setTitle(I18N.get("managePeople"));
        setContentPane(tabbedPane);
        pack();
        setLocationRelativeTo(null);

    }
}
