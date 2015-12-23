package edu.iut.gui.widget.persons;

import edu.iut.app.Agenda;
import edu.iut.app.Person;
import edu.iut.gui.actions.NewPersonAction;
import edu.iut.gui.widget.generic.IFormFilledListener;

import javax.swing.*;
import java.awt.*;

public class PersonsWidget extends JPanel implements IFormFilledListener<Person>{

    private Agenda agenda;
    private Person.PersonFunction function;
    private JScrollPane pane;
    private JList<Person> list;
    private JButton button;

    public PersonsWidget(Agenda agenda, Window owner, Person.PersonFunction function){
        this.agenda = agenda;
        this.function = function;

        list = new JList<>();
        button = new JButton(new NewPersonAction(agenda, owner, function, this));
        pane = new JScrollPane(list);
        pane.setPreferredSize(new Dimension(pane.getPreferredSize().width, 300));
        updateData();


        setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        setSize(400, 150);
    }

    public void updateData(){
        Person[] persons = agenda.getPersons().stream().filter(p -> p.getFunction() == function).toArray(Person[]::new);
        list.setListData(persons);
    }

    @Override
    public void onFormFilled(Person person) {
        agenda.getPersons().add(person);
        updateData();

    }
}