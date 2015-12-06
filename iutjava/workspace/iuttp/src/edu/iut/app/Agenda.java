package edu.iut.app;

import edu.iut.app.criteria.DateCriteria;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

public class Agenda extends LinkedList<ExamEvent> {

    private ArrayList<Person> persons;
    private ArrayList<ChangeListener> listeners;

	public Agenda() {
        this.persons = new ArrayList<>();
        this.listeners = new ArrayList<>();

        // Génération d'étudiants Fake
        persons.add(new Person(Person.PersonFunction.STUDENT, "Alexandre", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Arthur", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Aurélien", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Bernard", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Clément", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Camille", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Daniel", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Etienne", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Emma", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Florianne", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Fatou", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Guillaume", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Gregory", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Hector", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Igar", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Julie", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Killian", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Kévin", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Laura", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Mathilde", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Marie", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Noémie", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Ophélie", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Patricia", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Rémi", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Stéphane", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Sophie", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Thibault", "Dupont"));
        persons.add(new Person(Person.PersonFunction.STUDENT, "Timoté", "Dupont"));
	}

    /**
     * Ajoute un évènement à l'agenda
     * @param examEvent Evenement à ajouter
     */
	public void addCheckedEvent(ExamEvent examEvent) {

        this.add(examEvent);

        Collections.sort(this, (o1, o2) -> {
            if(o1 == null){
                if(o2 == null){
                    return 0;
                }else{
                    return 1;
                }
            }else if(o2 == null){
                return -1;
            }else{
                return o1.getExamDate().compareTo(o2.getExamDate());
            }
        });

        ChangeEvent event = new ChangeEvent(this);

        for(ChangeListener listener : listeners){
            listener.stateChanged(event);
        }
    }

    /**
     * Supprimer un évènement
     * @param o Evènement à supprimer
     * @return true si l'évènement était dans l'agenda
     */
    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);

        ChangeEvent event = new ChangeEvent(this);

        for(ChangeListener listener : listeners){
            listener.stateChanged(event);
        }

        return result;
    }

    /**
     * Récupère la liste des personnes enregistrés
     * @return liste de personnes
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Retourne tous les évènements dont la date est celle passé en paramètre. Les heures sont ignorés.
     * @param date la date
     * @return liste d'évènements filtrés
     */
    public List<ExamEvent> getByDay(Date date){
        DateCriteria criteria = new DateCriteria(date);
        List<ExamEvent> events = criteria.meetCriteria(this);
        events.sort((a, b) -> a.getExamDate().compareTo(b.getExamDate()));
        return events;
    }

    /**
     * Ajoute un listener qui sera appelé chaque fois que le contenu de l'agenda change
     * @param listener
     */
    public void addChangeListener(ChangeListener listener){
        this.listeners.add(listener);
    }

}
