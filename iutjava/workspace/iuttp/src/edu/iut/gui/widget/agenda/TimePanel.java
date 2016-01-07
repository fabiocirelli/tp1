package edu.iut.gui.widget.agenda;
import edu.iut.app.Agenda;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public abstract class TimePanel extends JPanel {
	
	protected AgendaPanelFactory.ActiveView activeView;
    protected Agenda agenda;
    protected Window owner;
    protected Date date;

    /**
     * Vue d'un élément temporel
     * @param activeView
     * @param agenda
     * @param date
     */
    public TimePanel(AgendaPanelFactory.ActiveView activeView, Agenda agenda, Window owner, Date date) {
        this.activeView = activeView;
        this.date = date;
        this.agenda = agenda;
        this.owner = owner;
	}

    public TimePanel(){

    }

    /**
     * Définit la date associé à la vue
     * @param date
     */
    public void setDate(Date date){
        this.date = date;
        refresh();
        revalidate();
    }

    /**
     * Crée ou recrée la vue
     */
    public abstract void refresh();



}
