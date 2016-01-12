package edu.iut.gui.frames;

import edu.iut.app.Agenda;
import edu.iut.app.ExamEvent;
import edu.iut.app.Person;
import edu.iut.gui.listeners.ApplicationInfoMessageDialog;
import edu.iut.utils.I18N;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConflictsDialog extends JDialog {

    private Agenda agenda;
    private Window owner;
    private JTextPane textPane;
    private StyledDocument doc;
    private Style errStyle;
    private boolean conflictsFound;

    public ConflictsDialog(Agenda agenda, Window owner){
        super(owner, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.agenda = agenda;
        this.owner = owner;
        this.textPane = new JTextPane();

        doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        errStyle = doc.addStyle("conflict", null);
        StyleConstants.setForeground(errStyle, Color.RED);

        findConflicts();

        if(!conflictsFound){
            dispose();
            new ApplicationInfoMessageDialog().newMessage(I18N.get("checkConflicts"), I18N.get("noConflicts"));
        }else {
            textPane.setMinimumSize(new Dimension(500, 300));
            textPane.setPreferredSize(new Dimension(500, 300));
            setContentPane(textPane);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public void findConflicts(){

        HashMap<Person, Integer> studentsHisto = new HashMap<>();
        for(ExamEvent event : agenda){
            if(studentsHisto.containsKey(event.getStudent())){
                studentsHisto.put(event.getStudent(), studentsHisto.get(event.getStudent())+1);
            }else{
                studentsHisto.put(event.getStudent(), 1);
            }
        }

        for(Map.Entry<Person, Integer> histoEntry : studentsHisto.entrySet()){
            if(histoEntry.getValue() > 1){
                addConflictMsg("La soutenance de l'étudiant " + histoEntry.getKey() + " a été programmée " + histoEntry.getValue() + " fois.");
            }
        }

        HashMap<JuryTimeslot, Integer> timeslotsHisto = new HashMap<>();
        for(ExamEvent event : agenda){
            for(Person jury : event.getJury()){
                JuryTimeslot jtl = new JuryTimeslot(jury, event.getExamDate());
                if(timeslotsHisto.containsKey(jtl)){
                    timeslotsHisto.put(jtl, timeslotsHisto.get(jtl)+1);
                }else{
                    timeslotsHisto.put(jtl, 1);
                }
            }
        }

        SimpleDateFormat format = new SimpleDateFormat("'le' dd/MM 'à' HH'h'");

        for(Map.Entry<JuryTimeslot, Integer> histoEntry : timeslotsHisto.entrySet()){
            if(histoEntry.getValue() > 1){
                addConflictMsg(histoEntry.getKey().person + " fait passer " + histoEntry.getValue() + " soutenances en même temps " + format.format(histoEntry.getKey().date));
            }
        }

    }

    public void addConflictMsg(String text){
        try {
            doc.insertString(doc.getLength(), text + "\n", errStyle);
            conflictsFound = true;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private class JuryTimeslot{
        public Person person;
        public Date date;

        public JuryTimeslot(Person person, Date date) {
            this.person = person;
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JuryTimeslot that = (JuryTimeslot) o;

            if (!person.equals(that.person)) return false;
            return date.equals(that.date);

        }

        @Override
        public int hashCode() {
            int result = person.hashCode();
            result = 31 * result + date.hashCode();
            return result;
        }
    }
}
