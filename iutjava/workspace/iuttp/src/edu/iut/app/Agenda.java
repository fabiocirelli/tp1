package edu.iut.app;

import edu.iut.app.criteria.DateCriteria;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.util.*;

public class Agenda extends LinkedList<ExamEvent> {

    private TreeSet persons;
    private ArrayList<ChangeListener> listeners;
    private File file;

	public Agenda() {
        this.persons = new TreeSet<>();
        this.listeners = new ArrayList<>();
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

        notifyChange();
    }

    /**
     * Supprimer un évènement
     * @param o Evènement à supprimer
     * @return true si l'évènement était dans l'agenda
     */
    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);

        notifyChange();

        return result;
    }

    /**
     * Notifie un changement dans l'agenda et prévient tous les listeners enregistrés
     */
    public void notifyChange(){
        ChangeEvent event = new ChangeEvent(this);

        for(ChangeListener listener : listeners){
            listener.stateChanged(event);
        }
    }

    /**
     * Récupère la liste des personnes enregistrées
     * @return liste de personnes
     */
    public TreeSet<Person> getPersons() {
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

    /**
     * Retourne le dernier fichier dans lequel cet agenda a été enregistré
     * @return fichier
     */
    public File getFile() {
        return file;
    }

    /**
     * Définit le fichier de cet agenda
     * @return fichier
     */
    public void setFile(File file) {
        this.file = file;
    }

    public void reset(){
        clear();
        getPersons().clear();
        file = null;

        notifyChange();
    }
}
