package edu.iut.gui.frames;

import edu.iut.app.Agenda;
import edu.iut.app.Person;
import edu.iut.utils.I18N;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JurySelectDialog extends JDialog implements ListSelectionListener{

    private List<Person> selected;
    private List<Person> jury;
    private JList<Person> personsList;
    private ExamEventDialog parentDialog;

    public JurySelectDialog(Agenda agenda, ExamEventDialog owner, ArrayList<Person> selected){
        super(owner, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.parentDialog = owner;
        this.jury = agenda.getPersons().stream().filter(p -> p.getFunction() == Person.PersonFunction.JURY).collect(Collectors.toList());
        this.selected = selected;
        this.personsList = new JList<>(jury.toArray(new Person[0]));

        int i = 0, j = 0;
        int[] indices = new int[selected.size()];
        for(Person person : jury){
            if(selected.contains(person)){
                indices[j] = i;
                j++;
            }
            i++;
        }

        personsList.setSelectedIndices(indices);
        personsList.addListSelectionListener(this);

        Dimension d = new Dimension(250, 400);

        personsList.setMinimumSize(d);
        personsList.setPreferredSize(d);

        setContentPane(personsList);
        setTitle(I18N.get("selectJury"));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        selected.clear();
        selected.addAll(personsList.getSelectedValuesList());
        parentDialog.notifyJuryChange();
    }
}
